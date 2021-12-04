/*
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Framework;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIEventBus extends Remote , Serializable {
	long register() throws RemoteException;
	void unRegister(long SenderID) throws RemoteException;
	void sendEvent(Event m ) throws RemoteException;
	EventQueue getEventQueue(long SenderID) throws RemoteException;
}
