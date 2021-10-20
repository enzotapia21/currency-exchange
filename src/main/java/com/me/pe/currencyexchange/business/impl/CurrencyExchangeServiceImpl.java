package com.me.pe.currencyexchange.business.impl;

import com.me.pe.currencyexchange.business.CurrencyExchangeService;
import com.me.pe.currencyexchange.dao.CurrencyExchangeDao;
import com.me.pe.currencyexchange.model.dto.CurrencyExchangeDto;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final CurrencyExchangeDao dao;

    @Override
    public Maybe<CurrencyExchangeDto> findByOriginCurrencyAndDestinationCurrency(CurrencyExchangeDto currencyExchange) {
        return dao.findByOriginCurrencyAndDestinationCurrency(currencyExchange);
    }

    @Override
    public Completable save(CurrencyExchangeDto currencyExchange) {
        return dao.save(currencyExchange);
    }

    @Override
    public Completable update(CurrencyExchangeDto currencyExchange) {
        return dao.update(currencyExchange);
    }
}
