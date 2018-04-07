package model;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import interfaces.ChatClientInterface;
import interfaces.ChatServerInterface;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {

	private static final long serialVersionUID = 1L;
	private HashMap<String, String> users;
	private Thread checkUserLoggedThread;
	
	public static void main(String[] args) {
		try {
		  // Cria o registro servidor
		  System.out.println("Criando registro na porta 1099.");
			LocateRegistry.createRegistry(1099);
			Remote chatServer = new ChatServer();
			System.out.println("Registro criado com sucesso.");
			// Insere a instância do ChatServer no registro
			System.out.println("Cadastrando objeto servidor no registro.");
			Naming.rebind("//localhost:1099/server", chatServer);
			System.out.println("Servidor cadastrado com sucesso.");
			System.out.println("Online.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ChatServer() throws RemoteException {
		super();
		users = new HashMap<String, String>();
		// Thread que checa quais usuários ainda estão alive
		checkUserLoggedThread = new Thread() {
			@Override
			public void run() {
				while(true) {
					try {
					  Thread.sleep(10);
						if(!users.isEmpty()) {
							for(String ip : users.keySet()){
								try {
									((ChatClientInterface)Naming.lookup("//" + ip + ":1098/username/" + users.get(ip))).getName();
								} catch (Exception e1) {
									try {
										logout(ip);
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
		  try {
	      users.put(getClientHost(), name);
	      System.out.println(name + ":" + getClientHost() + " logged in.");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
    }
	}

  // Remove o usuário da lista de usuários
	public void logout(String ip) throws RemoteException {
		synchronized(users) {
		  String name = users.get(ip);
			if(users.remove(ip, name))
				System.out.println(name + ":" + ip + " logged out.");
		}
	}

	// Faz um broadcast da mensagem recebida para todos os usuários existentes
	public void send(String messageToSend) throws RemoteException {
		for(String ip : users.keySet()){
			try {
			  ChatClientInterface client = (ChatClientInterface)Naming.lookup("//" + ip + ":1098/username/" + users.get(ip));
		    if(client.getMode()) {
		      System.out.println("Enviando mensagem para " + users.get(ip) + ":" + ip);
		      client.receive(messageToSend);
		    }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public HashMap<String, String> getUserList() throws RemoteException {
		return users;
	}

}