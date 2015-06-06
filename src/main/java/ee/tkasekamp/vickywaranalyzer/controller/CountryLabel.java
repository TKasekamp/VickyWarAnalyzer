package ee.tkasekamp.vickywaranalyzer.controller;

import ee.tkasekamp.vickywaranalyzer.core.Country;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * Created by Tonis on 6.06.2015.
 */
public class CountryLabel extends Label {

	private String tag;

	public CountryLabel(Country country) {
		super(country.getOfficialName());
		tag = country.getTag();

		ImageView iv2 = new ImageView(country.getFlag());
		iv2.setFitWidth(32); // 30 to 35 looks good
		iv2.setPreserveRatio(true);
		iv2.setSmooth(true);
		iv2.setCache(true);

		setGraphic(iv2);
		setContentDisplay(ContentDisplay.LEFT);

	}

	public String getTag() {
		return tag;
	}
}
