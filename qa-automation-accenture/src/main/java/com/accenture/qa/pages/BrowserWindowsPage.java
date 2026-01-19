package com.accenture.qa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class BrowserWindowsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final String URL = "https://demoqa.com/browser-windows";

    private final By newWindowButton = By.id("windowButton");
    private final By sampleHeading = By.id("sampleHeading");

    public BrowserWindowsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.wait.ignoring(NoSuchElementException.class);
        this.wait.ignoring(StaleElementReferenceException.class);
    }

    public void abrir() {
        driver.get(URL);
        removerBanners();
        esperarBotaoNewWindow();
        scrollIntoView(newWindowButton);
    }

    public void clicarNewWindow() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(newWindowButton));
        scrollEl(btn);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn); // click via JS para evitar overlay/ads
    }

    public String trocarParaNovaJanela(String handleOriginal) {
        wait.until(d -> d.getWindowHandles().size() > 1);

        Set<String> handles = driver.getWindowHandles();
        for (String h : handles) {
            if (!h.equals(handleOriginal)) {
                driver.switchTo().window(h);
                return h;
            }
        }
        throw new TimeoutException("Não encontrou handle da nova janela.");
    }

    public String obterTextoSampleHeading() {
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(sampleHeading));
        return heading.getText().trim();
    }

    private void esperarBotaoNewWindow() {
        wait.until(ExpectedConditions.presenceOfElementLocated(newWindowButton));
    }

    private void scrollIntoView(By locator) {
        try {
            WebElement el = driver.findElement(locator);
            scrollEl(el);
        } catch (Exception ignored) {}
    }

    private void scrollEl(WebElement el) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", el
            );
        } catch (Exception ignored) {}
    }

    private void removerBanners() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "let fb=document.querySelector('#fixedban'); if(fb) fb.remove();" +
                    "let ft=document.querySelector('footer'); if(ft) ft.remove();"
            );
        } catch (Exception ignored) {}
    }
}
