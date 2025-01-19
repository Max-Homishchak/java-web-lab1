package com.example.spacecatsmarket.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CosmicWordCheckValidator.class)
@Documented
public @interface CosmicWordCheck {

    String COSMIC_WORD_SHOULD_BE_VALID = "\"Invalid cosmic word: ensure the word include at least one cosmic word such as: 'star', 'galaxy', 'comet'.\"\n";

    String message() default COSMIC_WORD_SHOULD_BE_VALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
