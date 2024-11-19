package validatortest;

import inputvalidator.InputValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorTest {

    @Test
    public void testValidateEmail() {
        // Valid cases
        assertTrue(InputValidator.validateEmail("test@example.com"));
        assertTrue(InputValidator.validateEmail("user.name+alias@example.com"));
        assertTrue(InputValidator.validateEmail("user@mail.example.com"));
        assertTrue(InputValidator.validateEmail("user123@example.org"));
        assertTrue(InputValidator.validateEmail("USER@EXAMPLE.COM"));

        // Invalid cases
        assertFalse(InputValidator.validateEmail("")); // Empty input
        assertFalse(InputValidator.validateEmail(null)); // Null input
        assertFalse(InputValidator.validateEmail("userexample.com")); // Missing '@'
        assertFalse(InputValidator.validateEmail("user@.com")); // Missing domain name
        assertFalse(InputValidator.validateEmail("user@example")); // Missing domain extension
        assertFalse(InputValidator.validateEmail("user@@example.com")); // Multiple '@'
        assertFalse(InputValidator.validateEmail("user@example..com")); // Consecutive dots in domain
        assertFalse(InputValidator.validateEmail("user@-example.com")); // Domain starts with hyphen
        assertFalse(InputValidator.validateEmail("user@example-.com")); // Domain ends with hyphen

    }


    @Test
    public void testValidatePassword() {
        // Valid passwords
        assertTrue(InputValidator.validatePassword("Aa1!secure")); // Meets all criteria
        assertTrue(InputValidator.validatePassword("StrongP@ssw0rd!")); // Includes uppercase, lowercase, number, and special character
        assertTrue(InputValidator.validatePassword("C0mpl3x^Passw0rd~")); // Includes rarely used special characters
        assertTrue(InputValidator.validatePassword("Val!dP@55W0rd123")); // Valid and strong password
        assertTrue(InputValidator.validatePassword("UnicodeP@ss❤️123")); // Includes Unicode character as valid
        assertTrue(InputValidator.validatePassword("Shor@1Str0ng")); // Valid password with minimum length

        // Invalid passwords
        assertFalse(InputValidator.validatePassword(null)); // Null password
        assertFalse(InputValidator.validatePassword("")); // Empty password
        assertFalse(InputValidator.validatePassword("short")); // Too short
        assertFalse(InputValidator.validatePassword("thispasswordiswaytoolongtoeverbeaccepted1234567890!@#$%^&*()")); // Too long
        assertFalse(InputValidator.validatePassword("password1!")); // Weak and predictable
        assertFalse(InputValidator.validatePassword("Password123")); // Missing special character
        assertFalse(InputValidator.validatePassword("PASSWORD1!")); // Missing lowercase
        assertFalse(InputValidator.validatePassword("password1!")); // Missing uppercase
        assertFalse(InputValidator.validatePassword("P@sssss1")); // More than two consecutive repeating characters
        assertFalse(InputValidator.validatePassword("!Aa12aaaaa")); // Too many repeating characters
        assertFalse(InputValidator.validatePassword("Password!")); // Missing number
        assertFalse(InputValidator.validatePassword("Aa12345678")); // Missing special character
        assertFalse(InputValidator.validatePassword("NoSpecialCharacter1")); // Missing special character
    }

    @Test
    public void testValidateDateOfBirth() {
        assertTrue(InputValidator.validateDateOfBirth("2000-01-01"));
        assertFalse(InputValidator.validateDateOfBirth("2025-01-01")); // Future date
        assertFalse(InputValidator.validateDateOfBirth("2000-13-01")); // Invalid month
        assertFalse(InputValidator.validateDateOfBirth("2000-01-32")); // Invalid day
        assertFalse(InputValidator.validateDateOfBirth("invalid-date"));
        assertFalse(InputValidator.validateDateOfBirth(null));
    }

    @Test
    public void testValidateDateTime() {
        assertTrue(InputValidator.validateDateTime("2023-03-25T12:34:56Z"));
        assertTrue(InputValidator.validateDateTime("2023-03-25T12:34:56+01:00"));
        assertFalse(InputValidator.validateDateTime("2023-03-25"));
        assertFalse(InputValidator.validateDateTime("2023-03-25T12:34"));
        assertFalse(InputValidator.validateDateTime(null));
    }

    @Test
    public void testValidateCountry()
    {
        // Valid cases
        assertTrue( InputValidator.validateCountry( "Sri Lanka" ) );  // Exact match
        assertTrue( InputValidator.validateCountry( "sri lanka" ) );  // Case-insensitive
        assertTrue( InputValidator.validateCountry( " SRI LANKA " ) );  // Extra spaces
        assertTrue( InputValidator.validateCountry( "United States" ) );  // Valid country
        assertTrue( InputValidator.validateCountry( "canada" ) );  // Lowercase input
        assertTrue( InputValidator.validateCountry( "New Zealand" ) );  // Multi-word country name
        assertTrue( InputValidator.validateCountry( "germany" ) );  // Lowercase single-word country
        assertTrue( InputValidator.validateCountry( "Japan" ) );  // Valid country name

        // Invalid cases
        assertFalse( InputValidator.validateCountry( null ) );  // Null input
        assertFalse( InputValidator.validateCountry( "" ) );  // Empty string
        assertFalse( InputValidator.validateCountry( "   " ) );  // Spaces only
        assertFalse( InputValidator.validateCountry( "Srilanka" ) );  // Misspelled

    }


    @Test
    public void testValidateWebsiteURL() {
        assertTrue(InputValidator.validateWebsiteURL("https://www.example.com"));
        assertTrue(InputValidator.validateWebsiteURL("http://example.com"));
        assertFalse(InputValidator.validateWebsiteURL("example.com")); // No scheme
        assertFalse(InputValidator.validateWebsiteURL("http//invalid-url")); // Missing colon
        assertFalse(InputValidator.validateWebsiteURL(null)); // Null input
        assertFalse(InputValidator.validateWebsiteURL("www.example.com")); // No scheme
    }

    @Test
    public void testValidateString() {
        assertTrue(InputValidator.validateString("Valid String"));
        assertFalse(InputValidator.validateString(""));
        assertFalse(InputValidator.validateString(" "));
        assertFalse(InputValidator.validateString(null));
    }

    @Test
    public void testValidateNumber() {
        assertTrue(InputValidator.validateNumber("123"));
        assertTrue(InputValidator.validateNumber("123.45"));
        assertFalse(InputValidator.validateNumber("123a"));
        assertFalse(InputValidator.validateNumber("abc"));
    }
}
