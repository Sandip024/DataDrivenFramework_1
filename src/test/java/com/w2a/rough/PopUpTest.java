package com.w2a.rough;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class PopUpTest {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
			
		driver.get("http://www.dummysoftware.com/popupdummy_testpage.html");
		Thread.sleep(2000);
		String parentWindowID = driver.getWindowHandle();
		System.out.println("Parent Window ID is: "+parentWindowID);
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("/html/body/table[2]/tbody/tr/td[2]/p[3]/font/input")).click();
		Thread.sleep(2000);
		
		Set<String> handler = driver.getWindowHandles();
		
		Iterator<String> it = handler.iterator();
		
		Thread.sleep(2000);
		
		System.out.println("all active window ID's are: "+handler);
		
		Thread.sleep(2000);
		
		String popup_1 = it.next();
		System.out.println(driver.getTitle());
		
		String popup_2 = it.next();;
		driver.switchTo().window(popup_2);
		System.out.println(driver.getTitle());
		driver.close();
		Thread.sleep(2000);
		
		String popup_3 = it.next();;
		driver.switchTo().window(popup_3);
		System.out.println(driver.getTitle());
		driver.close();
		Thread.sleep(2000);
		
		String popup_4 = it.next();;
		driver.switchTo().window(popup_4);
		System.out.println(driver.getTitle());
		driver.close();
		Thread.sleep(2000);
		
		String popup_5 = it.next();;
		driver.switchTo().window(popup_5);
		System.out.println(driver.getTitle());
		driver.close();
		Thread.sleep(2000);
		
		driver.switchTo().window(parentWindowID);
		System.out.println("Title of main page: "+driver.getTitle());
		
		Thread.sleep(2000);
		
		Actions actions = new Actions(driver);
		
		
		}

}
