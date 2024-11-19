package inputvalidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class InputValidator {

    // Optimized Patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9]([a-zA-Z0-9-]*[a-zA-Z0-9])?\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?$"
    );

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>?/`~])\\S{8,128}$"
    );

    private static final Pattern ISO8601_DATETIME_PATTERN = Pattern.compile(
            "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?(?:Z|[+-]\\d{2}:\\d{2})$"
    );

    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?://)([\\w-]+\\.)+[a-zA-Z]{2,6}(:\\d{1,5})?(?:/[\\w\\-.~:@!$&'()*+,;=%]*)?$",
            Pattern.CASE_INSENSITIVE
    );

    private static final List<String> VALID_COUNTRIES = Arrays.asList(
            "United States", "Canada", "United Kingdom", "Sri Lanka", "India",
            "Australia", "Germany", "France", "Italy", "Spain", "Japan",
            "China", "Brazil", "South Africa", "New Zealand"
    );

    // Private constructor to prevent instantiation
    private InputValidator() {}

    /**
     * Validates if the input is a correctly formatted email address.
     *
     * The email should match the general pattern: <code>username@domain.extension</code>,
     * with a valid domain and TLD (e.g., .com, .org).
     *
     * @param email the email address to validate (String). Must not be null or empty.
     * @return {@code true} if the email is valid, {@code false} otherwise.
     *
     * <pre>
     * Example:
     * boolean isValid = validateEmail("test@example.com"); // returns true
     * boolean isValid = validateEmail("invalid-email");   // returns false
     * </pre>
     */
    public static boolean validateEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validates if the input password meets certain strength criteria.
     *
     * <p>Requirements:</p>
     * <ul>
     *   <li>At least 8 characters and no more than 128 characters.</li>
     *   <li>Includes at least one uppercase letter, one lowercase letter, one number, and one special character.</li>
     *   <li>Does not contain more than two consecutive identical characters.</li>
     *   <li>Not a weak or predictable password.</li>
     * </ul>
     *
     * @param password the password to validate, as a {@code String}.
     * @return {@code true} if the password is strong, {@code false} otherwise.
     *
     * <p>Example:</p>
     * <pre>
     * {@code
     * boolean isValid = validatePassword("Aa1!secure"); // true
     * boolean isInvalid = validatePassword("password1!"); // false
     * }
     * </pre>
     */
    public static boolean validatePassword(String password) {
        if (password == null || password.trim().isEmpty() || password.length() < 8 || password.length() > 128) {
            return false;
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return false;
        }

        // Reject passwords with more than two consecutive repeating characters
        if (password.matches(".*(.)\\1\\1.*")) {
            return false;
        }

        // Reject weak or predictable passwords
        List<String> weakPasswords = Arrays.asList(
                "password", "123456", "qwerty", "letmein", "12345678", "password1", "password1!"
        );
        return weakPasswords.stream().noneMatch(pw -> pw.equalsIgnoreCase(password));
    }

    /**
     * Validates if the input date of birth is in the past and follows the {@code YYYY-MM-DD} format.
     *
     * @param dob the date of birth to validate, as a {@code String}.
     *            It must adhere to the {@code YYYY-MM-DD} format and represent a valid date in the past.
     * @return {@code true} if the date is valid and in the past, {@code false} otherwise.
     *
     * <p>Example:</p>
     * <pre>
     * {@code
     * boolean isValid = validateDateOfBirth("2000-01-01"); // true
     * boolean isInvalid = validateDateOfBirth("2025-01-01"); // false
     * }
     * </pre>
     */
    public static boolean validateDateOfBirth(String dob) {
        if (dob == null || dob.trim().isEmpty()) {
            return false;
        }

        try {
            LocalDate date = LocalDate.parse(dob.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return date.isBefore(LocalDate.now()) && !date.isBefore(LocalDate.of(1900, 1, 1));
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Validates if the input string matches the ISO 8601 datetime format.
     *
     * @param dateTime the datetime string to validate, as a {@code String}.
     *                 It must adhere to the ISO 8601 format, e.g., {@code 2023-11-19T12:45:30+01:00}.
     * @return {@code true} if the input is a valid ISO 8601 datetime string, {@code false} otherwise.
     *
     * <p>Example:</p>
     * <pre>
     * {@code
     * boolean isValid = validateDateTime("2023-11-19T12:45:30+01:00"); // true
     * boolean isInvalid = validateDateTime("2023/11/19 12:45:30"); // false
     * }
     * </pre>
     */
    public static boolean validateDateTime(String dateTime) {
        return dateTime != null && ISO8601_DATETIME_PATTERN.matcher(dateTime.trim()).matches();
    }

    /**
     * Checks if the input string matches a predefined list of valid countries.
     *
     * @param country the country name to validate, as a {@code String}.
     *                Case-insensitive and ignores leading/trailing spaces.
     * @return {@code true} if the input matches a valid country, {@code false} otherwise.
     *
     * <p>Example:</p>
     * <pre>
     * {@code
     * boolean isValid = validateCountry("Sri Lanka"); // true
     * boolean isInvalid = validateCountry("Atlantis"); // false
     * }
     * </pre>
     */
    public static boolean validateCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            return false;
        }
        String normalizedCountry = country.trim().toLowerCase();
        return VALID_COUNTRIES.stream()
                              .map(String::toLowerCase)
                              .anyMatch(validCountry -> validCountry.equals(normalizedCountry));
    }

    /**
     * Validates if the input string is a correctly formatted website URL.
     *
     * @param url the website URL to validate, as a {@code String}.
     *            It must include a valid protocol (http or https), domain, and optionally ports, paths, queries, or fragments.
     * @return {@code true} if the input is a valid URL, {@code false} otherwise.
     *
     * <p>Example:</p>
     * <pre>
     * {@code
     * boolean isValid = validateWebsiteURL("http://example.com"); // true
     * boolean isInvalid = validateWebsiteURL("htp://example.com"); // false
     * }
     * </pre>
     */
    public static boolean validateWebsiteURL(String url) {
        return url != null && URL_PATTERN.matcher(url.trim()).matches();
    }

    /**
     * Checks if the input string is valid.
     *
     * @param input the string to validate, as a {@code String}.
     *              Typically used to check for non-empty strings without special characters, depending on the context.
     * @return {@code true} if the input is a valid string, {@code false} otherwise.
     *
     * <p>Example:</p>
     * <pre>
     * {@code
     * boolean isValid = validateString("ValidString"); // true
     * boolean isInvalid = validateString(""); // false
     * }
     * </pre>
     */
    public static boolean validateString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Checks if the input string is a valid number.
     *
     * @param number the number to validate, as a {@code String}.
     *               It must represent a valid numeric value, including integers and decimals.
     * @return {@code true} if the input is a valid number, {@code false} otherwise.
     *
     * <p>Example:</p>
     * <pre>
     * {@code
     * boolean isValid = validateNumber("123.45"); // true
     * boolean isInvalid = validateNumber("123abc"); // false
     * }
     * </pre>
     */
    public static boolean validateNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(number.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
