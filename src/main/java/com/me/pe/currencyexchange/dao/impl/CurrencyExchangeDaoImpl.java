package com.ibk.pe.currencyexchange.dao.impl;

import com.ibk.pe.currencyexchange.dao.CurrencyExchangeDao;
import com.ibk.pe.currencyexchange.model.dto.CurrencyExchangeDto;
import com.ibk.pe.currencyexchange.model.thirdparty.CurrencyExchange;
import com.ibk.pe.currencyexchange.dao.repository.CurrencyExchangeRepository;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Component
@Slf4j
public class CurrencyExchangeDaoImpl implements CurrencyExchangeDao {

    private final CurrencyExchangeRepository repository;

    @Override
    public Maybe<CurrencyExchangeDto> findByOriginCurrencyAndDestinationCurrency(CurrencyExchangeDto currencyExchange) {
        return Maybe.fromCallable(() ->
                repository.findByOriginCurrencyAndDestinationCurrency(
                        currencyExchange.getOriginCurrency(), currencyExchange.getDestinationCurrency()))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> log.debug("Searching currency exchange"))
                .onErrorResumeNext(Maybe.error(buildResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)))
                .doOnError(throwable -> log.error(throwable.toString()))
                .switchIfEmpty(Maybe.error(buildResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(this::buildCurrencyExchangeDto)
                .doOnSuccess(currencyExchangeDto -> log.debug("Found currency exchange"));
    }

    @Override
    public Completable save(CurrencyExchangeDto currencyExchange) {
        return Completable.fromCallable(() -> repository.save(buildCurrencyExchange(currencyExchange)))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> log.debug("Save is starting"));
    }

    @Override
    public Completable update(CurrencyExchangeDto currencyExchange) {
        return Completable.fromCallable(() -> repository.update(currencyExchange.getId(), currencyExchange.getExchangeRate()))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> log.debug("Update is starting"));
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

    private CurrencyExchange buildCurrencyExchangeToUpdate(CurrencyExchangeDto currencyExchange) {
        return CurrencyExchange.builder()
                .id(currencyExchange.getId())
                .exchangeRate(currencyExchange.getExchangeRate())
                .build();
    }

    private ResponseStatusException buildResponseStatusException(HttpStatus httpStatus) {
        return new ResponseStatusException(httpStatus);
    }
}
