package com.ibk.pe.currencyexchange.repository;

import com.ibk.pe.currencyexchange.model.thirdparty.CurrencyExchange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyExchangeRepository extends CrudRepository<CurrencyExchange, Integer> {

    List<CurrencyExchange> findAll();

    CurrencyExchange findByOriginCurrencyAndDestinationCurrency(String originCurrency, String destinationCurrency);

}
