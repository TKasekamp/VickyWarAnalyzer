package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/** This is the "main" class, which starts the interface.
 * Definition of the interface buttons and tables is done in GuiController. 
 * FileLoader fills the the second tab, War- and BattleDetailsContent fill those tabs.
 * 
 * @author Tõnis Kasekamp
 */
public class GUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}
    @Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("GUI2.fxml"));
    
        stage.setTitle("Victoria II war analyzer");
        stage.setScene(new Scene(root, 1400, 700));
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.show();
        stage.getIcons().add(new Image("/flags/EST.png")); /* Cause I'm Estonian, thats why */
    }



}
