package main;

import main.Battle.Result;

public class WarGoal {
	/* Definitions are directly from save file */
	private int state_province_id = 0;
	private String casus_belli = "";
	private String country="";
	private String actor="";
	private String receiver="";
	/* New from HoD */
	private String date = "";
	private double score = 0;
	private double change = 0;
	private Result fulfilled = Result.NO;
	
	
	public WarGoal(int state_province_id) {
		super();
		this.state_province_id = state_province_id;
	}
	public WarGoal() {
		super();
	}
	
	@Override
	public String toString() {
		return "WarGoal [state_province_id=" + state_province_id
				+ ", casus_belli=" + casus_belli + ", country=" + country
				+ ", actor=" + actor + ", receiver=" + receiver + ", date="
				+ date + ", score=" + score + ", change=" + change
				+ ", fulfilled=" + fulfilled + "]";
	}
	public int getState_province_id() {
		return state_province_id;
	}
	public void setState_province_id(int state_province_id) {
		this.state_province_id = state_province_id;
	}
	public String getCasus_belli() {
		return casus_belli;
	}
	public void setCasus_belli(String casus_belli) {
		this.casus_belli = casus_belli;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
	public Result getFulfilled() {
		return fulfilled;
	}
	public void setFulfilled(Result fulfilled) {
		this.fulfilled = fulfilled;
	}

	
}
