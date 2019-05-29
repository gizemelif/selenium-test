import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        FirefoxDriver firefoxDriver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(firefoxDriver, 30);

        firefoxDriver.navigate().to("https://ivd.gib.gov.tr/tvd_side/main.jsp?token=d1078f5e3dc646b78d5d4e5842f21e97feb48d366bc7617458b6679dec12675154a01fccc42292bb04d926bc259dbc75e39dd8e202535fd70a7098396c74a6f7");
        String verifyButtonXPath = "//*[@id=\"gen__1049\"]";

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(verifyButtonXPath)));

        firefoxDriver.findElement(By.xpath(verifyButtonXPath)).click();

        String searchText = "Vergi Kimlik Numarası Doğrulama";
        WebElement dropdown = firefoxDriver.findElement(By.id("gen__1119"));
        dropdown.click(); // assuming you have to click the "dropdown" to open it
        List<WebElement> dropList = dropdown.findElements(By.tagName("li"));
            for (WebElement list : dropList) {
            if (list.getText().equals(searchText)) {
                list.click(); // click the desired option
                break;
            }
        }

        ReadExcel readExcel = new ReadExcel();

        //vergi dairelerini dolaşıp foreach de click yapar.
        WebElement vergiDaire = firefoxDriver.findElement(By.xpath("//*[@id=\"gen__1188\"]"));
        vergiDaire.click();
        List<WebElement> vDaireElements = vergiDaire.findElements(By.tagName("option"));
        vDaireElements.remove(0);

        List<String> vergiNo = readExcel.readExcel();

        for(WebElement d : vDaireElements){
            WebElement element_enter = firefoxDriver.findElement(By.xpath("//*[@id=\"gen__1185\"]"));
            element_enter.click();
            element_enter.sendKeys(Keys.HOME+vergiNo.get(0));
            Thread.sleep(2000);

            selectState(firefoxDriver);
            Thread.sleep(2000);

            d.click();
            Thread.sleep(3000);



            //en son tıklanan DOĞRULA butonuna click özelliği verir
            WebElement button = firefoxDriver.findElement(By.id("gen__1190"));
            button.click();

            String faal = "-----";
            WebElement tab = firefoxDriver.findElement(By.id("gen__2240"));

            if(tab.getText().equals(faal)){
                Thread.sleep(6000);
                WebElement backBtn = firefoxDriver.findElement(By.id("gen__2246"));
                backBtn.click();


            }

            System.out.println(readExcel.readExcel()+"\t"+vDaireElements.get(0).getText()+"\t"+tab.getText());
        }

    }
    public static void selectState(FirefoxDriver driver, String state) throws InterruptedException {
        //String text = "";
        WebElement comboBox = driver.findElement(By.id("gen__1187"));
        comboBox.click();
        List<WebElement> states = comboBox.findElements(By.tagName("option"));
        states.remove(0);

        for(WebElement s : states){
            state = s.getText();
            s.click();



            Thread.sleep(5000);
        }
    }


}

