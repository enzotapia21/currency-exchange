package com.ibk.pe.currencyexchange.dao;

import com.ibk.pe.currencyexchange.model.dto.CurrencyExchangeDto;

import java.util.List;

public interface CurrencyExchangeDao {

    List<CurrencyExchangeDto> getAll();

    CurrencyExchangeDto findByOriginCurrencyAndDestinationCurrency(CurrencyExchangeDto currencyExchange);

}
