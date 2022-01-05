package Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberExample {
    public static final String DATE_REGEX = "^[0-9-_]{6,}$";
    private static final Pattern pattern = Pattern.compile(DATE_REGEX);

    public boolean validate(String account){
        Matcher matcher = pattern.matcher(account);
        return matcher.matches();
    }
}
