package com.utils;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtility {
	public static String getDateTime() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH.mm.ss.ms");
		LocalDateTime current = LocalDateTime.now();
		return current.format(format);
	}

	public static void switchToNewTab(WebDriver driver) throws InterruptedException {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tabs.size() - 1));
		Thread.sleep(2000);
	}

	public static void setClipboardData(String string) {
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	public static void uploadFile(String fileLocation) {
		try {
			setClipboardData(fileLocation);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public static ArrayList<String> readJsonValue(String jsonPath) {
		ArrayList<String> jsonValueList = new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<?, ?> map = mapper.readValue(Paths.get(jsonPath).toFile(), Map.class);
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				jsonValueList.add(entry.getValue().toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsonValueList;
	}

	public static String moveFile(File sourceFolder, String sourceFile, File destinationFolder)
			throws InterruptedException {
		File movedFile = null;
		if (sourceFolder.exists() && sourceFolder.isDirectory()) {
			// Get list of the files and iterate over them
			File[] listOfFiles = sourceFolder.listFiles();
			if (listOfFiles != null) {
				for (File child : listOfFiles) {
					if (child.getName().equals(sourceFile)) {
						Thread.sleep(1000);
						movedFile = new File(destinationFolder + "\\" + child.getName().replaceAll(".jpg", ""));
						child.renameTo(movedFile);
						Thread.sleep(1000);
						child.delete();
						break;
					}
				}
			} else {
				System.out.println(sourceFolder + " is empty");
			}
		} else {
			System.out.println(sourceFolder + " - Folder does not exists");
		}
		return movedFile.getAbsolutePath();
	}
}
