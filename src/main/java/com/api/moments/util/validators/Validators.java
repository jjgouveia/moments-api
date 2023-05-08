package com.api.moments.util.validators;

import java.util.regex.Pattern;

public class Validators {
  public static boolean isValidEmail(String email) {
    String emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
    Pattern pattern = Pattern.compile(emailRegex);
    return email != null && pattern.matcher(email).matches();
  }

  public static boolean isValidUsername(String username) {
    return username != null && username.trim().length() >= 3;
  }

  public static boolean isValidPassword(String password) {
    String regex = "\\S{6,}";
    return password != null && password.matches(regex);
  }
}
