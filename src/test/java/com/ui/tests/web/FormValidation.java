package com.ui.tests.web;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import org.testng.annotations.Test;

import com.basetest.AbstractTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.CommonUtility;

public class FormValidation extends AbstractTest {

	@Test
	public void sampleTest() {
		reporter().startTest("Form Comparator Test");
		String screenShot = goodsAndServiceTaxPage.scrollAndTakeScreenshot();
		String convertedJson = formRecognizer.analyzeScreenshot(screenShot);
		compareFormJson(convertedJson, CommonUtility.readJsonValue(APP_EXP_JSON_FOLDER + "TaxForm.json"));
	}

	public static void compareFormJson(String jsonFile, ArrayList<String> expectedJsonValue) {
		reporter().stepInfo("Comparing the Json with the expected Keys");
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<?, ?> map = mapper.readValue(Paths.get(jsonFile).toFile(), Map.class);
			for (String value : expectedJsonValue) {
				for (Map.Entry<?, ?> entry : map.entrySet()) {
					if (entry.getValue().toString().contains(value)) {
						reporter().stepPass(value + " --> Matches the key from Form Recognizer Json");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
