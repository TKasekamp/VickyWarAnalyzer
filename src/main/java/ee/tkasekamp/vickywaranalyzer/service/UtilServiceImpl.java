package ee.tkasekamp.vickywaranalyzer.service;

import java.io.IOException;

import ee.tkasekamp.vickywaranalyzer.util.FolderHandler;

public class UtilServiceImpl implements UtilService {
	// Have to be static so they can be saved at the end
	private String saveGameFolder = "";
	private String installFolder = "";

	@Override
	public void guessFolders() throws IOException {
		String [] folders = FolderHandler.getFolders();
		saveGameFolder = folders[0];
		installFolder = folders[1];


	}
	
	@Override
	public void writePathsToFile() throws IOException {
		FolderHandler.savePaths(saveGameFolder, installFolder);
		
	}

	@Override
	public String getSaveGameFolder() {
		return saveGameFolder;
	}

	@Override
	public String getInstallFolder() {
		return installFolder;
	}

	@Override
	public void reset() {

	}

	@Override
	public void setSaveGameFolder(String pathToFolder) {
		this.saveGameFolder = pathToFolder;
	}

	@Override
	public void setInstallFolder(String pathToFolder) {
		this.installFolder = pathToFolder;
	}



}
