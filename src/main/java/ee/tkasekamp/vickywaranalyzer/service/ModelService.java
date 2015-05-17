package ee.tkasekamp.vickywaranalyzer.service;

public interface ModelService {
	public String parseSaveGame(String saveGamePath);
	
	public String getDate();
	
	public String getPlayer();
	public String getStartDate();

	public void reset();
}
