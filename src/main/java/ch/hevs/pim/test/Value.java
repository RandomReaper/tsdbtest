package ch.hevs.pim.test;

public class Value {
	long id;
	String name;
	long ts;
	double value;
	
	Value(long _id, long _ts, double _value) {
		id = _id;
		name = "mes-"+id;
		ts = _ts;
		value = _value;
	}
}
