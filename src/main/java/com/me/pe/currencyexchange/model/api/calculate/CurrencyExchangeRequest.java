package com.ibk.pe.currencyexchange.model.api.calculate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CurrencyExchangeRequest {

    private BigDecimal amount;

    private String currencyOrigin;

    private String destinationCurrency;

}
