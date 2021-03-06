/*
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Framework;

import Utils.Props;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class RMIEventBusImpl extends UnicastRemoteObject implements RMIEventBus {
	private static final long serialVersionUID = Props.UID;
	static Vector<EventQueue> eventQueueList;

	public RMIEventBusImpl() throws RemoteException {
		super();
		eventQueueList = new Vector<>(Props.EQ_CAPA, Props.EQ_INCRE);
	}

	public static void main(String[] args) {
		try {
			RMIEventBusImpl eventBus = new RMIEventBusImpl();
			Registry registry = LocateRegistry.createRegistry(Props.PORT);
			registry.bind(Props.LOOKUP, eventBus);
			System.out.println(Props.BUS_RUNNING);
		} catch (Exception e) {
			System.out.println(Props.BUS_ERR + e);
			System.out.println(e);
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
