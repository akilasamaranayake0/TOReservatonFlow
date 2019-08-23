package com.rezgateway.automation.TOReservatonFlow;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class HotelOnlyWebReservationRunner {

	public WebDriver driver;
	private static Logger logger = null;
	private static WebDriverWait driverwait;

	public String driverPathChrome = "C:/eclipse/HY/TOReservatonFlow/Resourses/chromedriver.exe";
	public String driverPathFirfox = "C:/eclipse/HY/TOReservatonFlow/Resourses/geckodriver.exe";
	public String UN = "AkilaB2B";
	public String PW = "123456";
	public String reservationType = "H";
	public String Destination = "C|6.92708|79.86124|";
	public String HotelOccupancy = "{1,0,[]};{1,2,[5,2]};{1,1,[5]}";
	public int HotelCount = 1;

	public String[] HotelName = { "Test Supplement Hotel" };
	public String HotelSearchType = "Y";

	public HotelOnlyWebReservationRunner() {
		logger = Logger.getLogger(HotelOnlyWebReservationRunner.class);
	}

	@Parameters("browser")
	@BeforeClass
	public void setup(String browser) {

		try {

			if (browser.equalsIgnoreCase("chorome")) {

				System.setProperty("webdriver.chrome.driver", driverPathChrome);
				driver = new ChromeDriver();
				driverwait = new WebDriverWait(driver, 6);
				driver.manage().window().maximize();

			} else if (browser.equalsIgnoreCase("firefox")) {

				System.setProperty("webdriver.gecko.driver", driverPathFirfox);
				driver = new FirefoxDriver();
				driverwait = new WebDriverWait(driver, 6);
				driver.manage().window().maximize();

			} else {
				// TODO
			}

		} catch (Exception e) {
			Assert.fail(e.toString());
			logger.error(e);
		}

	}

	@Parameters({ "ProtalURL", "browser" })
	@Test(invocationCount = 1)
	public void runnerF(String ProtalURL, String browser) throws InterruptedException {

		driver.get(ProtalURL);
		driver.findElement(By.id("user_id")).sendKeys(UN);
		driver.findElement(By.id("password")).sendKeys(PW);
		driver.findElement(By.id("loginbutton")).submit();

		Thread.sleep(3000);

		driverwait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/table/tbody/tr[2]/td/div/table/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table[4]/tbody/tr[2]/td[2]/a")));
		driver.findElement(By.linkText("Make Reservations")).click();

		Thread.sleep(4000);

		driver.switchTo().frame("bec_container_frame");

		driver.findElement(By.id("hLi")).click();

		Thread.sleep(3000);

		// Set Destination
		driver.findElement(By.id("H_Loc")).sendKeys("Colombo");

		WebDriverWait wait = new WebDriverWait(driver, 7);

		driverwait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("/html/body/ul[5]")));
		Thread.sleep(1000);
		driver.findElement(By.id("H_Loc")).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(By.id("H_Loc")).sendKeys(Keys.ENTER);
		logger.debug("<<<<<<< Destination " + driver.findElement(By.id("H_Loc")).getText() + "<<<<<<<<");

		// Set Checkin Date
		driver.findElement(By.id("ho_departure_temp")).click();

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("ui-datepicker-div")));
		WebElement calender = driver.findElement(By.id("ui-datepicker-div"));
		calender.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/a[2]")).click();

		WebElement Week = driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/table/tbody/tr[3]"));

		String Month = driver.findElement(By.className("ui-datepicker-month")).getText();
		String Year = driver.findElement(By.className("ui-datepicker-year")).getText();
		String CINDate = driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/table/tbody/tr[3]/td[1]/a")).getText();

		Week.findElement(By.xpath("//*[@id='ui-datepicker-div']/table/tbody/tr[3]/td[1]")).click();

		String CheckIN = CINDate + "-" + Month + "-" + Year;
		logger.debug("<<<<<<< ChkIN_" + CheckIN + "<<<<<<<<");

		// Set Checkout Date
		driver.findElement(By.id("ho_arrival_temp")).click();

		driverwait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("ui-datepicker-div")));

		WebElement Week2 = driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/table/tbody/tr[3]"));
		String COUTDate = driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/table/tbody/tr[3]/td[4]")).getText();
		String Checkout = COUTDate + "-" + Month + "-" + Year;

		Week2.findElement(By.xpath("//*[@id='ui-datepicker-div']/table/tbody/tr[3]/td[4]")).click();

		logger.debug("<<<<<<< ChkOUT_" + Checkout + " <<<<<<<<");

		// Set Room Count

		driver.findElement(By.id("norooms_H")).click();

		Select select = new Select(driver.findElement(By.id("norooms_H")));

		select.selectByValue(Integer.toString(HotelOccupancy.split(";").length));

		// Set the Occupancy

		// "{1,0,[]};{1,2,[5,2]};{1,1,[5]}";

		driver.findElement(By.id("setvalnew_H")).click();

		driverwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("paxRoomDisplayWrap_H")));
		driver.findElement(By.id("paxRoomDisplayWrap_H")).click();
		List<WebElement> RoomOccu = driver.findElements(By.className("hotel-passenger-block"));

		int index = 0;
		int rcount = 1;
		for (WebElement ele : RoomOccu) {

			String RoomAdultCount = (HotelOccupancy.split(";")[index]).substring(1, 2);
			logger.debug("<<<<<<< " + rcount + "_RoomAdultCount_" + RoomAdultCount + " <<<<<<< ");

			Select rAdult = new Select(ele.findElement(By.id("R" + rcount + "occAdults_H")));
			rAdult.selectByValue(RoomAdultCount);

			String RoomChidCount = HotelOccupancy.split(";")[index].substring(3, 4);
			logger.debug("<<<<<<< " + rcount + "_RoomChidCount_" + RoomChidCount + " <<<<<<< ");

			// Set the Child Count for Each Room
			if ((!RoomChidCount.equals("0")) || (!(null == RoomAdultCount))) {

				Select rChild = new Select(ele.findElement(By.id("R" + rcount + "occChildren_H")));
				rChild.selectByValue(RoomChidCount);

				// Sample Occupancy Format = "{1,0,[]};{1,2,[5,2]};{1,1,[5]}";

				// Set the Child Ages for Each Room and each Child
				int childCount = 1;
				for (int i = 0; i < Integer.parseInt(RoomChidCount); i++) {

					String childAges = HotelOccupancy.split(";")[index].substring(5);
					String[] Ages = childAges.replace("{", "").replace("}", "").replace("[", "").replace("]", "").split(",");

					logger.debug("<<<<<<< " + rcount + "_RoomChid" + i + "Age " + Ages[i] + " <<<<<<< ");

					Select rChildAges = new Select(ele.findElement(By.id("H_R" + rcount + "childage_" + childCount)));

					rChildAges.selectByValue(Ages[i]);
					childCount++;
				}

			}
			index++;
			rcount++;
		}

		// click the search button

		driver.findElement(By.id("search_btns_h")).click();

		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		// Select the Hotel

		if ("Y".equals(HotelSearchType)) {

			// Select the Hotel by Hotel Name

			try {
				driver.switchTo().defaultContent();
				driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("rez-preloader")));
				Thread.sleep(10000);
				driverwait.until(ExpectedConditions.presenceOfElementLocated(By.id("name_text_WJ_6")));
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			if (browser.equals("chorome")) {
				try {
					driver.findElement(By.id("name_text_WJ_6")).click();
					driver.findElement(By.id("name_text_WJ_6")).sendKeys(HotelName[0]);
					driver.wait(3000);
					driverwait.until(ExpectedConditions.elementToBeClickable(driver.findElement((By.xpath(("/html/body/ul[1]/li/a"))))));
					WebElement ele1 = driver.findElement(By.xpath("/html/body/ul[1]/li/a"));
					ele1.click();
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				driver.findElement(By.id("name_text_WJ_6")).click();
				driver.findElement(By.id("name_text_WJ_6")).sendKeys(HotelName[0]);
				driver.findElement(By.id("name_text_WJ_6")).sendKeys(Keys.ARROW_DOWN);
				Actions builder = new Actions(driver);
				builder.moveToElement(driver.findElement((By.className(("ui-menu-item"))))).perform();	
				builder.moveToElement(driver.findElement((By.className(("ui-menu-item"))))).click().perform();
				
				Thread.sleep(3000);
			}

			// ele.findElement(By.xpath("/html/body/ul[2]/li/a")).sendKeys(Keys.ENTER);

			WebElement addTOCart = driver.findElement(By.xpath("/html/body/div[8]/div/div[6]/div[5]/div/div[2]/div[2]/div[1]/div[1]/div[2]/div/div[2]/div/div[4]"));
			addTOCart.findElement(By.id("fh-book-btn")).click();

			// Click the Check out Button from the Up-selling page
			try {
				driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("rez-preloader")));
				Thread.sleep(7000);
				driverwait.until(ExpectedConditions.presenceOfElementLocated(By.id("cart_display_WJ_1")));

			} catch (Exception e1) {
				logger.error(e1);
			}

			WebElement upsellingCart = driver.findElement(By.id("cart_display_WJ_1"));
			upsellingCart.findElement(By.className("btn-primary-success")).click();
			// Fill the Room Occupancy Details

			driverwait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("rez-preloader")));
			Thread.sleep(3000);
			List<WebElement> ele = driver.findElements(By.cssSelector("[type=text]"));

			logger.debug(ele.size());

			List<WebElement> ele2 = driver.findElements(By.cssSelector(".guest-first-name.form-control"));

			logger.debug(ele2.size());

			for (int i = 0; i < driver.findElements(By.xpath("//*[@id='firstName_guest-id~0']")).size(); i++) {

				try {

					ele.get(i).findElement(By.xpath("//*[@id='firstName_guest-id~0']")).clear();
					ele.get(i).findElement(By.xpath("//*[@id='firstName_guest-id~0']")).sendKeys("Adult_" + numberToWord(i + 1));
					ele.get(i).findElement(By.xpath("//*[@id='lastName_guest-id~0']")).clear();
					ele.get(i).findElement(By.xpath("//*[@id='firstName_guest-id~0']")).sendKeys("Adult_" + numberToWord(i + 1) + "lastName");
				} catch (Exception e) {
					logger.error(e);
				}

			}

		} else {
			Thread.sleep(3000);
			// Select the First Hotel from the Results set

			WebElement addTOCart = driver.findElement(By.xpath("/html/body/div[8]/div/div[6]/div[5]/div/div[2]/div[2]/div[1]/div[1]/div[2]/div/div[2]/div/div[4]"));
			addTOCart.findElement(By.id("fh-book-btn")).click();

			// Click the Check out Button from the Upselling page
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("cart_display_WJ_1")));
			WebElement upsellingCart = driver.findElement(By.id("cart_display_WJ_1"));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='cart_display_WJ_1']/div[2]/div/div/div/a")));
			upsellingCart.findElement(By.xpath("//*[@id='cart_display_WJ_1']/div[2]/div/div/div")).click();

			// enter the Hotel Room Occupancy Details
			List<WebElement> hotelList = driver.findElements(By.className("col-xs-12"));

			for (WebElement hotelTemp : hotelList) {

				List<WebElement> hotelOccupancy = hotelTemp.findElements(By.cssSelector(".occ-guest-details.guest-1"));

				if (HotelOccupancy.split(";").length == hotelOccupancy.size()) {

					for (WebElement roomOccu : hotelOccupancy) {

					}

				} else {
					logger.error("Actual Room Count not setted in the Payment Page" + HotelOccupancy.split(";").length + "_" + hotelOccupancy.size());
					Assert.fail("Actual Room Count not setted in the Payment Page");

				}
			}

		}

	}

	private static String numberToWord(int number) {
		// variable to hold string representation of number
		String words = "";
		String unitsArray[] = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
		String tensArray[] = { "zero", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };

		if (number == 0) {
			return "zero";
		}
		// add minus before conversion if the number is less than 0
		if (number < 0) { // convert the number to a string
			String numberStr = "" + number;
			// remove minus before the number
			numberStr = numberStr.substring(1);
			// add minus before the number and convert the rest of number
			return "minus " + numberToWord(Integer.parseInt(numberStr));
		}
		// check if number is divisible by 1 million
		if ((number / 1000000) > 0) {
			words += numberToWord(number / 1000000) + " million ";
			number %= 1000000;
		}
		// check if number is divisible by 1 thousand
		if ((number / 1000) > 0) {
			words += numberToWord(number / 1000) + " thousand ";
			number %= 1000;
		}
		// check if number is divisible by 1 hundred
		if ((number / 100) > 0) {
			words += numberToWord(number / 100) + " hundred ";
			number %= 100;
		}

		if (number > 0) {
			// check if number is within teens
			if (number < 20) {
				// fetch the appropriate value from unit array
				words += unitsArray[number];
			} else {
				// fetch the appropriate value from tens array
				words += tensArray[number / 10];
				if ((number % 10) > 0) {
					words += "-" + unitsArray[number % 10];
				}
			}
		}

		return words;
	}

	@AfterClass
	public void quit() {
		// driver.quit();
	}

}
