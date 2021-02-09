package ee.tkasekamp.vickywaranalyzer.core;

import java.util.Arrays;

/**
 * War class. All critical info and a list about war events.
 */
public class War {

	private String name = "";
	private String originalAttacker = ""; // like EST
	private String attacker = "";
	private String originalDefender = "";
	private String defender = "";
	private String action = ""; // Date
	private boolean isActive;
	private Battle[] battleList;
	private JoinedCountry[] joinedCountryList;
	private WarGoal[] warGoalList;
	private String startDate; // Set after reading
	private String endDate = ""; // Set during reading
	private String casus_belli = ""; // Primary casus belli displayed in table. Set after reading
	/* New from HoD */
	private WarGoal originalWarGoal = new WarGoal();

	public War() {
		super();

	}

	public War(boolean isActive) {
		super();
		this.isActive = isActive;

	}

	public void setCasusBelliAndStartDate() {
		// The first one in the list has the oldest startDate
		this.startDate = joinedCountryList[0].getStartDate();
		/* Check required as only HoD uses original wargoals. Checking if it has been given values */
		if (!(originalWarGoal.getCasus_belli().equals(""))) {
			this.casus_belli = originalWarGoal.getCasus_belli();
		} else if (!(warGoalList.length == 0)) {
			this.casus_belli = warGoalList[0].getCasus_belli();
		}

	}

	/**
	 * Calculate the losses for this war. Iterates through every battle and adds the losses for the
	 * war's attacker and defender.
	 *
	 * @return <code>int[]{attackerTotalLosses, attackerTotalShipLosses, defenderTotalLosses,
	 * defenderTotalShipLosses}</code>
	 */
	public int[] getLosses() {

		int attackerTotalLosses = 0;
		int attackerTotalShipLosses = 0;
		int defenderTotalLosses = 0;
		int defenderTotalShipLosses = 0;

		/*
		 * The most complicated for loop EVER. If you want to change it, good
		 * luck. Basically it looks at the battleList and compares the attacker
		 * and defender to the countryList. It also checks if the battle was
		 * land or naval and adds them to the total.
		 */
		for (Battle battle : battleList) {
			for (JoinedCountry joinedCountry : joinedCountryList) {
				if (battle.getAttacker().equals(joinedCountry.getTag())) {
					if (joinedCountry.isAttacker()) {
						if (battle.getBattleType() == Battle.Type.LAND) {
							attackerTotalLosses += battle.getAttackerLosses();
						} else {
							attackerTotalShipLosses += battle.getAttackerLosses();
						}
					} else {
						if (battle.getBattleType() == Battle.Type.LAND) {
							defenderTotalLosses += battle.getDefenderLosses();
						} else {
							defenderTotalShipLosses += battle.getDefenderLosses();
						}
					}

				}

				if (battle.getDefender().equals(joinedCountry.getTag())) {
					if (joinedCountry.isAttacker()) {
						if (battle.getBattleType() == Battle.Type.LAND) {
							attackerTotalLosses += battle.getAttackerLosses();
						} else {
							attackerTotalShipLosses += battle.getAttackerLosses();
						}
					} else {
						if (battle.getBattleType() == Battle.Type.LAND) {
							defenderTotalLosses += battle.getDefenderLosses();
						} else {
							defenderTotalShipLosses += battle.getDefenderLosses();
						}
					}
				}
			}
		}

		return new int[]{attackerTotalLosses, attackerTotalShipLosses, defenderTotalLosses,
				defenderTotalShipLosses};
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginalAttacker() {
		return originalAttacker;
	}

	public void setOriginalAttacker(String originalAttacker) {
		this.originalAttacker = originalAttacker;
	}

	public String getOriginalDefender() {
		return originalDefender;
	}

	public void setOriginalDefender(String originalDefender) {
		this.originalDefender = originalDefender;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Battle[] getBattleList() {
		return battleList;
	}

	public void setBattleList(Battle[] battleList) {
		this.battleList = battleList;
	}

	public JoinedCountry[] getCountryList() {
		return joinedCountryList;
	}

	public void setCountryList(JoinedCountry[] countryList) {
		this.joinedCountryList = countryList;
	}

	public WarGoal[] getWarGoalList() {
		return warGoalList;
	}

	public void setWarGoalList(WarGoal[] warGoalList) {
		this.warGoalList = warGoalList;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

	public String getCasus_belli() {
		return casus_belli;
	}

	public void setCasus_belli(String casus_belli) {
		this.casus_belli = casus_belli;
	}

	public String getAttacker() {
		return attacker;
	}

	public void setAttacker(String attacker) {
		this.attacker = attacker;
	}

	public String getDefender() {
		return defender;
	}

	public void setDefender(String defender) {
		this.defender = defender;
	}

	@Override
	public String toString() {
		return "War [name=" + name + ", originalAttacker=" + originalAttacker + ", attacker=" +
				attacker + ", originalDefender=" + originalDefender + ", defender=" + defender +
				", action=" + action + ", isActive=" + isActive + ", battleList=" +
				Arrays.toString(battleList) + ", joinedCountryList=" +
				Arrays.toString(joinedCountryList) + ", warGoalList=" +
				Arrays.toString(warGoalList) + ", startDate=" + startDate + ", endDate=" + endDate +
				", casus_belli=" + casus_belli + ", originalWarGoal=" + originalWarGoal + "]";
	}

	public WarGoal getOriginalWarGoal() {
		return originalWarGoal;
	}

}
