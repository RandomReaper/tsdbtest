package ch.hevs.pim.test;

import org.influxdb.InfluxDB;
import org.junit.ClassRule;
import org.testcontainers.containers.InfluxDBContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaHelper {

    @ClassRule
    public static InfluxDBContainer influxDbContainer = new InfluxDBContainer().withAuthEnabled(false);
    
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(JavaHelper.class);
    
    public static InfluxDB getNewInfluxDB() {
    	influxDbContainer.followOutput(new Slf4jLogConsumer(logger));

    	return influxDbContainer.getNewInfluxDB();
    }
}
