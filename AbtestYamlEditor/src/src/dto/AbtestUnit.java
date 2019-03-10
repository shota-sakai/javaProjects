package src.dto;

import java.util.List;

/**
 * 1つのabテストを表現するDTO
 *
 * @author sakaisyota
 *
 */
public class AbtestUnit {

	/** abテストID */
	private String id;
	/** バージョン */
	private int version;
	/** ケースのリスト */
	private List<AbtestCaseUnit> caseUnits;
	/** 削除フラグ */
	private boolean isDelete = false;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public List<AbtestCaseUnit> getCaseUnits() {
		return caseUnits;
	}
	public void setCaseUnits(List<AbtestCaseUnit> caseUnits) {
		this.caseUnits = caseUnits;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
}
