package ee.tkasekamp.vickywaranalyzer.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import ee.tkasekamp.vickywaranalyzer.controller.tab.AbstractController;
import ee.tkasekamp.vickywaranalyzer.gui.ObservableJoinedCountry;

public class WarCountryBox extends AbstractController {

	@FXML
	private ImageView flag;

	@FXML
	private Label originalLabel;

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
	private Label war;

	@FXML
	private Insets x3;

	/** Attacker or defender */
	private String side;

	public void init(String side) {
		this.side = side;
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

	}

	public void populate() {

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
