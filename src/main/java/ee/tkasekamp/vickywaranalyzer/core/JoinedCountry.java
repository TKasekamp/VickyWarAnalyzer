package ee.tkasekamp.vickywaranalyzer.core;

/**
 * Class for storing "add_attacker", "add_defender", "rem_attacker", "rem_defender" data
 */
public class JoinedCountry {
	private String tag = "";
	private boolean isAttacker; // True is attacker, false is defender
	private String startDate = "";
	private String endDate = "";


	public JoinedCountry(String tag, boolean isAttacker, String startDate) {
		super();
		this.tag = tag;
		this.isAttacker = isAttacker;
		this.startDate = startDate;
	}


	@Override
	public String toString() {
		return "JoinedCountry [tag=" + tag + ", joinType=" + isAttacker
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public boolean isAttacker() {
		return isAttacker;
	}


	public void setAttacker(boolean attacker) {
		this.isAttacker = attacker;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


}
