package edu.workingsystem.marta.util;
import edu.workingsystem.marta.model.Event;

import java.util.Comparator;

public class EventComparator implements Comparator<Event>{
	public int compare(Event o1, Event o2) {
		if (o2 == null)
			return -1;
		if (o1 == null) {
			return 1;
		}
		int i=o1.getEventRank().intValue() - o2.getEventRank().intValue();
		return i;
	}

}
