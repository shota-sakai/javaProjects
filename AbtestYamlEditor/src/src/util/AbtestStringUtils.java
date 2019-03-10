package src.util;

import org.apache.commons.lang3.StringUtils;

public class AbtestStringUtils {

	/**
	 * 文字列に指定されたABテストの
	 *
	 * @param originalStr
	 * @param targetAbtestStr
	 * @return
	 */
	public static String subAbtestStr( String originalStr, String targetAbtestStr ){

		if( StringUtils.isEmpty(originalStr) || StringUtils.isEmpty(targetAbtestStr) ) {
			return "";
		}

		int start = originalStr.indexOf(targetAbtestStr);

		// ABテストの文字列が見つからない場合は空文字を返却する
		if( start == -1 ) {
			return "";
		}

		return originalStr.substring(targetAbtestStr.length(), originalStr.length());
	}
}
