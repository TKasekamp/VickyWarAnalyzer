package gui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import main.Battle;
import main.Battle.Result;
import main.Battle.Type;
import main.Localisation;
import main.PathLoader;
import main.Reference;
import main.SaveGameReader;
import main.Unit;
import main.War;
import main.WarGoal;

/** This class handles the events passed on by the interface.
 * All the labels, buttons, tables and such are defined here.
 * I should have made a separate controller for each tab, but 
 * I didn't.
 * 
 * The tablecolums are given values in either <code>FileLoader</code> for the savegame tab table,
 * <code>WarDetailsTabContent</code> for the war details tables and such, 
 * <code>BattleDetailsTabContent</code> for the battle details tables.
 */
public class GuiController implements Initializable {
	/** Naming convention:
	 * Buttons are issues and in them is a reference to a table they control. 
	 * warTable describes what class elements are shown in that table.
	 * Column names end with the class which they display.
	 * ABOUT NAMES: THE NAMING SYSTEM IS NOT ALWAYS CORRECT NOR CONSISTENT.
	 */
	/* All settings tab buttons */
    @FXML private Button directoryIssue;
    @FXML private Button saveGameIssue;
    @FXML private Button startIssue;
    /* Settings tab labels */
    @FXML private Label errorLabel;
    /* Settings tab textfields */
    @FXML private TextField installTextField;
    @FXML private TextField saveGameTextField;
    @FXML private CheckBox localisationCheck; // Checked in Localisation
    
    /* Saveame tab labels */
    @FXML
    private Label playerLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label currentDateLabel;
    /* Savegame tab toolbar buttons */
    @FXML
    private Button showAllWarsIssue;
    @FXML
    private Button showPreviousWarsIssue;
    @FXML
    private Button showActiveWarsIssue;    
    @FXML
    private Button showMyWarsIssue;

    @FXML
    private ListView<Label> selectCountryIssue;
    
    /* Savegame tab war table */
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
    private final ObservableList<War> warTableContent = FXCollections.observableArrayList();
       
    /* War details tab */
    @FXML
    private Tab warDetailsTab;
    /* Center box labels */
    @FXML
    private Label warNameLabel;
    @FXML
    private Label warStartDateLabel;
    @FXML
    private Label warEndDateLabel;
    @FXML
    private Label warActionLabel;
    @FXML
    private Label warTotalLossesLabel;
    @FXML
    private Label warTotalShipLossesLabel;
    @FXML
    private Label warHasEndedLabel;
    /* Original wargoal */
    @FXML private Label warGoalActorLabel;
    @FXML private Label warGoalReceiverLabel;
    @FXML private Label warGoalCBLabel; //Casus belli
    @FXML private Label warGoalStateLabel;
    @FXML private Label warGoalCountryLabel;
    @FXML private Label warGoalDateLabel;
    @FXML private Label warGoalScoreLabel;
    @FXML private Label warGoalChangeLabel;
    @FXML private Label warGoalFulfilledLabel;
    
    @FXML private Label warGoalDateHelper;
    @FXML private Label warGoalScoreHelper;
    @FXML private Label warGoalChangeHelper;
    @FXML private Label warGoalFulfilledHelper;
    

    /* Attacker box */
    @FXML private ImageView attackerFlag; 
    @FXML private Label warAttacker;
    @FXML private Label warAttackerHelper; // This is not changed, only hidden 
    @FXML private Label originalAttackerLabel;
    @FXML
    private Label attackerTotalLossesLabel;
    @FXML
    private Label attackerTotalShipLossesLabel;  
    @FXML
    private TableView<ObservableJoinedCountry> attackerTable;
    @FXML
    private TableColumn<ObservableJoinedCountry, String> colAttackerName;
    @FXML
    private TableColumn<ObservableJoinedCountry, ImageView> colAttackerFlag;
    @FXML
    private TableColumn<ObservableJoinedCountry, String> colAttackerStartDate; // Join and start are the same
    @FXML
    private TableColumn<ObservableJoinedCountry, String> colAttackerEndDate;   
    private final ObservableList<ObservableJoinedCountry> attackerTableContent = FXCollections.observableArrayList(); // List managing the attackerTable
    
    /* Defender box */
    @FXML
    private ImageView defenderFlag;   
    @FXML private Label warDefender;
    @FXML private Label warDefenderHelper; // This is not changed, only hidden 
    @FXML
    private Label originalDefenderLabel;
    @FXML
    private Label defenderTotalLossesLabel;
    @FXML
    private Label defenderTotalShipLossesLabel;  
    @FXML
    private TableView<ObservableJoinedCountry> defenderTable;
    @FXML
    private TableColumn<ObservableJoinedCountry, String> colDefenderName;
    @FXML
    private TableColumn<ObservableJoinedCountry, ImageView> colDefenderFlag;
    @FXML
    private TableColumn<ObservableJoinedCountry, String> colDefenderStartDate;
    @FXML
    private TableColumn<ObservableJoinedCountry, String> colDefenderEndDate;  
    private final ObservableList<ObservableJoinedCountry> defenderTableContent = FXCollections.observableArrayList(); // List managing the attackerTable
    
    /* WAR_NAME details tab battle table */
    @FXML
    private TableView<Battle> battleTable;
    @FXML
    private TableColumn<Battle, String> colNameBattle;
    @FXML
    private TableColumn<Battle, String> colAttackerBattle;
    @FXML
    private TableColumn<Battle, String> colDefenderBattle;
    @FXML
    private TableColumn<Battle, String> colDateBattle;
    @FXML
    private TableColumn<Battle, Type> colTypeBattle; 
    @FXML
    private TableColumn<Battle, Integer> colBattleTotalLosses;
    @FXML private TableColumn<Battle, Result> colBattleResult; 
    private final ObservableList<Battle> battleTableContent = FXCollections.observableArrayList();
	
	/* Battle details tab */
    @FXML private Tab battleDetailsTab;
    
	/* Attacker side */
	@FXML private ImageView battleAttackerFlag;
    @FXML private Label battleAttacker;
    @FXML private Label battleAttackerArmySize;
    @FXML private Label battleAttackerLosses;
    @FXML private TableView<Unit> attackerUnitsTable;
    @FXML private TableColumn<Unit, String> colAttackerUnitType; 
    @FXML private TableColumn<Unit, Integer> colAttackerUnitNumber; 
    private final ObservableList<Unit> attackerUnitsTableContent = FXCollections.observableArrayList();
	
	/* Defender side */
	@FXML private ImageView battleDefenderFlag;
    @FXML private Label battleDefender;
    @FXML private Label battleDefenderArmySize;
    @FXML private Label battleDefenderLosses;
    @FXML private TableView<Unit> defenderUnitsTable;
    @FXML private TableColumn<Unit, String> colDefenderUnitType; 
    @FXML private TableColumn<Unit, Integer> colDefenderUnitNumber; 
    private final ObservableList<Unit> defenderUnitsTableContent = FXCollections.observableArrayList();  
	
	/* Center panel with other information */
	@FXML private Label battleName;
	@FXML private Label battleDate;
	@FXML private Label battleType;
	@FXML private Label battleLocation;
	@FXML private Label battleResult;
    @FXML private Label battleAttackerLeader;
    @FXML private Label battleDefenderLeader;
	@FXML private Label battleTotalLosses;
	
	/* Wargoal tab */
    @FXML private Tab warGoalTab;
    @FXML private Label warName2;
    
    /* War goal table */
    @FXML private TableView<WarGoal> warGoalTable;
    @FXML private TableColumn<WarGoal, String> colWarGoalActor;
    @FXML private TableColumn<WarGoal, String> colWarGoalReceiver;
    @FXML private TableColumn<WarGoal, String> colWarGoalCasusBelli; // Join and start are the same
    @FXML private TableColumn<WarGoal, Integer> colWarGoalStateID;  
    @FXML private TableColumn<WarGoal, String> colWarGoalCountry; 
    @FXML private TableColumn<WarGoal, Double> colWarGoalScore;
    @FXML private TableColumn<WarGoal, Double> colWarGoalChange;
    @FXML private TableColumn<WarGoal, String> colWarGoalDate;
    @FXML private TableColumn<WarGoal, Result> colWarGoalFulfilled;
    
    private final ObservableList<WarGoal> warGoalTableContent = FXCollections.observableArrayList(); // List manging the warGoalTable 
    
    // The tabs
    private BattleDetailsTabContent battleTab;
    private FileLoader fileLoader;
    private WarDetailsTabContent detailsTab;
    private Localisation loc;
    private PathLoader pathLoader;
    private SaveGameReader reader;
    
//	public static GuiController getController() {
//		return this;
//	}
	@Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		battleTab = new BattleDetailsTabContent(this);
		fileLoader = new FileLoader(this);
		detailsTab = new WarDetailsTabContent(this);
		loc = new Localisation(this);
		pathLoader = new PathLoader(this);
		reader = new SaveGameReader(this);
		
    	/* Checking if paths exist */
		pathLoader.pathLoader();
    	setSettingsTextFields();
    	
//        /* Listening to selections in list */
        selectCountryIssue.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener<Label>() {

			@Override
			public void changed(ObservableValue<? extends Label> arg0,
					Label arg1, Label arg2) {
				fileLoader.warTableShowCountry(arg2.getText());
			}
        });

        /* Listening to selections in warTable */
        final ObservableList<War> warTableSelection = warTable.getSelectionModel().getSelectedItems();
        warTableSelection.addListener(tableSelectionChanged);
        /* Listening to selections in battleTable */
        final ObservableList<Battle> battleTableSelection = battleTable.getSelectionModel().getSelectedItems();
        battleTableSelection.addListener(battleTableSelectionChanged);
        warDetailsTab.setDisable(true); // Cannot be used until it is populated. There is stuff I don't want people to see...
        battleDetailsTab.setDisable(true);
        warGoalTab.setDisable(true);
        
        /* Defining columns for the tables in those tabs*/
        fileLoader.setWarTableColumnValues();
        detailsTab.setColumnValues();
        battleTab.setColumnValues();
    
    }
	/**
     * Uses Reference to set text to both fields
     */
    private final void setSettingsTextFields() {
		saveGameTextField.setText(Reference.SAVEGAMEPATH);   
		installTextField.setText(Reference.INSTALLPATH);	
	}

	/**
     * Called when the directoryIssue button is fired.
     * Opens a window to get installation folder
     * @param event the action event.
     */
    public final void directoryIssueFired(ActionEvent event) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Victoria II directory");
		/* Only if there is a path is it given to the chooser */
		if (!Reference.INSTALLPATH.equals("")) {
			chooser.setInitialDirectory(new File(Reference.INSTALLPATH));			
		}
		// Throws error when user cancels selection
		try {
			File file = chooser.showDialog(null);
			installTextField.setText(file.getPath());
			String temp = file.getPath().replace("\\", "/"); // Usable path 
			Reference.INSTALLPATH = temp;
		} catch (NullPointerException e) {
		}
    }
    
    /**
     * Called when the saveGameIssue button is fired.
     * Opens a window to browse for the save game
     * @param event the action event.
     */ 
    public final void saveGameIssueFired(ActionEvent event) {
    	
    	errorLabel.setText(""); // Resetting the error field 
        FileChooser fileChooser = new FileChooser();
        /* Only if there is a path is it given to the filechooser */
        if (!Reference.SAVEGAMEPATH.equals("")) {
        	fileChooser.setInitialDirectory(new File(Reference.SAVEGAMEPATH));	      	
        }
		fileChooser.setTitle("Victoria II save game");
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Save game (*.v2)", "*.v2");
        fileChooser.getExtensionFilters().add(extFilter);
        // Throws error when user cancels selection
        try {
			File file = fileChooser.showOpenDialog(null);
			saveGameTextField.setText(file.getPath());
			String temp = file.getPath().replace("\\", "/"); // Usable path 
			Reference.saveGameFile = temp;
			errorLabel.setText(getErrorLabel().getText() + " ");
		} catch (NullPointerException e) {
		}

    }
    /**
     * Called when the directoryIssue button is fired.
     * Saves the paths to a file
     * @param event the action event.
     */
    public final void startIssueFired(ActionEvent event) {
    	newGameReadCleanUp(); // Resetting fields
    	// All the action happens in readLauncher to make this method as small as possible
    	fileLoader.readLauncher();
    	
    	/* Saving the used directories to a file. Starting in a new thread because this is what I'm learning at the moment.  */
    	Thread thread = new Thread(new Runnable() {
    		public void run()
    		{
    			pathLoader.savePaths();
    		}
    	});
    	// start the thread
    	thread.start(); 

    	/* Setting wargoal table values. It must be done AFTER the file has been read as the column values are dependent on the version
    	 * of the game */
    	detailsTab.setWarGoalTabColumnValues();
    	

    }
    /* War tab Button actions */
    public final void showAllWarsIssue(ActionEvent event) {
    	fileLoader.warTablePopulateAll();
    }
    public final void showActiveWarsIssue(ActionEvent event) {
    	// All the action happens in readLauncher to make this method as small as possible
    	fileLoader.warTableShowActive();
    }
    
    public final void showPreviousWarsIssue(ActionEvent event) {
    	// All the action happens in readLauncher to make this method as small as possible
    	fileLoader.warTableShowPrevious();
    }
    public final void showMyWarsIssue(ActionEvent event) {
    	// All the action happens in readLauncher to make this method as small as possible
    	fileLoader.warTableShowMyWars();
    }
    /** warTable selection listener */
    private final ListChangeListener<War> tableSelectionChanged = new ListChangeListener<War>() {
                @Override
                public void onChanged(Change<? extends War> c) {
                	// At the moment does nothing as I don't know how to get mouse doubleclick on row
                	// But it is used by showMoreDetailsWarIssue
                	if (!warTable.getSelectionModel().getSelectedItems().isEmpty()) {
                		detailsTab.warDetailsTabPopulate((War)warTable.getSelectionModel().getSelectedItems().toArray()[0]);
                		} 
                	}             	
                
            };
            
    /** Battle table selection listener in the war details tab*/
    private final ListChangeListener<Battle> battleTableSelectionChanged = new ListChangeListener<Battle>() {
    	@Override
    	public void onChanged(Change<? extends Battle> c) {
    		if (!battleTable.getSelectionModel().getSelectedItems().isEmpty()) {
    			battleDetailsTab.setDisable(false);
        		battleTab.battleDetailsTabPopulate((Battle)battleTable.getSelectionModel().getSelectedItems().toArray()[0]);    			
    		}
    	}             	
    };
    /** Disables tabs and resets their names on new game relooad */
    private final void newGameReadCleanUp() {
        warDetailsTab.setText("War");
        battleDetailsTab.setText("Battle");
    	warDetailsTab.setDisable(true);
        battleDetailsTab.setDisable(true);
        warGoalTab.setDisable(true);

    }
	public Button getDirectoryIssue() {
		return directoryIssue;
	}
	public Button getSaveGameIssue() {
		return saveGameIssue;
	}
	public Button getStartIssue() {
		return startIssue;
	}
	public Label getErrorLabel() {
		return errorLabel;
	}
	public TextField getInstallTextField() {
		return installTextField;
	}
	public TextField getSaveGameTextField() {
		return saveGameTextField;
	}
	public CheckBox getLocalisationCheck() {
		return localisationCheck;
	}
	public Label getPlayerLabel() {
		return playerLabel;
	}
	public Label getStartDateLabel() {
		return startDateLabel;
	}
	public Label getCurrentDateLabel() {
		return currentDateLabel;
	}
	public Button getShowAllWarsIssue() {
		return showAllWarsIssue;
	}
	public Button getShowPreviousWarsIssue() {
		return showPreviousWarsIssue;
	}
	public Button getShowActiveWarsIssue() {
		return showActiveWarsIssue;
	}
	public Button getShowMyWarsIssue() {
		return showMyWarsIssue;
	}
	public ListView<Label> getSelectCountryIssue() {
		return selectCountryIssue;
	}
	public TableView<War> getWarTable() {
		return warTable;
	}
	public TableColumn<War, String> getColNameWar() {
		return colNameWar;
	}
	public TableColumn<War, String> getColAttackerWar() {
		return colAttackerWar;
	}
	public TableColumn<War, String> getColDefenderWar() {
		return colDefenderWar;
	}
	public TableColumn<War, String> getColCasusBelliWar() {
		return colCasusBelliWar;
	}
	public TableColumn<War, String> getColStartDateWar() {
		return colStartDateWar;
	}
	public TableColumn<War, String> getColEndDateWar() {
		return colEndDateWar;
	}
	public ObservableList<War> getWarTableContent() {
		return warTableContent;
	}
	public Tab getWarDetailsTab() {
		return warDetailsTab;
	}
	public Label getWarNameLabel() {
		return warNameLabel;
	}
	public Label getWarStartDateLabel() {
		return warStartDateLabel;
	}
	public Label getWarEndDateLabel() {
		return warEndDateLabel;
	}
	public Label getWarActionLabel() {
		return warActionLabel;
	}
	public Label getWarTotalLossesLabel() {
		return warTotalLossesLabel;
	}
	public Label getWarTotalShipLossesLabel() {
		return warTotalShipLossesLabel;
	}
	public Label getWarHasEndedLabel() {
		return warHasEndedLabel;
	}
	public Label getWarGoalActorLabel() {
		return warGoalActorLabel;
	}
	public Label getWarGoalReceiverLabel() {
		return warGoalReceiverLabel;
	}
	public Label getWarGoalCBLabel() {
		return warGoalCBLabel;
	}
	public Label getWarGoalStateLabel() {
		return warGoalStateLabel;
	}
	public Label getWarGoalCountryLabel() {
		return warGoalCountryLabel;
	}
	public Label getWarGoalDateLabel() {
		return warGoalDateLabel;
	}
	public Label getWarGoalScoreLabel() {
		return warGoalScoreLabel;
	}
	public Label getWarGoalChangeLabel() {
		return warGoalChangeLabel;
	}
	public Label getWarGoalFulfilledLabel() {
		return warGoalFulfilledLabel;
	}
	public Label getWarGoalDateHelper() {
		return warGoalDateHelper;
	}
	public Label getWarGoalScoreHelper() {
		return warGoalScoreHelper;
	}
	public Label getWarGoalChangeHelper() {
		return warGoalChangeHelper;
	}
	public Label getWarGoalFulfilledHelper() {
		return warGoalFulfilledHelper;
	}
	public ImageView getAttackerFlag() {
		return attackerFlag;
	}
	public Label getWarAttacker() {
		return warAttacker;
	}
	public Label getWarAttackerHelper() {
		return warAttackerHelper;
	}
	public Label getOriginalAttackerLabel() {
		return originalAttackerLabel;
	}
	public Label getAttackerTotalLossesLabel() {
		return attackerTotalLossesLabel;
	}
	public Label getAttackerTotalShipLossesLabel() {
		return attackerTotalShipLossesLabel;
	}
	public TableView<ObservableJoinedCountry> getAttackerTable() {
		return attackerTable;
	}
	public TableColumn<ObservableJoinedCountry, String> getColAttackerName() {
		return colAttackerName;
	}
	public TableColumn<ObservableJoinedCountry, ImageView> getColAttackerFlag() {
		return colAttackerFlag;
	}
	public TableColumn<ObservableJoinedCountry, String> getColAttackerStartDate() {
		return colAttackerStartDate;
	}
	public TableColumn<ObservableJoinedCountry, String> getColAttackerEndDate() {
		return colAttackerEndDate;
	}
	public ObservableList<ObservableJoinedCountry> getAttackerTableContent() {
		return attackerTableContent;
	}
	public ImageView getDefenderFlag() {
		return defenderFlag;
	}
	public Label getWarDefender() {
		return warDefender;
	}
	public Label getWarDefenderHelper() {
		return warDefenderHelper;
	}
	public Label getOriginalDefenderLabel() {
		return originalDefenderLabel;
	}
	public Label getDefenderTotalLossesLabel() {
		return defenderTotalLossesLabel;
	}
	public Label getDefenderTotalShipLossesLabel() {
		return defenderTotalShipLossesLabel;
	}
	public TableView<ObservableJoinedCountry> getDefenderTable() {
		return defenderTable;
	}
	public TableColumn<ObservableJoinedCountry, String> getColDefenderName() {
		return colDefenderName;
	}
	public TableColumn<ObservableJoinedCountry, ImageView> getColDefenderFlag() {
		return colDefenderFlag;
	}
	public TableColumn<ObservableJoinedCountry, String> getColDefenderStartDate() {
		return colDefenderStartDate;
	}
	public TableColumn<ObservableJoinedCountry, String> getColDefenderEndDate() {
		return colDefenderEndDate;
	}
	public ObservableList<ObservableJoinedCountry> getDefenderTableContent() {
		return defenderTableContent;
	}
	public TableView<Battle> getBattleTable() {
		return battleTable;
	}
	public TableColumn<Battle, String> getColNameBattle() {
		return colNameBattle;
	}
	public TableColumn<Battle, String> getColAttackerBattle() {
		return colAttackerBattle;
	}
	public TableColumn<Battle, String> getColDefenderBattle() {
		return colDefenderBattle;
	}
	public TableColumn<Battle, String> getColDateBattle() {
		return colDateBattle;
	}
	public TableColumn<Battle, Type> getColTypeBattle() {
		return colTypeBattle;
	}
	public TableColumn<Battle, Integer> getColBattleTotalLosses() {
		return colBattleTotalLosses;
	}
	public TableColumn<Battle, Result> getColBattleResult() {
		return colBattleResult;
	}
	public ObservableList<Battle> getBattleTableContent() {
		return battleTableContent;
	}
	public Tab getBattleDetailsTab() {
		return battleDetailsTab;
	}
	public ImageView getBattleAttackerFlag() {
		return battleAttackerFlag;
	}
	public Label getBattleAttacker() {
		return battleAttacker;
	}
	public Label getBattleAttackerArmySize() {
		return battleAttackerArmySize;
	}
	public Label getBattleAttackerLosses() {
		return battleAttackerLosses;
	}
	public TableView<Unit> getAttackerUnitsTable() {
		return attackerUnitsTable;
	}
	public TableColumn<Unit, String> getColAttackerUnitType() {
		return colAttackerUnitType;
	}
	public TableColumn<Unit, Integer> getColAttackerUnitNumber() {
		return colAttackerUnitNumber;
	}
	public ObservableList<Unit> getAttackerUnitsTableContent() {
		return attackerUnitsTableContent;
	}
	public ImageView getBattleDefenderFlag() {
		return battleDefenderFlag;
	}
	public Label getBattleDefender() {
		return battleDefender;
	}
	public Label getBattleDefenderArmySize() {
		return battleDefenderArmySize;
	}
	public Label getBattleDefenderLosses() {
		return battleDefenderLosses;
	}
	public TableView<Unit> getDefenderUnitsTable() {
		return defenderUnitsTable;
	}
	public TableColumn<Unit, String> getColDefenderUnitType() {
		return colDefenderUnitType;
	}
	public TableColumn<Unit, Integer> getColDefenderUnitNumber() {
		return colDefenderUnitNumber;
	}
	public ObservableList<Unit> getDefenderUnitsTableContent() {
		return defenderUnitsTableContent;
	}
	public Label getBattleName() {
		return battleName;
	}
	public Label getBattleDate() {
		return battleDate;
	}
	public Label getBattleType() {
		return battleType;
	}
	public Label getBattleLocation() {
		return battleLocation;
	}
	public Label getBattleResult() {
		return battleResult;
	}
	public Label getBattleAttackerLeader() {
		return battleAttackerLeader;
	}
	public Label getBattleDefenderLeader() {
		return battleDefenderLeader;
	}
	public Label getBattleTotalLosses() {
		return battleTotalLosses;
	}
	public Tab getWarGoalTab() {
		return warGoalTab;
	}
	public Label getWarName2() {
		return warName2;
	}
	public TableView<WarGoal> getWarGoalTable() {
		return warGoalTable;
	}
	public TableColumn<WarGoal, String> getColWarGoalActor() {
		return colWarGoalActor;
	}
	public TableColumn<WarGoal, String> getColWarGoalReceiver() {
		return colWarGoalReceiver;
	}
	public TableColumn<WarGoal, String> getColWarGoalCasusBelli() {
		return colWarGoalCasusBelli;
	}
	public TableColumn<WarGoal, Integer> getColWarGoalStateID() {
		return colWarGoalStateID;
	}
	public TableColumn<WarGoal, String> getColWarGoalCountry() {
		return colWarGoalCountry;
	}
	public TableColumn<WarGoal, Double> getColWarGoalScore() {
		return colWarGoalScore;
	}
	public TableColumn<WarGoal, Double> getColWarGoalChange() {
		return colWarGoalChange;
	}
	public TableColumn<WarGoal, String> getColWarGoalDate() {
		return colWarGoalDate;
	}
	public TableColumn<WarGoal, Result> getColWarGoalFulfilled() {
		return colWarGoalFulfilled;
	}
	public ObservableList<WarGoal> getWarGoalTableContent() {
		return warGoalTableContent;
	}
	public ListChangeListener<War> getTableSelectionChanged() {
		return tableSelectionChanged;
	}
	public ListChangeListener<Battle> getBattleTableSelectionChanged() {
		return battleTableSelectionChanged;
	}
	public BattleDetailsTabContent getBattleTab() {
		return battleTab;
	}
	public FileLoader getFileLoader() {
		return fileLoader;
	}
	public WarDetailsTabContent getDetailsTab() {
		return detailsTab;
	}
	public Localisation getLoc() {
		return loc;
	}
	public PathLoader getPathLoader() {
		return pathLoader;
	}
	public SaveGameReader getReader() {
		return reader;
	}

}