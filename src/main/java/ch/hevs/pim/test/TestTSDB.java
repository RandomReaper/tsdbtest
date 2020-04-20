package ch.hevs.pim.test;

import java.util.List;

public class TestTSDB {
	public static TestResult test(ITSDB db, long nr_series, long nr_values, long batch_size) {
		TestResult t = new TestResult();
		long before;
		System.out.println(String.format("Gen %d values in %d series (total:%d) batch_size:%d", nr_values, nr_series,
				nr_values * nr_series, batch_size));
		List<Value> data = Generator.gen(nr_series, nr_values);
		System.out.print("push data   ...");
		before = System.currentTimeMillis();
		db.push(data, batch_size);
		t.push = System.currentTimeMillis() - before;
		System.out.println(String.format("done in %d ms", t.push));
		System.out.print("now counting...");
		before = System.currentTimeMillis();
		long count = db.count();
		t.count = System.currentTimeMillis() - before;
		System.out.println(String.format("done in %d ms", t.count));
		System.out.println(String.format("count:%d", count));
		
		return t;
	}
}
