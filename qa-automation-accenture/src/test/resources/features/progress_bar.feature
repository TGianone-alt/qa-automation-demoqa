@progressbar
Feature: Progress Bar - Controle de progresso

  Scenario: Start, parar antes de 25, validar <=25 e retomar ate 100
    Given que estou na tela de Progress Bar
    When inicio a progress bar, paro antes de 25, valido e retomo ate 100
    Then o valor ao parar deve ser 25 ou menor e a progress bar deve chegar a 100
