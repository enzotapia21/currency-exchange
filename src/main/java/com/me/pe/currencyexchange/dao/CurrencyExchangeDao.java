package com.ibk.pe.currencyexchange.dao;

import com.ibk.pe.currencyexchange.model.dto.CurrencyExchangeDto;
import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface CurrencyExchangeDao {

    Maybe<CurrencyExchangeDto> findByOriginCurrencyAndDestinationCurrency(CurrencyExchangeDto currencyExchange);

    Completable save(CurrencyExchangeDto currencyExchange);

    Completable update(CurrencyExchangeDto currencyExchange);

}
