/*************************************************************
 *                                                           * 
 *                 MODEL (do modelo MVC)                     *
 *                                                           *
 ************************************************************/

package application;

import java.util.Random;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ChatModel {

	private ChatService chatService;
	private StringProperty messagesDecrypted; // Guarda todas as mensagens recebidas 
	private StringProperty messageReceived;   // Mensagem recebida para ser descriptografada
	private String username;                  // Nome de usu�rio relacionado com a inst�ncia
	
	public ChatModel() {
	  // Seta um nome random para o usu�rio
		Random rand = new Random();
		username = "name" + String.valueOf(rand.nextInt(999));
		messagesDecrypted = new SimpleStringProperty();
		messageReceived = new SimpleStringProperty();
		// Listener que checa a altera��o (que significa recep��o) de uma mensagem
		messageReceived.addListener(new ChangeListener<Object>(){
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
            	Platform.runLater(new Runnable() {
        			@Override
        			public void run() {
        				messagesDecrypted.set((messagesDecrypted.getValue() == null ? "" : messagesDecrypted.getValue() + "\n") + decrypt((String)observable.getValue()));
        			}
        		});
            }
        });
		try {
			chatService = new ChatService(username, messageReceived);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public StringProperty getMessagesDecrypted() {
		return messagesDecrypted;
	}
	
	// Prepara a mensagem para ser enviada
	// Adiciona o r�tulo de usu�rio e chama o m�todo de criptografia
	public void send(String messageToSend) {
		chatService.send(encrypt("[" + username + "]" + ": " + messageToSend)); 
	}
	
	// Descriptografa a mensagem recebida
	public String decrypt(String messageEncrypted) {
		/*String messageDecrypted = "";
		if(messageEncrypted != null) {
			for(int i = messageEncrypted.length()-1 ; i >= 0 ; i--)
				messageDecrypted += messageEncrypted.charAt(i);
		}
		return messageDecrypted;*/

	   // SER� IMPLEMENTADO ALGORITMO MLT-3
	  
	  return messageEncrypted;
	}
	
	// Criptografa a mensagem a ser enviada
	public String encrypt(String message) {
		/*String messageEncrypted = "";
		if(messageEncrypted != null) {
			for(int i = message.length()-1 ; i >= 0 ; i--)
				messageEncrypted += message.charAt(i);
		}
		return messageEncrypted;*/
	  
	  // SER� IMPLEMENTADO ALGORITMO MLT-3
	  
	  return message;
	}
	
}
