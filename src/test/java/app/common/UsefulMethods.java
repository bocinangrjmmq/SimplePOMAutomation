package app.common;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

public class UsefulMethods {

	WebDriver driver;
	ConfigReader config = new ConfigReader();
	private String parentHandle;
	private Set<String> handles;
	private static final Logger log = LogManager.getLogger(UsefulMethods.class.getName());

	//Exclusive Constructor for Email Report method
	public UsefulMethods() {
		super();
	}

	public UsefulMethods(WebDriver driver) {
		this.driver = driver;
	}

	/*public static String getBase64Screenshot(WebDriver driver, String screenshotName) throws IOException {
		String encodedBase64 = null;
		FileInputStream fileInputStream = null;
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		String destination = windowsPath + "\\FailedTestsScreenshots\\"+screenshotName+timeStamp+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);

		try {
			fileInputStream =new FileInputStream(finalDestination);
			byte[] bytes =new byte[(int)finalDestination.length()];
			fileInputStream.read(bytes);
			encodedBase64 = new String(Base64.encodeBase64(bytes));
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}

		return encodedBase64;
	}*/

	/**
	 * Captures a screenshot of the page in PNG and saves it into the /ScreenShot folder. 
	 * @param driver
	 * @param filename
	 * @return the screenshot in base64 format to be attached into the ExtentReport 
	 */
	public String getScreenshotBase64(WebDriver driver, String filename) {
		String encodedBase64 = null;
		TakesScreenshot tsc = (TakesScreenshot) driver;
		File src = tsc.getScreenshotAs(OutputType.FILE);
		
		String directory;
		
		//IF we are running as a Remote Execution:
		if( driver.toString().contains("RemoteWebDriver")) {
			((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
			log.debug("RemoteWebDriver detected..");
			directory = "./ScreenShot/";
		
		} else {
			log.debug("Local WebDriver detected..");
			directory = ".\\ScreenShot\\";
		}
		String path = directory + filename + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		
		try {		
			FileUtils.copyFile(src, destination);
			FileInputStream fileInputStream = new FileInputStream(destination);
			byte[] bytes = new byte[(int) destination.length()];
			fileInputStream.read(bytes);
			encodedBase64 = new String(Base64.encodeBase64(bytes));
			
			fileInputStream.close();
		} catch (IOException e) {
			System.out.println("Capture Failed " + e.getMessage());
		}
		return encodedBase64;
	}

	/**
	 * Capture the screenshot and store it in the specified location. 
	 * 
	 * @param driver
	 * @param filename
	 * @return Full path with filename of the screenshot taken.
	 */
	public String getScreenshot(WebDriver driver, String filename) {
		TakesScreenshot tsc = (TakesScreenshot) driver;
		File src = tsc.getScreenshotAs(OutputType.FILE);
		String directory = "./ScreenShot/";
		String path = directory + filename + System.currentTimeMillis() + ".png";
		File destination = new File(path);	
		String absolutePath = destination.getAbsolutePath();
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			System.out.println("Capture Failed " + e.getMessage());
		}
		return absolutePath;
	}
	
	public void getParentHandle() {
		parentHandle = driver.getWindowHandle();
		log.info("The parent handle is: " + parentHandle);
	}

	public String getParentHandleName() {
		parentHandle = driver.getWindowHandle();
		log.info("The parent handle is: " + parentHandle);
		return parentHandle;
	}

	public void getAllHadles() {
		handles = driver.getWindowHandles();
		for (String handle : handles) {
			log.info("Handle found :" + handle);
		}
	}

	public void switchToHandle() {
		try {
			for (String handle : handles) {
				log.info(handle);
				if (!handle.equals(parentHandle)) {
					driver.switchTo().window(handle);
					log.info("Move to handle: " + handle);
					break;
				}
			}
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void closeHandle() {
		driver.close();
		log.info("The tab was closed");
	}

	public void returnToParentHandle() {
		try {
			driver.switchTo().window(parentHandle);
			log.info("Return to the parent handle: " + parentHandle);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public File lastestDownloadedFile(String downloadPath) {
		File dir = new File(downloadPath);
		File[] dirList = dir.listFiles();
		if (dirList == null || dirList.length == 0) {
			log.info("No files downloaded");
			return null;
		}

		File lmFile = dirList[0];
		for (int i = 1; i < dirList.length; i++) {
			if (lmFile.lastModified() < dirList[i].lastModified()) {
				lmFile = dirList[i];
			}
		}
		log.info("The latest downloaded file is: " + lmFile.getName());
		return lmFile;
	}

	public void robotAltS() {
		try {
			Robot robot = new Robot();
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_S);
			log.info("Pressing ALT+S");
			Thread.sleep(3000);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_S);
			log.info("Releasing ALT+S");
		} catch (AWTException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void verifyLastestDownload(String downloadPath) {
		try {
			Thread.sleep(10000);
			if (config.getBrowser().equalsIgnoreCase("internet explorer")) {
				robotAltS();				
			}
			String home = System.getProperty("user.home");
			File glf = lastestDownloadedFile(home+downloadPath);
			String fileName = glf.getName();
			log.info("The file downloaded was: " + fileName);
			Boolean fileNameC = fileName.contains("Invoice");
			Assert.assertTrue(fileNameC);
			log.info("Contains Invoice and was successfully downloaded");
			glf.delete();
			log.info("The File: " + fileNameC + " was deleted successfully");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Double roundValues(Double value) {
		DecimalFormat df = new DecimalFormat("#####.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		String nfv=df.format(value);
		Double dNewFormat =Double.parseDouble(nfv);
		log.info(df.format(value));
		return dNewFormat;		
	}	

	//------------------------------------------------------------------------
	
	/**
	 * Pause the execution for the given time duration in Milliseconds.
	 * @param milliseconds
	 */
	public void waitThreadTime(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Returns the yesterday's date & noon time (12:00 PM).
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getYesterdayNoonDateString() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR, 12);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		Date yesterdayNoon = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		log.debug("Yesterday's date at noon with format: " +dateFormat.format(yesterdayNoon));

		return dateFormat.format(yesterdayNoon);
	}

	/**
	 * Returns the yesterday's date & time from current system.
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getYesterdayDateString() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date yesterday = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Yesterday's date with format: " + dateFormat.format(yesterday));

		return dateFormat.format(yesterday);
	}
	
	/**
	 * Returns the yesterday's date & time from current system with the requested hour.
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getYesterdayDateStringAtHour(int hour) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		Date yesterdayAtHour = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Yesterday's date with format: " + dateFormat.format(yesterdayAtHour));

		return dateFormat.format(yesterdayAtHour);
	}
	
	/**
	 * Returns the yesterday's date & time from current system with the requested hour and minutes.
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getYesterdayDateStringAtHourMinutes(int hour, int min) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, 00);
		Date yesterdayAtHourMin = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Yesterday's date with format: " + dateFormat.format(yesterdayAtHourMin));

		return dateFormat.format(yesterdayAtHourMin);
	}
	
	/**
	 * Returns the yesterday's date & time from current system wwith the additional amount of minutes given
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getYesterdayDateStringPlusMinutes(int minutes) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.add(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, 00);
		Date yesterdayPlusMinutes = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Yesterday's date with format: " + dateFormat.format(yesterdayPlusMinutes));

		return dateFormat.format(yesterdayPlusMinutes);
	}

	/**
	 * Returns the current date & time from system.
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getTodayDateString() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		Date today = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Today's date with format: " + dateFormat.format(today));

		return dateFormat.format(today);
	}

	/**
	 * Returns the current date & time from system minus the hours given.
	 * @param hour
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getTodayDateStringMinusHour(int hour) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -hour);
		Date todayMinusHour = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Today's date with format: " + dateFormat.format(todayMinusHour));

		return dateFormat.format(todayMinusHour);
	}
	
	/**
	 * Returns the current date & time from system minus the minutes given.
	 * @param hour
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getTodayDateStringMinusMinutes(int minutes) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -minutes);
		Date todayMinusMinutes = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Today's date with format: " + dateFormat.format(todayMinusMinutes));

		return dateFormat.format(todayMinusMinutes);
	}
	
	/**
	 * Returns the current date & time from system plus the minutes given.
	 * @param hour
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getTodayDateStringPlusMinutes(int minutes) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, minutes);
		Date todayPlusMinutes = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Today's date with format: " + dateFormat.format(todayPlusMinutes));

		return dateFormat.format(todayPlusMinutes);
	}
	
	/**
	 * Returns the current date & time from system with the additional amount of hours given.
	 * @param hour
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getTodayDateStringPlusHour(int hour) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, hour);
		Date todayPlusOneHour = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Today's date with format: " + dateFormat.format(todayPlusOneHour));

		return dateFormat.format(todayPlusOneHour);
	}
	
	/**
	 * Returns the current date & time from system with the additional amount of hours given.
	 * @param hour
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getTodaysDateStringAtHour(int hour) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		Date tomorrowAtHour = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Tomorrow's date with format: " + dateFormat.format(tomorrowAtHour));

		return dateFormat.format(tomorrowAtHour);
	}
	
	/**
	 * Returns the tomorrow's date & time from system.
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getTomorrowsDateString() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		Date tomorrow = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Tomorrow's date with format: " + dateFormat.format(tomorrow));

		return dateFormat.format(tomorrow);
	}
	
	/**
	 * Returns the tomorrows date & time from system with the additional amount of hours given.
	 * @param hour
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getTomorrowDateStringAtHour(int hour) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		Date tomorrowAtHour = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Tomorrow's date with format: " + dateFormat.format(tomorrowAtHour));

		return dateFormat.format(tomorrowAtHour);
	}

	/**
	 * Returns the tomorrows date & time from current system plus the requested hours.
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getTomorrowsDateStringPlusHour(int hour) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		Date tomorrowsPlusHour = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Tomorrow's date with format: " + dateFormat.format(tomorrowsPlusHour));

		return dateFormat.format(tomorrowsPlusHour);
	}
	
	/**
	 * Returns the Tomorrows date & time from system plus the minutes given.
	 * @param hour
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getTomorrowsDateStringPlusMinutes(int minutes) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, minutes);
		Date tomorrowsPlusMinutes = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Tomorrows date with format: " + dateFormat.format(tomorrowsPlusMinutes));

		return dateFormat.format(tomorrowsPlusMinutes);
	}
	
	/**
	 * Returns the Tomorrow's date & time from current system with the requested hour and minutes.
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getTomorrowsDateStringAtHourMinutes(int hour, int min) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, 00);
		Date tomorrowAtHourMin = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Tomorrow's date with format: " + dateFormat.format(tomorrowAtHourMin));

		return dateFormat.format(tomorrowAtHourMin);
	}
	
	/**
	 * Returns the current date & time from system adjusted to set the date to the next week of day number.
	 * @param weekOfDay - standard, from 1 (Monday) to 7 (Sunday).
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getNextDayString(DayOfWeek weekOfDay) {
		LocalDateTime nextDay = LocalDateTime.now().with(TemporalAdjusters.next(weekOfDay));
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		System.out.println("Next date with format: " + dateFormat.format(nextDay));

		return dateFormat.format(nextDay);
	}
	
	public String getDaysFromNow(int days) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		Date nextDays = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Getting "+days+" from now with format: " + dateFormat.format(nextDays));
		
		return dateFormat.format(nextDays);
	}
	
	public String getDaysFromNowPlusHour(int days, int hour) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		Date nextDays = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Getting "+days+" from now with format: " + dateFormat.format(nextDays));
		
		return dateFormat.format(nextDays);
	}

	/**
	 * Sums the two given hours, minutes and seconds.
	 * 
	 * Format should be: "hh:mm:ss"
	 *  
	 * @param firstTime
	 * @param secondTime
	 * @return Time in the following format: hh:mm:ss
	 */
	public String sumTwoHours(String firstTime, String secondTime) {
		//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" // "2019-12-18 16:20:41");
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		Calendar cal = Calendar.getInstance();

		//		String firstDay = firstTime.split(":")[0];
		String firstHour = firstTime.split(":")[0];
		String firstMinute = firstTime.split(":")[1];
		String firstSecond = firstTime.split(":")[2];

		//		cal.set(Calendar.DATE, Integer.parseInt(firstDay));
		cal.set(Calendar.HOUR, Integer.parseInt(firstHour));
		cal.set(Calendar.MINUTE, Integer.parseInt(firstMinute));
		cal.set(Calendar.SECOND, Integer.parseInt(firstSecond));

		Date d = cal.getTime();
		System.out.println(dateFormat.format(d));

		//		String secondDay = secondTime.split(":")[0];
		String secondHour = secondTime.split(":")[0];
		String secondMinute = secondTime.split(":")[1];
		String secondSecond = secondTime.split(":")[2];

		//		cal.add(Calendar.DATE, Integer.parseInt(secondDay));
		cal.add(Calendar.HOUR, Integer.parseInt(secondHour));
		cal.add(Calendar.MINUTE, Integer.parseInt(secondMinute));
		cal.add(Calendar.SECOND, Integer.parseInt(secondSecond));
		d = cal.getTime();

		return dateFormat.format(d);
	}


	/***
	 * Calculates time between given dates.
	 * 
	 * Format should be: "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param firstTime: First Date
	 * @param secondTime: Second Date
	 * 
	 * @return Calculates difference between given dates in following format:
	 *  "yyyy-MM-dd HH:mm:ss"
	 *  
	 *  @throws Negative results if second date is smaller than the first date. 
	 */
	public String diffTwoDates(String firstTime, String secondTime) {
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String dateDate1 = firstTime.split(" ")[0];
		int year1 = Integer.parseInt(dateDate1.split("-")[0]);
		int month1 = Integer.parseInt(dateDate1.split("-")[1]);
		int day1 =Integer.parseInt( dateDate1.split("-")[2]);

		String dateTime1 = firstTime.split(" ")[1];
		int hour1 = Integer.parseInt(dateTime1.split(":")[0]);
		int minute1 = Integer.parseInt(dateTime1.split(":")[1]);
		int second1 = Integer.parseInt(dateTime1.split(":")[2]);

		String dateDate2 = secondTime.split(" ")[0];
		int year2 = Integer.parseInt(dateDate2.split("-")[0]);
		int month2 = Integer.parseInt(dateDate2.split("-")[1]);
		int day2 =Integer.parseInt( dateDate2.split("-")[2]);

		String dateTime2 = secondTime.split(" ")[1];
		int hour2 = Integer.parseInt(dateTime2.split(":")[0]);
		int minute2 = Integer.parseInt(dateTime2.split(":")[1]);
		int second2 = Integer.parseInt(dateTime2.split(":")[2]);

		LocalDateTime dt1 = LocalDateTime.of(year1, month1, day1, hour1, minute1, second1);
		LocalDateTime dt2 = LocalDateTime.of(year2, month2, day2, hour2, minute2, second2);

		Period period = Period.between(dt1.toLocalDate(), dt2.toLocalDate());
		Duration duration = Duration.between(dt1, dt2);

		int years = period.getYears(); 
		int months =  period.getMonths();
		int days = period.getDays();

		long seconds = duration.getSeconds();

		long hours = seconds / (60 * 60);
		long minutes = ((seconds % (60 * 60)) / 60);
		long secs = (seconds % 60);

		String diffDates = years + "-"+months+ "-"+days+ " "+hours+ ":"+minutes+ ":"+secs;

		log.debug(years + " years " + months  + " months "  + days + " days " +
				hours + " hours " + minutes + " minutes " + secs + " seconds.");

		return diffDates;
	}

	/***
	 * Calculates time between given dates in a simplified format
	 * 
	 * Format should be: "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param firstTime: First Date
	 * @param secondTime: Second Date
	 * 
	 * @return Calculates difference between given dates in following format:
	 *  "yyyy-MM-dd HH:mm:ss"
	 *  
	 *  @throws Negative results if second date is smaller than the first date. 
	 */
	public String diffTwoDatesSimplied(String firstTime, String secondTime) {
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String dateDate1 = firstTime.split(" ")[0];
		int year1 = Integer.parseInt(dateDate1.split("-")[0]);
		int month1 = Integer.parseInt(dateDate1.split("-")[1]);
		int day1 =Integer.parseInt( dateDate1.split("-")[2]);

		String dateTime1 = firstTime.split(" ")[1];
		int hour1 = Integer.parseInt(dateTime1.split(":")[0]);
		int minute1 = Integer.parseInt(dateTime1.split(":")[1]);
		int second1 = Integer.parseInt(dateTime1.split(":")[2]);

		String dateDate2 = secondTime.split(" ")[0];
		int year2 = Integer.parseInt(dateDate2.split("-")[0]);
		int month2 = Integer.parseInt(dateDate2.split("-")[1]);
		int day2 =Integer.parseInt( dateDate2.split("-")[2]);

		String dateTime2 = secondTime.split(" ")[1];
		int hour2 = Integer.parseInt(dateTime2.split(":")[0]);
		int minute2 = Integer.parseInt(dateTime2.split(":")[1]);
		int second2 = Integer.parseInt(dateTime2.split(":")[2]);

		LocalDateTime dt1 = LocalDateTime.of(year1, month1, day1, hour1, minute1, second1);
		LocalDateTime dt2 = LocalDateTime.of(year2, month2, day2, hour2, minute2, second2);

		Period period = Period.between(dt1.toLocalDate(), dt2.toLocalDate());
		Duration duration = Duration.between(dt1, dt2);

		int years = period.getYears(); 
		int months =  period.getMonths();
		int days = period.getDays();

		long seconds = duration.getSeconds();

		long hours = seconds / (60 * 60);
		long minutes = ((seconds % (60 * 60)) / 60);
		long secs = (seconds % 60);

		String outputDate = "";
		String outputDateShort = "";

		if (years > 0) {
			if (years == 1) {
				outputDate += years + " Year ";
				outputDateShort += years + "-";
			}
			else {
				outputDate += years + " Years ";
				outputDateShort += years + "-";
			}
		}
		if (months > 0) {
			if (months == 1) {
				outputDate += months + " Month ";
				outputDateShort += months + "-";
			}
			else {
				outputDate += months + " Months ";
				outputDateShort += months + "-";
			}
		}
		if (days > 0) {
			if (days == 1) {
				outputDate += days + " Day ";
				outputDateShort += days + " ";
			}
			else {
				outputDate += days + " Days ";
				outputDateShort += days + " ";
			}
		}
		if (hours > 0) {
			if (hours == 1) {
				outputDate += hours + " Hour ";
				outputDateShort += hours + ":";
			}
			else {
				outputDate += hours + " Hours ";
				outputDateShort += hours + ":";
			}
		}
		if (minutes > 0) {
			if (minutes == 1) {
				outputDate += minutes + " Minute ";
				outputDateShort += minutes + ":";
			}
			else {
				outputDate += minutes + " Minutes ";
				outputDateShort += minutes + ":";
			}
		}
		if (secs > 0) {
			if (secs == 1) {
				outputDate += secs + " Second";
				outputDateShort += secs;
			}
			else {
				outputDate += secs + " Seconds";
				outputDateShort += secs;
			}
		}

		return outputDateShort;
	}

	/***
	 * Calculates time between given dates and returns value in seconds.
	 * 
	 * Format should be: "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param firstTime: First Date
	 * @param secondTime: Second Date
	 * 
	 * @return Calculates difference between given dates in following format:
	 *  "yyyy-MM-dd HH:mm:ss"
	 *  
	 *  @throws Negative results if second date is smaller than the first date. 
	 *  
	 */
	public long diffTwoDatesInSeconds(String firstTime, String secondTime) {

		String dateDate1 = firstTime.split(" ")[0];
		int year1 = Integer.parseInt(dateDate1.split("-")[0]);
		int month1 = Integer.parseInt(dateDate1.split("-")[1]);
		int day1 =Integer.parseInt( dateDate1.split("-")[2]);

		String dateTime1 = firstTime.split(" ")[1];
		int hour1 = Integer.parseInt(dateTime1.split(":")[0]);
		int minute1 = Integer.parseInt(dateTime1.split(":")[1]);
		int second1 = Integer.parseInt(dateTime1.split(":")[2]);

		String dateDate2 = secondTime.split(" ")[0];
		int year2 = Integer.parseInt(dateDate2.split("-")[0]);
		int month2 = Integer.parseInt(dateDate2.split("-")[1]);
		int day2 =Integer.parseInt( dateDate2.split("-")[2]);

		String dateTime2 = secondTime.split(" ")[1];
		int hour2 = Integer.parseInt(dateTime2.split(":")[0]);
		int minute2 = Integer.parseInt(dateTime2.split(":")[1]);
		int second2 = Integer.parseInt(dateTime2.split(":")[2]);

		LocalDateTime dt1 = LocalDateTime.of(year1, month1, day1, hour1, minute1, second1);
		LocalDateTime dt2 = LocalDateTime.of(year2, month2, day2, hour2, minute2, second2);

		Duration duration = Duration.between(dt1, dt2);

		long seconds = duration.getSeconds();

		return seconds;
	}

	/**
	 * 
	 * Calculates the time passed in Minutes and seconds
	 * 
	 * @param timeInMinutes
	 * @param timeInSeconds
	 * 
	 * @return String representation of calculated time mm:ss
	 */
	public String calculateTimeFromSecs(String timeInMinutes, String timeInSeconds) {
		try {
			double minutes = Double.parseDouble(timeInMinutes);
			double secondsInMinutes = Double.parseDouble(timeInSeconds) / 60;

			double timeCalculated = secondsInMinutes - minutes;

			//			System.out.println(timeCalculated * 60);

			String outputTime = (int) Math.round(minutes) + ":" + (int) Math.round(timeCalculated * 60);

			return String.valueOf(outputTime);


		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Get the tag name of this element.
	 * E.g. "input"
	 * 
	 * @param webEl - The WebElement
	 * 
	 * @return Tag of the webElement
	 */
	public String getTagName(WebElement webEl) {
		String tagName = webEl.getTagName();
		return tagName;
	}

	/**
	 * Gets the Value of the attribute "data-type"
	 * 
	 * @param webEl - The WebElement
	 * @return Value of the attribute "data-type"
	 */
	public String getDataType(WebElement webEl) {
		String dataType = webEl.getAttribute("data-type");
		return dataType;
	}

	/**
	 * Gets the Value of the attribute "type"
	 * 
	 * @param webEl - The WebElement
	 * @return Value of the attribute "type"
	 */
	public String getType(WebElement webEl) {
		String dataType = webEl.getAttribute("type");
		return dataType;
	}

	/**
	 * Gets the Value of the attribute "class"
	 * 
	 * @param webEl - The WebElement
	 * @return Value of the attribute "class"
	 */
	public String getClass(WebElement webEl) {
		String dataType = webEl.getAttribute("class");
		return dataType;
	}

	/**
	 * Gets the Value of the attribute "aria-required"
	 * 
	 * @param webEl - The WebElement
	 * @return Value of the attribute "aria-required"
	 */
	public String getAriaRequired(WebElement webEl) {
		String ariaRequired = webEl.getAttribute("aria-required");
		return ariaRequired;
	}

	/**
	 * Gets the Value of the attribute "required"
	 * 
	 * @param webEl - The WebElement
	 * @return Value of the attribute "required"
	 */
	public String getRequired(WebElement webEl) {
		String required = webEl.getAttribute("required");
		return required;
	}

	/***
	 * Allows the access in an OS window to search for a file to be uploaded.
	 * 
	 * @exception: Only works running locally and in a Windows based environment.  
	 * 
	 * @param fileLocation (full path with filename)
	 */
	public void uploadFile(String fileLocation) {
		StringSelection stringSelection = new StringSelection(fileLocation);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

		Robot robot = null;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		robot.delay(300);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(250);
		robot.keyRelease(KeyEvent.VK_ENTER);

	}

	/**
	 * Send the ExtentReport.html file via email to the StkAppAutomation Team
	 * 
	 * @param reportPath
	 * @param reportName
	 */
	public void sendEmailWithReport(String reportPath, String reportName) {
		// Create object of Property file
		Properties props = new Properties();

		// this will set host of server- you can change based on your requirement 
		props.put("mail.smtp.host", "smtp.office365.com");

		// set the port of socket factory 
		props.put("mail.smtp.socketFactory.port", "587");
	    props.put("mail.smtp.socketFactory.fallback", "false");

		// set socket factory
//		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

		// set the authentication to true
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable",true);

		// set the port of SMTP server
		props.put("mail.smtp.port", "587");

		// This will handle the complete authentication
		Session session = Session.getDefaultInstance(props,

			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("jorge.morales@App.edu", "Droanougmk3}");
				}
			});

		try {

			// Create object of MimeMessage class
			Message message = new MimeMessage(session);

			// Set the from address
			message.setFrom(new InternetAddress("jorge.morales@App.edu"));

			// Set the recipient address
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("jorge.morales@App.edu,moises.valdes@App.edu,eduardo.alvarez@App.edu"));

			// Add the subject link
			message.setSubject("AppName Execution report : " + this.getTodayDateString());

			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();

			// Set the body of email
			messageBodyPart1.setText("AppName Execution report");

			// Create another object to add another content
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			// Mention the file which you want to send
			// Create data source and pass the filename
			DataSource source = new FileDataSource(reportPath);

			// set the handler
			messageBodyPart2.setDataHandler(new DataHandler(source));

			// set the file
			messageBodyPart2.setFileName(reportName);

			// Create object of MimeMultipart class
			Multipart multipart = new MimeMultipart();

			// add body part 1
			multipart.addBodyPart(messageBodyPart2);

			// add body part 2
			multipart.addBodyPart(messageBodyPart1);

			// set the content
			message.setContent(multipart);

			// finally send the email
			Transport.send(message);

			System.out.println("=====Email Sent=====");

		} catch (MessagingException e) {

			throw new RuntimeException(e);

		}
	}


}


