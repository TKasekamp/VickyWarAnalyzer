package ee.tkasekamp.vickywaranalyzer.controller.tab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ee.tkasekamp.vickywaranalyzer.controller.MainController;

public class WargoalController extends AbstractController{

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

	public void init(MainController mainController) {
		main = mainController;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
