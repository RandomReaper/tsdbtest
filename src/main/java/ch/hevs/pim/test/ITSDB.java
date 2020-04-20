package ch.hevs.pim.test;

import java.util.List;

interface ITSDB {
	void push(List<Value> data, long batch_size);
    long count();
}
