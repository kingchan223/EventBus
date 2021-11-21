/*
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Framework;

import Components.Props;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class RMIEventBusImpl extends UnicastRemoteObject implements RMIEventBus {
	@Serial
	private static final long serialVersionUID = Props.UID;
	static Vector<EventQueue> eventQueueList;

	public RMIEventBusImpl() throws RemoteException {
		super();
		eventQueueList = new Vector<EventQueue>(Props.EQ_CAPA, Props.EQ_INCRE);
	}

	public static void main(String[] args) {
		try {
			//EventBus는 이벤트를 처리할 각 이벤트 리스너를 등록하고 각 리스너에게 이벤트를 전파하는 역할
			RMIEventBusImpl eventBus = new RMIEventBusImpl();
			Registry rgsty = LocateRegistry.createRegistry(Props.PORT);
			rgsty.bind(Props.LOOKUP, eventBus);
			System.out.println(Props.BUS_RUNNING);
		} catch (Exception e) {
			System.out.println(Props.BUS_ERR + e);
		}
	}

	synchronized public long register() throws RemoteException {
		EventQueue newEventQueue = new EventQueue();
		eventQueueList.add( newEventQueue );
		System.out.println(Props.COMPONENT_REGI+ newEventQueue.getId());
		return newEventQueue.getId();
	}

	synchronized public void unRegister(long id) throws RemoteException {
		EventQueue eventQueue;
		for ( int i = 0; i < eventQueueList.size(); i++ ) {
			eventQueue =  eventQueueList.get(i);
			if (eventQueue.getId() == id) {
				eventQueue = eventQueueList.remove(i);
				System.out.println(Props.COMPONENT_UNREGI+ id);
			}
		}
	}

	synchronized public void sendEvent(Event sentEvent) throws RemoteException {
		EventQueue eventQueue;
		for ( int i = 0; i < eventQueueList.size(); i++ ) {
			eventQueue = eventQueueList.get(i);
			eventQueue.addEvent(sentEvent);
			eventQueueList.set(i, eventQueue);
		}
		System.out.println(Props.EVENT_INFO_ID+sentEvent.getEventId()+Props.EVENT_INFO_MSG+sentEvent.getMessage());
	}

	synchronized public EventQueue getEventQueue(long id) throws RemoteException {
		EventQueue originalQueue = null;
		EventQueue copiedQueue =  null;
		for (EventQueue eventQueue : eventQueueList) {
			originalQueue = eventQueue;
			if (originalQueue.getId() == id) {
				originalQueue = eventQueue;
				copiedQueue = originalQueue.getCopy();
				originalQueue.clearEventQueue();
			}
		}
		return copiedQueue;
	}
}