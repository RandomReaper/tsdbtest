package ch.hevs.pim.test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;

class TSDBInflux implements ITSDB {

	InfluxDB db;
	String db_name;

	TSDBInflux(InfluxDB _db, String _db_name) {
		db = _db;
		db_name = _db_name;
	}

	@Override
	public void push(List<Value> data, long batch_size) {
		BatchPoints batch = null;

		long count = 0;
		for (Value d : data) {
			if (count == 0) {
				batch = BatchPoints.database("test").build();
			}
			batch.point(Point.measurement(d.name).time(d.ts, TimeUnit.SECONDS).addField("value", d.value).build());
			count++;
			
			if (count == batch_size) {
				db.write(batch);
				count = 0;
			}
		}
		
		if (count != 0)
			db.write(batch);
	}

	@Override
	public long count() {
		long count = 0;
		QueryResult a = db.query(new Query("SELECT COUNT(*) FROM /.*/"));
		for (Result r : a.getResults()) {
			for (Series s : r.getSeries()) {
				count += Double.parseDouble(""+s.getValues().get(0).get(1));
			}
		}

		return count;
	}

}
