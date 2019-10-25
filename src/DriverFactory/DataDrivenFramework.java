package DriverFactory;


import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import CommonFunLibrary.ERP_Functions;
import Utilities.ExcelFileUtil;

public class DataDrivenFramework {
WebDriver driver;
//creating reference object for Function library
ERP_Functions erp=new ERP_Functions();
@BeforeTest
public void lauchapp()
{
	String app=erp.launchurl("http://webapp.qedge.com");
	System.out.println(app);
	//calling login
	String login=erp.verifyLogin("admin","master");
	System.out.println(login);
}

@AfterTest  
public void suplierscreation()throws Throwable
{
	//to call excel util methods
	ExcelFileUtil xl=new ExcelFileUtil();
	//count no of rows in a sheet
	int rc=xl.rowcount("supplier");
	//count no of columns
	int cc=xl.colcount("supplier");
	Reporter.log("no nof rows are::"+rc+" "+"no of colmun are::"+cc,true);
	for (int i = 1; i <=rc; i++)
	{
		String sname=xl.getData("supplier", i, 0);
		String addres=xl.getData("supplier", i, 1);
		String city=xl.getData("supplier", i, 2);
		String coutry=xl.getData("supplier", i, 3);
		String cperson=xl.getData("supplier", i, 4);
		String pnumber=xl.getData("supplier", i, 5);
		String mail=xl.getData("supplier", i, 6);
		String mnumber=xl.getData("supplier", i, 7);
		String note=xl.getData("supplier", i, 8);
		String result=erp.verifysupplier(sname, addres, city, coutry, cperson, pnumber, mail, mnumber, note);
		xl.setCellData("supplier", i, 9, result);
		
	}
}
@AfterTest
public void logout()
{
	String logoutapp=erp.verifyLogout();
	System.out.println(logoutapp);
}
}
