package net.enesbayram.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTestleri {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.get("https://www.cagri.com/pinar-macar-salam-kg");
		WebElement element = driver.findElement(By.id("imgUrunResim"));
		
		System.out.println("URL : " + element.getAttribute("src"));
		
		driver.close();
		
	}
}
