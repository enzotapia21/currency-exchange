package com.ibk.pe.currencyexchange.business.impl;

import com.ibk.pe.currencyexchange.business.CurrencyExchangeService;
import com.ibk.pe.currencyexchange.dao.CurrencyExchangeDao;
import com.ibk.pe.currencyexchange.model.dto.CurrencyExchangeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final CurrencyExchangeDao dao;

    @Override
    public CurrencyExchangeDto findByOriginCurrencyAndDestinationCurrency(CurrencyExchangeDto currencyExchange) {
        return dao.findByOriginCurrencyAndDestinationCurrency(currencyExchange);
    }

    @Override
    public void save(CurrencyExchangeDto currencyExchange) {
        dao.save(currencyExchange);
    }
}
