package ee.tkasekamp.vickywaranalyzer.controller;

import ee.tkasekamp.vickywaranalyzer.controller.tab.BattleController;
import ee.tkasekamp.vickywaranalyzer.controller.tab.SettingsController;
import ee.tkasekamp.vickywaranalyzer.controller.tab.WarDetailsController;
import ee.tkasekamp.vickywaranalyzer.controller.tab.WarListController;
import ee.tkasekamp.vickywaranalyzer.controller.tab.WargoalController;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class MainController {
	@FXML
	private BattleController battleController;
	@FXML
	private SettingsController settingsController;
	@FXML
	private WarDetailsController warDetailsController;
	@FXML
	private WargoalController wargoalController;
	@FXML
	private WarListController warListController;
	@FXML
	private Tab warListTab;
	@FXML
	private Tab battleDetailstTab;
	@FXML
	private Tab settingsTab;
	@FXML
	private Tab wargoalTab;

	@FXML
	public void initialize() {
		battleController.init(this);
		settingsController.init(this);
		warDetailsController.init(this);
		wargoalController.init(this);
		warListController.init(this);
	}
	
	private void reset() {
		battleController.reset();
		settingsController.reset();
		warDetailsController.reset();
		wargoalController.reset();
		warListController.reset();
		warListTab.setDisable(true);
		battleDetailstTab.setDisable(true);
		settingsTab.setDisable(true);
		wargoalTab.setDisable(true);
	}
}
