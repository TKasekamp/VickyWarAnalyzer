package ee.tkasekamp.vickywaranalyzer.controller.tab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ee.tkasekamp.vickywaranalyzer.controller.MainController;

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

	public void init(MainController mainController) {
		main = mainController;
	}

	@FXML
	void directoryIssueFired(ActionEvent event) {
		
	}

	@FXML
	void saveGameIssueFired(ActionEvent event) {

	}

	@FXML
	void startIssueFired(ActionEvent event) {

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}
}
