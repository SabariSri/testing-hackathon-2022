package com.ui.pages;

import java.io.File;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ui.basepage.AbstractPage;
import com.utils.CommonUtility;

public class FormRecognizer extends AbstractPage {
	// Constants
	private static final String FORM_REC_URL = "https://formrecognizer.appliedai.azure.com/studio";
	private static final String USERNAME = "18oct1990@gmail.com";
	private static final String PASSWORD = "Hackathon@123";
	private static final String END_POINT = "https://formtest001.cognitiveservices.azure.com/";
	private static final String ACCESS_KEY = "08dd29ee0d6e4625b8941b5f543826bc";

	@FindBy(xpath = "//div[text()='General documents']")
	WebElement generalDoc;

	@FindBy(xpath = "//input[@type='email']")
	WebElement usernameField;

	@FindBy(xpath = "//input[@type='submit']")
	WebElement nextBtn;

	@FindBy(xpath = "//input[@type='password']")
	WebElement pwdField;

	@FindBy(xpath = "//span[@id='ChoiceGroupLabel26-apiEndpointAndKey']")
	WebElement apiRadioBtn;

	@FindBy(xpath = "//input[@placeholder='Enter your API endpoint']")
	WebElement apiEndPointField;

	@FindBy(xpath = "//input[@placeholder='Enter your API key']")
	WebElement apiKeyField;

	@FindBy(xpath = "//i[@data-icon-name='CheckMark']")
	WebElement acceptCheckBox;

	@FindBy(id = "step-next-button")
	WebElement apiContinueBtn;

	@FindBy(xpath = "//span[text()='Finish']")
	WebElement finishBtn;

	@FindBy(xpath = "//label[@for='upload-button']")
	WebElement browseBtn;

	@FindBy(xpath = "//span[text()='Analyze']")
	WebElement analyzeBtn;

	@FindBy(xpath = "//img")
	WebElement firstImg;

	@FindBy(xpath = "//span[text()='Result']")
	WebElement resultSection;

	@FindBy(xpath = "//*[@data-icon-name='Download']")
	WebElement downloadBtn;

	private void loginFormRecognizer() {
		try {
			((JavascriptExecutor) getDriver()).executeScript("window.open()");
			CommonUtility.switchToNewTab(getDriver());
			getDriver().get(FORM_REC_URL);
			Thread.sleep(4000);
			clickOnWithoutLogs(generalDoc, "General Documents Option");
			CommonUtility.switchToNewTab(getDriver());
			setTextWithoutLogs(usernameField, USERNAME, "Username field");
			clickOnWithoutLogs(nextBtn, "Next Button");
			CommonUtility.switchToNewTab(getDriver());
			setTextWithoutLogs(pwdField, PASSWORD, "Password field");
			clickOnWithoutLogs(nextBtn, "Sign in Button");
			CommonUtility.switchToNewTab(getDriver());
			clickOnWithoutLogs(nextBtn, "Stay signed in - Yes button");
			CommonUtility.switchToNewTab(getDriver());
			clickOnWithoutLogs(apiRadioBtn, "API radio button");
			setTextWithoutLogs(apiEndPointField, END_POINT, "API endpoint field");
			setTextWithoutLogs(apiKeyField, ACCESS_KEY, "API key field");
			clickOnWithoutLogs(acceptCheckBox, "API accept checkbox");
			clickOnWithoutLogs(apiContinueBtn, "API continue button");
			Thread.sleep(2000);
			clickOnWithoutLogs(finishBtn, "Finish button");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void uploadScreenshot(String screenshotPath) {
		try {
			clickOnWithoutLogs(browseBtn, "Browse button");
			Thread.sleep(2000);
			CommonUtility.uploadFile(screenshotPath);
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String analyzeScreenshot(String screenshotPath) {
		String jsonFile = "";
		try {
			loginFormRecognizer();
			uploadScreenshot(screenshotPath);
			clickOnWithoutLogs(firstImg, "First Image");
			clickOnWithoutLogs(analyzeBtn, "Analyse button");
			Thread.sleep(6000);
			clickOnWithoutLogs(resultSection, "Result Section");
			Thread.sleep(2000);
			clickOnWithoutLogs(downloadBtn, "Download Button");
			Thread.sleep(3000);
			jsonFile = moveDownloadedJson();
			reporter().stepPass("Analyzed the sceenshot & fetched as Json at " + jsonFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonFile;
	}

	public static String moveDownloadedJson() throws InterruptedException {
		String downloadsFolder = System.getProperty("user.home") + "\\Downloads\\".replaceAll("//", File.separator);
		return CommonUtility.moveFile(new File(downloadsFolder), screenshotName + ".json", new File(FORM_REC_OUTPUT));
	}
}
