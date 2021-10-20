package com.me.pe.currencyexchange.business;

import com.me.pe.currencyexchange.model.dto.CurrencyExchangeDto;
import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface CurrencyExchangeService {

    Maybe<CurrencyExchangeDto> findByOriginCurrencyAndDestinationCurrency(CurrencyExchangeDto currencyExchange);

    Completable save(CurrencyExchangeDto currencyExchange);

    Completable update(CurrencyExchangeDto currencyExchange);

}
