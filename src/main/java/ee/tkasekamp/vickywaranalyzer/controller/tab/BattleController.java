package ee.tkasekamp.vickywaranalyzer.controller.tab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import ee.tkasekamp.vickywaranalyzer.controller.MainController;
import ee.tkasekamp.vickywaranalyzer.controller.box.BattleCountryBox;
import ee.tkasekamp.vickywaranalyzer.core.Battle;
import ee.tkasekamp.vickywaranalyzer.service.ModelService;

public class BattleController extends AbstractController{

	@FXML
	private Label battleName;

	@FXML
	private Label battleType;

	@FXML
	private Label battleResult;

	@FXML
	private Label battleLocation;

	@FXML
	private Label battleDate;

	@FXML
	private Label battleTotalLosses;
	
	@FXML
	private BattleCountryBox attackerBoxController;
	@FXML
	private BattleCountryBox defenderBoxController;
	
	private MainController main;
	private Tab tab;
	private ModelService modelService;
	
	public void init(MainController mainController,ModelService modelService, Tab tab) {
		main = mainController;
		this.tab = tab;
		this.modelService = modelService;
		attackerBoxController.init(modelService, "Attacker");
		defenderBoxController.init(modelService, "Defender");
	}

	@Override
	public void reset() {
		tab.setDisable(true);
		tab.setText("Battle");
		
	}

	public void populate(Battle battle) {
		
	}
}
