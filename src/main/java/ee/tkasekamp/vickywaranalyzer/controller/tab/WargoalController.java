package ee.tkasekamp.vickywaranalyzer.controller.tab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ee.tkasekamp.vickywaranalyzer.controller.MainController;
import ee.tkasekamp.vickywaranalyzer.core.Battle.Result;
import ee.tkasekamp.vickywaranalyzer.core.War;
import ee.tkasekamp.vickywaranalyzer.core.WarGoal;
import ee.tkasekamp.vickywaranalyzer.service.ModelService;

public class WargoalController extends AbstractController {

	/* War goal table */
	@FXML
	private TableView<WarGoal> warGoalTable;
	@FXML
	private TableColumn<WarGoal, String> colWarGoalActor;
	@FXML
	private TableColumn<WarGoal, String> colWarGoalReceiver;
	@FXML
	private TableColumn<WarGoal, String> colWarGoalCasusBelli;
	@FXML
	private TableColumn<WarGoal, Integer> colWarGoalStateID;
	@FXML
	private TableColumn<WarGoal, String> colWarGoalCountry;
	@FXML
	private TableColumn<WarGoal, Double> colWarGoalScore;
	@FXML
	private TableColumn<WarGoal, Double> colWarGoalChange;
	@FXML
	private TableColumn<WarGoal, String> colWarGoalDate;
	@FXML
	private TableColumn<WarGoal, Result> colWarGoalFulfilled;

	private ObservableList<WarGoal> warGoalTableContent;

	@FXML
	private Label warName2;

	private MainController main;
	private Tab tab;
	private ModelService modelService;

	public void init(MainController mainController, Tab tab,
			ModelService modelService) {
		main = mainController;
		this.tab = tab;
		this.modelService = modelService;
		warGoalTableContent = FXCollections.observableArrayList();
		setWarGoalTabColumnValues();
	}

	@Override
	public void reset() {
		tab.setDisable(true);

	}

	public void populate(War war) {
		warName2.setText(war.getName());
		/* Show the tab */
		tab.setDisable(false);
		warGoalTableContent.clear(); // A bit of cleaning

		// Adding to list
		for (WarGoal goal : war.getWarGoalList()) {
			warGoalTableContent.add(goal);
		}

		warGoalTable.setItems(warGoalTableContent);

		setColumnVisiblility(modelService.isHOD());

	}

	/**
	 * Sets the column values for wargoaltab. If the version is HoD, more column
	 * are shown. Otherwise they are hidden.
	 */
	private void setWarGoalTabColumnValues() {
		/* Wargoal tab columns */
		colWarGoalActor
				.setCellValueFactory(new PropertyValueFactory<WarGoal, String>(
						"actor"));
		colWarGoalReceiver
				.setCellValueFactory(new PropertyValueFactory<WarGoal, String>(
						"receiver"));
		colWarGoalCasusBelli
				.setCellValueFactory(new PropertyValueFactory<WarGoal, String>(
						"casus_belli"));
		colWarGoalCountry
				.setCellValueFactory(new PropertyValueFactory<WarGoal, String>(
						"country"));
		colWarGoalStateID
				.setCellValueFactory(new PropertyValueFactory<WarGoal, Integer>(
						"state_province_id"));

		// Only used for HoD
		colWarGoalDate
				.setCellValueFactory(new PropertyValueFactory<WarGoal, String>(
						"date"));
		colWarGoalScore
				.setCellValueFactory(new PropertyValueFactory<WarGoal, Double>(
						"score"));
		colWarGoalChange
				.setCellValueFactory(new PropertyValueFactory<WarGoal, Double>(
						"change"));
		colWarGoalFulfilled
				.setCellValueFactory(new PropertyValueFactory<WarGoal, Result>(
						"fulfilled"));

	}

	/**
	 * Some columns are only necessary for HoD.
	 * 
	 * @param isHOD
	 *            If is Heart of Darkness
	 */
	private void setColumnVisiblility(boolean isHOD) {
		colWarGoalDate.setVisible(isHOD);
		colWarGoalScore.setVisible(isHOD);
		colWarGoalChange.setVisible(isHOD);
		colWarGoalFulfilled.setVisible(isHOD);
	}

}
