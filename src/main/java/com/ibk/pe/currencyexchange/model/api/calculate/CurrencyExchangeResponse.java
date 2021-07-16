package com.ibk.pe.currencyexchange.model.api.calculate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CurrencyExchangeResponse {

    private BigDecimal amount;

    private BigDecimal calculatedAmount;

    private String originCurrency;

    private String destinationCurrency;

    private BigDecimal exchangeRate;

}
