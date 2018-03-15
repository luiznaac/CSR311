package application;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import interfaces.ChatServerInterface;
import javafx.beans.property.StringProperty;

public class ChatService {

	private Remote client;
	private ChatServerInterface server;
	
	/*
	 * @param name - nome do usuário
	 * @param messageReceived - instância do mesmo parâmetro do ChatModel, 
	 *                          que será alterada pelo ChatClient.receive()
	 *                          após chamada do send() 
	 */
	public ChatService(String name, StringProperty messageReceived) throws RemoteException, MalformedURLException, NotBoundException {
		client = new ChatClient(name, messageReceived);
		Naming.rebind("//localhost:1099/username/"  + name, client);
		server = (ChatServerInterface)Naming.lookup("server");
		server.login(name);
	}

	// Chama send no server
	public void send(String messageToSend) {
		try {
			server.send(messageToSend);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
