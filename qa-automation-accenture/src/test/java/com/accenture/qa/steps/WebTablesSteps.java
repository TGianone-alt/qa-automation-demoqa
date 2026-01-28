package com.accenture.qa.steps;

import com.accenture.qa.base.Hooks;
import com.accenture.qa.pages.WebTablesPage;
import com.accenture.qa.pages.WebTablesPage.RowData;
import com.accenture.qa.utils.RandomDataUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WebTablesSteps {

    private WebTablesPage page;

    // CREATE (12)
    private final List<String> emailsCriados = new ArrayList<>();

    // UPDATE / DELETE (1)
    private String emailRegistroCriado;
    private String novoNome;
    private String novaIdade;
    private String novoDept;

    @Given("que estou na tela de Web Tables")
    public void que_estou_na_tela_de_web_tables() {
        page = new WebTablesPage(Hooks.driver);
        page.abrir();
    }

    // ------------------------- CREATE (12 + delete all + validate) -------------------------

    @When("adiciono 12 registros aleatorios")
    public void adiciono_12_registros_aleatorios() {
        emailsCriados.clear();

        for (int i = 0; i < 12; i++) {
            String fn = RandomDataUtils.gerarPrimeiroNome();
            String ln = RandomDataUtils.gerarSobrenome();
            String mail = RandomDataUtils.gerarEmail();
            String idade = String.valueOf(RandomDataUtils.gerarIdade());
            String sal = String.valueOf(RandomDataUtils.gerarSalario());
            String dept = RandomDataUtils.gerarDepartamento();

            page.cadastrarRegistro(fn, ln, mail, idade, sal, dept);
            emailsCriados.add(mail);

            assertTrue(page.registroExiste(mail),
                    "Registro nao apareceu apos cadastro (email): " + mail);
        }

        assertEquals(12, emailsCriados.size(),
                "Lista de emailsCriados deveria ter 12 itens, mas tem: " + emailsCriados.size());
    }

    @And("deleto os 12 registros criados")
    public void deleto_os_12_registros_criados() {
        assertEquals(12, emailsCriados.size(),
                "Lista de emailsCriados nao contem 12 itens para deletar.");

        for (String mail : emailsCriados) {
            page.deletarPorEmail(mail);
            assertTrue(page.registroNaoExisteMais(mail),
                    "Registro ainda existe apos deletar (email): " + mail);
        }
    }

    @Then("devo visualizar 12 registros removidos da tabela")
    public void devo_visualizar_12_registros_removidos_da_tabela() {
        // valida novamente (double-check)
        for (String mail : emailsCriados) {
            assertTrue(page.registroNaoExisteMais(mail),
                    "Registro ainda existe na tabela (email): " + mail);
        }
    }

    // ------------------------- UPDATE -------------------------

    @When("adiciono 1 registro aleatorio")
    public void adiciono_1_registro_aleatorio() {
        String fn = RandomDataUtils.gerarPrimeiroNome();
        String ln = RandomDataUtils.gerarSobrenome();
        String mail = RandomDataUtils.gerarEmail();
        String idade = String.valueOf(RandomDataUtils.gerarIdade());
        String sal = String.valueOf(RandomDataUtils.gerarSalario());
        String dept = RandomDataUtils.gerarDepartamento();

        page.cadastrarRegistro(fn, ln, mail, idade, sal, dept);
        emailRegistroCriado = mail;

        assertTrue(page.registroExiste(mail),
                "Registro nao apareceu apos cadastro (email): " + mail);
    }

    @And("edito o registro criado")
    public void edito_o_registro_criado() {
        assertNotNull(emailRegistroCriado, "Email do registro criado nao foi definido.");

        novoNome = RandomDataUtils.gerarPrimeiroNome();
        novaIdade = String.valueOf(RandomDataUtils.gerarIdade());
        novoDept = RandomDataUtils.gerarDepartamento();

        page.editarRegistroPorEmail(emailRegistroCriado, novoNome, novaIdade, novoDept);
    }

    @Then("o registro deve refletir os dados atualizados")
    public void o_registro_deve_refletir_os_dados_atualizados() {
        assertNotNull(emailRegistroCriado, "Email do registro criado nao foi definido.");

        RowData row = page.obterLinhaFiltradaPorEmail(emailRegistroCriado);

        assertEquals(novoNome, row.firstName, "First Name nao foi atualizado corretamente.");
        assertEquals(novaIdade, row.age, "Age nao foi atualizado corretamente.");
        assertEquals(novoDept, row.department, "Department nao foi atualizado corretamente.");
    }

    // ------------------------- DELETE -------------------------

    @And("deleto o registro criado")
    public void deleto_o_registro_criado() {
        assertNotNull(emailRegistroCriado, "Email do registro criado nao foi definido.");
        page.deletarPorEmail(emailRegistroCriado);
    }

    @Then("o registro nao deve mais existir na tabela")
    public void o_registro_nao_deve_mais_existir_na_tabela() {
        assertNotNull(emailRegistroCriado, "Email do registro criado nao foi definido.");
        assertTrue(page.registroNaoExisteMais(emailRegistroCriado),
                "Registro ainda existe na tabela (email): " + emailRegistroCriado);
    }
}
