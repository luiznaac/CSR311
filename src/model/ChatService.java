package model;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import interfaces.ChatServerInterface;
import javafx.beans.property.StringProperty;

public class ChatService {

	private Remote client;
	private ChatServerInterface server;
	
	/*
	 * @param name - nome do usuário
	 * @param signalReceived - instância do mesmo parâmetro do ChatModel, 
	 *                          que será alterada pelo ChatClient.receive()
	 *                          após chamada do send() 
	 */
	public ChatService(String username, StringProperty signalReceived, String serverIP, Boolean mode) throws RemoteException, MalformedURLException, NotBoundException {
	  try {
	    System.out.println("Criando registro na porta 1098.");
	    LocateRegistry.createRegistry(1098);
	    System.out.println("Registro criado com sucesso.");
	  } catch (Exception e) {
	    System.out.println("Registro já existente na porta 1098.");
	  }
	  
	  try {
	    client = new ChatClient(username, signalReceived, mode);
	    System.out.println("Cadastrando usuário no registro.");
	    System.out.println("Usuário: " + username + " Modo: " + mode);
      Naming.rebind("//localhost:1098/username/" + username, client);
      System.out.println("Usuário cadastrado com sucesso.");
      System.out.println("Efetuando login.");
      server = (ChatServerInterface)Naming.lookup("//" + serverIP + ":1099/server");
      server.login(username);
      System.out.println("Logado.");
	  } catch(Exception e) {
	    e.printStackTrace();
	  }
		
	}

	// Chama send no server
	public void send(String messageToSend) {
	  System.out.println(messageToSend);
		try {
			server.send(messageToSend);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
