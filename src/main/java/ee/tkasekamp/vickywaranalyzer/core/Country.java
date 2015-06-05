package ee.tkasekamp.vickywaranalyzer.core;

import javafx.scene.image.Image;

/**
 * Holds the flag and a longer name than just three letters.
 * There will only be one instance of this class for each country as opposed to <code>JoinedCountry</code>,
 * which has many instances.
 */
public class Country {
	private String tag = "";
	private String officialName = "";
	private Image flag;

	public Country(String tag) {
		super();
		this.tag = tag;
		this.officialName = tag; // By default
	}

	@Override
	public String toString() {
		return "Country [tag=" + tag + ", officialName=" + officialName
				+ ", flag=" + flag + "]";
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getOfficialName() {
		return officialName;
	}

	public void setOfficialName(String officialName) {
		this.officialName = officialName;
	}

	public Image getFlag() {
		return flag;
	}

	public void setFlag(Image flag) {
		this.flag = flag;
	}

}
