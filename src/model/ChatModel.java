/*************************************************************
 *                                                           * 
 *                 MODEL (do modelo MVC)                     *
 *                                                           *
 ************************************************************/

package model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;

public class ChatModel {

	private ChatService chatService;
	private StringProperty messagesDecoded;   // Guarda todas as mensagens recebidas 
	private StringProperty messageBinary;     // A mensagem em binário
	private StringProperty signalReceived;    // Mensagem recebida para ser decodificada
	private GraphicRender graphicRender;
	
	public ChatModel() {
    messagesDecoded = new SimpleStringProperty();
    messageBinary = new SimpleStringProperty();
    signalReceived = new SimpleStringProperty();
    // Listener que checa a alteração (que significa recepção) de uma mensagem
    signalReceived.addListener(new ChangeListener<Object>(){
        @Override
        public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
          Platform.runLater(new Runnable() {
            @Override
            public void run() {
              messageBinary.set(decode((String)observable.getValue()));
              generateMessageDecoded();
            }
          });
        }
    });
	}
	
	public boolean initialize(String username, String serverIP, Boolean mode) {
	  if(username.equals("") || serverIP.equals(""))
	    return false;
    try {
      chatService = new ChatService(username, signalReceived, serverIP, mode);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
	}
	
	// Prepara a mensagem para ser enviada
	public void send(String messageToSend) {
	  String signalToSend;
	  generateBinary(messageToSend);
	  signalToSend = encode(messageBinary.get());
	  signalReceived.set(signalToSend);
		chatService.send(signalToSend);
	  graphicRender.render(signalToSend);
	}
	
	// Descriptografa a mensagem recebida
	public String decode(String messageEncoded) {
	  return MLT3.decode(messageEncoded);
	}
	
	// Criptografa a mensagem a ser enviada
	public String encode(String message) {
	  return MLT3.encode(message);
	}
	
	public void initializeGraphicRender(Group graphicLines, Group auxLines) {
	  graphicRender = new GraphicRender(graphicLines, auxLines);
	  signalReceived.addListener(new ChangeListener<Object>(){
      @Override
      public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            graphicRender.render(signalReceived.get());
          }
        });
      }
	  });
	}
	
	private void generateMessageDecoded() {
	  String bin = messageBinary.get();
	  String message = "";
	  for(int i = 0 ; i < bin.length() ; i += 9) {
	    message += (char)calcBinToInt(bin.substring(i, i+8));
	  }
	  messagesDecoded.set(message);
	}
	
	private void generateBinary(String message) {
	  String binary = "";
	  for(int i = 0 ; i < message.length() ; i++) {
	    char c = message.charAt(i);
	    binary += fillLeadingZeros(calcIntToBin(c)) + " ";
	  }
	  messageBinary.set(binary);
	}
	
	private int calcBinToInt(String bin) {
    int value = 0;
    
    for(int i = 0 ; i < bin.length() ; i++) {
      value += Integer.parseInt(bin.substring(i, i+1)) * (int)Math.pow(2, bin.length()-1-i);
    }
    
    return value;
  }
	
	private String calcIntToBin(int value) {
	  String binary = "";
	  
	  while(value != 0) {
	    binary = Integer.toString(value%2) + binary;
	    value = value / 2;
	  }
	  
	  return binary;
	}
	
	private String fillLeadingZeros(String binary) {
    for(int i = 0 ; i < binary.length() % 8 ; i++) {
      binary = "0" + binary;
    }
	  return binary;	
  }
	
	public StringProperty getMessagesDecoded() {
    return messagesDecoded;
  }

  public StringProperty getMessageBinary() {
    return messageBinary;
  }
  
  public StringProperty getSignalReceived() {
    return signalReceived;
  }
	
}