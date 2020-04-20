package ch.hevs.pim.test;

import java.sql.Connection;
import java.sql.SQLException;

class TSDBTimeScale extends TSDBPostgreSQL {

	TSDBTimeScale(Connection _db, String _db_name) {
		super(_db, _db_name);
		
		String qry = "SELECT create_hypertable('"+db_name+"', 'time');\n";
		
		try {
			performQuery(qry);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
