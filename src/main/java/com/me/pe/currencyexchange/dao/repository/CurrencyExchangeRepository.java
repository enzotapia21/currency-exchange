package com.ibk.pe.currencyexchange.dao.repository;

import com.ibk.pe.currencyexchange.model.thirdparty.CurrencyExchange;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface CurrencyExchangeRepository extends CrudRepository<CurrencyExchange, Integer> {

    CurrencyExchange findByOriginCurrencyAndDestinationCurrency(String originCurrency, String destinationCurrency);

    @Transactional
    @Modifying
    @Query("update CurrencyExchange c set c.exchangeRate = :exchangeRate where c.id = :id")
    int update(@Param("id") int id, @Param("exchangeRate") BigDecimal exchangeRate);

}
