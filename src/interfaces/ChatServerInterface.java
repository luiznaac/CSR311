/*************************************************************
 *                                                           * 
 *  Responsável por gerir os clientes logados e enviar as    *
 *  mensagens passadas pelos clientes                        *
 *                                                           *
 ************************************************************/

package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatServerInterface extends Remote {

	void login(String name) throws RemoteException;

	void logout(String name) throws RemoteException;

	void send(String message) throws RemoteException;

	List<String> getUserList() throws RemoteException;

}