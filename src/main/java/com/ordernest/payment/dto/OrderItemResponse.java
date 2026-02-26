package com.ordernest.payment.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse(
        BigDecimal totalAmount,
        String currency
) {
}
