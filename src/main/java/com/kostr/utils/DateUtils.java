package main.java.com.kostr.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class DateUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public static boolean handleDate(String date) {
        return fromDateString(date)
                .filter(parsedDate -> !parsedDate.isAfter(LocalDate.now()))
                .isPresent();
    }

    public static Optional<String> toDateString(LocalDate date) {
        if (date == null) {
            return Optional.empty();
        }
        return Optional.of(date.format(formatter));
    }

    public static Optional<LocalDate> fromDateString(String date) {
        try {
            return Optional.of(LocalDate.parse(date, formatter));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }
}
