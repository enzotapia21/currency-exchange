package com.ibk.pe.currencyexchange.dao.impl;

import com.ibk.pe.currencyexchange.dao.CurrencyExchangeDao;
import com.ibk.pe.currencyexchange.model.dto.CurrencyExchangeDto;
import com.ibk.pe.currencyexchange.model.thirdparty.CurrencyExchange;
import com.ibk.pe.currencyexchange.repository.CurrencyExchangeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CurrencyExchangeDaoImpl implements CurrencyExchangeDao {

    private final CurrencyExchangeRepository repository;

    @Override
    public CurrencyExchangeDto findByOriginCurrencyAndDestinationCurrency(CurrencyExchangeDto currencyExchange) {
        return buildCurrencyExchangeDto(repository.findByOriginCurrencyAndDestinationCurrency(
                currencyExchange.getOriginCurrency(), currencyExchange.getDestinationCurrency()));
    }

    @Override
    public void save(CurrencyExchangeDto currencyExchange) {
        repository.save(buildCurrencyExchange(currencyExchange));
    }

    private CurrencyExchangeDto buildCurrencyExchangeDto(CurrencyExchange currencyExchange) {
        return CurrencyExchangeDto.builder()
                .destinationCurrency(currencyExchange.getDestinationCurrency())
                .originCurrency(currencyExchange.getOriginCurrency())
                .exchangeRate(currencyExchange.getExchangeRate())
                .build();
    }

    private CurrencyExchange buildCurrencyExchange(CurrencyExchangeDto currencyExchange) {
        return CurrencyExchange.builder()
                .destinationCurrency(currencyExchange.getDestinationCurrency())
                .originCurrency(currencyExchange.getOriginCurrency())
                .exchangeRate(currencyExchange.getExchangeRate())
                .build();
    }

}
