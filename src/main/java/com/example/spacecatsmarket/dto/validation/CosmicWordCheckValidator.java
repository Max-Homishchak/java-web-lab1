package com.example.spacecatsmarket.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

@Component
public class CosmicWordCheckValidator implements ConstraintValidator<CosmicWordCheck, String> {

    private static final Set<String> COSMIC_WORDS = Set.of(
            "star", "galaxy", "comet"
    );

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.hasText(s)) {
            String lowerCaseInput = s.toLowerCase();
            String[] inputWords = lowerCaseInput.split(" ");
            for (String word : inputWords) {
                if (COSMIC_WORDS.contains(word)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
