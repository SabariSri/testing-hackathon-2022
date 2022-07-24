package com.ui.tests.web;

import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import com.basetest.AbstractTest;

public class FormValidation extends AbstractTest {

	@Test
	public void sampleTest() {
		reporter().startTest("Form Comparator Test");
		String screenShot = goodsAndServiceTaxPage.scrollAndTakeScreenshot();
		String convertedJson = formRecognizer.analyzeScreenshot(screenShot);
		//compareFormJson(convertedJson);
	}

	public static void compareFormJson(String jsonFile) {
		JSONParser parser = new JSONParser();
		try {
			System.out.println(jsonFile);
			Object obj = parser.parse(new FileReader(jsonFile));
			JSONArray array = new JSONArray();
			JSONObject jsonObject = (JSONObject) obj;
			String name = (String) jsonObject.get("Name");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
