/*************************************************************
 * 													   	                          	 * 
 * 	Responsável pela comunicação do servidor com o cliente	 *
 * 	Uma cópia do objeto remoto desta classe fica no registro *
 * 													                                 *
 ************************************************************/


package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClientInterface extends Remote {

	void receive(String message) throws RemoteException;
	String getName() throws RemoteException;
	Boolean getMode() throws RemoteException;

}
