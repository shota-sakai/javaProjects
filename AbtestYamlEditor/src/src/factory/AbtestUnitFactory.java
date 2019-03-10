package src.factory;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import src.dto.AbtestCaseUnit;
import src.dto.AbtestUnit;
import src.util.AbtestPartsConstants;
import src.util.AbtestStringUtils;

public class AbtestUnitFactory {

	/**
	 * yamlファイル内の文字列からAbtestUnitのリストを生成する
	 *
	 * @param inputString
	 * @return
	 */
	public static List<AbtestUnit> create(List<String> inputString) {
		// TODO 自動生成されたメソッド・スタブ
		if( CollectionUtils.isEmpty(inputString) ) {
			return Collections.emptyList();
		}

		List<List<String>> divideList = dividedInputString(inputString);

		List<AbtestUnit> resultList = Lists.newArrayList();

		for( List<String> abtestString : divideList ) {
			AbtestUnit unit = createAbtestUnit(abtestString);

			if( unit != null ) {
				resultList.add(unit);
			}
		}

		return resultList;
	}

	/**
	 * abtest.yaml内の文字列をABテスト1つ分の文字列に分割する
	 *
	 * @param inputString
	 * @return
	 */
	public static List<List<String>> dividedInputString( List<String> inputString ){

		if( CollectionUtils.isEmpty(inputString) ) {
			return Collections.emptyList();
		}

		List<List<String>> resultList= Lists.newArrayList();

		for( int i=0; i<inputString.size(); i++ ) {
			if( !StringUtils.equals(inputString.get(i), AbtestPartsConstants.ID_START)) {
				continue;
			}

			List<String> addList = Lists.newArrayList();
			for( int j=i; j<inputString.size(); j++ ) {
				if( StringUtils.equals(inputString.get(j), AbtestPartsConstants.ID_START) &&
						addList.size() != 0) {
					i = j-1;
					break;
				}
				addList.add(inputString.get(j));
			}
			resultList.add(addList);
		}
		return resultList;
	}

	/**
	 * ABテスト1つ分の文字列からabtestUnitを生成する
	 *
	 * @param abtestStrList
	 * @return
	 */
	public static AbtestUnit createAbtestUnit( List<String> abtestStrList ) {

		if( CollectionUtils.isEmpty(abtestStrList) ||
				!StringUtils.equals(abtestStrList.get(0), AbtestPartsConstants.ID_START) ) {
			return null;
		}

		AbtestUnit resultUnit = new AbtestUnit();

		for( int i=0; i<abtestStrList.size(); i++ ) {
			// IDを取得
			if( abtestStrList.get(i).indexOf(AbtestPartsConstants.ABTEST_ID) != -1 ) {
				String abtestId = AbtestStringUtils.subAbtestStr(abtestStrList.get(i), AbtestPartsConstants.ABTEST_ID);
				resultUnit.setId(abtestId);
			}

			// バージョンを取得
			if( abtestStrList.get(i).indexOf(AbtestPartsConstants.VERSTION) != -1 ) {
				String vesrionStr = AbtestStringUtils.subAbtestStr(abtestStrList.get(i), AbtestPartsConstants.VERSTION);
				int vesrion = Integer.parseInt(vesrionStr);
				resultUnit.setVersion(vesrion);
			}

			// ABテストケースを取得
			if( abtestStrList.get(i).indexOf(AbtestPartsConstants.CASES) != -1 ) {

				List<AbtestCaseUnit> caseList = Lists.newArrayList();
				for( int j=i+1; j<abtestStrList.size()-1; j++ ) {
					if( abtestStrList.get(j).indexOf(AbtestPartsConstants.PATTERN_ID) != -1) {
						AbtestCaseUnit caseUnit = new AbtestCaseUnit();
						String patternId = AbtestStringUtils.subAbtestStr(abtestStrList.get(j), AbtestPartsConstants.PATTERN_ID);
						String ratioStr = AbtestStringUtils.subAbtestStr(abtestStrList.get(j+1), AbtestPartsConstants.RATIO);

						if( StringUtils.isNotBlank(patternId) && StringUtils.isNotBlank(ratioStr) ) {
							int ratio = Integer.parseInt(ratioStr);
							caseUnit.setPatternId(patternId);
							caseUnit.setRatio(ratio);
							caseList.add(caseUnit);
						}
					}
					i=j;
				}

				resultUnit.setCaseUnits(caseList);
			}
		}

		return resultUnit;
	}
}
