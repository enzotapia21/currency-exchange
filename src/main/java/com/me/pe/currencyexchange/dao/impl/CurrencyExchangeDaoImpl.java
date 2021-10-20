package com.me.pe.currencyexchange.dao.impl;

import com.me.pe.currencyexchange.dao.CurrencyExchangeDao;
import com.me.pe.currencyexchange.model.dto.CurrencyExchangeDto;
import com.me.pe.currencyexchange.model.thirdparty.CurrencyExchange;
import com.me.pe.currencyexchange.dao.repository.CurrencyExchangeRepository;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@AllArgsConstructor
@Component
@Slf4j
public class CurrencyExchangeDaoImpl implements CurrencyExchangeDao {

    private final CurrencyExchangeRepository repository;

    @Override
    public Maybe<CurrencyExchangeDto> findByOriginCurrencyAndDestinationCurrency(CurrencyExchangeDto currencyExchange) {

        String originCurrency = currencyExchange.getOriginCurrency();
        String destinationCurrency = currencyExchange.getDestinationCurrency();

        return Maybe.fromCallable(() ->
                repository.findByOriginCurrencyAndDestinationCurrency(originCurrency, destinationCurrency))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable ->
                        log.debug("Getting Currency Exchange for [currencyOrigin={}, destinationCurrency={}] starts",
                                originCurrency, destinationCurrency))
                .onErrorResumeNext(Maybe.error(buildResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)))
                .doOnError(throwable -> log.error(throwable.toString()))
                .switchIfEmpty(Maybe.error(buildResponseStatusException(HttpStatus.NOT_FOUND)))
                .doOnError(throwable -> log.error("Error getting Currency Exchange. Not found resource"))
                .map(this::buildCurrencyExchangeDto)
                .doOnSuccess(currencyExchangeDto ->
                        log.debug("Getting Currency Exchange for [currencyOrigin={}, destinationCurrency={}] completed",
                                originCurrency, destinationCurrency));
    }

    @Override
    public Completable save(CurrencyExchangeDto currencyExchange) {
        String originCurrency = currencyExchange.getOriginCurrency();
        String destinationCurrency = currencyExchange.getDestinationCurrency();
        BigDecimal exchangeRate = currencyExchange.getExchangeRate();

        return Completable.fromCallable(() -> repository.save(buildCurrencyExchange(currencyExchange)))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable ->
                        log.debug("Save Currency Exchange for [currencyOrigin={}, destinationCurrency={}, exchangeRate={}] starts",
                                originCurrency, destinationCurrency, exchangeRate))
                .doOnComplete(() ->
                        log.debug("Save Currency Exchange for [currencyOrigin={}, destinationCurrency={}, exchangeRate={}] completed",
                        originCurrency, destinationCurrency, exchangeRate))
                .doOnError(throwable -> log.error("Error save currency exchange in H2"));
    }

    @Override
    public Completable update(CurrencyExchangeDto currencyExchange) {
        int id = currencyExchange.getId();
        BigDecimal exchangeRate = currencyExchange.getExchangeRate();
        return Completable.fromCallable(() -> repository.update(currencyExchange.getId(), currencyExchange.getExchangeRate()))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable ->
                        log.debug("Update Currency Exchange for [idCurrencyExchange={}, exchangeRate={}] starts",
                                id, exchangeRate))
                .doOnComplete(() ->
                        log.debug("Update Currency Exchange for [idCurrencyExchange={}, exchangeRate={}] completed",
                                id, exchangeRate))
                .doOnError(throwable -> log.error("Error update currency exchange in H2"));
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

    private ResponseStatusException buildResponseStatusException(HttpStatus httpStatus) {
        return new ResponseStatusException(httpStatus);
    }
}
