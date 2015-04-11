package ee.tkasekamp.vickywargame.core;

/**
 * Class for storing "add_attacker", "add_defender", "rem_attacker", "rem_defender" data
 */
public class JoinedCountry {
	private String tag ="";
	private boolean joinType; // True is attacker, false is defender
	private String startDate ="";
	private String endDate ="";
	
	
	public JoinedCountry(String tag, boolean joinType, String startDate) {
		super();
		this.tag = tag;
		this.joinType = joinType;
		this.startDate = startDate;
	}


	@Override
	public String toString() {
		return "JoinedCountry [tag=" + tag + ", joinType=" + joinType
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public boolean isJoinType() {
		return joinType;
	}


	public void setJoinType(boolean joinType) {
		this.joinType = joinType;
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
