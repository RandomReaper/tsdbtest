package ch.hevs.pim.test;

import org.influxdb.InfluxDB;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.InfluxDBContainer;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class InfluxTest {

    @ClassRule
    public static InfluxDBContainer influxDBContainer = new InfluxDBContainer().withAuthEnabled(false).withDatabase("test");

    @Test
    public void test() {
        long before;
        InfluxDB influx = influxDBContainer.getNewInfluxDB();

        assertThat(influx, notNullValue());
        assertThat(influx.ping(), notNullValue());
        
        ITSDB db = new TSDBInflux(influx, "test");
        
        long nr_series = 100;
        long nr_values = 100000;
        long batch_size = 100;

        System.out.println("Testing influxdb");
        before = System.currentTimeMillis();
        TestTSDB.test(db, nr_series, nr_values, batch_size);
        System.out.println(String.format("done in %d ms", System.currentTimeMillis()-before));

        influx.close();
    }
}