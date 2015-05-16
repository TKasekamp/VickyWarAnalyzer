package ee.tkasekamp.vickywaranalyzer.util;

import ee.tkasekamp.vickywaranalyzer.gui.GuiController;
import static ee.tkasekamp.vickywaranalyzer.util.Constants.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/** Class for handling savegame path reading, finding and loading from a file 
 */
public class PathLoader {
	private GuiController controller;
	
	public PathLoader(GuiController controller) {
		this.controller = controller;
	}
	
	
	/** Checking if the path file exists. If not, attempts to construct 
	 * the default save game paths. If the file exist, reads from it.
	 * 
	 */
	public void pathLoader() {
		if ((new File("./paths.txt")).exists()) {
			readPaths();
		}
		else { 
			checkSaveGamePath();
			checkInstallPath();	
		}
	}
	/** Gets the user of the system and constructs a default save game path.
	 * If it is not found, sets the path to "". 
	 */
	private static void checkSaveGamePath() {
		String user = System.getProperty("user.name");
		Reference.SAVEGAMEPATH = "C:/Users/" + user + "/Documents/Paradox Interactive/Victoria II/save games/";
		if (!(new File(Reference.SAVEGAMEPATH)).exists()) {
			Reference.SAVEGAMEPATH = "";
		}
	}
	
	/** Checks several places where I think the game directory could be. Starting from the newest version.
	 */
	private String checkInstallPath() {
		/* Heart of Darkness */
		if (new File(PROGRAM_FILES + SLASH + PARADOX_FOLDER + SLASH + VICTORIA_HOD).exists()) {
			return PROGRAM_FILES + SLASH + PARADOX_FOLDER + SLASH + VICTORIA_HOD;
		}
		else if ((new File(PROGRAM_FILES + SLASH + STEAM + SLASH + VICTORIA_HOD)).exists()) {
			return PROGRAM_FILES + SLASH + STEAM + SLASH + VICTORIA_HOD;
		}
		else if ((new File(PROGRAM_FILES_X86 + SLASH + STEAM + SLASH + VICTORIA_HOD)).exists()) {
			return PROGRAM_FILES_X86 + SLASH + STEAM + SLASH + VICTORIA_HOD;
		}
		else if ((new File(PROGRAM_FILES_X86 + SLASH + PARADOX_FOLDER + SLASH + VICTORIA_HOD)).exists()) {
			return PROGRAM_FILES_X86 + SLASH + PARADOX_FOLDER + SLASH + VICTORIA_HOD;
		}
		/* A House Divided */
		if (new File(PROGRAM_FILES + SLASH + PARADOX_FOLDER + SLASH + VICTORIA_AHD).exists()) {
			return PROGRAM_FILES + SLASH + PARADOX_FOLDER + SLASH + VICTORIA_AHD;
		}
		else if ((new File(PROGRAM_FILES + SLASH + STEAM + SLASH + VICTORIA_AHD)).exists()) {
			return PROGRAM_FILES + SLASH + STEAM + SLASH + VICTORIA_AHD;
		}
		else if ((new File(PROGRAM_FILES_X86 + SLASH + STEAM + SLASH + VICTORIA_AHD)).exists()) {
			return PROGRAM_FILES_X86 + SLASH + STEAM + SLASH + VICTORIA_AHD;
		}
		else if ((new File(PROGRAM_FILES_X86 + SLASH + PARADOX_FOLDER + SLASH + VICTORIA_AHD)).exists()) {
			return PROGRAM_FILES_X86 + SLASH + PARADOX_FOLDER + SLASH + VICTORIA_AHD;
		}
		/* Vanilla */
		if (new File(PROGRAM_FILES + SLASH + PARADOX_FOLDER + SLASH + VICTORIA_2).exists()) {
			return PROGRAM_FILES + SLASH + PARADOX_FOLDER + SLASH + VICTORIA_2;
		}
		else if ((new File(PROGRAM_FILES + SLASH + STEAM + SLASH + VICTORIA_2)).exists()) {
			return PROGRAM_FILES + SLASH + STEAM + SLASH + VICTORIA_2;
		}
		else if ((new File(PROGRAM_FILES_X86 + SLASH + STEAM + SLASH + VICTORIA_2)).exists()) {
			return PROGRAM_FILES_X86 + SLASH + STEAM + SLASH + VICTORIA_2;
		}
		else if ((new File(PROGRAM_FILES_X86 + SLASH + PARADOX_FOLDER + SLASH + VICTORIA_2)).exists()) {
			return PROGRAM_FILES_X86 + SLASH + PARADOX_FOLDER + SLASH + VICTORIA_2;
		}
		else {
			return "";
		}
	}
	/** Takes the path of the full path of the savegame and return
	 * the directory it was in 
	 * @param path
	 */
	private static String getDirectoryOnly(String path) {
		StringBuilder line = new StringBuilder(path);
		int index = line.lastIndexOf("/");

		line.delete(index + 1, line.length());
		Reference.SAVEGAMEPATH = line.toString();
		return line.toString();
	}
	
	/** This method saves the paths so the user does not have
	 * to choose the file every time. Saved after every loading of a savefile
	 * in GuiController.startIssueFired
	 */
	public void savePaths() {
			try {
//				FileWriter out = new FileWriter("paths.txt");
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("paths.txt"),"UTF-8"));
				out.write(getDirectoryOnly(Reference.saveGameFile));
				out.write("\n");
				out.write(Reference.INSTALLPATH);
				out.close();
			} catch (IOException e) {
				 controller.getErrorLabel().setText(controller.getErrorLabel().getText() + " Could not save the paths.txt.");
			}
			
	}
	/** Reads file ./paths.txt.
	 * First line is the SAVEGAMEPATH, second the INSTALLPATH
	 */
	private void readPaths() {
		try {
			/* Lifted from saveGameReader */
			InputStreamReader reader = new InputStreamReader(new FileInputStream("./paths.txt"), "UTF-8"); // This encoding seems to work for õ
			BufferedReader scanner = new BufferedReader(reader);
			
			String line;
			while ((line = scanner.readLine()) != null) {
				/* Simple solution to only give value if the paths are empty */
				if (Reference.SAVEGAMEPATH.equals("")){
					Reference.SAVEGAMEPATH = line;
				}
				else if (Reference.INSTALLPATH.equals("")){
					Reference.INSTALLPATH = line;
				}
			}
			scanner.close();
		} catch (IOException e) {
			controller.getErrorLabel().setText(controller.getErrorLabel().getText() + " Could not read paths.txt. ");
		}
	}


}
