package com.accenture.qa.steps;

import com.accenture.qa.base.Hooks;
import com.accenture.qa.pages.ProgressBarPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class ProgressBarSteps {

    private ProgressBarPage page;

    private int valorAoParar;
    private int valorFinal;

    @Given("que estou na tela de Progress Bar")
    public void que_estou_na_tela_de_progress_bar() {
        page = new ProgressBarPage(Hooks.driver);
        page.abrir();
        page.prepararTela();
    }

    @When("inicio a progress bar, paro antes de 25, valido e retomo ate 100")
    public void inicio_paro_valido_retomo_ate_100() {
        page.startStop();// inicia
        page.aguardarAteMaiorQueZero(); // garante que começou

        valorAoParar = page.pararAntesDe(25);

        page.startStop();               // retoma
        page.aguardarAte100();

        valorFinal = page.obterValorAtual();
    }

    @Then("o valor ao parar deve ser 25 ou menor e a progress bar deve chegar a 100")
    public void validar_resultados() {
        assertTrue(valorAoParar <= 25,
                "A barra passou de 25 ao parar. Valor: " + valorAoParar);

        assertTrue(valorFinal >= 100,
                "A barra nao chegou a 100. Valor final: " + valorFinal);
    }
}
