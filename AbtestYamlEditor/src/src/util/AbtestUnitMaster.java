package src.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

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
	private List<AbtestUnit> abtestUnitListMaster = Lists.newArrayList();
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
		return this.abtestUnitListMaster;
	}

	/**
	 * abtestUnitマスタを設定する
	 *
	 * @param abtestUnitList
	 */
	public void setList(List<AbtestUnit> abtestUnitList) {
		this.abtestUnitListMaster = abtestUnitList;
	}

	/**
	 *
	 * abtestUnitマスタにabtesUnit追加する
	 * @param abtestUnit
	 */
	public void addList( AbtestUnit abtestUnit ) {
		this.abtestUnitListMaster.add(abtestUnit);
	}

	/**
	 * abtestUnitマスタを更新する
	 *
	 * @param index
	 * @param afterUnit
	 */
	public void updateAbtestUnit( int index, AbtestUnit afterUnit ) {

		// 引数が不正な場合は抜ける
		if( (index < 0 || this.abtestUnitListMaster.size() < index) ||
				afterUnit == null) {
			return;
		}

		//マスタを更新
		this.abtestUnitListMaster.set(index, afterUnit);
	}

	/**
	 * Deleteフラグが true に設定されているABテストIDをマスタから削除する
	 */
	public void cleanAbtestUnitByDeleteFlg() {

		if( CollectionUtils.isEmpty(this.abtestUnitListMaster) ) {
			return;
		}

		List<AbtestUnit> deletedAbtestList = this.abtestUnitListMaster.stream()
				.filter( unit -> !unit.isDelete() )
				.collect(Collectors.toList());

		this.abtestUnitListMaster = Lists.newArrayList(deletedAbtestList);
	}

	/**
	 * ABテスト情報マスタのテストIDをリストで取得する
	 *
	 * @return マスタ情報が空の場合は空リストを返却する
	 */
	public List<String> getTestIdList(){
		 if( CollectionUtils.isEmpty(abtestUnitListMaster) ) {
			 return Collections.emptyList();
		 }

		return this.abtestUnitListMaster.stream().map(AbtestUnit::getId).collect(Collectors.toList());
	}
}
