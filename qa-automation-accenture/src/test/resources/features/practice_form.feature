@practiceform
Feature: Formulário Practice Form

  Scenario: Preencher e enviar o formulário com dados randomizados
    Given que estou na pagina do Practice Form
    When preencho e envio o formulario com dados randomizados
    Then devo ver o envio com sucesso, fechar o popup e validar os dados
