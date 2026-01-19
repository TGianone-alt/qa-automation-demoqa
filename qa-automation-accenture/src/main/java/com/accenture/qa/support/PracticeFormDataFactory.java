package com.accenture.qa.support;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class PracticeFormDataFactory {

    private static final Random R = new Random();

    private static final String[] FIRST_NAMES = {"Thiago", "Ana", "Bruno", "Carla", "Diego", "Fernanda"};
    private static final String[] LAST_NAMES  = {"Silva", "Souza", "Oliveira", "Costa", "Santos", "Almeida"};
    private static final String[] GENDERS     = {"Male", "Female", "Other"};
    private static final String[] SUBJECTS    = {"Maths", "Physics", "Computer Science", "English", "Chemistry"};
    private static final String[] HOBBIES     = {"Sports", "Reading", "Music"};
    private static final String[] STATES      = {"NCR", "Uttar Pradesh", "Haryana", "Rajasthan"};

    private static final DateTimeFormatter DOB_FMT =
            DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

    public static PracticeFormData random() {
        String firstName = pick(FIRST_NAMES);
        String lastName = pick(LAST_NAMES);

        String email = gerarEmail(firstName, lastName);
        String gender = pick(GENDERS);
        String mobile = gerarMobile10();

        String dob = gerarDobFormatada();
        String subject = pick(SUBJECTS);
        String hobby = pick(HOBBIES);

        String uploadResourcePath = "files/upload.txt";

        String address = "Rua " + (R.nextInt(900) + 100) + ", Centro";

        String state = pick(STATES);
        String city = cityValidaPara(state);

        return new PracticeFormData(
                firstName, lastName, email, gender, mobile,
                dob, subject, hobby, uploadResourcePath,
                address, state, city
        );
    }

    private static String pick(String[] arr) {
        return arr[R.nextInt(arr.length)];
    }

    private static String gerarEmail(String fn, String ln) {
        String base = (fn + "." + ln + R.nextInt(10000)).toLowerCase(Locale.ENGLISH);
        return base.replace(" ", "") + "@test.com";
    }

    private static String gerarMobile10() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) sb.append(R.nextInt(10));
        return sb.toString();
    }

    private static String gerarDobFormatada() {
        LocalDate now = LocalDate.now();
        LocalDate max = now.minusYears(18);
        LocalDate min = now.minusYears(45);

        long minDay = min.toEpochDay();
        long maxDay = max.toEpochDay();
        long randomDay = minDay + (long) (R.nextDouble() * (maxDay - minDay));

        return LocalDate.ofEpochDay(randomDay).format(DOB_FMT);
    }

    private static String cityValidaPara(String state) {
        if ("NCR".equals(state)) {
            return pick(new String[]{"Delhi", "Gurgaon", "Noida"});
        }
        if ("Uttar Pradesh".equals(state)) {
            return pick(new String[]{"Agra", "Lucknow", "Merrut"});
        }
        if ("Haryana".equals(state)) {
            return pick(new String[]{"Karnal", "Panipat"});
        }
        return pick(new String[]{"Jaipur", "Jaiselmer"});
    }
}
