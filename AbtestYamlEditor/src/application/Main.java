package application;

import javafx.application.Application;
import javafx.stage.Stage;
import src.manager.ScreenManager;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
//		try {
//			Parent parent = FXMLLoader.load(getClass().getResource("AbtestEdit.fxml"));
//			Scene scene = new Scene(parent);
//			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		ScreenManager.getInstance().displayMainScreen();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
