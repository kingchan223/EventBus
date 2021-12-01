/*
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.ClientOutput;

import Components.RmiConnection;
import Framework.*;
import Utils.Props;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class ClientOutputMain {
	public static void main(String[] args) throws RemoteException, IOException, NotBoundException {
		RMIEventBus eventBus = RmiConnection.getInstance();
		System.out.println("eventBus = " + eventBus);
		long componentId = eventBus.register();
		System.out.println(Props.CLIENT_OUTPUT_SUCCESS + componentId);
		
		Event event = null;
		boolean done = false;
		while (!done) {
			try {
				Thread.sleep(Props.CLOCK_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			for(int i = 0; i < eventQueue.getSize(); i++)  {
				event = eventQueue.getEvent();
				if (event.getEventId() == EventId.ClientOutput) {
					printOutput(event);
				} else if (event.getEventId() == EventId.QuitTheSystem) {
					//printLogReceive(event);
					eventBus.unRegister(componentId);
					done = true;
				}
			}
		}
	}
	private static void printOutput(Event event) {
		System.out.println(event.getMessage());
	}
}
