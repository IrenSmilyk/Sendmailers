import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class SendMailers {
    @Test
    public void test1() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "driver/chrome/chromedriver1.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://secure.alternativesecure.com/");
        driver.findElement(By.name("username")).sendKeys("");//login
        driver.findElement(By.name("password")).sendKeys("");//password
        driver.findElement(By.xpath("//input[@value='Login']")).click();
        driver.findElement(By.xpath("//a[@href='/utilities/login/list_change/?sb=1']")).click();
        driver.switchTo().frame("viewtable");
        /* Choose product List
         docudesk
         get-pdf-format
         pdfarchitect
         pdfarchitect-triggers
         pdfsam
         pdfsuite
         sodapdf
         soda-pdf
         soda-triggers      */
        driver.findElement(By.xpath("//a[text()='pdfsuite']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@id='lyrSideBar']//table[2]//a[@onclick='menuflip(3)']")).click();
        driver.findElement(By.linkText("View Content")).click();
        ArrayList<String> arr = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("content")))//dates from file
        {
            String content;
            while ((content = br.readLine()) != null) {
                arr.add(content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 0;
        while(i < arr.size()) {
            String content1 = arr.get(i);
            String page = "http://secure.alternativesecure.com/vtable.tml?f=content::page_index&d=content&secx=ec606a40&nsn=viewallcontenttable&max=&min=&rawresults=0&skip=0&max=20&loading=1&q=" + content1;
            driver.get(page);
            driver.findElement(By.xpath("//a[text()='" + content1 + "']")).click();
            driver.findElement(By.name("save_create_mailing")).click();
            Thread.sleep(1000);
            WebElement element = driver.findElement(By.xpath("//a[@title='help']"));
            element.sendKeys(Keys.TAB);
            driver.findElement(By.xpath("//input[@title='Recipients (unselected)']")).click();
            driver.findElement(By.name("choose_segments")).click();
            driver.findElement(By.xpath("//select[@name='rightsegments']//option[contains(text(),'yoram')]")).click();// find segment yoram
            driver.findElement(By.name("rightsegmentsButton")).click();
            /*Select one of the required segments:
            *   docudesk: dsx-test-smilyk_irina_accounts
            *   get-pdf-format: gp-test-smilyk_irina_accounts
            *   pdfarchitect: pa-test-smilyk_irina_accounts
            *   pdfsam: pe-test-smilyk_irina_accounts
            *   pdfsuite: ps-test-smilyk_irina_accounts
            *   soda-pdf: s-p-test-smilyk_irina_accounts
            *   soda-triggers: st-test-smilyk_irina_accounts
            *   sodapdf: sp-test-smilyk_irina_accounts
            *   pdfarchitect-triggers: pat-test-smilyk_irina_accounts */
            driver.findElement(By.xpath("//option[text()='pdfarchitect-triggers: pat-test-smilyk_irina_accounts']")).click();
            driver.findElement(By.name("leftsegmentsButton")).click();
            driver.findElement(By.name("chosen_segments")).click();
            driver.findElement(By.name("save")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//img[@alt='Ok']")).click();
            driver.findElement(By.xpath("//img[@alt='Send All']")).click();
            driver.findElement(By.xpath("//input[@title='Ok']")).click();
          i++;
        }
        driver.quit();
    }
}
