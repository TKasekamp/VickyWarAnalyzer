package ee.tkasekamp.vickywaranalyzer.controller.box;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import ee.tkasekamp.vickywaranalyzer.controller.tab.AbstractController;
import ee.tkasekamp.vickywaranalyzer.core.Country;
import ee.tkasekamp.vickywaranalyzer.core.War;
import ee.tkasekamp.vickywaranalyzer.gui.ObservableJoinedCountry;
import ee.tkasekamp.vickywaranalyzer.service.ModelService;

public class WarCountryBox extends AbstractController {

	@FXML
	private ImageView flag;

	@FXML
	private Label originalLabel;

	@FXML
	private Label originalHelper;

	@FXML
	private Label totalLossesLabel;

	@FXML
	private Label totalShipLossesLabel;

	@FXML
	private TableView<ObservableJoinedCountry> table;

	@FXML
	private TableColumn<ObservableJoinedCountry, ImageView> colFlag;

	@FXML
	private TableColumn<ObservableJoinedCountry, String> colName;

	@FXML
	private TableColumn<ObservableJoinedCountry, String> colStartDate;

	@FXML
	private TableColumn<ObservableJoinedCountry, String> colEndDate;

	@FXML
	private Label warHelper;

	@FXML
	private Label warLabel;

	/** Attacker or defender */
	private String side;
	private ModelService modelService;
	private ObservableList<ObservableJoinedCountry> tableContent;

	public void init(ModelService modelService, String side) {
		this.side = side;
		this.modelService = modelService;
		tableContent = FXCollections.observableArrayList();
		colName.setCellValueFactory(new PropertyValueFactory<ObservableJoinedCountry, String>(
				"officialName"));
		colFlag.setCellValueFactory(new PropertyValueFactory<ObservableJoinedCountry, ImageView>(
				"flag"));
		colStartDate
				.setCellValueFactory(new PropertyValueFactory<ObservableJoinedCountry, String>(
						"joinDate"));
		colEndDate
				.setCellValueFactory(new PropertyValueFactory<ObservableJoinedCountry, String>(
						"endDate"));
		setHelperLabels(side);
	}

	@Override
	public void reset() {
		warLabel.setText("");
		originalLabel.setText("");
		totalLossesLabel.setText("");
		totalShipLossesLabel.setText("");
		tableContent.clear(); // Clearing list
	}

	public void populate(War war) {
		reset();
		if (side == "Attacker")
			populateAttacker(war);

		else
			populateDefender(war);
	}

	private void populateAttacker(War war) {
		/* If there is no attacker, the flag will be the original attacker's */
		if (war.getAttacker().equals("")) {
			for (Country country : modelService.getCountries()) {
				if (war.getOriginalAttacker().equals(country.getTag())) {
					flag.setImage(country.getFlag());
					originalLabel.setText(country.getOfficialName());
				}
			}
			// Hiding the labels as there is no use for them
			warLabel.setVisible(false);
			warHelper.setVisible(false);
		} else {
			for (Country country : modelService.getCountries()) {
				if (war.getAttacker().equals(country.getTag())) {
					flag.setImage(country.getFlag());

				}
			}
			// Showing labels and giving value
			warLabel.setVisible(true);
			warHelper.setVisible(true);
			warLabel.setText(war.getOriginalAttackerOfficial());
		}


		/*
		 * Comparing war.countrylist items to reference.countrylist items Join
		 * type true for attackers
		 */
		for (int i = 0; i < war.getCountryList().length; i++) {
			for (Country country : modelService.getCountries()) {
				if (country.getTag().equals(war.getCountryList()[i].getTag())
						&& war.getCountryList()[i].isJoinType()) {
					/* Creating a row with items from both classes */
					ObservableJoinedCountry temp = new ObservableJoinedCountry(
							country.getOfficialName(), country.getFlag(),
							war.getCountryList()[i].getStartDate(),
							war.getCountryList()[i].getEndDate());
					tableContent.add(temp);
					break;

				}
			}
		}

		/* Adding the countries to table */
		table.setItems(tableContent);
	}

	private void populateDefender(War war) {
		/* If there is no defender, the flag will be the original defender's */
		if (war.getDefender().equals("")) {
			for (Country country : modelService.getCountries()) {
				if (war.getOriginalDefender().equals(country.getTag())) {
					flag.setImage(country.getFlag());
					originalLabel.setText(country.getOfficialName());
				}
			}
			// Hiding the labels as there is no use for them
			warLabel.setVisible(false);
			warHelper.setVisible(false);
		} else {
			for (Country country : modelService.getCountries()) {
				if (war.getDefender().equals(country.getTag())) {
					flag.setImage(country.getFlag());

				}
			}
			// Showing labels and giving value
			warLabel.setVisible(true);
			warHelper.setVisible(true);
			warLabel.setText(war.getOriginalDefenderOfficial());
		}

		/*
		 * Comparing war.countrylist items to reference.countrylist items Join
		 * type true for attackers
		 */
		for (int i = 0; i < war.getCountryList().length; i++) {
			for (Country country : modelService.getCountries()) {
				if (country.getTag().equals(war.getCountryList()[i].getTag())
						&& !war.getCountryList()[i].isJoinType()) {
					/* Creating a row with items from both classes */
					ObservableJoinedCountry temp = new ObservableJoinedCountry(
							country.getOfficialName(), country.getFlag(),
							war.getCountryList()[i].getStartDate(),
							war.getCountryList()[i].getEndDate());
					tableContent.add(temp);
					break;

				}
			}
		}

		/* Adding the countries to table */
		table.setItems(tableContent);
	}

	public void setTotalLosses(int totalLosses, int totalShipLosses) {
		totalLossesLabel.setText(Integer.toString(totalLosses));
		totalShipLossesLabel.setText(Integer.toString(totalShipLosses));
	}

	private void setHelperLabels(String side) {
		originalHelper.setText("Original " + side.toLowerCase() + ":");
		warHelper.setText(side + ":");
	}
}
