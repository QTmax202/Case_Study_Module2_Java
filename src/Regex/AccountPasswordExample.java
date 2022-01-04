package Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountPasswordExample {
    private static final String ACCOUNT_PASS_REGEX = "^[A-Za-z0-9]+$";
    private static final Pattern pattern = Pattern.compile(ACCOUNT_PASS_REGEX);

    public boolean validate(String account){
        Matcher matcher = pattern.matcher(account);
        return matcher.matches();
    }
}
