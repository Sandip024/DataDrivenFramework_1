package com.w2a.rough;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Iframe {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
			
		driver.get("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_form_submit");
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//*[@id=\"breadcrumb\"]/ul/li[1]/a")).click();
		System.out.println(driver.getTitle());
		Thread.sleep(2000);
		
		driver.navigate().back();
		Thread.sleep(2000);
		driver.switchTo().frame("iframeResult");
		Thread.sleep(3000);
		WebElement f= driver.findElement(By.id("fname"));
		f.clear();
		f.sendKeys("sandy");
		Thread.sleep(2000);
		
		driver.switchTo().defaultContent();
		//driver.switchTo().parentFrame();
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//*[@id=\"breadcrumb\"]/ul/li[1]/a")).click();
		Thread.sleep(2000);
		System.out.println(driver.getTitle());
		
		
	}

}
