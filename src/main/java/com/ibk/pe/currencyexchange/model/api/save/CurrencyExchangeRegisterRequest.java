package com.ibk.pe.currencyexchange.model.api.save;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CurrencyExchangeRegisterRequest {

    private String currencyOrigin;

    private String destinationCurrency;

    private BigDecimal exchangeRate;
}
