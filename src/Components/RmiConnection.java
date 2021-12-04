package Components;

import Framework.RMIEventBus;
import Utils.Props;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiConnection {

    private static RMIEventBus rmiEventBus = null;

    public RmiConnection(){}

    public static RMIEventBus getInstance() throws RemoteException, NotBoundException {
        if(rmiEventBus==null){
            Registry registry = LocateRegistry.getRegistry(Props.HOST, Props.PORT);
//            System.out.println("registry = " + registry);
            return (RMIEventBus) registry.lookup(Props.LOOKUP);
        }
        else{
            return rmiEventBus;
        }
    }
}
