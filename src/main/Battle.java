package main;

import java.util.Arrays;
//import static gui.GuiController.getErrorLabel;

public class Battle {
	private String date = "";
	private String name = "";
	private int location;
	private Result res; // To display in the table
	private Type battleType = Type.LAND; // Default value
	private int totalLosses = 0;
	// Attacker 
	private String attacker = ""; // Not shown, used to identfy 
	private String attackerOfficial =""; // Full name
	private String leaderAttacker = "";
	private Unit [] attackerUnits; // There are only 20 different unit types in total
	private int attackerLosses = 0; 
	// Defender
	private String defender =""; // Not shown, used to identfy 
	private String defenderOfficial = "";
	private String leaderDefender ="";
	private Unit [] defenderUnits; // There are only 20 different unit types in total
	private int defenderLosses = 0; 
	
	
	
	public Battle(String date, String name) {
		super();
		this.date = date;
		this.name = name;
	}
	

	/** Battle result. Yes or no. 
	 * Used in wargoal aswell because it also has yes and no states. */
	public static enum Result {
		YES, NO 
	}
	/** Battle result. Land or naval. */
	public static enum Type {
		LAND, NAVAL
	}
	@Override
	public String toString() {
		return "Battle [date=" + date + ", name=" + name + ", location="
				+ location + ", res=" + res + ", battleType=" + battleType
				+ ", totalLosses=" + totalLosses + ", attacker=" + attacker
				+ ", leaderAttacker=" + leaderAttacker + ", attackerUnits="
				+ Arrays.toString(attackerUnits) + ", attackerLosses="
				+ attackerLosses + ", defender=" + defender
				+ ", leaderDefender=" + leaderDefender + ", defenderUnits="
				+ Arrays.toString(defenderUnits) + ", defenderLosses="
				+ defenderLosses + "]";
	}
	/** Checks if any of the units are ships. If they are, sets the battleType to true.
	 * Also sets the Result.
	 * 
	 */
	public void determineType() {
		/* Some battles have no attacker units. Catching these strange battles */
		String unit = null;
		try {	
			if (!(attackerUnits.length == 0) || !(defenderUnits.length == 0)) {
				unit = attackerUnits[0].getType();		
				for (String ship : Reference.navalUnitsList) {
				if (unit.equals(ship)) {
					setBattleType(Type.NAVAL);
				}
			}				
			}
		}
		catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
			// Had problems at this point, so I'll leave the catch in place
			// Throwing an error text won't actually help the user in fixing this
//			getErrorLabel().setText(getErrorLabel().getText() + " Something went wrong in determining the battle type. Error " + e + unit);
		}


	}
	/** Finding the official attacker and defender names for this battle */
	public void setOfficialNames() {
		defenderOfficial = Country.findOfficalName(defender);
		attackerOfficial = Country.findOfficalName(attacker);
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public String getAttacker() {
		return attacker;
	}
	public void setAttacker(String attacker) {
		this.attacker = attacker;
	}
	public String getLeaderAttacker() {
		return leaderAttacker;
	}
	public void setLeaderAttacker(String leaderAttacker) {
		this.leaderAttacker = leaderAttacker;
	}
	public Unit[] getAttackerUnits() {
		return attackerUnits;
	}
	public void setAttackerUnits(Unit[] attackerUnits) {
		this.attackerUnits = attackerUnits;
	}
	public String getDefender() {
		return defender;
	}
	public void setDefender(String defender) {
		this.defender = defender;
	}
	public String getLeaderDefender() {
		return leaderDefender;
	}
	public void setLeaderDefender(String leaderDefender) {
		this.leaderDefender = leaderDefender;
	}
	public Unit[] getDefenderUnits() {
		return defenderUnits;
	}
	public void setDefenderUnits(Unit[] defenderUnits) {
		this.defenderUnits = defenderUnits;
	}
	public int getAttackerLosses() {
		return attackerLosses;
	}
	public void setAttackerLosses(int attackerLosses) {
		this.attackerLosses = attackerLosses;
	}
	public int getDefenderLosses() {
		return defenderLosses;
	}
	public void setDefenderLosses(int defenderLosses) {
		this.defenderLosses = defenderLosses;
	}

	public int getTotalLosses() {
		return totalLosses;
	}

	public void setTotalLosses(int totalLosses) {
		this.totalLosses = totalLosses;
	}

	public Type getBattleType() {
		return battleType;
	}

	public void setBattleType(Type battleType) {
		this.battleType = battleType;
	}

	public Result getRes() {
		return res;
	}

	public void setRes(Result res) {
		this.res = res;
	}
	public String getAttackerOfficial() {
		return attackerOfficial;
	}
	public String getDefenderOfficial() {
		return defenderOfficial;
	}
	public void setAttackerOfficial(String attackerOfficial) {
		this.attackerOfficial = attackerOfficial;
	}
	public void setDefenderOfficial(String defenderOfficial) {
		this.defenderOfficial = defenderOfficial;
	}
	
	
	
}
