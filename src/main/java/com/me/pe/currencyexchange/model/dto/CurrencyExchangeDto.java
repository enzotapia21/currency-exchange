package com.me.pe.currencyexchange.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CurrencyExchangeDto {

    private int id;

    private String originCurrency;

    private String destinationCurrency;

    private BigDecimal exchangeRate;

}
