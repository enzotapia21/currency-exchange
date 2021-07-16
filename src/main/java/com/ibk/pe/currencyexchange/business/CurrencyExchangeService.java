package com.ibk.pe.currencyexchange.business;

import com.ibk.pe.currencyexchange.model.dto.CurrencyExchangeDto;

import java.util.List;

public interface CurrencyExchangeService {

    List<CurrencyExchangeDto> getAll();

    CurrencyExchangeDto findByOriginCurrencyAndDestinationCurrency(CurrencyExchangeDto currencyExchange);

}
