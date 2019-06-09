package test.util;

import static org.junit.Assert.*;

import org.junit.Test;

import src.util.AbtestStringUtils;

public class TestAbtestStringUtils {
	@Test
	public void test_subAbtestStr_ABテストID() {

		String inputString = "    testId: PC_ADDEXAMSEARCHPANEL.034";
		String abtestIdString = "    testId: ";

		String actual = AbtestStringUtils.subAbtestStr(inputString, abtestIdString);

		assertEquals("subAbtestStr_ABテストID_期待値が違います", "PC_ADDEXAMSEARCHPANEL.034", actual);
	}

	@Test
	public void test_subAbtestStr_Version() {

		String inputString = "    version: 100";
		String abtestIdString = "    version: ";

		String actual = AbtestStringUtils.subAbtestStr(inputString, abtestIdString);

		assertEquals("subAbtestStr_version_期待値が違います", "100", actual);
	}

	@Test
	public void test_subAbtestStr_case() {

		String inputString = "        patternId: CASE.A";
		String abtestIdString = "        patternId: ";

		String actual = AbtestStringUtils.subAbtestStr(inputString, abtestIdString);

		assertEquals("subAbtestStr_case_期待値が違います", "CASE.A", actual);
	}

	@Test
	public void test_subAbtestStr_ratio() {

		String inputString = "        ratio: 100";
		String abtestIdString = "        ratio: ";

		String actual = AbtestStringUtils.subAbtestStr(inputString, abtestIdString);

		assertEquals("subAbtestStr_ratio_期待値が違います", "100", actual);
	}

}
