package ee.tkasekamp.vickywaranalyzer.controller.tab;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import ee.tkasekamp.vickywaranalyzer.controller.MainController;
import ee.tkasekamp.vickywaranalyzer.service.UtilService;

public class SettingsController extends AbstractController {
	@FXML
	private TextField installTextField;

	@FXML
	private Button directoryIssue;

	@FXML
	private TextField saveGameTextField;

	@FXML
	private Button saveGameIssue;

	@FXML
	private Button startIssue;

	@FXML
	private Label errorLabel;

	@FXML
	private CheckBox localisationCheck;

	private MainController main;
	private UtilService utilServ;

	public void init(MainController mainController, UtilService utilServ) {
		main = mainController;
		this.utilServ = utilServ;
		populate();
		
	}
	
	public void setErrorText(String text) {
		errorLabel.setText(text);
	}

	@FXML
	void directoryIssueFired(ActionEvent event) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Victoria II directory");
		/* Only if there is a path is it given to the chooser */
		if (!utilServ.getInstallFolder().equals("")) {
			chooser.setInitialDirectory(new File(utilServ.getInstallFolder()));			
		}
		// Throws error when user cancels selection
		try {
			File file = chooser.showDialog(null);
			installTextField.setText(file.getPath());
			String temp = file.getPath().replace("\\", "/"); // Usable path 
			utilServ.setInstallFolder(temp);
		} catch (NullPointerException e) {
		}
	}

	@FXML
	void saveGameIssueFired(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Victoria II save game");
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Save game (*.v2)", "*.v2");
        fileChooser.getExtensionFilters().add(extFilter);
        /* Only if there is a path is it given to the filechooser */
        if (!utilServ.getSaveGameFolder().equals("")) {
        	fileChooser.setInitialDirectory(new File(utilServ.getSaveGameFolder()));	      	
        }

        // Throws error when user cancels selection
        try {
			File file = fileChooser.showOpenDialog(null);
			saveGameTextField.setText(file.getPath());
			String temp = file.getPath().replace("\\", "/"); // Usable path 
//			utilServ.setInstallFolder(temp);
//			errorLabel.setText(getErrorLabel().getText() + " ");
		} catch (NullPointerException e) {
		}
	}

	@FXML
	void startIssueFired(ActionEvent event) {
		System.out.println(saveGameTextField.getText());
		System.out.println(main == null);
		main.readSaveGame(saveGameTextField.getText());
	}

	@Override
	public void reset() {
		errorLabel.setText("");

	}

	@Override
	public void populate() {
		installTextField.setText(utilServ.getInstallFolder());
		saveGameTextField.setText(utilServ.getSaveGameFolder());
		
	}
}
