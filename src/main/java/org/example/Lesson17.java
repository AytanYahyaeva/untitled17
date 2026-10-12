package org.example;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.openqa.selenium.By.id;
/*
TASK 1 — Explicit Waitt
Dynamic Controls səhifəsini açın:
Task
1. Browser-i açın.
2. Səhifəni açın.
3. Enable button-u tapın.
4. Button-a klik edin.
5. Input-un aktiv olmasını Explicit Wait ilə gözləyin.
6. Input-a "Selenium" yazın.
7. Input-un value-sunu getAttribute("value") ilə əldə edin.
8. Console-a print edin.
Gözlənilən:
 
Selenium
 */

public class Lesson17 {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/dynamic_controls?utm_source=chatgpt.com");
        driver.manage().window().maximize();
        WebElement enable = driver.findElement(By.xpath("//button[text()='Enable']"));
        enable.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text']")));

        input.sendKeys("Selenium");
        System.out.println(input.getAttribute("value"));




//        TASK 2 — Checkbox
//        Bu səhifəni açın:
//        Checkboxes (https://the-internet.herokuapp.com/checkboxes?utm_source=chatgpt.com)
//        Task
//        1. Bütün checkbox-ları findElements() ilə tapın.
//        2. Birinci checkbox-un seçilib-seçilmədiyini yoxlayın.
//        3. Əgər seçilməyibsə, seçin.
//        4. İkinci checkbox-un seçilib-seçilmədiyini yoxlayın.
//        5. Əgər seçilibsə, deselect edin.
//                Əsas məntiq:
//        if (!checkbox.isSelected()) {
//            checkbox.click();
//        }


        driver.get("https://the-internet.herokuapp.com/checkboxes?utm_source=chatgpt.com");
        driver.manage().window().maximize();
        List<WebElement> boxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
        WebElement checkbox1 = boxes.get(0);
        WebElement checkbox2 = boxes.get(1);

        if (!checkbox1.isSelected()){
            checkbox1.click();
        }
        if (checkbox2.isSelected()){
            checkbox2.click();
        }


//        TASK 3 — Dropdown
//        Bu səhifəni açın:
//        Dropdown
//        Task
//        Dropdown-dan:
//        Option 2
//        seçin.
//                Sonra seçilmiş option-u tapıb console-a çıxarın.
//                Gözlənilən:
//        Option 2


        driver.get("https://the-internet.herokuapp.com/dropdown?utm_source=chatgpt.com");
        driver.manage().window().maximize();
        Select option = new Select(driver.findElement(By.id("dropdown")));
        option.selectByVisibleText("Option 2");
        WebElement selectedOption = option.getFirstSelectedOption();
        System.out.println(selectedOption.getText());


//
//        TASK 4 — JavaScript Alert
//        Bu səhifəni açın:
//        JavaScript Alerts
//        Task
//        1. Click for JS Alert button-a klik edin.
//        2. Alert-in text-ini alın.
//        3. Console-a yazdırın.
//        4. Alert-i accept() edin.
//                Sonra ikinci button üçün:
//        1. Click for JS Confirmbutton-a klik edin.
//        2. Alert-i dismiss() edin.



        driver.get("https://the-internet.herokuapp.com/javascript_alerts?utm_source=chatgpt.com");
        WebElement alert1 = driver.findElement(By.xpath("//button[text()='Click for JS Alert']"));
        alert1.click();
        Alert alert = driver.switchTo().alert();
        String text1 = alert.getText();
        System.out.println(text1);
        alert.accept();

        WebElement button2 = driver.findElement(By.xpath("//button[text()='Click for JS Confirm']"));
        button2.click();
        Alert alert22 = driver.switchTo().alert();
        alert22.dismiss();
//
//        TASK 5 — Multiple Windows
//        Bu səhifəni açın:
//        Multiple Windows
//        Task
//        1. Original window-un ID-sini əldə edin.
//        2. Click Here düyməsinə klik edin.
//        3. Bütün window handle-ları əldə edin.
//        4. Yeni window-a keçin.
//        5. Yeni səhifənin title-ını console-a çıxarın.
//        6. Original window-a geri qayıdın.

        driver.get("https://the-internet.herokuapp.com/windows?utm_source=chatgpt.com");
        String originalWindow = driver.getWindowHandle();
        System.out.println(originalWindow);
        WebElement click = driver.findElement(By.xpath("//a[text()='Click Here']"));
        click.click();

        Set<String>allWindows = driver.getWindowHandles();
        for (String windowhandle : allWindows){
            if (!windowhandle.equals(originalWindow)){
                driver.switchTo().window(windowhandle);
            }
        }
        String newp = driver.getTitle();
        System.out.println(newp);

        driver.switchTo().window(originalWindow);

//        TASK 6 — Mouse Hover
//        Bu səhifəni açın:
//        Hovers (https://the-internet.herokuapp.com/hovers?utm_source=chatgpt.com)
//        Task
//        1. Birinci profile image elementini tapın.
//        2. Actions istifadə edərək mouse-u onun üzərinə aparın.
//        3. Hover-dan sonra görünən text-i əldə edin.
//        4. Console-a yazdırın.
//                Məntiq:
//        Find image
//    ↓
//        moveToElement()
//    ↓
//        perform()
//    ↓
//        Find text
//    ↓
//        getText()

        driver.get("https://the-internet.herokuapp.com/hovers?utm_source=chatgpt.com");
        driver.manage().window().maximize();
        WebElement image1 = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/img"));
        Actions actions = new Actions(driver);
        actions.moveToElement(image1).perform();
        WebElement text5 = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div/h5"));
        System.out.println(text5.getText());



//        Final Challenge
//        İndi bütün öyrəndiklərimizi bir test ssenarisində istifadə edin.
//        Scenario
//        Aşağıdakı səhifəni açın:
//        The Internet Practice Site (https://the-internet.herokuapp.com/?utm_source=chatgpt.com)
//        Aşağıdakı funksiyalardan ən azı 4-nü avtomatlaşdırın:
//☐ Explicit Wait
//☐ Checkbox
//☐ Dropdown
//☐ Alert
//☐ Multiple Window
//☐ Mouse Hover
//☐ Scroll
//        Tələblər
//        Kodunuzda:
//• WebDriver
//• WebElement
//• findElement()
//• findElements()
//• ən azı 1 Wait
//• ən azı 1 interaction
//• ən azı 1 verification
//        istifadə olunmalıdır.

        driver.get("https://the-internet.herokuapp.com/?utm_source=chatgpt.com");
        WebDriverWait waitt = new WebDriverWait(driver,Duration.ofSeconds(5));
        WebElement dropdawn1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href=\"/dropdown\"]")));
        dropdawn1.click();
        Select dropdawn = new Select(driver.findElement(By.xpath("//*[@id=\"dropdown\"]")));
        dropdawn.selectByVisibleText("Option 2");
        driver.navigate().back();

        driver.get("https://the-internet.herokuapp.com/checkboxes");
        List<WebElement>checkboxes = driver.findElements(By.xpath("//input[@type=\"checkbox\"]"));
        WebElement checkbox12 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[1]"));
        if (!checkbox12.isSelected()){
            checkbox12.click();
        }

        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        WebElement js = driver.findElement(By.xpath("//button[text()='Click for JS Alert']"));
        js.click();
        Alert alert2 = driver.switchTo().alert();
        System.out.println(alert2.getText());
        alert2.accept();

        driver.get("https://the-internet.herokuapp.com/iframe");
        driver.switchTo().frame("mce_0_ifr");
        WebElement itext = driver.findElement(By.xpath("//p[text()='Your content goes here.']"));
        System.out.println(itext.getText());

        driver.get("https://the-internet.herokuapp.com/infinite_scroll");
        WebElement scroll = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div/div/div[4]"));
        Actions actionss = new Actions(driver);
        actionss.scrollToElement(scroll).perform();


        driver.quit();



    }
}