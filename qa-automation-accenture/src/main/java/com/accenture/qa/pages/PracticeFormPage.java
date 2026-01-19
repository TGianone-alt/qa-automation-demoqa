package com.accenture.qa.pages;

import com.accenture.qa.support.PracticeFormData;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class PracticeFormPage {

    private final WebDriver driver;
    private final String URL_FORM = "https://demoqa.com/automation-practice-form";

    // inputs principais
    private final By firstName = By.id("firstName");
    private final By lastName = By.id("lastName");
    private final By email = By.id("userEmail");
    private final By mobile = By.id("userNumber");
    private final By address = By.id("currentAddress");

    // date / subject
    private final By dobInput = By.id("dateOfBirthInput");
    private final By subjectsInput = By.id("subjectsInput");

    // upload
    private final By uploadInput = By.id("uploadPicture");

    // state/city (react select)
    private final By stateInput = By.id("react-select-3-input");
    private final By cityInput = By.id("react-select-4-input");

    private final By submit = By.id("submit");

    // modal
    private final By modalTitle = By.id("example-modal-sizes-title-lg");
    private final By modalClose = By.id("closeLargeModal");

    public PracticeFormPage(WebDriver driver) {
        this.driver = driver;
    }

    public void abrir() {
        driver.get(URL_FORM);
    }

    public void prepararTela() {
        removerBanners();
        esperarVisivel(firstName, 20);
        scroll(firstName);
    }

    public void preencherFormulario(PracticeFormData d) {
        preencher(firstName, d.getFirstName());
        preencher(lastName, d.getLastName());
        preencher(email, d.getEmail());

        selecionarGender(d.getGender());
        preencher(mobile, d.getMobile());

        preencherData(d.getDob());
        preencherSubject(d.getSubject());

        selecionarHobby(d.getHobby());
        uploadArquivo(d.getUploadResourcePath());

        preencher(address, d.getAddress());
        selecionarStateCity(d.getState(), d.getCity());
    }

    public void enviar() {
        scroll(submit);
        jsClick(submit);
    }

    public boolean modalEstaVisivel() {
        try {
            esperarVisivel(modalTitle, 5);
            return driver.findElement(modalTitle).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void fecharModal() {
        if (!modalEstaVisivel()) return;

        jsClick(modalClose);

        new WebDriverWait(driver, Duration.ofSeconds(8))
                .until(d -> d.findElements(modalTitle).isEmpty());
    }

    public boolean formularioEstaDisponivel() {
        try {
            return driver.findElement(firstName).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean urlEhDaPaginaDoForm() {
        return driver.getCurrentUrl().contains("automation-practice-form");
    }

    // ========================= Helpers =========================

    private void selecionarGender(String gender) {
        // wrapper id real do site: genterWrapper
        By opt = By.xpath("//*[@id='genterWrapper']//label[normalize-space()='" + gender + "']");
        jsClick(opt);
    }

    private void selecionarHobby(String hobby) {
        // wrapper id real do site: hobbiesWrapper
        By opt = By.xpath("//*[@id='hobbiesWrapper']//label[normalize-space()='" + hobby + "']");
        jsClick(opt);
    }

    private void preencher(By locator, String valor) {
        WebElement el = esperarElemento(locator, 10);
        el.clear();
        el.sendKeys(valor);
    }

    private void preencherData(String data) {
        WebElement el = esperarElemento(dobInput, 10);
        scrollEl(el);
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        el.sendKeys(data);
        el.sendKeys(Keys.ENTER);
    }

    private void preencherSubject(String subject) {
        WebElement el = esperarElemento(subjectsInput, 10);
        el.sendKeys(subject);
        el.sendKeys(Keys.ENTER);
    }

    private void selecionarStateCity(String state, String city) {
        WebElement st = esperarElemento(stateInput, 10);
        st.sendKeys(state);
        st.sendKeys(Keys.ENTER);

        WebElement ct = esperarElemento(cityInput, 10);
        ct.sendKeys(city);
        ct.sendKeys(Keys.ENTER);
    }

    private void uploadArquivo(String resourcePath) {
        try {
            URL res = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
            if (res == null) {
                throw new RuntimeException("Arquivo de upload não encontrado no resources: " + resourcePath);
            }

            File file = new File(res.toURI());
            if (!file.exists()) {
                throw new RuntimeException("Arquivo de upload não existe no disco: " + file.getAbsolutePath());
            }

            driver.findElement(uploadInput).sendKeys(file.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException("Erro no upload do arquivo", e);
        }
    }

    private WebElement esperarElemento(By locator, int timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(d -> d.findElement(locator));
    }

    private void esperarVisivel(By locator, int timeout) {
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(d -> {
                    WebElement el = d.findElement(locator);
                    return el.isDisplayed();
                });
    }

    private void jsClick(By locator) {
        WebElement el = esperarElemento(locator, 10);
        scrollEl(el);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    private void scroll(By locator) {
        try {
            scrollEl(driver.findElement(locator));
        } catch (Exception ignored) {}
    }

    private void scrollEl(WebElement el) {
        try {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        } catch (Exception ignored) {}
    }

    private void removerBanners() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "let b=document.querySelector('#fixedban'); if(b)b.remove();" +
                            "let f=document.querySelector('footer'); if(f)f.remove();"
            );
        } catch (Exception ignored) {}
    }
}
