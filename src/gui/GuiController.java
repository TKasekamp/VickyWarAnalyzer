package gui;



import static gui.WarDetailsTabContent.warDetailsTabPopulate;

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
import main.PathLoader;
import main.Reference;
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
    @FXML static Button directoryIssue;
    @FXML static Button saveGameIssue;
    @FXML static Button startIssue;
    /* Settings tab labels */
    @FXML static Label errorLabel;
    /* Settings tab textfields */
    @FXML static TextField installTextField;
    @FXML static TextField saveGameTextField;
    @FXML static CheckBox localisationCheck; // Checked in Localisation
    
    /* Saveame tab labels */
    @FXML
    static Label playerLabel;
    @FXML
    static Label startDateLabel;
    @FXML
    static Label currentDateLabel;
    /* Savegame tab toolbar buttons */
    @FXML
    static Button showAllWarsIssue;
    @FXML
    static Button showPreviousWarsIssue;
    @FXML
    static Button showActiveWarsIssue;    
    @FXML
    static Button showMyWarsIssue;

    @FXML
    static ListView<Label> selectCountryIssue;
    
    /* Savegame tab war table */
    @FXML
    static TableView<War> warTable;
    @FXML
    static TableColumn<War, String> colNameWar;
    @FXML
    static TableColumn<War, String> colAttackerWar;
    @FXML
    static TableColumn<War, String> colDefenderWar;
    @FXML
    static TableColumn<War, String> colCasusBelliWar;
    @FXML
    static TableColumn<War, String> colStartDateWar;
    @FXML
    static TableColumn<War, String> colEndDateWar;
    static final ObservableList<War> warTableContent = FXCollections.observableArrayList();
       
    /* War details tab */
    @FXML
    static Tab warDetailsTab;
    /* Center box labels */
    @FXML
    static Label warNameLabel;
    @FXML
    static Label warStartDateLabel;
    @FXML
    static Label warEndDateLabel;
    @FXML
    static Label warActionLabel;
    @FXML
    static Label warTotalLossesLabel;
    @FXML
    static Label warTotalShipLossesLabel;
    @FXML
    static Label warHasEndedLabel;
    /* Original wargoal */
    @FXML static Label warGoalActorLabel;
    @FXML static Label warGoalReceiverLabel;
    @FXML static Label warGoalCBLabel; //Casus belli
    @FXML static Label warGoalStateLabel;
    @FXML static Label warGoalCountryLabel;
    @FXML static Label warGoalDateLabel;
    @FXML static Label warGoalScoreLabel;
    @FXML static Label warGoalChangeLabel;
    @FXML static Label warGoalFulfilledLabel;
    
    @FXML static Label warGoalDateHelper;
    @FXML static Label warGoalScoreHelper;
    @FXML static Label warGoalChangeHelper;
    @FXML static Label warGoalFulfilledHelper;
    

    /* Attacker box */
    @FXML static ImageView attackerFlag; 
    @FXML static Label warAttacker;
    @FXML static Label warAttackerHelper; // This is not changed, only hidden 
    @FXML static Label originalAttackerLabel;
    @FXML
    static Label attackerTotalLossesLabel;
    @FXML
    static Label attackerTotalShipLossesLabel;  
    @FXML
    static TableView<ObservableJoinedCountry> attackerTable;
    @FXML
    static TableColumn<ObservableJoinedCountry, String> colAttackerName;
    @FXML
    static TableColumn<ObservableJoinedCountry, ImageView> colAttackerFlag;
    @FXML
    static TableColumn<ObservableJoinedCountry, String> colAttackerStartDate; // Join and start are the same
    @FXML
    static TableColumn<ObservableJoinedCountry, String> colAttackerEndDate;   
    static final ObservableList<ObservableJoinedCountry> attackerTableContent = FXCollections.observableArrayList(); // List managing the attackerTable
    
    /* Defender box */
    @FXML
    static ImageView defenderFlag;   
    @FXML static Label warDefender;
    @FXML static Label warDefenderHelper; // This is not changed, only hidden 
    @FXML
    static Label originalDefenderLabel;
    @FXML
    static Label defenderTotalLossesLabel;
    @FXML
    static Label defenderTotalShipLossesLabel;  
    @FXML
    static TableView<ObservableJoinedCountry> defenderTable;
    @FXML
    static TableColumn<ObservableJoinedCountry, String> colDefenderName;
    @FXML
    static TableColumn<ObservableJoinedCountry, ImageView> colDefenderFlag;
    @FXML
    static TableColumn<ObservableJoinedCountry, String> colDefenderStartDate;
    @FXML
    static TableColumn<ObservableJoinedCountry, String> colDefenderEndDate;  
    static final ObservableList<ObservableJoinedCountry> defenderTableContent = FXCollections.observableArrayList(); // List managing the attackerTable
    
    /* WAR_NAME details tab battle table */
    @FXML
    static TableView<Battle> battleTable;
    @FXML
    static TableColumn<Battle, String> colNameBattle;
    @FXML
    static TableColumn<Battle, String> colAttackerBattle;
    @FXML
    static TableColumn<Battle, String> colDefenderBattle;
    @FXML
    static TableColumn<Battle, String> colDateBattle;
    @FXML
    static TableColumn<Battle, Type> colTypeBattle; 
    @FXML
    static TableColumn<Battle, Integer> colBattleTotalLosses;
    @FXML static TableColumn<Battle, Result> colBattleResult; 
	static final ObservableList<Battle> battleTableContent = FXCollections.observableArrayList();
	
	/* Battle details tab */
    @FXML static Tab battleDetailsTab;
    
	/* Attacker side */
	@FXML static ImageView battleAttackerFlag;
    @FXML static Label battleAttacker;
    @FXML static Label battleAttackerArmySize;
    @FXML static Label battleAttackerLosses;
    @FXML static TableView<Unit> attackerUnitsTable;
    @FXML static TableColumn<Unit, String> colAttackerUnitType; 
    @FXML static TableColumn<Unit, Integer> colAttackerUnitNumber; 
	static final ObservableList<Unit> attackerUnitsTableContent = FXCollections.observableArrayList();
	
	/* Defender side */
	@FXML static ImageView battleDefenderFlag;
    @FXML static Label battleDefender;
    @FXML static Label battleDefenderArmySize;
    @FXML static Label battleDefenderLosses;
    @FXML static TableView<Unit> defenderUnitsTable;
    @FXML static TableColumn<Unit, String> colDefenderUnitType; 
    @FXML static TableColumn<Unit, Integer> colDefenderUnitNumber; 
	static final ObservableList<Unit> defenderUnitsTableContent = FXCollections.observableArrayList();  
	
	/* Center panel with other information */
	@FXML static Label battleName;
	@FXML static Label battleDate;
	@FXML static Label battleType;
	@FXML static Label battleLocation;
	@FXML static Label battleResult;
    @FXML static Label battleAttackerLeader;
    @FXML static Label battleDefenderLeader;
	@FXML static Label battleTotalLosses;
	
	/* Wargoal tab */
    @FXML static Tab warGoalTab;
    @FXML static Label warName2;
    
    /* War goal table */
    @FXML static TableView<WarGoal> warGoalTable;
    @FXML static TableColumn<WarGoal, String> colWarGoalActor;
    @FXML static TableColumn<WarGoal, String> colWarGoalReceiver;
    @FXML static TableColumn<WarGoal, String> colWarGoalCasusBelli; // Join and start are the same
    @FXML static TableColumn<WarGoal, Integer> colWarGoalStateID;  
    @FXML static TableColumn<WarGoal, String> colWarGoalCountry; 
    @FXML static TableColumn<WarGoal, Double> colWarGoalScore;
    @FXML static TableColumn<WarGoal, Double> colWarGoalChange;
    @FXML static TableColumn<WarGoal, String> colWarGoalDate;
    @FXML static TableColumn<WarGoal, Result> colWarGoalFulfilled;
    
    static final ObservableList<WarGoal> warGoalTableContent = FXCollections.observableArrayList(); // List manging the warGoalTable 
    
    
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	
    	/* Checking if paths exist */
    	PathLoader.pathLoader();
    	setSettingsTextFields();
    	
        /* Listening to selections in list */
        selectCountryIssue.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener<Label>() {

			@Override
			public void changed(ObservableValue<? extends Label> arg0,
					Label arg1, Label arg2) {
				FileLoader.warTableShowCountry(arg2.getText());
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
        FileLoader.setWarTableColumnValues();
        WarDetailsTabContent.setColumnValues();
        BattleDetailsTabContent.setColumnValues();
    
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
    	FileLoader.readLauncher();
    	
    	/* Saving the used directories to a file. Starting in a new thread because this is what I'm learning at the moment.  */
    	Thread thread = new Thread(new Runnable() {
    		public void run()
    		{
    			PathLoader.savePaths();
    		}
    	});
    	// start the thread
    	thread.start(); 

    	/* Setting wargoal table values. It must be done AFTER the file has been read as the column values are dependent on the version
    	 * of the game */
    	WarDetailsTabContent.setWarGoalTabColumnValues();
    	

    }
    /* War tab Button actions */
    public final void showAllWarsIssue(ActionEvent event) {
    	FileLoader.warTablePopulateAll();
    }
    public final void showActiveWarsIssue(ActionEvent event) {
    	// All the action happens in readLauncher to make this method as small as possible
    	FileLoader.warTableShowActive();
    }
    
    public final void showPreviousWarsIssue(ActionEvent event) {
    	// All the action happens in readLauncher to make this method as small as possible
    	FileLoader.warTableShowPrevious();
    }
    public final void showMyWarsIssue(ActionEvent event) {
    	// All the action happens in readLauncher to make this method as small as possible
    	FileLoader.warTableShowMyWars();
    }
    /** warTable selection listener */
    private final ListChangeListener<War> tableSelectionChanged = new ListChangeListener<War>() {
                @Override
                public void onChanged(Change<? extends War> c) {
                	// At the moment does nothing as I don't know how to get mouse doubleclick on row
                	// But it is used by showMoreDetailsWarIssue
                	if (!warTable.getSelectionModel().getSelectedItems().isEmpty()) {
                		warDetailsTabPopulate((War)warTable.getSelectionModel().getSelectedItems().toArray()[0]);
                		} 
                	}             	
                
            };
            
    /** Battle table selection listener in the war details tab*/
    private final ListChangeListener<Battle> battleTableSelectionChanged = new ListChangeListener<Battle>() {
    	@Override
    	public void onChanged(Change<? extends Battle> c) {
    		if (!battleTable.getSelectionModel().getSelectedItems().isEmpty()) {
    			battleDetailsTab.setDisable(false);
        		BattleDetailsTabContent.battleDetailsTabPopulate((Battle)battleTable.getSelectionModel().getSelectedItems().toArray()[0]);    			
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
    
    /* Somw of these are used, some aren't. Some static fields don't have getters. */
	public static Button getDirectoryIssue() {
		return directoryIssue;
	}
	public static Button getSaveGameIssue() {
		return saveGameIssue;
	}
	public static Button getStartIssue() {
		return startIssue;
	}
	public static Label getErrorLabel() {
		return errorLabel;
	}
	public static TextField getInstallTextField() {
		return installTextField;
	}
	public static TextField getSaveGameTextField() {
		return saveGameTextField;
	}
	public static Label getPlayerLabel() {
		return playerLabel;
	}
	public static Label getStartDateLabel() {
		return startDateLabel;
	}
	public static Label getCurrentDateLabel() {
		return currentDateLabel;
	}
	public static Button getShowAllWarsIssue() {
		return showAllWarsIssue;
	}
	public static Button getShowPreviousWarsIssue() {
		return showPreviousWarsIssue;
	}
	public static Button getShowActiveWarsIssue() {
		return showActiveWarsIssue;
	}
	public static Button getShowMyWarsIssue() {
		return showMyWarsIssue;
	}
	public static ListView<Label> getSelectCountryIssue() {
		return selectCountryIssue;
	}
	public static TableView<War> getWarTable() {
		return warTable;
	}
	public static TableColumn<War, String> getColNameWar() {
		return colNameWar;
	}
	public static TableColumn<War, String> getColAttackerWar() {
		return colAttackerWar;
	}
	public static TableColumn<War, String> getColDefenderWar() {
		return colDefenderWar;
	}
	public static TableColumn<War, String> getColCasusBelliWar() {
		return colCasusBelliWar;
	}
	public static TableColumn<War, String> getColStartDateWar() {
		return colStartDateWar;
	}
	public static TableColumn<War, String> getColEndDateWar() {
		return colEndDateWar;
	}
	public static ObservableList<War> getWarTableContent() {
		return warTableContent;
	}
	public static Tab getWarDetailsTab() {
		return warDetailsTab;
	}
	public static Label getWarNameLabel() {
		return warNameLabel;
	}
	public static Label getWarStartDateLabel() {
		return warStartDateLabel;
	}
	public static Label getWarEndDateLabel() {
		return warEndDateLabel;
	}
	public static Label getWarActionLabel() {
		return warActionLabel;
	}
	public static Label getWarTotalLossesLabel() {
		return warTotalLossesLabel;
	}
	public static Label getWarTotalShipLossesLabel() {
		return warTotalShipLossesLabel;
	}
	public static ImageView getAttackerFlag() {
		return attackerFlag;
	}
	public static Label getOriginalAttackerLabel() {
		return originalAttackerLabel;
	}
	public static Label getAttackerTotalLossesLabel() {
		return attackerTotalLossesLabel;
	}
	public static Label getAttackerTotalShipLossesLabel() {
		return attackerTotalShipLossesLabel;
	}
	public static ImageView getDefenderFlag() {
		return defenderFlag;
	}
	public static Label getOriginalDefenderLabel() {
		return originalDefenderLabel;
	}
	public static Label getDefenderTotalLossesLabel() {
		return defenderTotalLossesLabel;
	}
	public static Label getDefenderTotalShipLossesLabel() {
		return defenderTotalShipLossesLabel;
	}
	public static TableView<Battle> getBattleTable() {
		return battleTable;
	}
	public static TableColumn<Battle, String> getColNameBattle() {
		return colNameBattle;
	}
	public static TableColumn<Battle, String> getColAttackerBattle() {
		return colAttackerBattle;
	}
	public static TableColumn<Battle, String> getColDefenderBattle() {
		return colDefenderBattle;
	}
	public static TableColumn<Battle, String> getColDateBattle() {
		return colDateBattle;
	}
	public static TableColumn<Battle, Type> getColTypeBattle() {
		return colTypeBattle;
	}
	public static TableColumn<Battle, Integer> getColBattleTotalLosses() {
		return colBattleTotalLosses;
	}
	public static ObservableList<Battle> getBattleTableContent() {
		return battleTableContent;
	}
	public ListChangeListener<War> getTableSelectionChanged() {
		return tableSelectionChanged;
	}
	public static Label getWarHasEndedLabel() {
		return warHasEndedLabel;
	}
	public static TableView<ObservableJoinedCountry> getAttackerTable() {
		return attackerTable;
	}
	public static TableColumn<ObservableJoinedCountry, String> getColAttackerName() {
		return colAttackerName;
	}
	public static TableColumn<ObservableJoinedCountry, ImageView> getColAttackerFlag() {
		return colAttackerFlag;
	}
	public static TableColumn<ObservableJoinedCountry, String> getColAttackerStartDate() {
		return colAttackerStartDate;
	}
	public static TableColumn<ObservableJoinedCountry, String> getColAttackerEndDate() {
		return colAttackerEndDate;
	}
	public static ObservableList<ObservableJoinedCountry> getAttackerTableContent() {
		return attackerTableContent;
	}
	public static TableView<ObservableJoinedCountry> getDefenderTable() {
		return defenderTable;
	}
	public static TableColumn<ObservableJoinedCountry, String> getColDefenderName() {
		return colDefenderName;
	}
	public static TableColumn<ObservableJoinedCountry, ImageView> getColDefenderFlag() {
		return colDefenderFlag;
	}
	public static TableColumn<ObservableJoinedCountry, String> getColDefenderStartDate() {
		return colDefenderStartDate;
	}
	public static TableColumn<ObservableJoinedCountry, String> getColDefenderEndDate() {
		return colDefenderEndDate;
	}
	public static ObservableList<ObservableJoinedCountry> getDefenderTableContent() {
		return defenderTableContent;
	}
	public static TableView<WarGoal> getWarGoalTable() {
		return warGoalTable;
	}
	public static TableColumn<WarGoal, String> getColWarGoalActor() {
		return colWarGoalActor;
	}
	public static TableColumn<WarGoal, String> getColWarGoalCasusBelli() {
		return colWarGoalCasusBelli;
	}
	public static TableColumn<WarGoal, String> getColWarGoalCountry() {
		return colWarGoalCountry;
	}
	public static ObservableList<WarGoal> getWarGoalTableContent() {
		return warGoalTableContent;
	}
	public static TableColumn<WarGoal, String> getColWarGoalReceiver() {
		return colWarGoalReceiver;
	}
	public static TableColumn<WarGoal, Integer> getColWarGoalStateID() {
		return colWarGoalStateID;
	}
    public static TableColumn<Battle, Result> getColBattleResult() {
		return colBattleResult;
	}
	public static TableColumn<WarGoal, Double> getColWarGoalScore() {
		return colWarGoalScore;
	}
	public static TableColumn<WarGoal, Double> getColWarGoalChange() {
		return colWarGoalChange;
	}
	public static TableColumn<WarGoal, String> getColWarGoalDate() {
		return colWarGoalDate;
	}
	public static TableColumn<WarGoal, Result> getColWarGoalFulfilled() {
		return colWarGoalFulfilled;
	}
	public static CheckBox getLocalisationCheck() {
		return localisationCheck;
	}

}