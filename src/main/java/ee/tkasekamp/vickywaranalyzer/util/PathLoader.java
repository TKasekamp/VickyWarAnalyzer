package ee.tkasekamp.vickywaranalyzer.util;

import ee.tkasekamp.vickywaranalyzer.gui.GuiController;

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
	
	/** Checks several places where I think the game directory could be. Starting from the newest version
	 */
	private static void checkInstallPath() {
		/* Heart of Darkness */
		if ((new File("C:/Program Files (x86)/Paradox Interactive/Victoria II - A Heart of Darkness")).exists()) {
			Reference.INSTALLPATH = "C:/Program Files (x86)/Paradox Interactive/Victoria II - A Heart of Darkness";
		}
		else if ((new File("C:/Program Files (x86)/Steam/steamapps/common/Victoria II - A Heart of Darkness")).exists()) {
			Reference.INSTALLPATH = "C:/Program Files (x86)/Steam/steamapps/common/Victoria II - A Heart of Darkness";
		}
		else if ((new File("C:/Program Files/Paradox Interactive/Victoria II - A Heart of Darkness")).exists()) {
			Reference.INSTALLPATH = "C:/Program Files/Paradox Interactive/Victoria II - A Heart of Darkness";
		}
		/* A House Divided */
		else if ((new File("C:/Program Files (x86)/Paradox Interactive/Victoria 2 A House Divided")).exists()) {
			Reference.INSTALLPATH = "C:/Program Files (x86)/Paradox Interactive/Victoria 2 A House Divided";
		}
		else if ((new File("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2 A House Divided")).exists()) {
			Reference.INSTALLPATH = "C:/Program Files (x86)/Steam/steamapps/common/Victoria 2 A House Divided";
		}
		else if ((new File("C:/Program Files/Paradox Interactive/Victoria 2 A House Divided")).exists()) {
			Reference.INSTALLPATH = "C:/Program Files/Paradox Interactive/Victoria 2 A House Divided";
		}
		/* Vanilla */
		else if ((new File("C:/Program Files (x86)/Paradox Interactive/Victoria 2")).exists()) {
			Reference.INSTALLPATH = "C:/Program Files (x86)/Paradox Interactive/Victoria 2";
		}
		else if ((new File("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2")).exists()) {
			Reference.INSTALLPATH = "C:/Program Files (x86)/Steam/steamapps/common/Victoria 2";
		}
		else if ((new File("C:/Program Files/Paradox Interactive/Victoria 2")).exists()) {
			Reference.INSTALLPATH = "C:/Program Files/Paradox Interactive/Victoria 2";
		}
		else {
			Reference.INSTALLPATH = "";
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
		} catch (NullPointerException | IOException e) {
			controller.getErrorLabel().setText(controller.getErrorLabel().getText() + " Could not read paths.txt. ");
		}
	}


}
