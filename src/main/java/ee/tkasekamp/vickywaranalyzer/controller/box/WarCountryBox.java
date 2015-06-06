package ee.tkasekamp.vickywaranalyzer.controller.box;

import java.util.Arrays;

import ee.tkasekamp.vickywaranalyzer.controller.other.ObservableJoinedCountry;
import ee.tkasekamp.vickywaranalyzer.controller.tab.AbstractController;
import ee.tkasekamp.vickywaranalyzer.core.JoinedCountry;
import ee.tkasekamp.vickywaranalyzer.core.War;
import ee.tkasekamp.vickywaranalyzer.service.ModelService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

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

	/**
	 * Attacker or defender
	 */
	private String side;
	private ModelService modelService;
	private ObservableList<ObservableJoinedCountry> tableContent;

	public void init(ModelService modelService, String side) {
		this.side = side;
		this.modelService = modelService;
		tableContent = FXCollections.observableArrayList();
		colName.setCellValueFactory(new PropertyValueFactory<>("officialName"));
		colFlag.setCellValueFactory(new PropertyValueFactory<>("flag"));
		colStartDate.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
		colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
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
		if (side == "Attacker") {
			populateHelper(war.getAttacker(), war.getOriginalAttacker());
			populateTable(war.getCountryList(), true);
		} else {
			populateHelper(war.getDefender(), war.getOriginalDefender());
			populateTable(war.getCountryList(), false);
		}
	}

	private void populateHelper(String warSide, String warSideOriginal) {
		originalLabel.setText(modelService.getOfficialName(warSideOriginal));

		if (warSide.equals("")) {
			flag.setImage(modelService.getFlag(warSideOriginal));

			// Hiding the labels as there is no use for them
			warLabel.setVisible(false);
			warHelper.setVisible(false);
		} else {
			flag.setImage(modelService.getFlag(warSide));
			warLabel.setText(warSide);

			warLabel.setVisible(true);
			warHelper.setVisible(true);
		}
	}

	/**
	 * Filters all countries in joinedCountries that have a specific joinType. Then adds them to the
	 * table.
	 *
	 * @param joinedCountries
	 * 		JoinedCountry Array
	 * @param joinType
	 * 		true for attacker, false for defender.
	 */
	private void populateTable(JoinedCountry[] joinedCountries, boolean joinType) {
		Arrays.stream(joinedCountries).filter(x -> joinType == x.isJoinType()).forEach(
				x -> tableContent
						.add(new ObservableJoinedCountry(modelService.getOfficialName(x.getTag()),
								modelService.getFlag(x.getTag()), x.getStartDate(),
								x.getEndDate())));

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
