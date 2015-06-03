package ee.tkasekamp.vickywaranalyzer.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ObservableJoinedCountry {
	private String officialName = "";
	private ImageView flag;
	private String joinDate = "";
	private String endDate = "";

	public ObservableJoinedCountry(String officialName, Image flag, String joinDate, String endDate) {
		super();
		this.officialName = officialName;
		this.flag = new ImageView(flag);
		this.joinDate = joinDate;
		this.endDate = endDate;
		this.flag.setFitWidth(32); // 30 to 35 look good
		this.flag.setPreserveRatio(true);
		this.flag.setSmooth(true);
		this.flag.setCache(true);
	}

	public String getOfficialName() {
		return officialName;
	}

	public ImageView getFlag() {
		return flag;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public String getEndDate() {
		return endDate;
	}
}
