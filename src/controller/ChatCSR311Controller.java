/*************************************************************
 *                                                           * 
 *               CONTROLLER (do modelo MVC)                  *
 *                                                           *
 ************************************************************/

package controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.ChatModel;

public class ChatCSR311Controller {
  
  private ChatModel model;
  @FXML private TextArea txtMessage;
  @FXML private TextArea txtBinary;
  @FXML private TextArea txtAlgorithm;
  @FXML private TextField txtIP;
  @FXML private TextField txtUsername;
  @FXML private HBox hbxSend;
  @FXML private HBox hbxLogin;
  @FXML private Button btnLogin;
  @FXML private Button btnSend;
  @FXML private Button btnTest;
  @FXML private RadioButton rbSender;
  @FXML private RadioButton rbReceiver;
  @FXML private ToggleGroup tgRxTx;
  @FXML private Pane pnGraphic;
  private Group graphicLines;
  private Group auxLines;
  
  @FXML
  public void initialize() {
    hbxSend.setDisable(true);
    btnLogin.setDefaultButton(true);
    graphicLines = new Group();
    auxLines = new Group();
    pnGraphic.getChildren().addAll(graphicLines, auxLines);
  }
  
  /*****************************
   *     MÉTODOS AUXILIARES    *
   *****************************/
  
  private void send() {
    long time = System.currentTimeMillis();
    model.send(txtMessage.getText());
    System.out.println(System.currentTimeMillis() - time + " ms");
  }
  
  
  /*****************************
   *    EVENTOS DA TELA    *
   *****************************/
  
  @FXML
  public void loginPressed() {
    if(model.initialize(txtUsername.getText(), txtIP.getText(), ((RadioButton)tgRxTx.getSelectedToggle()).getId().equals("rbReceiver"))) {
      hbxSend.setDisable(false);
      hbxLogin.setDisable(true);
      btnLogin.setDefaultButton(false);
      rbSender.setDisable(true);
      rbReceiver.setDisable(true);
      if(((RadioButton)tgRxTx.getSelectedToggle()).getId().equals("rbReceiver")) {
        txtMessage.textProperty().bind(model.getMessagesDecoded());
        btnSend.setDisable(true);
      }
      txtBinary.textProperty().bind(model.getMessageBinary());
      txtAlgorithm.textProperty().bind(model.getSignalReceived());
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
  
  /*****************************
   *    SETTERS/GETTERS    *
   *****************************/
  
  public void setModel(ChatModel model) {
    this.model = model;
    model.initializeGraphicRender(graphicLines, auxLines);
  }
  
}
