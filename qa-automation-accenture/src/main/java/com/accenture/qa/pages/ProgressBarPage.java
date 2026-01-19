package com.accenture.qa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProgressBarPage {

    private final WebDriver driver;

    private static final String URL = "https://demoqa.com/progress-bar";

    private final By startStopButton = By.id("startStopButton");
    private final By progressBar = By.cssSelector("div[role='progressbar']");

    public ProgressBarPage(WebDriver driver) {
        this.driver = driver;
    }

    public void abrir() {
        driver.get(URL);
    }

    public void prepararTela() {
        // 1) garante load do DOM (e lida com "pagina em branco")
        garantirCarregamentoComRefreshSePreciso();

        // 2) remove banners (as vezes atrapalham clique/visibilidade)
        removerBanners();

        // 3) espera PRESENCA (mais estável que isDisplayed pra esse site)
        esperarPresenca(startStopButton, 20);
        esperarPresenca(progressBar, 20);

        // 4) scroll no botão (pra evitar overlay/viewport)
        scrollAte(startStopButton);

        // 5) garante que está interagível
        esperarClicavel(startStopButton, 20);
    }

    public void startStop() {
        safeClick(startStopButton, 20);
    }

    public int pararAntesDe(int limite) {
        int alvo = Math.max(0, limite - 1); // 25 => 24

        FluentWait<WebDriver> wait = waitIgnorando(20, 50);
        wait.until(d -> obterValorAtual() >= alvo);

        // parar
        safeClick(startStopButton, 20);

        return obterValorAtual();
    }

    public void aguardarAteMaiorQueZero() {
        FluentWait<WebDriver> wait = waitIgnorando(15, 80);
        wait.until(d -> obterValorAtual() > 0);
    }

    public void aguardarAte100() {
        FluentWait<WebDriver> wait = waitIgnorando(80, 120);
        wait.until(d -> valorEh100());
    }

    public int obterValorAtual() {
        try {
            WebElement bar = driver.findElement(progressBar);

            String raw = bar.getAttribute("aria-valuenow");
            if (raw != null && !raw.trim().isEmpty()) {
                return Integer.parseInt(raw.trim());
            }

            String txt = bar.getText();
            if (txt != null) {
                txt = txt.replace("%", "").trim();
                if (!txt.isEmpty()) return Integer.parseInt(txt);
            }

            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    // -------------------- robust load helpers --------------------

    private void garantirCarregamentoComRefreshSePreciso() {
        esperarDocumentoPronto(20);

        // se não achou os elementos base, faz 1 refresh
        if (!existe(startStopButton) || !existe(progressBar)) {
            driver.navigate().refresh();
            esperarDocumentoPronto(20);
        }
    }

    private void esperarDocumentoPronto(int seconds) {
        WebDriverWait w = waitNormal(seconds);
        w.until(d -> {
            try {
                Object rs = ((JavascriptExecutor) d).executeScript("return document.readyState");
                return rs != null && "complete".equals(rs.toString());
            } catch (Exception e) {
                return false;
            }
        });
    }

    private boolean existe(By locator) {
        try {
            return driver.findElements(locator).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean valorEh100() {
        int v = obterValorAtual();
        if (v >= 100) return true;

        // fallback extra (algumas vezes o aria/text dá uma oscilada)
        try {
            WebElement bar = driver.findElement(progressBar);
            String aria = bar.getAttribute("aria-valuenow");
            if ("100".equals(aria)) return true;

            String txt = bar.getText();
            if (txt != null && "100".equals(txt.replace("%", "").trim())) return true;
        } catch (Exception ignored) {}

        return false;
    }

    // -------------------- selenium helpers --------------------

    private FluentWait<WebDriver> waitIgnorando(int timeoutSeconds, int pollingMs) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(pollingMs))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementClickInterceptedException.class);
    }

    private WebDriverWait waitNormal(int seconds) {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        w.ignoring(NoSuchElementException.class);
        w.ignoring(StaleElementReferenceException.class);
        return w;
    }

    private void esperarPresenca(By locator, int seconds) {
        WebDriverWait w = waitNormal(seconds);
        w.until(d -> d.findElements(locator).size() > 0);
    }

    private void esperarClicavel(By locator, int seconds) {
        WebDriverWait w = waitNormal(seconds);
        w.until(d -> {
            WebElement el = d.findElement(locator);
            return el.isDisplayed() && el.isEnabled();
        });
    }

    private void safeClick(By locator, int seconds) {
        WebDriverWait w = waitNormal(seconds);
        WebElement el = w.until(d -> d.findElement(locator));

        scrollEl(el);

        try {
            el.click(); 
        } catch (Exception e) {     
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    private void scrollAte(By locator) {
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
