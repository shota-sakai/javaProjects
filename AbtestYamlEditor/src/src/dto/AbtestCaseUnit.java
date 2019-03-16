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

	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public int getRatio() {
		return ratio;
	}
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
}
