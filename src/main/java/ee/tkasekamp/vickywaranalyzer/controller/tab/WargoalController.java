package ee.tkasekamp.vickywaranalyzer.controller.tab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ee.tkasekamp.vickywaranalyzer.controller.MainController;
import ee.tkasekamp.vickywaranalyzer.core.WarGoal;

public class WargoalController extends AbstractController {

	@FXML
	private TableView<?> warGoalTable;

	@FXML
	private TableColumn<?, ?> colWarGoalActor;

	@FXML
	private TableColumn<?, ?> colWarGoalReceiver;

	@FXML
	private TableColumn<?, ?> colWarGoalCasusBelli;

	@FXML
	private TableColumn<?, ?> colWarGoalCountry;

	@FXML
	private TableColumn<?, ?> colWarGoalStateID;

	@FXML
	private TableColumn<?, ?> colWarGoalDate;

	@FXML
	private TableColumn<?, ?> colWarGoalScore;

	@FXML
	private TableColumn<?, ?> colWarGoalChange;

	@FXML
	private TableColumn<?, ?> colWarGoalFulfilled;

	@FXML
	private Label warName2;

	private MainController main;
	private Tab tab;

	public void init(MainController mainController, Tab tab) {
		main = mainController;
		this.tab = tab;
	}

	@Override
	public void reset() {
		tab.setDisable(true);

	}
	
	public void populate(WarGoal wargoal) {
		
	}

}
