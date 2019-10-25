package CommonFunLibrary;

 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.PropertyFileUtil;
import junit.framework.Assert;

public class FunctionLibrary {
public static WebDriver driver;
//method for browser launching
public static WebDriver StartBrowser(WebDriver driver)throws Throwable
{
	if(PropertyFileUtil.getvalueForkey("Browser").equalsIgnoreCase("chrome"))
	{
		System.setProperty("webdriver.chrome.driver", "D:\\Durgaprasad\\ERP_Stock\\CommonJars\\chromedriver.exe");
		driver=new ChromeDriver();
		
	}
	else if (PropertyFileUtil.getvalueForkey("Browser").equalsIgnoreCase("firefox"))
	{
	}
	else
	{
		System.out.println("No browser is matching");
	}
	return driver;
	}
//launching url
public static void openApplication(WebDriver driver)throws Throwable
{
	
driver.get(PropertyFileUtil.getvalueForkey("Url"));
driver.manage().window().maximize();
}

//method for waitforElement
public static void waitForElement(WebDriver driver,String locatortype,String locatorvalue,String waittime)
{
WebDriverWait myWait=new WebDriverWait(driver, Integer.parseInt(waittime));
if (locatortype.equalsIgnoreCase("id"))
{
myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
}
else if (locatortype.equalsIgnoreCase("name"))
{
myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
}
else if (locatortype.equalsIgnoreCase("xpath"))
{
myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
}
else{
	System.out.println("Unable to wait for element");
}
}	
//methods for actions
public static void typeAction(WebDriver driver,String locatortype,String locatorvalue,String testdata)
{
	if (locatortype.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(locatorvalue)).clear();
		driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
	}
	else if (locatortype.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(locatorvalue)).clear();
		driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
		
	}
	else if (locatorvalue.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(locatorvalue)).clear();
		driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);	
}
	else{
		
	}
	System.out.println("unable to execute typeaction metod");

}
//method for click actions
public static void clickAction(WebDriver driver,String locatortype,String locatorvalue)
{
	if (locatortype.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(locatortype)).click();
	}
	else if (locatortype.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(locatorvalue)).click();
	}
	else if (locatortype.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(locatorvalue)).click();
	}
	else
	{
		System.out.println("Unable to execute ClickAction method");
		
	}
		
	}
		//method for closing browser
public static void closeBrowser(WebDriver driver)
{
	driver.close();
}
//method for date generate
public static String generateDate()
{
	Date date=new Date();
	SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_ss");
	return sdf.format(date);
	
}
/*public static void main(String[] args)throws Throwable{
	FunctionLibrary fi=new FunctionLibrary();
	fi.StartBrowser(driver);
	fi.closeBrowser(driver);
	
}*/
//capture data into notepad
public static void captureData(WebDriver driver,String locatortype,String locatorvalue) throws Throwable
{
	String supplierdata=null;
	if (locatortype.equalsIgnoreCase("id"))
	{
		supplierdata=driver.findElement(By.id(locatorvalue)).getAttribute("value");
	}
	else if (locatortype.equalsIgnoreCase("name"))
	{
		supplierdata=driver.findElement(By.name(locatorvalue)).getAttribute("value");
		
	}
	else if (locatortype.equalsIgnoreCase("xpath"))
	{
		supplierdata=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
		
	}
	FileWriter fr=new FileWriter("D:\\Durgaprasad\\ERP_Stock\\CaptureData\\supplier.txt");
	BufferedWriter bw=new BufferedWriter(fr);
bw.write(supplierdata);
bw.flush();
bw.close();
}

//table validation for supplier creation
public static void tableValidation(WebDriver driver,String column)throws Throwable
{
	FileReader fr=new FileReader("D:\\Durgaprasad\\ERP_Stock\\CaptureData\\supplier.txt");
	BufferedReader br=new BufferedReader(fr);
String Exp_data=br.readLine();
//convert column data into integer
int column1=Integer.parseInt(column);
if (driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("search-box"))).isDisplayed())
{
	driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("search-box"))).clear();
	driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("search-box"))).sendKeys(Exp_data);
	driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("click-search"))).click();
	Thread.sleep(5000);
}
else{
	driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("Click-searchpanel"))).click();
	Thread.sleep(5000);
	driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("search-box"))).clear();
	driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("search-box"))).sendKeys(Exp_data);
	driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("click-search"))).click();
	Thread.sleep(5000);
}
//table validation
WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("sup-table")));
List<WebElement>rows=table.findElements(By.tagName("tr"));
System.out.println("no of rows are::"+rows.size());
for (int i = 0; i <rows.size(); i++)
{
	String Act_data=driver.findElement(By.xpath("//table[@id ='tbl_a_supplierslist']/tbody/tr["+i+"]/tb["+column1+"]/div/span/span")).getText();
	Thread.sleep(5000);
	Assert.assertEquals(Exp_data, Act_data,"supplier number is not matching");
	System.out.println(Exp_data+" "+Act_data);
	break;
}

}
//mouse click for stock items
public static void stockCategories(WebDriver driver)throws Throwable
{
	Actions ac=new Actions(driver);
	ac.moveToElement(driver.findElement(By.xpath("//li[@id='mi_a_stock_items']//a[contains(text(),'Stock Items')]"))).perform();
	Thread.sleep(3000);
	ac.moveToElement(driver.findElement(By.xpath("//li[@id='mi_a_stock_categories']//a[contains(text(),'Stock Categories')]"))).click().perform();
	Thread.sleep(3000);
}
//table validation for stock items
public static void tableValidation1(WebDriver driver,String Exp_data)throws Throwable
{
	if (driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("search-box1"))).isDisplayed())
	{
		Thread.sleep(2000);
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("search-box1"))).clear();
		Thread.sleep(2000);
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("search-box1"))).sendKeys(Exp_data);
		Thread.sleep(2000);
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("click-search1"))).click();
		Thread.sleep(2000);
	}
	else{
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("Click-searchpanel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("search-box1"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("search-box1"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("click-search1"))).click();
	}
	WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getvalueForkey("cat-table")));
	List<WebElement>rows=table.findElements(By.tagName("tr"));
	System.out.println("no of rows are::"+rows.size());
	for (int i = 01; i <rows.size(); i++)
	{
		//get table text in a column
	String Act_data=driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody["+i+"]/td[4]/div/span/span")).getText();	
		Thread.sleep(3000);
		Assert.assertEquals(Exp_data, Act_data,"Data in Not Matching");
		System.out.println(Exp_data+" "+Act_data);
		break;
	}
	}
}

