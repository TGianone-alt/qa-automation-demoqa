package com.accenture.qa.steps;

import com.accenture.qa.base.Hooks;
import com.accenture.qa.pages.BrowserWindowsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BrowserWindowsSteps {

    private BrowserWindowsPage page;
    private String handleOriginal;
    private String handleNovaJanela;

    @Given("que estou na tela de Browser Windows")
    public void que_estou_na_tela_de_browser_windows() {
        page = new BrowserWindowsPage(Hooks.driver);
        page.abrir();

        handleOriginal = Hooks.driver.getWindowHandle();
        assertNotNull(handleOriginal, "Handle da janela original não deveria ser null.");
    }

    @When("clico em New Window")
    public void clico_em_new_window() {
        page.clicarNewWindow();
        handleNovaJanela = page.trocarParaNovaJanela(handleOriginal);
        assertNotNull(handleNovaJanela, "Handle da nova janela não deveria ser null.");
    }

    @Then("devo ver a mensagem {string} na nova janela")
    public void devo_ver_a_mensagem_na_nova_janela(String esperado) {
        String atual = page.obterTextoSampleHeading();
        assertEquals(esperado, atual);

        Hooks.driver.close();
        Hooks.driver.switchTo().window(handleOriginal);
    }
}
