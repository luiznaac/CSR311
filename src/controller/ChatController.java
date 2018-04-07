/*************************************************************
 *                                                           * 
 *               CONTROLLER (do modelo MVC)                  *
 *                                                           *
 ************************************************************/

package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import model.ChatModel;

public class ChatController {
	
	private ChatModel model;
	@FXML private TextArea txtMessage;
	@FXML private TextArea txtMessages;
	@FXML private TextField txtIP;
	@FXML private TextField txtUsername;
	@FXML private HBox hbxSend;
	@FXML private HBox hbxLogin;
	@FXML private Button btnLogin;
	@FXML private Button btnSend;
	@FXML private Button btnTest;
	
	@FXML
	public void initialize() {
	  hbxSend.setDisable(true);
	  btnLogin.setDefaultButton(true);
	}
	
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
	public void loginPressed() {
	  if(model.initialize(txtUsername.getText(), txtIP.getText(), true)) {
	    hbxSend.setDisable(false);
	    hbxLogin.setDisable(true);
	    btnLogin.setDefaultButton(false);
	    btnSend.setDefaultButton(true);
	    btnTest.setDisable(false);
	  }
	}
	
	@FXML
	public void sendPressed() {
		send();
		txtMessage.requestFocus();
	}
	
	@FXML
	public void sendEnter(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			send();
			event.consume();
		}
	}
	
	@FXML
	public void testPressed() {
	  txtMessages.setScrollTop(Double.MAX_VALUE);
	}
	
	/*****************************
	 *		SETTERS/GETTERS		 *
	 *****************************/
	
	public void setModel(ChatModel model) {
		this.model = model;
		txtMessages.textProperty().bind(model.getMessagesDecoded());
	}
	
}
