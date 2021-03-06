/*
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Framework;

import Utils.Props;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Vector;

public class EventQueue implements Serializable {

	private static final long serialVersionUID = Props.UID;
    private Vector<Event> eventList;
	private long componentId;

	public EventQueue() {
		eventList = new Vector<Event> (Props.EQ_CAPA, Props.EQ_INCRE);
		componentId = Calendar.getInstance().getTimeInMillis();
	}

	public long getId()	{
		return componentId;
	}
	public int getSize() {
		return eventList.size();
	}
	public void addEvent(Event newEvent) {
		eventList.add(newEvent);
	}
	public Event getEvent() {
		Event event = null;
		if (eventList.size() > 0) {
			event = eventList.get(0);
			eventList.removeElementAt(0);
		}
		return event;
	}
	public void clearEventQueue() {
		eventList.removeAllElements();
	}
	@SuppressWarnings("unchecked")
	public EventQueue getCopy() {
		EventQueue eventQueue = new EventQueue();
		eventQueue.componentId = componentId;
		eventQueue.eventList = (Vector<Event>) eventList.clone();

		return eventQueue;
	}
}