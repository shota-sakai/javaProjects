package src.util;

import java.util.List;

import com.google.common.collect.Lists;

import src.dto.AbtestUnit;

/**
 * abtest情報(マスタ)を保持するクラス
 *
 * @author sakaisyota
 *
 */
public class AbtestInfoMaster {

	private static AbtestInfoMaster instance;
	private List<AbtestUnit> abtestInfoList = Lists.newArrayList();
	private AbtestInfoMaster() {}

	public static AbtestInfoMaster getInstance() {
		if( instance == null ) {
			instance = new AbtestInfoMaster();
		}

		return instance;
	}

	public List<AbtestUnit> getList(){
		return this.abtestInfoList;
	}

	public void setList(List<AbtestUnit> abtestUnitList) {
		this.abtestInfoList = abtestUnitList;
	}

	public void addList( AbtestUnit abtestUnit ) {
		this.abtestInfoList.add(abtestUnit);
	}
}
