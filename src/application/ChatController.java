/*************************************************************
 *                                                           * 
 *               CONTROLLER (do modelo MVC)                  *
 *                                                           *
 ************************************************************/

package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ChatController {
	
	private ChatModel model;
	@FXML private TextArea txtMessage;
	@FXML private TextArea txtMessages;
	
	/*****************************
	 *	   MÉTODOS AUXILIARES    *
	 *****************************/
	
	private void send() {
		model.send(txtMessage.getText());
		txtMessage.clear();
	}
	
	
	/*****************************
	 *		EVENTOS DA TELA		 *
	 *****************************/
	
	@FXML
	public void sendPressed() {
		send();
	}
	
	@FXML
	public void sendEnter(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			send();
			event.consume();
		}
	}
	
	/*****************************
	 *		SETTERS/GETTERS		 *
	 *****************************/
	
	public void setModel(ChatModel model) {
		this.model = model;
		txtMessages.textProperty().bind(model.getMessagesDecrypted());
	}
	
}
