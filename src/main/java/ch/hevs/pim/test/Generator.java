package ch.hevs.pim.test;

import java.util.LinkedList;
import java.util.List;

public class Generator {
	public static List<Value> gen(long nr_series, long nr_points) {
		
		List<Value> l = new LinkedList<Value>();
		
		long x=0;
		for (int i = 0 ; i < nr_points ; i++) {
			for (int j = 0 ; j < nr_series; j++) {
				l.add(new Value(j, x++, Math.sin(i+j)));
			}
		}

		return l;
	}
}
