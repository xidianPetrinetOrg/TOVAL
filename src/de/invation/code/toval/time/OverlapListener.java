package de.invation.code.toval.time;

public interface OverlapListener<T extends Interval> {

	public void overlapDetected(OverlapEvent<T> overlapEvent);
	
}
