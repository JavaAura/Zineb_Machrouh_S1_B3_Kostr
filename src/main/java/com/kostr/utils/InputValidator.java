package main.java.com.kostr.utils;

import java.util.Optional;

public class InputValidator {

    // Handle names
    public static Optional<String> handleName(String name) {
        if (name != null && name.matches("[a-zA-Z]+")) {
            return Optional.of(name);
        }
        return Optional.empty();
    }

    // Handle addresses (a number followed by one or more words)
    public static Optional<String> handleAddress(String address) {
        if (address != null && address.matches("[0-9]+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)")) {
            return Optional.of(address);
        }
        return Optional.empty();
    }

    // Handle phone numbers (country code + phone number)
    public static Optional<String> handlePhone(String phone) {
        if (phone != null && phone.matches("^\\+\\d{1,3}\\d{7,12}$")) {
            return Optional.of(phone);
        }
        return Optional.empty();
    }

    // Handle doubles
    public static Optional<Double> handleDouble(String number) {
        try {
            double value = Double.parseDouble(number);
            if (value > 0) {
                return Optional.of(value);
            }
        } catch (NumberFormatException e) {
            // Ignore and return Optional.empty()
        }
        return Optional.empty();
    }

    // Handle integers
    public static Optional<Integer> handleInt(String number) {
        try {
            int value = Integer.parseInt(number);
            if (value > 0) {
                return Optional.of(value);
            }
        } catch (NumberFormatException e) {
            // Ignore and return Optional.empty()
        }
        return Optional.empty();
    }
}
