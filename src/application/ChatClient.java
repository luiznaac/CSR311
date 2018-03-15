package application;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import interfaces.ChatClientInterface;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;

public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {

	private static final long serialVersionUID = 1L;
	private StringProperty message;
	private String name;

	protected ChatClient(String name, StringProperty message) throws RemoteException {
		super();
		this.message = message;
		this.name = name;
	}

	// Recebe a mensagem, setando o parâmetro 'message', a qual é a mesma instância do messageReceived do ChatModel
	public void receive(String messageReceived) throws RemoteException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				message.set(messageReceived);
			}
		});
	}

	public String getName() throws RemoteException {
		return name;
	}

}
