package ee.tkasekamp.vickywaranalyzer.service;

import java.io.IOException;

import ee.tkasekamp.vickywaranalyzer.util.PathLoader;

public class UtilServiceImpl implements UtilService {
	private String saveGameFolder = "";
	private String installFolder = "";

	@Override
	public void guessFolders() throws IOException {
		String [] folders = PathLoader.getFolders();
		saveGameFolder = folders[0];
		installFolder = folders[1];


	}

	@Override
	public String getSaveGameFolder() {
		return saveGameFolder;
	}

	@Override
	public String getInstallFolder() {
		return installFolder;
	}

}
