package src.util;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * abtestファイルに関するバリデーションチェック機能を提供するクラス
 *
 * @author sakaisyota
 *
 */
public class AbtestValidator {

	/**
	 * 読み込んだファイルがabtestファイルかどうかをチェックする
	 *
	 * @param yamlString　読み込んだファイル内の文字列
	 * @return　true：yamlファイルではない　false：正常
	 */
	public static boolean validateYamlFile( List<String> yamlString ) {

		if( CollectionUtils.isEmpty(yamlString) || yamlString.size() > 2 ) {
			return true;
		}

		if( !StringUtils.equals(yamlString.get(0), AbtestPartsConstants.YAML_FILE_FIRST_STR) ||
				!StringUtils.equals(yamlString.get(1), AbtestPartsConstants.YAML_FILE_SECOND_STR)) {
			return true;
		}

		return false;
	}
}
