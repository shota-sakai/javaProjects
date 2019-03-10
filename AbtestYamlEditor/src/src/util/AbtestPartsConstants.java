package src.util;

public class AbtestPartsConstants{

	/**
	 * abtest.yamlの1行目の文字列<br>
	 * --- !r2framework.abtest.entity.Site
	 */
	public static final String YAML_FILE_FIRST_STR = "--- !r2framework.abtest.entity.Site";

	/**
	 * abtest.yamlの2行目の文字列<br>
	 * tests:
	 */
	public static final String YAML_FILE_SECOND_STR = "tests:";

	/**
	 * abtestIdの開始文字列<br>
	 * - !r2framework.abtest.entity.AbTest
	 */
	public static final String ID_START = "  - !r2framework.abtest.entity.AbTest";

	/**
	 * abtestId<br>
	 * testId:
	 */
	public static final String ABTEST_ID = "    testId: ";

	/**
	 * バージョン<br>
	 * version:
	 */
	public static final String VERSTION = "    version: ";

	/**
	 * casesの開始文字列<br>
	 * cases:
	 */
	public static final String CASES = "    cases:";

	/**
	 * caseの開始文字列<br>
	 * - !r2framework.abtest.entity.AbTestCase
	 */
	public static final String CASE_START = "      - !r2framework.abtest.entity.AbTestCase";

	/**
	 * パターンID<br>
	 * patternId:
	 */
	public static final String PATTERN_ID = "        patternId: ";

	/**
	 * 割合<br>
	 * ratio:
	 */
	public static final String RATIO = "        ratio: ";
}
