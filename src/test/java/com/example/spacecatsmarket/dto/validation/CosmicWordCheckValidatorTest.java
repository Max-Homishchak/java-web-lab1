package com.example.spacecatsmarket.dto.validation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CosmicWordCheckValidatorTest {

    CosmicWordCheckValidator validator = new CosmicWordCheckValidator();

    @ParameterizedTest
    @ValueSource(strings = {
           "Young star", "Young Star", "GaLaXy track 2.0", "Invisible coMEt"
    })
    void isValid_whenValid(String word) {
        // given

        // when
        boolean valid = validator.isValid(word, null);

        // then
        assertTrue(valid);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Young stark", "Some word", "Invisible commit"
    })
    void isValid_whenInvalid(String word) {
        // given

        // when
        boolean valid = validator.isValid(word, null);

        // then
        assertFalse(valid);
    }

}