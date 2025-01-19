package com.example.spacecatsmarket.util;

import com.example.spacecatsmarket.web.exception.ParamsViolationDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ProblemDetail;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PaymentDetailsUtilsTest {

    @Test
    void getValidationErrorsProblemDetail() {
        // given
        ParamsViolationDetails paramsViolationDetails = ParamsViolationDetails.builder().fieldName("name").reason("reason").build();

        // when
        ProblemDetail validationErrorsProblemDetail = PaymentDetailsUtils.getValidationErrorsProblemDetail(List.of(paramsViolationDetails));

        // then
        assertNotNull(validationErrorsProblemDetail);
        assertEquals("Field Validation Exception", validationErrorsProblemDetail.getTitle());
        assertEquals(List.of(paramsViolationDetails), validationErrorsProblemDetail.getProperties().get("invalidParams"));
    }

}