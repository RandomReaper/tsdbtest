package ch.hevs.pim.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

class TSDBPostgreSQL implements ITSDB {

	Connection db;
	String db_name;

	TSDBPostgreSQL(Connection _db, String _db_name) {
		db = _db;
		db_name = _db_name;
		
		String qry = "CREATE TABLE " + db_name + " (\n" + "  time        TIMESTAMPTZ       NOT NULL,\n"
				+ "  id          BIGINT       NOT NULL,\n" + "  value       DOUBLE PRECISION  NOT NULL\n" + ");";

		try {
			performQuery(qry);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void push(List<Value> data, long batch_size) {
		try {

			String batch = null;

			long count = 0;
			for (Value d : data) {
				if (count == 0) {
					batch = "INSERT into " + db_name + " (id, time, value) VALUES";
				}
				batch += String.format("(%d,'%s',%f),", d.id, new Timestamp(d.ts), d.value);
				count++;

				if (count == batch_size) {
					batch = batch.substring(0, batch.length() - 1)+";";
					performQuery(batch);
					count = 0;
				}
			}

			if (count != 0) {
				batch = batch.substring(0, batch.length() - 1)+";";
				performQuery(batch);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public long count() {
		long count = 0;
		try {
			count = performQuery("SELECT COUNT(*) as count FROM "+db_name+";").getLong("count");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	ResultSet performQuery(String sql) throws SQLException {
		Statement statement = db.createStatement();
		statement.execute(sql);
		ResultSet resultSet = statement.getResultSet();

		if (resultSet != null) {
			resultSet.next();
		}
		return resultSet;
	}
}
