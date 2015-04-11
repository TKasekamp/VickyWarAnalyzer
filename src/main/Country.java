package main;

import static main.Reference.countryList;
import javafx.scene.image.Image;

/** Holds the flag and a longer name than just three letters.   
 * There will only be one instance of this class for each country as opposed to <code>JoinedCountry</code>, 
 * which has many instances.*/
public class Country {
	private String tag = "";
	private String officialName = "";
	private Image flag;
	
	public Country(String tag) {
		super();
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "Country [tag=" + tag + ", officialName=" + officialName
				+ ", flag=" + flag + "]";
	}
	
	/** Returns an official name for the given tag. If not found, the same tag is returned (but this is unlikely) */
	public static String findOfficalName(String tag) {
		for (Country country: countryList) { 
			if (country.getTag().equals(tag)) {
				return country.getOfficialName();
			}}
		return tag;
		
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
