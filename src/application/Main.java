package application;
	
import controller.ChatCSR311Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ChatModel;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			BorderPane root = (BorderPane)fxmlLoader.load(getClass().getResource("/view/ChatCSR311.fxml").openStream());
			ChatCSR311Controller controller = fxmlLoader.getController();
			Scene scene = new Scene(root, 600.0, 650.0);
			ChatModel model = new ChatModel();
			controller.setModel(model);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
