package com.accenture.qa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SortablePage {

    private final WebDriver driver;

    private final String URL = "https://demoqa.com/sortable";

    private final By listTabPane = By.cssSelector("#demo-tabpane-list");
    private final By items = By.cssSelector("#demo-tabpane-list .list-group-item");

    public SortablePage(WebDriver driver) {
        this.driver = driver;
    }

    public void abrir() {
        driver.get(URL);
    }

    public void prepararTela() {
        esperarDocumentoPronto(20);
        removerBanners();
        esperarPresenca(listTabPane, 20);
        esperarPresenca(items, 20);
    }

    public void ordenarDecrescente() {
        // Vai corrigindo inversões até ficar 6..1
        for (int tentativa = 0; tentativa < 16; tentativa++) {
            List<String> ordem = obterTextos();
            if (estaDecrescente(ordem)) return;

            for (int i = 0; i < ordem.size() - 1; i++) {
                int a = toNumber(ordem.get(i));
                int b = toNumber(ordem.get(i + 1));

                // Para decrescente, se a < b, tá invertido -> subir o maior (de baixo) pra cima
                if (a < b) {
                    List<WebElement> els = driver.findElements(items);
                    dragAndDropSeguro(els.get(i + 1), els.get(i));
                    sleep(250);
                    break; // recalcula a lista
                }
            }
        }
    }

    public boolean estaEmOrdemDecrescente() {
        return estaDecrescente(obterTextos());
    }

    public List<String> obterOrdemAtual() {
        return obterTextos();
    }

    // ---------------- helpers ----------------

    private List<String> obterTextos() {
        List<WebElement> els = driver.findElements(items);
        List<String> txt = new ArrayList<>();
        for (WebElement e : els) txt.add(e.getText().trim());
        return txt;
    }

    private boolean estaDecrescente(List<String> ordem) {
        for (int i = 0; i < ordem.size() - 1; i++) {
            if (toNumber(ordem.get(i)) < toNumber(ordem.get(i + 1))) return false;
        }
        return true;
    }

    private int toNumber(String s) {
        String v = s.trim().toLowerCase();

        if ("one".equals(v)) return 1;
        if ("two".equals(v)) return 2;
        if ("three".equals(v)) return 3;
        if ("four".equals(v)) return 4;
        if ("five".equals(v)) return 5;
        if ("six".equals(v)) return 6;

        try {
            return Integer.parseInt(v.replaceAll("[^0-9]", ""));
        } catch (Exception ignored) {}

        return 999;
    }

    private void dragAndDropSeguro(WebElement from, WebElement to) {
        scrollEl(from);
        scrollEl(to);

        try {
            new Actions(driver)
                    .clickAndHold(from)
                    .pause(Duration.ofMillis(150))
                    .moveToElement(to)
                    .pause(Duration.ofMillis(150))
                    .release()
                    .perform();
        } catch (Exception e) {
            new Actions(driver).dragAndDrop(from, to).perform();
        }
    }

    private void esperarPresenca(By locator, int seconds) {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        w.until(d -> d.findElements(locator).size() > 0);
    }

    private void esperarDocumentoPronto(int seconds) {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        w.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }

    private void removerBanners() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "let fb=document.querySelector('#fixedban'); if(fb) fb.remove();" +
                    "let ft=document.querySelector('footer'); if(ft) ft.remove();"
            );
        } catch (Exception ignored) {}
    }

    private void scrollEl(WebElement el) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", el
            );
        } catch (Exception ignored) {}
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
