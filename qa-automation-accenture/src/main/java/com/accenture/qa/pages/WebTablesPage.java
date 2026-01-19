package com.accenture.qa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WebTablesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WebTablesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Form (modal)
    private final By addButton = By.id("addNewRecordButton");

    private final By firstName = By.id("firstName");
    private final By lastName = By.id("lastName");
    private final By email = By.id("userEmail");
    private final By age = By.id("age");
    private final By salary = By.id("salary");
    private final By department = By.id("department");
    private final By submit = By.id("submit");

    // Search / Table
    private final By searchBox = By.id("searchBox");
    private final By noRows = By.className("rt-noData");

    public void abrir() {
        driver.get("https://demoqa.com/webtables");
        wait.until(ExpectedConditions.visibilityOfElementLocated(addButton));
    }

    public void cadastrarRegistro(String fn, String ln, String mail, String idade, String sal, String dept) {
        driver.findElement(addButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));

        preencher(firstName, fn);
        preencher(lastName, ln);
        preencher(email, mail);
        preencher(age, idade);
        preencher(salary, sal);
        preencher(department, dept);

        driver.findElement(submit).click();

        // espera modal fechar
        wait.until(ExpectedConditions.visibilityOfElementLocated(addButton));
    }

    private void preencher(By locator, String value) {
        WebElement el = driver.findElement(locator);
        el.clear();
        el.sendKeys(value);
    }

    public void buscarPorEmail(String mail) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        WebElement box = driver.findElement(searchBox);
        box.clear();
        box.sendKeys(mail);

        // espera tabela atualizar para "No rows found" OU para aparecer a célula do email
        wait.until(d -> d.findElements(noRows).size() > 0
                || d.findElements(By.xpath("//*[contains(@class,'rt-td') and text()='" + mail + "']")).size() > 0);
    }

    public boolean registroExiste(String mail) {
        buscarPorEmail(mail);
        return driver.findElements(noRows).isEmpty();
    }

    public RowData obterLinhaFiltradaPorEmail(String mail) {
        buscarPorEmail(mail);

        if (!driver.findElements(noRows).isEmpty()) {
            throw new RuntimeException("Nenhuma linha encontrada para o email: " + mail);
        }

        By cellsLocator = By.xpath(
                "//div[contains(@class,'rt-tr-group')][.//div[contains(@class,'rt-td') and text()='" + mail + "']]"
                        + "//div[contains(@class,'rt-td')]"
        );

        wait.until(d -> d.findElements(cellsLocator).size() >= 6);

        List<WebElement> cells = driver.findElements(cellsLocator);

        return new RowData(
                cells.get(0).getText().trim(),
                cells.get(1).getText().trim(),
                cells.get(2).getText().trim(),
                cells.get(3).getText().trim(),
                cells.get(4).getText().trim(),
                cells.get(5).getText().trim()
        );
    }

    public void editarRegistroPorEmail(String mail, String novoNome, String novaIdade, String novoDept) {
        buscarPorEmail(mail);
        By editBtn = By.xpath("//span[@title='Edit']/ancestor::div[contains(@class,'rt-tr-group')][.//div[text()='" + mail + "']]//span[@title='Edit']");
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));

        preencher(firstName, novoNome);
        preencher(age, novaIdade);
        preencher(department, novoDept);

        driver.findElement(submit).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addButton));
    }

    public void deletarPorEmail(String mail) {
        buscarPorEmail(mail);

        if (!driver.findElements(noRows).isEmpty()) {
            return; // já não existe
        }

        By deleteBtn = By.xpath("//span[@title='Delete']/ancestor::div[contains(@class,'rt-tr-group')][.//div[text()='" + mail + "']]//span[@title='Delete']");
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();

        // espera refletir remoção
        wait.until(d -> registroNaoExisteMais(mail));
    }

    public boolean registroNaoExisteMais(String mail) {
        buscarPorEmail(mail);
        return !driver.findElements(noRows).isEmpty();
    }

    public static class RowData {
        public final String firstName;
        public final String lastName;
        public final String age;
        public final String email;
        public final String salary;
        public final String department;

        public RowData(String firstName, String lastName, String age, String email, String salary, String department) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.email = email;
            this.salary = salary;
            this.department = department;
        }
    }
}
