package com.accenture.qa.steps;

import com.accenture.qa.base.Hooks;
import com.accenture.qa.pages.PracticeFormPage;
import com.accenture.qa.support.PracticeFormData;
import com.accenture.qa.support.PracticeFormDataFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class PracticeFormSteps {

    private PracticeFormPage page;
    private PracticeFormData data;

    @Given("que estou na pagina do Practice Form")
    public void que_estou_na_pagina_do_practice_form() {
        page = new PracticeFormPage(Hooks.driver);
        page.abrir();
        page.prepararTela();
    }

    @When("preencho e envio o formulario com dados randomizados")
    public void preencho_e_envio_o_formulario_com_dados_randomizados() {
        data = PracticeFormDataFactory.random();
        page.preencherFormulario(data);
        page.enviar();
    }

    @Then("devo ver o envio com sucesso, fechar o popup e validar os dados")
    public void devo_ver_o_envio_com_sucesso_fechar_o_popup_e_validar_os_dados() {
        assertTrue(page.modalEstaVisivel(),
                "Modal de sucesso nao apareceu apos enviar o formulario.");

        // fecha primeiro (como você pediu)
        page.fecharModal();

        // valida que fechou e que voltou pro form
        assertFalse(page.modalEstaVisivel(),
                "O modal deveria estar fechado apos clicar em Close.");

        assertTrue(page.formularioEstaDisponivel(),
                "O formulario nao voltou a ficar disponivel apos fechar o modal.");

        assertTrue(page.urlEhDaPaginaDoForm(),
                "A URL atual nao parece ser a pagina do Practice Form.");
    }
}
