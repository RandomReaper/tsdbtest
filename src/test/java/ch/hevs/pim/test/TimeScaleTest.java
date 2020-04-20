package ch.hevs.pim.test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;


public class TimeScaleTest {    

	public static long folderSize(File directory) {
	    long length = 0;
	    for (File file : directory.listFiles()) {
	        if (file.isFile())
	            length += file.length();
	        else
	            length += folderSize(file);
	    }
	    return length;
	}
	
	@Rule
    public PostgreSQLContainer dbc = new PostgreSQLContainer("timescale/timescaledb:latest-pg11").withDatabaseName("test").withUsername("pg").withPassword("pg");

    @Test
    public void test() throws SQLException {
        long before;
        dbc.start();
        String url = dbc.getJdbcUrl();
        Connection pg = DriverManager.getConnection(url,"pg","pg");

        ITSDB db = new TSDBTimeScale(pg, "test");
        
        long nr_series = 100;
        long nr_values = 100000;
        long batch_size = 100;

        System.out.println("Testing timescale");
        before = System.currentTimeMillis();
        TestTSDB.test(db, nr_series, nr_values, batch_size);
        System.out.println(String.format("done in %d ms", System.currentTimeMillis()-before));

        dbc.close();
    }
}