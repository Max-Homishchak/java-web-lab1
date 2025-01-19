package com.example.spacecatsmarket.dto.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class SpaceAddressValidatorTest {

    SpaceAddressValidator spaceAddressValidator = new SpaceAddressValidator();

    @Test
    void isValid_whenValidAddress_shouldReturnTrue() {
        // given

        // when
        boolean valid = spaceAddressValidator.isValid("Sector 5, Planet Earth, Quadrant 55", null);

        // then
        assertTrue(valid);
    }

    @Test
    void isValid_whenInvalidAddress_shouldReturnFalse() {
        // given

        // when
        boolean valid = spaceAddressValidator.isValid("invalid", null);

        // then
        assertFalse(valid);
    }

}