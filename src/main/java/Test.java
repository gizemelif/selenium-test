import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.*;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\geatalay\\Desktop\\chromedriver.exe");
        ChromeDriver chromeDriver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(chromeDriver, 30);

        chromeDriver.navigate().to("https://ivd.gib.gov.tr/tvd_side/main.jsp?token=d1078f5e3dc646b78d5d4e5842f21e97feb48d366bc7617458b6679dec12675154a01fccc42292bb04d926bc259dbc75e39dd8e202535fd70a7098396c74a6f7");
        chromeDriver.manage().window().maximize();
        String verifyButtonXPath = "//*[@id=\"gen__1049\"]";

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(verifyButtonXPath)));

        chromeDriver.findElement(By.xpath(verifyButtonXPath)).click();

        String searchText = "Vergi Kimlik Numarası Doğrulama";
        WebElement dropdown = chromeDriver.findElement(By.id("gen__1120"));
        dropdown.click(); // assuming you have to click the "dropdown" to open it
        List<WebElement> dropList = dropdown.findElements(By.tagName("li"));
            for (WebElement list : dropList) {
            if (list.getText().equals(searchText)) {
                list.click(); // click the desired option
                break;
            }
        }

            Select s = new Select(chromeDriver.findElement(By.id("gen__1187")));

            s.selectByValue("038");

            ReadExcel readExcel = new ReadExcel();
            List<String> vergiNo = readExcel.readExcel();
            vergiNo.listIterator().next();

        label1:
            for(String v : vergiNo){
                WebElement element_enter = chromeDriver.findElement(By.id("gen__1185"));
                element_enter.click();

                Thread.sleep(2000);
                element_enter.sendKeys(Keys.HOME+v);

                Thread.sleep(1000);


                for(int k = 1; k < 19; k++){
                    Thread.sleep(1000);

                    Select select = new Select(chromeDriver.findElement(By.id("gen__1188")));
                    select.selectByIndex(k);

                    //vDaireElements.get(k).click();
                    Thread.sleep(2000);

                    //en son tıklanan DOĞRULA butonuna click özelliği verir
                    WebElement button = chromeDriver.findElement(By.id("gen__1190"));
                    button.click();

                    try{
                        Thread.sleep(1000);
                        WebElement closeTab = chromeDriver.findElement(By.cssSelector(".csc-tab-close"));


                        wait.until(ExpectedConditions.elementToBeClickable(closeTab));
                        closeTab.click();
                        //WebElement goBackbtn = chromeDriver.findElement(By.cssSelector("#gen__1179 > div.csc-tab-buttons-section.top-buttons > div.csc-tab-buttons-container > ul > li:nth-child(1) > span"));

                       // WebElement goBackbtn = chromeDriver.findElement(By.cssSelector("input[value=\"GERİ DÖN\"]"));
                        //goBackbtn.click();

                    }catch (Exception e){
                        e.printStackTrace();
                        Thread.sleep(3000);
                        chromeDriver.findElement(By.cssSelector("#runtime-body > div.cs-popup-window.project-css.tvd-css.adminTrend.cs-popup-msg-box > div.cs-popup-content > div > div > div > input")).click();

                        continue label1;

                    }


                }
            }

    }
}


