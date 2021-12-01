package Components;

import Framework.RMIEventBus;
import Utils.Props;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiConnection {

    private static RMIEventBus rmiEventBus = null;

    public RmiConnection() throws RemoteException, NotBoundException {
//        System.out.println("constructor");
//        Registry registry = LocateRegistry.getRegistry("127.0.0.1", Props.PORT);
//        rmiEventBus = (RMIEventBus) registry.lookup("EventBus");
    }

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
