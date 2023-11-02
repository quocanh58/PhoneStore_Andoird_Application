package com.example.login_form_2.utils;

public class Function {
    // Function to check email validity
    public  static boolean  isValidEmail(String email) {
        // Use a regular expression to validate the email format
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        return email.matches(emailPattern);
    }

    // Function to check password complexity
    public  static boolean isValidPassword(String password) {
        // Implement your password complexity requirements here
        // For example, requiring a minimum length of 8 characters, at least one uppercase letter, and one digit:
        return password.length() >= 8 && containsUppercase(password) && containsDigit(password);
    }

    // Function to check if the password contains an uppercase letter
    public  static boolean containsUppercase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    // Function to check if the password contains a digit
    public  static  boolean containsDigit(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

}
