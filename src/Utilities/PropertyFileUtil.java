package Utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil {
public static String getvalueForkey(String key)throws Throwable
{
	Properties configprop=new Properties();
	FileInputStream fis=new FileInputStream("D:\\Durgaprasad\\ERP_Stock\\PropertyFile\\Environment.properties");
	configprop.load(fis);
	return configprop.getProperty(key);
}
}
