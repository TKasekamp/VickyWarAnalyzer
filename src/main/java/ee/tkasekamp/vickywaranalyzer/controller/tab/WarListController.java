package ee.tkasekamp.vickywaranalyzer.controller.tab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ee.tkasekamp.vickywaranalyzer.controller.MainController;
import ee.tkasekamp.vickywaranalyzer.service.ModelService;

public class WarListController extends AbstractController{

	@FXML
	private Label playerLabel;

	@FXML
	private Label startDateLabel;

	@FXML
	private Label currentDateLabel;

	@FXML
	private ListView<?> selectCountryIssue;

	@FXML
	private Button showAllWarsIssue;

	@FXML
	private Button showActiveWarsIssue;

	@FXML
	private Button showPreviousWarsIssue;

	@FXML
	private Button showMyWarsIssue;

	@FXML
	private TableView<?> warTable;

	@FXML
	private TableColumn<?, ?> colNameWar;

	@FXML
	private TableColumn<?, ?> colAttackerWar;

	@FXML
	private TableColumn<?, ?> colDefenderWar;

	@FXML
	private TableColumn<?, ?> colCasusBelliWar;

	@FXML
	private TableColumn<?, ?> colStartDateWar;

	@FXML
	private TableColumn<?, ?> colEndDateWar;
	
	private Tab warListTab;

	private MainController main;
	private ModelService modelServ;

	public void init(MainController mainController, ModelService model, Tab tab) {
		main = mainController;
		modelServ = model;
		warListTab = tab;
	}

	@FXML
	void showActiveWarsIssue(ActionEvent event) {

	}

	@FXML
	void showAllWarsIssue(ActionEvent event) {

	}

	@FXML
	void showMyWarsIssue(ActionEvent event) {

	}

	@FXML
	void showPreviousWarsIssue(ActionEvent event) {

	}

	@Override
	public void reset() {
		playerLabel.setText("");
		startDateLabel.setText("");
		currentDateLabel.setText("");
		warListTab.setDisable(true);
		
	}

	@Override
	public void populate() {
		playerLabel.setText(modelServ.getPlayerOfficial());
		startDateLabel.setText(modelServ.getStartDate());
		currentDateLabel.setText(modelServ.getDate());
		warListTab.setDisable(false);
		
	}

}
