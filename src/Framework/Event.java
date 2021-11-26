/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Framework;

import Utils.Props;

import java.io.Serial;
import java.io.Serializable;


public class Event implements Serializable {
    @Serial
	private static final long serialVersionUID = Props.UID; //Default serializable value
    private String message;
	private EventId eventId;
	private Method method;

	public Event(EventId id, String text, Method method ) {
		this.message = text;
		this.eventId = id;
		this.method = method;
	}

//	public Event(EventId id, String text) {
//		this.message = text;
//		this.eventId = id;
//	}

	public Event(EventId id ) {
		this.method = null;
		this.message = null;
		this.eventId = id;
	}

	public EventId getEventId() {
		return eventId;
	}
	public String getMessage() {
		return message;
	}

	public Method getMethod() {
		return method;
	}
}