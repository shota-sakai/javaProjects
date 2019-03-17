package src.util;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import src.factory.MessageDialogCreator;

/**
 * abtestファイルに関するバリデーションチェック機能を提供するクラス
 *
 * @author sakaisyota
 *
 */
public class AbtestYamlValidator {

	/**
	 * 読み込んだファイルがabtestファイルかどうかをチェックする
	 *
	 * @param yamlString　読み込んだファイル内の文字列
	 * @return　true：yamlファイルではない　false：正常
	 */
	public static boolean validateYamlFile( List<String> yamlString ) {

		if( CollectionUtils.isEmpty(yamlString) ) {
			MessageDialogCreator.createErrorDialog("yamlファイル読み込みに失敗しました：ファイルが空です");
			return true;
		}

		int lineNo = 0;
		for( int i=0;  i<yamlString.size(); i++ ) {
			lineNo++;

			// 「--- !r2framework.abtest.entity.Site」と一致するかチェック
			if( StringUtils.equals(yamlString.get(i), AbtestPartsConstants.YAML_FILE_FIRST_STR)) {
				// 次の行が「tests:」と一致するかチェック
				if( StringUtils.equals(getNextString(yamlString, i+1), AbtestPartsConstants.YAML_FILE_SECOND_STR) ) {
					continue;
				}
			}

			// 「tests:」と一致するかチェック
			if( StringUtils.equals(yamlString.get(i), AbtestPartsConstants.YAML_FILE_SECOND_STR) ) {
				// 次の行が「  - !r2framework.abtest.entity.AbTest」と一致するかチェック
				if( StringUtils.equals(getNextString(yamlString, i+1), AbtestPartsConstants.ID_START) ) {
					continue;
				}
			}

			// 「  - !r2framework.abtest.entity.AbTest」と一致するかチェック
			if( StringUtils.equals(yamlString.get(i), AbtestPartsConstants.ID_START) ) {
				// 次の行がABテストID指定部分と一致するかチェック
				if( !validateAbtestStr(getNextString(yamlString, i+1)) ) {
					continue;
				}
			}

			// ABテストID指定部分と一致するかチェック
			if( !validateAbtestStr(yamlString.get(i)) ) {
				// 次の行がABテストのバージョン指定部分と一致するかチェック
				if( !validateVesionStr(getNextString(yamlString, i+1)) ) {
					continue;
				}
			}

			// ABテストのバージョン指定部分と一致するかチェック
			if( !validateVesionStr(yamlString.get(i)) ) {
				// 次の行が「      - !r2framework.abtest.entity.AbTestCase」と一致するかチェック
				if( StringUtils.equals(getNextString(yamlString, i+1), AbtestPartsConstants.CASES) ) {
					continue;
				}
			}

			// 「    cases:」と一致するかチェック
			if( StringUtils.equals(yamlString.get(i), AbtestPartsConstants.CASES) ) {
				// 次の行が「    cases:」と一致するかチェック
				if( StringUtils.equals(getNextString(yamlString, i+1), AbtestPartsConstants.CASE_START) ) {
					continue;
				}
			}

			// 「      - !r2framework.abtest.entity.AbTestCase」と一致するかチェック
			if( StringUtils.equals(yamlString.get(i), AbtestPartsConstants.CASE_START) ) {
				// 次の行がケースID指定部分と一致するかチェック
				if( !validateCaseIdStr(getNextString(yamlString, i+1)) ) {
					continue;
				}
			}

			// ケースID指定部分と一致するかチェック
			if( !validateCaseIdStr(yamlString.get(i)) ) {
				// 次の行がケース割合指定部分と一致するかチェック
				if( !validateRatioStr(getNextString(yamlString, i+1)) ) {
					continue;
				}
			}

			// ケース割合指定部分と一致するかチェック
			if( !validateRatioStr(yamlString.get(i)) ) {
				// 次の行が「  - !r2framework.abtest.entity.AbTest」と一致する
				// または「      - !r2framework.abtest.entity.AbTestCase」と一致する
				// またはyamlファイルの終端かどうかチェック
				if( StringUtils.equals(getNextString(yamlString, i+1), AbtestPartsConstants.ID_START)
						|| StringUtils.equals(getNextString(yamlString, i+1), AbtestPartsConstants.CASE_START)
						|| isEndYamlStringList(yamlString, i+1)) {
					continue;
				}
			}

			// どの条件にも該当しない場合はyamlファイルの内容が不正なのでエラーメッセージ表示
			MessageDialogCreator.createErrorDialog("yamlファイルの内容が不正です：" + lineNo + "行目付近");
			return true;
		}

		return false;
	}

	/**
	 * 指定された文字列がABテストID指定部分("    testId: ")と一致するかチェックする
	 *
	 * @param abtestId
	 * @return 一致しない場合は true を返す
	 */
	private static boolean validateAbtestStr( String abtestId ) {

		// 「    testId: 」で始めるかつ、完全一致しないかチェック
		if( abtestId.startsWith(AbtestPartsConstants.ABTEST_ID)
				&& !StringUtils.equals(abtestId, AbtestPartsConstants.ABTEST_ID)) {
			return false;
		}

		return true;
	}

	/**
	 * 指定された文字列がABテストのバージョン指定部分("    version: ")と一致するかチェックする
	 *
	 * @param version
	 * @return 一致しない場合は true を返す
	 */
	private static boolean validateVesionStr( String version ) {

		// 「    version: 」で始めるかつ、完全一致しないかチェック
		if( version.startsWith(AbtestPartsConstants.VERSTION)
				&& !StringUtils.equals(version, AbtestPartsConstants.VERSTION) ) {
			return false;
		}

		return true;
	}

	/**
	 * 指定された文字列がABテストのケースID指定部分("        patternId: ")と一致するかチェックする
	 *
	 * @param caseId
	 * @return 一致しない場合は true を返す
	 */
	private static boolean validateCaseIdStr( String caseId ) {

		// 「        patternId: 」で始めるかつ、完全一致しないかチェック
		if( caseId.startsWith(AbtestPartsConstants.CASE_ID)
				&& !StringUtils.equals(caseId, AbtestPartsConstants.CASE_ID) ) {
			return false;
		}

		return true;
	}

	/**
	 * 指定された文字列がABテストのケース割合指定部分("        ratio: ")と一致するかチェックする
	 *
	 * @param ratio
	 * @return 一致しない場合は true を返す
	 */
	private static boolean validateRatioStr( String ratio ) {

		// 「        ratio: 」で始めるかつ、完全一致しないかチェック
		if( ratio.startsWith(AbtestPartsConstants.RATIO)
				&& !StringUtils.equals(ratio, AbtestPartsConstants.RATIO) ) {
			return false;
		}

		return true;
	}

	/**
	 * 指定されたインデックスがyamlファイル文字列の終端かどうかをチェックする
	 *
	 * @param yamlStringList
	 * @param index
	 * @return 終端の場合は true を返す
	 */
	private static boolean isEndYamlStringList( List<String> yamlStringList, int index ) {

		if( yamlStringList.size() <= index ) {
			return true;
		}

		return false;
	}

	/**
	 * 指定されたインデックスの次の文字列を取得する
	 *
	 * @param StringList
	 * @param index
	 * @return 指定されたインデックスが文字列リストのサイズを超えている場合は空文字を返却する
	 */
	private static String getNextString( List<String> StringList, int index ) {
		if( StringList.size() > index ) {
			return StringList.get(index);
		}
		return "";
	}
}
