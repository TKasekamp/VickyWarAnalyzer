package ee.tkasekamp.vickywaranalyzer.controller.box;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import ee.tkasekamp.vickywaranalyzer.core.Battle;
import ee.tkasekamp.vickywaranalyzer.core.Country;
import ee.tkasekamp.vickywaranalyzer.core.Unit;
import ee.tkasekamp.vickywaranalyzer.service.ModelService;

public class BattleCountryBox {
	@FXML
	private TableView<Unit> unitsTable;
	@FXML
	private TableColumn<Unit, String> colUnitType;
	@FXML
	private TableColumn<Unit, Integer> colUnitNumber;

	@FXML
	private Label losses;

	@FXML
	private Label armySize;

	@FXML
	private Label country;

	@FXML
	private ImageView flag;

	@FXML
	private Label leader;

	// Helper labels that describe side of the country
	@FXML
	private Label sideHelper;
	@FXML
	private Label leaderHelper;

	@FXML
	private Label sideUnitsHelper;

	private ObservableList<Unit> unitsTableContent;

	private String side;
	private ModelService modelService;

	public void init(ModelService modelService, String side) {
		unitsTableContent = FXCollections.observableArrayList();
		this.side = side;
		this.modelService = modelService;
		setHelperLabels();
		setColumnValues();
	}

	public void populate(Battle battle) {
		unitsTableContent.clear();
		if (side == "Attacker")
			populateAttacker(battle);

		else
			populateDefender(battle);
	}

	private void populateAttacker(Battle battle) {
		/* Finding the main flag */
		for (Country country : modelService.getCountries()) {
			if (battle.getAttacker().equals(country.getTag())) {
				flag.setImage(country.getFlag());
			}
		}
		country.setText(battle.getAttackerOfficial());

		/* Army size and adding to table list */
		int size = 0;
		for (Unit unit : battle.getAttackerUnits()) {
			size += unit.getNumber();
			unitsTableContent.add(unit);
		}
		unitsTable.setItems(unitsTableContent);

		armySize.setText(Integer.toString(size));
		losses.setText(Integer.toString(battle.getAttackerLosses()));

		leader.setText(battle.getLeaderAttacker());
	}

	private void populateDefender(Battle battle) {
		/* Finding the main flag */
		for (Country country : modelService.getCountries()) {
			if (battle.getDefender().equals(country.getTag())) {
				flag.setImage(country.getFlag());
			}
		}
		country.setText(battle.getDefenderOfficial());

		/* Army size and adding to table list */
		int size = 0;
		for (Unit unit : battle.getDefenderUnits()) {
			size += unit.getNumber();
			unitsTableContent.add(unit);
		}
		unitsTable.setItems(unitsTableContent);

		armySize.setText(Integer.toString(size));
		losses.setText(Integer.toString(battle.getDefenderLosses()));
		leader.setText(battle.getLeaderDefender());
	}

	private void setHelperLabels() {
		sideHelper.setText(side + ":");
		leaderHelper.setText(side + " leader:");
		sideUnitsHelper.setText(side + " units");
	}

	private void setColumnValues() {
		/* Attacker side */
		colUnitType.setCellValueFactory(new PropertyValueFactory<Unit, String>(
				"type"));
		colUnitNumber
				.setCellValueFactory(new PropertyValueFactory<Unit, Integer>(
						"number"));

	}
}
