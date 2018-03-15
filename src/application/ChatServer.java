package application;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import interfaces.ChatClientInterface;
import interfaces.ChatServerInterface;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {

	private static final long serialVersionUID = 1L;
	private List<String> users;
	private Thread checkUserLoggedThread;
	
	public static void main(String[] args) {
		try {
		  // Cria o registro 
			LocateRegistry.createRegistry(1099);
			Remote chatServer = new ChatServer();
			// Insere a instância do ChatServer no registro
			Naming.rebind("//localhost:1099/server", chatServer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ChatServer() throws RemoteException {
		super();
		users = new ArrayList<String>();
		// Thread que checa quais usuários ainda estão alive
		checkUserLoggedThread = new Thread() {
			@Override
			public void run() {
				while(true) {
					try {
						if(!users.isEmpty()) {
							for(String user : users){
								try {
									((ChatClientInterface)Naming.lookup("username/" + user)).getName();
								} catch (Exception e1) {
									try {
										logout(user);
									} catch (RemoteException e2) {
										e2.printStackTrace();
									}
								}
							}
						}
					} catch(Exception e) {
						
					}
				}
			}
		};
		checkUserLoggedThread.start();
	}

	// Adiciona o usuário na lista de usuários
	public void login(String name) throws RemoteException {
		synchronized(users) {
			users.add(name);
		}
		System.out.println(name + " logged in.");
	}

  // Remove o usuário da lista de usuários
	public void logout(String name) throws RemoteException {
		synchronized(users) {
			if(users.remove(name))
				System.out.println(name + " logged out.");
		}
	}

	// Faz um broadcast da mensagem recebida para todos os usuários existentes
	public void send(String messageToSend) throws RemoteException {
		System.out.println(messageToSend);
		for(String user : users){
			try {
				((ChatClientInterface)Naming.lookup("username/" + user)).receive(messageToSend);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> getUserList() throws RemoteException {
		return users;
	}

}