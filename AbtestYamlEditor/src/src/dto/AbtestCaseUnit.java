package src.dto;

/**
 * ABテストの1ケースを表現するDTO
 * @author sakaisyota
 *
 */
public class AbtestCaseUnit {

	/** ケースID */
	private String caseId;
	/** 割合 */
	private int ratio;

	public String getPatternId() {
		return caseId;
	}
	public void setPatternId(String patternId) {
		this.caseId = patternId;
	}
	public int getRatio() {
		return ratio;
	}
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
}
