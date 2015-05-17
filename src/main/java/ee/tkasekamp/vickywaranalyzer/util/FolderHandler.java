package ee.tkasekamp.vickywaranalyzer.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FolderHandler {
	public static final String PROGRAM_FILES_X86 = "C:/Program Files (x86)";
	public static final String PROGRAM_FILES = "C:/Program Files";
	public static final String STEAM = "Steam/steamapps/common";
	public static final String PARADOX_FOLDER = "Paradox Interactive";
	public static final String VICTORIA_2 = "Victoria 2";
	public static final String VICTORIA_AHD = "Victoria 2 A House Divided";
	public static final String VICTORIA_HOD = "Victoria II - A Heart of Darkness";
	public static final String SLASH = "/";
	
	public static final String PATHS = "./paths.txt";
	/**
	 * Checking if the path file exists. If not, attempts to guess the default
	 * save game and install folders.
	 * 
	 * @throws IOException
	 * 
	 */
	public static String[] getFolders() throws IOException {
		String[] paths = new String[2];
		if ((new File(PATHS)).exists()) {
			paths = readPaths();
		} else {
			paths[0] = checkSaveGameFolder();
			paths[1] = checkInstallFolder();
		}
		return paths;
	}

	/**
	 * Gets the user of the system and constructs a default save game path. If
	 * it is not found, sets the path to "".
	 */
	private static String checkSaveGameFolder() {
		String user = System.getProperty("user.name");
		String saveGameFolder = "C:/Users/" + user
				+ "/Documents/Paradox Interactive/Victoria II/save games/";
		if (new File(saveGameFolder).exists())
			return saveGameFolder;
		else
			return "";

	}

	/**
	 * Checks several places where I think the game directory could be. Starting
	 * from the newest version.
	 */
	private static String checkInstallFolder() {
		/* Heart of Darkness */
		if (new File(PROGRAM_FILES + SLASH + PARADOX_FOLDER + SLASH
				+ VICTORIA_HOD).exists()) {
			return PROGRAM_FILES + SLASH + PARADOX_FOLDER + SLASH
					+ VICTORIA_HOD;
		} else if ((new File(PROGRAM_FILES + SLASH + STEAM + SLASH
				+ VICTORIA_HOD)).exists()) {
			return PROGRAM_FILES + SLASH + STEAM + SLASH + VICTORIA_HOD;
		} else if ((new File(PROGRAM_FILES_X86 + SLASH + STEAM + SLASH
				+ VICTORIA_HOD)).exists()) {
			return PROGRAM_FILES_X86 + SLASH + STEAM + SLASH + VICTORIA_HOD;
		} else if ((new File(PROGRAM_FILES_X86 + SLASH + PARADOX_FOLDER + SLASH
				+ VICTORIA_HOD)).exists()) {
			return PROGRAM_FILES_X86 + SLASH + PARADOX_FOLDER + SLASH
					+ VICTORIA_HOD;
		}
		/* A House Divided */
		if (new File(PROGRAM_FILES + SLASH + PARADOX_FOLDER + SLASH
				+ VICTORIA_AHD).exists()) {
			return PROGRAM_FILES + SLASH + PARADOX_FOLDER + SLASH
					+ VICTORIA_AHD;
		} else if ((new File(PROGRAM_FILES + SLASH + STEAM + SLASH
				+ VICTORIA_AHD)).exists()) {
			return PROGRAM_FILES + SLASH + STEAM + SLASH + VICTORIA_AHD;
		} else if ((new File(PROGRAM_FILES_X86 + SLASH + STEAM + SLASH
				+ VICTORIA_AHD)).exists()) {
			return PROGRAM_FILES_X86 + SLASH + STEAM + SLASH + VICTORIA_AHD;
		} else if ((new File(PROGRAM_FILES_X86 + SLASH + PARADOX_FOLDER + SLASH
				+ VICTORIA_AHD)).exists()) {
			return PROGRAM_FILES_X86 + SLASH + PARADOX_FOLDER + SLASH
					+ VICTORIA_AHD;
		}
		/* Vanilla */
		if (new File(PROGRAM_FILES + SLASH + PARADOX_FOLDER + SLASH
				+ VICTORIA_2).exists()) {
			return PROGRAM_FILES + SLASH + PARADOX_FOLDER + SLASH + VICTORIA_2;
		} else if ((new File(PROGRAM_FILES + SLASH + STEAM + SLASH + VICTORIA_2))
				.exists()) {
			return PROGRAM_FILES + SLASH + STEAM + SLASH + VICTORIA_2;
		} else if ((new File(PROGRAM_FILES_X86 + SLASH + STEAM + SLASH
				+ VICTORIA_2)).exists()) {
			return PROGRAM_FILES_X86 + SLASH + STEAM + SLASH + VICTORIA_2;
		} else if ((new File(PROGRAM_FILES_X86 + SLASH + PARADOX_FOLDER + SLASH
				+ VICTORIA_2)).exists()) {
			return PROGRAM_FILES_X86 + SLASH + PARADOX_FOLDER + SLASH
					+ VICTORIA_2;
		} else {
			return "";
		}
	}

	/**
	 * Takes the path of the full path of the savegame and return the directory
	 * it was in
	 * 
	 * @param path
	 */
	private static String getDirectoryOnly(String path) {
		StringBuilder line = new StringBuilder(path);
		int index = line.lastIndexOf("/");

		line.delete(index + 1, line.length());
//		Reference.SAVEGAMEPATH = line.toString();
		return line.toString();
	}

	/**
	 * This method saves the paths so the user does not have to choose the file
	 * every time.
	 * 
	 * @throws IOException
	 */
	public static void savePaths(String saveGameFolder, String installFolder) throws IOException {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("paths.txt"), "UTF-8"));
			out.write(saveGameFolder);
			out.write("\n");
			out.write(installFolder);
			out.close();
		} catch (IOException e) {
			throw new IOException("Could not save the paths.txt.");

		}

	}

	/**
	 * Reads file {@link Constants#PATHS}. First line is the SAVEGAMEPATH,
	 * second the INSTALLPATH
	 * 
	 * @throws IOException
	 */
	private static String[] readPaths() throws IOException {
		String[] paths = new String[2];

		InputStreamReader reader = new InputStreamReader(new FileInputStream(
				PATHS), "UTF-8");
		BufferedReader scanner = new BufferedReader(reader);

		String line;
		int counter = 0;
		while ((line = scanner.readLine()) != null) {
			if (counter == 0 | counter == 1)
				paths[counter] = line;

			counter++;

		}
		scanner.close();

		return paths;
	}

}
