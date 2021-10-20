package com.me.pe.currencyexchange.dao;

import com.me.pe.currencyexchange.model.dto.CurrencyExchangeDto;
import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface CurrencyExchangeDao {

    Maybe<CurrencyExchangeDto> findByOriginCurrencyAndDestinationCurrency(CurrencyExchangeDto currencyExchange);

    Completable save(CurrencyExchangeDto currencyExchange);

    Completable update(CurrencyExchangeDto currencyExchange);

}
