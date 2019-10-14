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
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.rezgateway.automation.core.UIBaseTest;

public class HotelOnlyWebReservationRunner extends UIBaseTest {

	public WebDriver driver;
	private static Logger logger = null;
	private static WebDriverWait driverwait;

	public String driverPathChrome = "Resourses/chromedriver.exe";
	public String driverPathFirfox = "Resourses/geckodriver.exe";

	public String UN = "AkilaB2B";
	public String PW = "123456";
	public String reservationType = "H";
	public String city = "Colombo";
	public String Destination = ", Sri Lanka";
	public String fromLocationData = "C|6.92708|79.86124|";
	public String HotelOccupancy = "{1,0,[]};{1,2,[5,2]};{1,1,[5]}";
	public int HotelCount = 1;

	public String[] HotelName = { "Test Supplement Hotel" };
	public String HotelSearchType = "Y";

	public String CheckInDate = "01-April-2020";
	public String CheckOutDate = "05-April-2020";

	// Bandaranaike International Airport(CMB),

	@FindBy(id = "user_id")
	private WebElement input_field_user_name;

	@FindBy(id = "password")
	private WebElement input_field_password;

	@FindBy(id = "loginbutton")
	private WebElement button_login;

	@FindBy(xpath = "/html/body/table/tbody/tr[2]/td/div/table/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table[4]/tbody/tr[2]/td[2]/a")
	private WebElement button_make_reservation;

	@FindBy(linkText = "Make Reservations")
	private WebElement button_make_reservation_linktext;

	@FindBy(id = "hLi")
	private WebElement button_hotel_only_bec;

	@FindBy(id = "H_Loc")
	private WebElement input_field_hotel_destination;

	@FindBy(xpath = "/html/body/ul[5]")
	private WebElement element_hotel_destinations;

	@FindBys({ @FindBy(xpath = "/html/body/ul[5]/li/a/span/span/text()[2]") })
	private List<WebElement> elements_country_list;

	@FindBys({ @FindBy(xpath = "/html/body/ul[5]/li/a/span/span[1]") })
	private List<WebElement> elements_city_list;

	@FindBy(id = "ho_departure_temp")
	private WebElement button_check_in_date;

	@FindBy(id = "ho_arrival_temp")
	private WebElement button_check_out_date;

	@FindBy(id = "ui-datepicker-div")
	private WebElement element_bec_hotelonly_calender;

	@FindBy(xpath = "//*[@id='ui-datepicker-div']/div/a[2]")
	private WebElement button_calender_next;

	@FindBy(xpath = "//*[@id'ui-datepicker-div']/div/a[1]")
	private WebElement button_calender_previous;

	@FindBy(className = "ui-datepicker-month")
	private WebElement element_calender_current_displaying_month;

	@FindBy(className = "ui-datepicker-year")
	private WebElement element_calender_current_displaying_year;

	@FindBy(id = "norooms_H")
	private WebElement drop_down_room_count;

	@FindBy(id = "setvalnew_H")
	private WebElement button_select_occupancy;

	@FindBy(id = "paxRoomDisplayWrap_H")
	private WebElement element_select_occupancy_pane;

	@FindBys({ @FindBy(className = "hotel-passenger-block") })
	private List<WebElement> elements_room_occupancy;

	@FindBy(id = "search_btns_h")
	private WebElement button_search;

	@FindBys({ @FindBy(className = "fh-hotel-results-block") })
	private List<WebElement> elements_hotel_result_blocks;

	@FindBy(id = "rez-preloader")
	private WebElement element_rez_preloader;

	@FindBy(id = "name_text_WJ_6")
	private WebElement input_field_hotel_name;

	@FindBys({ @FindBy(xpath = "//*[@class='ui-corner-all']") })
	private List<WebElement> elements_hotel_suggestion_list;

	@FindBy(xpath = "//*[@id='fh-book-btn' and contains(@onclick,'cartHandle')]")
	private WebElement button_add_to_cart;

	@FindBy(id = "cart_display_WJ_1")
	private WebElement element_cart_panel;
	
	@FindBy(linkText="Checkout ")
	private WebElement button_checkout_linktext;

	public HotelOnlyWebReservationRunner() {

		logger = Logger.getLogger(HotelOnlyWebReservationRunner.class);
	}

	@Parameters("browser")
	@BeforeClass
	public void setup(String browser) {

		try {

			if (browser.equalsIgnoreCase("chrome")) {

				System.setProperty("webdriver.chrome.driver", driverPathChrome);
				driver = new ChromeDriver();
				PageFactory.initElements(driver, this);
				driverwait = new WebDriverWait(driver, 6);
				driver.manage().window().maximize();

			} else if (browser.equalsIgnoreCase("firefox")) {

				System.setProperty("webdriver.gecko.driver", driverPathFirfox);
				driver = new FirefoxDriver();
				PageFactory.initElements(driver, this);
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

	@Parameters("ProtalURL")
	@Test(invocationCount = 1, priority = 0)
	public void LoginTest(String ProtalURL) {

		/******************************************************************/
		ITestResult result = Reporter.getCurrentTestResult();
		result.setAttribute("TestName", "Login Test");
		result.setAttribute("Expected", "Should be logged in Successfully");
		/******************************************************************/

		try {
			driver.get(ProtalURL);
			this.input_field_user_name.sendKeys(UN);
			this.input_field_password.sendKeys(PW);
			this.button_login.submit();

			if (this.button_make_reservation_linktext.isDisplayed()) {
				result.setAttribute("Actual", "Successfully Pass Logged the TO Back Office ");
				takeScreenshot("Successfully Pass Logged the TO Back Office");
			} else {
				result.setAttribute("Actual", "Login is Failed ");
				Assert.fail("Login is Failed ");
			}

		} catch (Exception e) {
			result.setAttribute("Actual", "Error Genrated ");
			Assert.fail("Error Genrated ", e);
		}

	}

	@Test(priority = 1)
	public void TestBookingEngineAvailability() throws InterruptedException {

		/******************************************************************/
		ITestResult result = Reporter.getCurrentTestResult();
		result.setAttribute("TestName", "Test Web Hotel Only BEC loading in the WEB Reservation Flow");
		result.setAttribute("Expected", "Should Display Hotel Only BEC in the Main BEC Page");
		/******************************************************************/

		try {

			this.button_make_reservation_linktext.click();
			Thread.sleep(4000);
			driver.switchTo().frame("bec_container_frame");
			if (this.button_hotel_only_bec.isDisplayed()) {

				this.button_hotel_only_bec.click();
				Thread.sleep(3000);
				if (this.input_field_hotel_destination.isDisplayed()) {

					result.setAttribute("Actual", "HotelOnly BEC is Available in the TO Web Booking Engine Page");
					takeScreenshot("HotelOnly BEC is Available in the TO Web Booking Engine Page");
				} else {

					result.setAttribute("Actual", "HotelOnly BEC Button is Not Available in the TO Web Booking Engine Page");
					Assert.fail("HotelOnly BEC Button is Not Available in the TO Web Booking Engine Page");
				}

			} else {

				result.setAttribute("Actual", "HotelOnly BEC Button is Not Available in the TO Web Booking Engine Page");
				Assert.fail("HotelOnly BEC Button is Not Available in the TO Web Booking Engine Page");
			}

		} catch (Exception e) {
			result.setAttribute("Actual", "Error Genrated ");
			Assert.fail("Error Genrated ", e);
		}

	}

	@Test(priority = 2)
	public void testHotelDestinaitionSuggestionFromTheBEC() {

		/******************************************************************/
		ITestResult result = Reporter.getCurrentTestResult();
		result.setAttribute("TestName", "Testing the Given Destinaition Suggestions From The BEC");
		result.setAttribute("Expected", "Should be Suggested entered Hotel Destination From the BEC");
		/******************************************************************/

		try {
			// Set Destination
			this.input_field_hotel_destination.sendKeys(city);

			WebDriverWait wait = new WebDriverWait(driver, 7);

			wait.until(ExpectedConditions.visibilityOf(this.element_hotel_destinations));

			Thread.sleep(1000);

			if (this.elements_city_list.size() >= 1) {

				this.input_field_hotel_destination.sendKeys(Keys.ARROW_DOWN);
				this.input_field_hotel_destination.sendKeys(Keys.ENTER);

				logger.debug("<<<<<<< Destination " + this.input_field_hotel_destination.getText() + "<<<<<<<<");

				result.setAttribute("Actual", "City List Is Suggested from the Hotel Only Bec ");
				takeScreenshot("City List Is Suggested from the Hotel Only Bec");
			} else {
				result.setAttribute("Actual", "City List Is Empty");
				Assert.fail("City List Is Empty");
			}

		} catch (Exception e) {
			result.setAttribute("Actual", "Error Genrated <br>" + e + "</br>");
			Assert.fail("Error Genrated  ", e);
		}

	}

	@Test(priority = 3)
	public void testHotelResultsAvialablity() {

		/******************************************************************/
		ITestResult result = Reporter.getCurrentTestResult();
		result.setAttribute("TestName", "Testing Hotel Results availablity");
		result.setAttribute("Expected", "Should be display Hotel Results for the given Search Criteria");
		/******************************************************************/

		try {

			// Set Check-in Date

			this.button_check_in_date.click();
			WebDriverWait wait = new WebDriverWait(driver, 7);
			wait.until(ExpectedConditions.visibilityOfAllElements(this.element_bec_hotelonly_calender));

			CheckInDate = selectDateFromTheCalendar(CheckInDate, this.element_calender_current_displaying_year, this.element_calender_current_displaying_month, this.button_calender_next, this.element_bec_hotelonly_calender);
			logger.debug("<<<<<<< ChkOUT_" + CheckInDate + " <<<<<<<<");

			// Set Check-out Date
			this.button_check_out_date.click();
			wait.until(ExpectedConditions.visibilityOfAllElements(this.element_bec_hotelonly_calender));

			CheckOutDate = selectDateFromTheCalendar(CheckOutDate, this.element_calender_current_displaying_year, this.element_calender_current_displaying_month, this.button_calender_next, this.element_bec_hotelonly_calender);
			logger.debug("<<<<<<< ChkOUT_" + CheckOutDate + " <<<<<<<<");

			// Set Room Count

			this.drop_down_room_count.click();

			Select select = new Select(this.drop_down_room_count);

			select.selectByValue(Integer.toString(HotelOccupancy.split(";").length));

			// Set the Occupancy
			this.button_select_occupancy.click();

			driverwait.until(ExpectedConditions.visibilityOfAllElements(this.element_select_occupancy_pane));
			this.element_select_occupancy_pane.click();

			int index = 0;
			int rcount = 1;

			// "{1,0,[]};{1,2,[5,2]};{1,1,[5]}";

			for (WebElement ele : elements_room_occupancy) {

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

			this.button_search.click();

			if (this.elements_hotel_result_blocks.size() >= 2) {
				result.setAttribute("Actual", " Hotel Results are Avilable ");
				takeScreenshot("Hotel Results are Avilable");
			} else {
				result.setAttribute("Actual", "Hotel Results are <b>Not</b> Avilable");
				Assert.fail("Hotel Results are <b>Not</b> Avilable");
			}

		} catch (Exception e) {
			result.setAttribute("Actual", "Error Genrated <br>" + e + "</br>");
			Assert.fail("Error Genrated <br> ", e);
		}

	}

	@Parameters("browser")
	@Test(priority = 4)
	public void testUserGivenHotelAvilability(String webBrowserType) {

		/******************************************************************/
		ITestResult result = Reporter.getCurrentTestResult();
		result.setAttribute("TestName", "Testing Hotel Results availablity");
		result.setAttribute("Expected", "Should be display Hotel Results for the given Search Criteria");
		/******************************************************************/

		try {
			// Select the Hotel
			if ("Y".equals(HotelSearchType)) {

				// Select the Hotel by Hotel Name

				try {
					driver.switchTo().defaultContent();
					driverwait.until(ExpectedConditions.invisibilityOf(this.element_rez_preloader));
					Thread.sleep(10000);
					driverwait.until(ExpectedConditions.visibilityOfAllElements(this.input_field_hotel_name));
				} catch (Exception e3) {
					result.setAttribute("Actual", "Error Genrated ");
					Assert.fail("Error Genrated ", e3);
				}

				if (webBrowserType.equals("chorome")) {
					try {
						this.input_field_hotel_name.click();
						this.input_field_hotel_name.sendKeys(HotelName[0]);

						Thread.sleep(2000);

						driverwait.until(ExpectedConditions.visibilityOfAllElements(elements_hotel_suggestion_list));

						if (this.elements_hotel_suggestion_list.size() >= 2) {

							result.setAttribute("Actual", HotelName[0] + " is Displaying in the Hotel DropDown List");
							takeScreenshot(HotelName[0] + " is Displaying in the Hotel DropDown List");
							this.elements_hotel_suggestion_list.get(0).click();
							logger.debug("<<<<<<<<< " + this.input_field_hotel_name.getText() + " >>>>>>>>>>>>>>>");

						} else {
							result.setAttribute("Actual", HotelName[0] + " is <b> Not </b> Displaying in the Hotel DropDown List");
							Assert.fail(HotelName[0] + " is <b> Not </b> Displaying in the Hotel DropDown List");
						}
						Thread.sleep(3000);
					} catch (Exception e) {
						result.setAttribute("Actual", "Error Genrated ");
						Assert.fail("Error Genrated ", e);
					}
				} else {
					this.input_field_hotel_name.click();
					this.input_field_hotel_name.sendKeys(HotelName[0]);
					this.input_field_hotel_name.sendKeys(Keys.ARROW_DOWN);

					if (this.elements_hotel_suggestion_list.size() >= 2) {

						result.setAttribute("Actual", HotelName[0] + " is Displaying in the Hotel DropDown List");
						takeScreenshot(HotelName[0] + " is Displaying in the Hotel DropDown List");
						logger.debug("<<<<<<<<< " + this.input_field_hotel_name.getText() + " >>>>>>>>>>>>>>>");

					} else {
						result.setAttribute("Actual", HotelName[0] + " is <b> Not </b> Displaying in the Hotel DropDown List");
						Assert.fail(HotelName[0] + " is <b> Not </b> Displaying in the Hotel DropDown List");
					}

					Actions builder = new Actions(driver);
					builder.moveToElement(this.elements_hotel_suggestion_list.get(0)).perform();
					builder.moveToElement(this.elements_hotel_suggestion_list.get(0)).click().perform();

					Thread.sleep(3000);
				}

			}
		} catch (Exception e) {
			result.setAttribute("Actual", "Error Genrated ");
			Assert.fail("Error Genrated ", e);
		}
	}

	@Test(priority = 5)
	public void testHotelRatesIntheUpsellingPage() {
		
		/******************************************************************/
		ITestResult result = Reporter.getCurrentTestResult();
		result.setAttribute("TestName", "Testing Hotel Results availablity");
		result.setAttribute("Expected", "Should be display Hotel Results for the given Search Criteria");
		/******************************************************************/

		// Click the Check out Button from the Up-selling page
		try {

			this.button_add_to_cart.click();

			driverwait.until(ExpectedConditions.invisibilityOf(this.element_rez_preloader));
			
			Thread.sleep(7000);
			
			driverwait.until(ExpectedConditions.elementToBeClickable(this.element_cart_panel));

			Thread.sleep(2000);
			button_checkout_linktext.click();
			
			// Fill the Room Occupancy Details

			driverwait.until(ExpectedConditions.invisibilityOf(this.element_rez_preloader));
			Thread.sleep(3000);
			
			List<WebElement> ele = driver.findElements(By.cssSelector("[type=text]"));

			logger.debug(ele.size());

			List<WebElement> ele2 = driver.findElements(By.cssSelector(".guest-first-name.form-control"));

			logger.debug(ele2.size());

			for (int i = 0; i < driver.findElements(By.xpath("//*[@id='firstName_guest-id~0']")).size(); i++) {

				ele.get(i).findElement(By.xpath("//*[@id='firstName_guest-id~0']")).clear();
				ele.get(i).findElement(By.xpath("//*[@id='firstName_guest-id~0']")).sendKeys("Adult_" + numberToWord(i + 1));
				ele.get(i).findElement(By.xpath("//*[@id='lastName_guest-id~0']")).clear();
				ele.get(i).findElement(By.xpath("//*[@id='firstName_guest-id~0']")).sendKeys("Adult_" + numberToWord(i + 1) + "lastName");

			}

		} catch (Exception e) {
			result.setAttribute("Actual", "Error Genrated ");
			Assert.fail("Error Genrated ", e);
		}

	}

	public static String selectDateFromTheCalendar(String Date, WebElement yearEle, WebElement month, WebElement nextButtonEle, WebElement eleCalendar) {
		// Date Format Is 14-April-2020

		String Month = month.getText();
		String Year = yearEle.getText();
		String Day = null;

		while (!Year.equals(Date.split("-")[2])) {

			Month = month.getText();
			Year = yearEle.getText();

			if (Year.equals(Date.split("-")[2])) {
				break;
			} else {
				nextButtonEle.click();
			}

		}

		while (!Month.equalsIgnoreCase(Date.split("-")[1])) {

			Month = month.getText();

			if (Month.equals(Date.split("-")[1])) {
				break;
			} else {
				nextButtonEle.click();
			}
		}

		for (WebElement date : eleCalendar.findElements(By.xpath("table/tbody/tr/td/a"))) {
			if (date.getText().equalsIgnoreCase(Date.split("-")[0])) {
				date.click();
				Day = date.getText();
			}
		}

		return Day;
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
