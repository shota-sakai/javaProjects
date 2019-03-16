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
public class AbtestUnitMaster {

	private static AbtestUnitMaster instance;
	private List<AbtestUnit> abtestInfoList = Lists.newArrayList();
	private AbtestUnitMaster() {}

	/**
	 * 唯一のAbtestUnitMasterインスタンスを取得する
	 *
	 * @return
	 */
	public static AbtestUnitMaster getInstance() {
		if( instance == null ) {
			instance = new AbtestUnitMaster();
		}

		return instance;
	}

	/**
	 * AbtestUnitマスタを取得
	 *
	 * @return
	 */
	public List<AbtestUnit> getList(){
		return this.abtestInfoList;
	}

	/**
	 * abtestUnitマスタを設定する
	 *
	 * @param abtestUnitList
	 */
	public void setList(List<AbtestUnit> abtestUnitList) {
		this.abtestInfoList = abtestUnitList;
	}

	/**
	 *
	 * abtestUnitマスタにabtesUnit追加する
	 * @param abtestUnit
	 */
	public void addList( AbtestUnit abtestUnit ) {
		this.abtestInfoList.add(abtestUnit);
	}

	/**
	 * abtestUnitマスタを更新する
	 *
	 * @param index
	 * @param afterUnit
	 */
	public void updateAbtestInfo( int index, AbtestUnit afterUnit ) {

		// 引数が不正な場合は抜ける
		if( (index < 0 || this.abtestInfoList.size() < index) ||
				afterUnit == null) {
			return;
		}

		//マスタを更新
		this.abtestInfoList.set(index, afterUnit);
	}
}
