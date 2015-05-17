package ee.tkasekamp.vickywaranalyzer.service;

import java.io.IOException;

public class ModelServiceImpl implements ModelService {
	private String date = "";
	private String player = "";
	private String startDate = "";
	
	private UtilService utilServ;
	
	public ModelServiceImpl(UtilService utilServ) {
		this.utilServ = utilServ;
	}

	@Override
	public String createModel(String saveGamePath, boolean useLocalisation) {
		System.out.println("Save game path "+ saveGamePath);
		
		try {
			utilServ.writePathsToFile();
		} catch (IOException e) {
			return "Couldn't write to file";
		}
		return "Everything is OK";
	}

	@Override
	public String getDate() {
		return date;
	}

	@Override
	public String getPlayer() {
		return player;
	}

	@Override
	public String getStartDate() {
		return startDate;
	}

	@Override
	public void reset() {
		date = "";
		player = "";
		startDate = "";
	}

}
