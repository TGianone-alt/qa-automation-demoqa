package com.accenture.qa.utils;

import java.util.Random;
import java.util.UUID;

public class RandomDataUtils {

    private static final Random R = new Random();

    private static final String[] FIRST_NAMES = {"Ana", "Bruno", "Carla", "Diego", "Fernanda", "Gustavo", "Helena", "Igor"};
    private static final String[] LAST_NAMES  = {"Silva", "Souza", "Oliveira", "Costa", "Santos", "Pereira", "Almeida", "Lima"};
    private static final String[] DEPTS       = {"QA", "Engineering", "Support", "Sales", "HR", "Finance", "DevOps", "Product"};

    public static String gerarPrimeiroNome() {
        return FIRST_NAMES[R.nextInt(FIRST_NAMES.length)];
    }

    public static String gerarSobrenome() {
        return LAST_NAMES[R.nextInt(LAST_NAMES.length)];
    }

    public static String gerarEmail() {
        String id = UUID.randomUUID().toString().substring(0, 8);
        return "qa_" + id + "@mail.com";
    }

    public static int gerarIdade() {
        return 18 + R.nextInt(43); // 18..60
    }

    public static int gerarSalario() {
        return 1000 + R.nextInt(20000); // 1000..20999
    }

    public static String gerarDepartamento() {
        return DEPTS[R.nextInt(DEPTS.length)];
    }
}
