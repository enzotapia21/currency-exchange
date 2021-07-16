package com.ibk.pe.currencyexchange.business.impl;

import com.ibk.pe.currencyexchange.business.CurrencyExchangeService;
import com.ibk.pe.currencyexchange.dao.CurrencyExchangeDao;
import com.ibk.pe.currencyexchange.model.dto.CurrencyExchangeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final CurrencyExchangeDao dao;

    @Override
    public List<CurrencyExchangeDto> getAll() {
        return dao.getAll();
    }

    @Override
    public CurrencyExchangeDto findByOriginCurrencyAndDestinationCurrency(CurrencyExchangeDto currencyExchange) {
        return dao.findByOriginCurrencyAndDestinationCurrency(currencyExchange);
    }

}
