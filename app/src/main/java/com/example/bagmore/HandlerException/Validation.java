package com.example.bagmore.HandlerException;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean checkRegexEmail(String email) {
        String regex = "^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        boolean matches = matcher.matches();
        return matches;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean checkAge(String date) {
        int minAge = 13;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate datePicker = LocalDate.parse(date, formatter);
        LocalDate currentDate = LocalDate.now();

        Period age = Period.between(datePicker, currentDate);

        if (age.getYears() >= minAge) {
            return true;
        }
        return false;
    }
}
