package ee.tkasekamp.vickywaranalyzer.service;

import java.io.IOException;

public interface UtilService {
	/**
	 * Try to find the install and save game folders.
	 * 
	 * @throws IOException
	 */
	public void guessFolders() throws IOException;
	
	public String getSaveGameFolder();
	public String getInstallFolder();
	
}
