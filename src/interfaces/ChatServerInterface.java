/*************************************************************
 *                                                           * 
 *  Responsável por gerir os clientes logados e enviar as    *
 *  mensagens passadas pelos clientes                        *
 *                                                           *
 ************************************************************/

package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface ChatServerInterface extends Remote {

	void login(String name) throws RemoteException;

	void logout(String name) throws RemoteException;

	void send(String message) throws RemoteException;

	HashMap<String, String> getUserList() throws RemoteException;

}