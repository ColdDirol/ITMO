package utils;

import org.junit.jupiter.api.Test;
import vladimir.utils.Validator;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void testCheckStrings() {
        assertDoesNotThrow(() -> Validator.checkStrings("Hello", "World"));
        assertThrows(NullPointerException.class, () -> Validator.checkStrings(null));
        assertThrows(IllegalArgumentException.class, () -> Validator.checkStrings(" ", "Hello"));
    }

    @Test
    void testCheckPositiveIntegers() {
        assertDoesNotThrow(() -> Validator.checkPositiveIntegers(1, 2, 3));
        assertThrows(NullPointerException.class, () -> Validator.checkPositiveIntegers(null));
        assertThrows(IllegalArgumentException.class, () -> Validator.checkPositiveIntegers(-1, 0));
    }

    @Test
    void testCheckEnum() {
        assertDoesNotThrow(() -> Validator.checkEnum(Thread.State.NEW));
        assertThrows(NullPointerException.class, () -> Validator.checkEnum(null));
    }

    @Test
    void testCheckObject() {
        assertDoesNotThrow(() -> Validator.checkObject(new Object()));
        assertThrows(NullPointerException.class, () -> Validator.checkObject(null));
    }
}
