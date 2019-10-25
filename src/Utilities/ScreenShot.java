package Utilities;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {
public static void Takescreen(WebDriver driver,String sname)throws Throwable
{
	String path="D:\\Durgaprasad\\ERP_Stock\\Screens\\"+sname+".ping";
	File screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(screen, new File(path));
	
}
}
