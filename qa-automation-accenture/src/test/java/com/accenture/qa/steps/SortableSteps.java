package com.accenture.qa.steps;

import com.accenture.qa.base.Hooks;
import com.accenture.qa.pages.SortablePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortableSteps {

    private SortablePage page;

    @Given("que estou na tela de Sortable")
    public void que_estou_na_tela_de_sortable() {
        page = new SortablePage(Hooks.driver);
        page.abrir();
        page.prepararTela();
    }

    @When("organizo os itens do Sortable em ordem decrescente")
    public void organizo_os_itens_do_sortable_em_ordem_decrescente() {
        page.ordenarDecrescente();
    }

    @Then("devo ver os itens na ordem decrescente")
    public void devo_ver_os_itens_na_ordem_decrescente() {
        assertTrue(page.estaEmOrdemDecrescente(),
                "Itens não estão em ordem decrescente. Ordem atual: " + page.obterOrdemAtual());
    }
}
