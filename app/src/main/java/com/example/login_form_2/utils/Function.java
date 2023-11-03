package com.example.login_form_2.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

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

    public  static int getIntNumber(String s){
        try{
            int a = Integer.parseInt(s);
            return a;
        }
        catch (Exception e){
            return 0;
        }
    }

    public static float getFloatNumber(String s) {
        try {
            return Float.parseFloat(s);
        } catch (Exception e) {
            return 0.0f; // Default value in case of an error
        }
    }

    public static double getDoubleNumber(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0.0; // Default value in case of an error
        }
    }
    public static long getLongNumber(String s) {
        try {
            return Long.parseLong(s);
        } catch (Exception e) {
            return 0L; // Default value in case of an error
        }
    }

    public static String formatCurrency(double amount) {
        // Tạo một định dạng số tiền với Locale là tiếng Việt và ký tự ngăn cách là dấu phẩy
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
        symbols.setGroupingSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);

        // Sử dụng định dạng để định dạng số tiền
        String formattedAmount = decimalFormat.format(amount);

        // Thêm đơn vị tiền tệ (VND)
        formattedAmount += " VND";

        return formattedAmount;
    }
    public  static long parseCurrency(String formattedAmount) {
        try {
            // Loại bỏ dấu phẩy và các ký tự không phải số
            String cleanAmount = formattedAmount.replaceAll("[^0-9]+", "");
            // Chuyển chuỗi thành số nguyên
            return Long.parseLong(cleanAmount);
        } catch (NumberFormatException e) {
            // Xử lý lỗi nếu có
            e.printStackTrace();
            return 0;
        }
    }


}
