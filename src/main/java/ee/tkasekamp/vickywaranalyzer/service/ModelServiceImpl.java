package ee.tkasekamp.vickywaranalyzer.service;

public class ModelServiceImpl implements ModelService {
	private String date = "";
	private String player = "";
	private String startDate = "";

	@Override
	public String parseSaveGame(String saveGamePath) {
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
