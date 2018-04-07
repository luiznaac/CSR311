package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import interfaces.ChatClientInterface;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;

public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {

	private static final long serialVersionUID = 1L;
	private StringProperty signal;
	private String name;
	private Boolean mode;

	protected ChatClient(String name, StringProperty signal, Boolean mode) throws RemoteException {
		super();
		this.signal = signal;
		this.name = name;
		this.mode = mode;
	}

	// Recebe a mensagem, setando o parâmetro 'signal', a qual é a mesma instância do signalReceived do ChatModel
	public void receive(String signalReceived) throws RemoteException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
			  signal.set(new String(signalReceived));
			}
		});
	}

	public String getName() throws RemoteException {
		return name;
	}

	public Boolean getMode() throws RemoteException {
    return mode;
  }
	
}
