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
    public List<CurrencyExchangeDto> getAll() {
        return repository.findAll()
                .parallelStream()
                .map(this::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public CurrencyExchangeDto findByOriginCurrencyAndDestinationCurrency(CurrencyExchangeDto currencyExchange) {
        return mapTo(repository.findByOriginCurrencyAndDestinationCurrency(currencyExchange.getOriginCurrency(),
                currencyExchange.getDestinationCurrency()));
    }

    private CurrencyExchangeDto mapTo(CurrencyExchange currencyExchange) {
        return CurrencyExchangeDto.builder()
                .destinationCurrency(currencyExchange.getDestinationCurrency())
                .originCurrency(currencyExchange.getOriginCurrency())
                .exchangeRate(currencyExchange.getExchangeRate())
                .build();
    }
}
