package com.ordernest.payment.dto;

import jakarta.validation.constraints.NotBlank;

public record ProcessPaymentRequest(
        @NotBlank(message = "orderId is required")
        String orderId
) {
}
