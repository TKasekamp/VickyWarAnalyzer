package ee.tkasekamp.vickywaranalyzer.controller.tab;

import ee.tkasekamp.vickywaranalyzer.controller.MainController;
import ee.tkasekamp.vickywaranalyzer.core.Country;
import ee.tkasekamp.vickywaranalyzer.core.JoinedCountry;
import ee.tkasekamp.vickywaranalyzer.core.War;
import ee.tkasekamp.vickywaranalyzer.service.ModelService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.util.stream.Collectors;

public class WarListController extends AbstractController {

	@FXML
	private Label playerLabel;

	@FXML
	private Label startDateLabel;

	@FXML
	private Label currentDateLabel;

	@FXML
	private ListView<Label> selectCountryIssue;

	@FXML
	private Button showAllWarsIssue;

	@FXML
	private Button showActiveWarsIssue;

	@FXML
	private Button showPreviousWarsIssue;

	@FXML
	private Button showMyWarsIssue;

	@FXML
	private TableView<War> warTable;

	@FXML
	private TableColumn<War, String> colNameWar;

	@FXML
	private TableColumn<War, String> colAttackerWar;

	@FXML
	private TableColumn<War, String> colDefenderWar;

	@FXML
	private TableColumn<War, String> colCasusBelliWar;

	@FXML
	private TableColumn<War, String> colStartDateWar;

	@FXML
	private TableColumn<War, String> colEndDateWar;

	private Tab tab;

	private MainController main;
	private ModelService modelServ;
	private ObservableList<War> warTableContent;

	public void init(MainController mainController, ModelService model, Tab tab) {
		main = mainController;
		modelServ = model;
		this.tab = tab;
		warTableContent = FXCollections.observableArrayList();

		selectCountryIssue.getSelectionModel().selectedItemProperty()
				.addListener((arg0, arg1, arg2) -> {
					warTableShowCountry(arg2.getText());
				});
		/* Listening to selections in warTable */
		final ObservableList<War> warTableSelection = warTable
				.getSelectionModel().getSelectedItems();
		warTableSelection.addListener(tableSelectionChanged);

		warTable.setItems(warTableContent);
		setColumnValues();
	}

	@FXML
	void showActiveWarsIssue(ActionEvent event) {
		warTableShowActive();
	}

	@FXML
	void showAllWarsIssue(ActionEvent event) {
		warTablePopulateAll();
	}

	@FXML
	void showMyWarsIssue(ActionEvent event) {
		warTableShowMyWars();
	}

	@FXML
	void showPreviousWarsIssue(ActionEvent event) {
		warTableShowPrevious();
	}

	@Override
	public void reset() {
		playerLabel.setText("");
		startDateLabel.setText("");
		currentDateLabel.setText("");
		tab.setDisable(true);
		selectCountryIssue.getItems().clear();

	}


	public void populate() {
		playerLabel.setText(modelServ.getPlayerOfficial());
		startDateLabel.setText(modelServ.getStartDate());
		currentDateLabel.setText(modelServ.getDate());
		tab.setDisable(false);

		populateCountryList();
		warTablePopulateAll();

	}

	private void setColumnValues() {
		/* Connecting the War fields with warTable columns */
		colNameWar.setCellValueFactory(new PropertyValueFactory<>(
				"name"));
		colAttackerWar
				.setCellValueFactory(new PropertyValueFactory<>(
						"originalAttackerOfficial"));
		colDefenderWar
				.setCellValueFactory(new PropertyValueFactory<>(
						"originalDefenderOfficial"));
		colCasusBelliWar
				.setCellValueFactory(new PropertyValueFactory<>(
						"casus_belli"));
		colStartDateWar
				.setCellValueFactory(new PropertyValueFactory<>(
						"startDate"));
		colEndDateWar
				.setCellValueFactory(new PropertyValueFactory<>(
						"endDate"));
	}

	private void warTablePopulateAll() {
		warTableContent.clear();
		warTableContent.addAll(modelServ.getWars());

	}

	private void warTableShowActive() {
		warTableContent.clear();
		warTableContent.addAll(modelServ.getWars().stream().filter(item -> item.isActive()).collect(Collectors.toList()));
	}

	private void warTableShowPrevious() {
		warTableContent.clear();
		warTableContent.addAll(modelServ.getWars().stream().filter(item -> !item.isActive()).collect(Collectors.toList()));
	}

	private void warTableShowMyWars() {
		warTableContent.clear();
		for (War item : modelServ.getWars()) {
			for (JoinedCountry country : item.getCountryList()) {
				if (country.getTag().equals(modelServ.getPlayer())) {
					warTableContent.add(item);
				}
			}

		}
	}

	private void warTableShowCountry(String tag) {
		warTableContent.clear();
		for (War item : modelServ.getWars()) {
			for (JoinedCountry country : item.getCountryList()) {
				if (modelServ.getOfficialName(country.getTag()).equals(tag)) {
					warTableContent.add(item);
				} else if (country.getTag().equals(tag)) {
					warTableContent.add(item);
				}
			}

		}
	}

	private void populateCountryList() {

		for (Country country : modelServ.getCountries()) {
			ImageView iv2 = new ImageView(country.getFlag());

			iv2.setFitWidth(32); // 30 to 35 looks good
			iv2.setPreserveRatio(true);
			iv2.setSmooth(true);
			iv2.setCache(true);

			Label label = new Label(country.getOfficialName(), iv2);
			label.setContentDisplay(ContentDisplay.LEFT);
			selectCountryIssue.getItems().add(label);
		}
	}

	private ListChangeListener<War> tableSelectionChanged = new ListChangeListener<War>() {
		@Override
		public void onChanged(Change<? extends War> c) {
			if (!warTable.getSelectionModel().getSelectedItems().isEmpty()) {
				main.populateWarTab((War) warTable.getSelectionModel().getSelectedItems().toArray()[0]);
			}
		}

	};

}
