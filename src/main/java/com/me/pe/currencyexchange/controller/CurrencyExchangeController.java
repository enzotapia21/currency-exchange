package com.me.pe.currencyexchange.controller;

import com.me.pe.currencyexchange.business.CurrencyExchangeService;
import com.me.pe.currencyexchange.model.api.calculate.CurrencyExchangeRequest;
import com.me.pe.currencyexchange.model.api.calculate.CurrencyExchangeResponse;
import com.me.pe.currencyexchange.model.api.save.CurrencyExchangeRegisterRequest;
import com.me.pe.currencyexchange.model.api.update.CurrencyExchangeUpdateRequest;
import com.me.pe.currencyexchange.model.dto.CurrencyExchangeDto;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@Slf4j
public class CurrencyExchangeController {

    private final CurrencyExchangeService service;

    @GetMapping("/currency-exchange/calculate")
    public Maybe<CurrencyExchangeResponse> calculate(@RequestBody CurrencyExchangeRequest request) {
        return Maybe.fromCallable(() -> buildCurrencyExchangeDtoRequest(request))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> log.info("Endpoint /calculate - Starting"))
                .flatMap(service::findByOriginCurrencyAndDestinationCurrency)
                .map(currencyExchangeDto -> buildCurrencyExchangeResponse(request, currencyExchangeDto))
                .doOnSuccess(currencyExchangeResponse -> log.info("Endpoint /calculate - Completed"));
    }

    @PostMapping("/currency-exchange")
    @ResponseStatus(HttpStatus.CREATED)
    public Completable register(@RequestBody CurrencyExchangeRegisterRequest request) {
        return service.save(buildCurrencyExchangeDtoRequest(request))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> log.info("Endpoint /currency-exchange POST - Starting"))
                .doOnComplete(() -> log.info("Endpoint /currency-exchange POST - Completed"));
    }

    @PatchMapping("/currency-exchange/{exchangeCurrencyId}")
    public Completable update(@PathVariable String exchangeCurrencyId,
                              @RequestBody CurrencyExchangeUpdateRequest request) {
        return service.update(buildCurrencyExchangeDtoRequestToUpdate(Integer.parseInt(exchangeCurrencyId), request))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> log.info("Endpoint /currency-exchange PATCH - Starting"))
                .doOnComplete(() -> log.info("Endpoint /currency-exchange PATCH - Completed"));
    }

    private CurrencyExchangeDto buildCurrencyExchangeDtoRequest(CurrencyExchangeRequest request) {
        return CurrencyExchangeDto.builder()
                .originCurrency(request.getCurrencyOrigin())
                .destinationCurrency(request.getDestinationCurrency())
                .build();
    }

    private CurrencyExchangeResponse buildCurrencyExchangeResponse(CurrencyExchangeRequest request,
                                                                   CurrencyExchangeDto currencyExchangeDto) {
        BigDecimal amount = request.getAmount();
        BigDecimal exchangeRate = currencyExchangeDto.getExchangeRate();
        return CurrencyExchangeResponse.builder()
                .amount(amount)
                .calculatedAmount(exchangeRate.multiply(amount))
                .originCurrency(currencyExchangeDto.getOriginCurrency())
                .destinationCurrency(currencyExchangeDto.getDestinationCurrency())
                .exchangeRate(exchangeRate)
                .build();
    }

    private CurrencyExchangeDto buildCurrencyExchangeDtoRequest(CurrencyExchangeRegisterRequest request) {
        return CurrencyExchangeDto.builder()
                .originCurrency(request.getCurrencyOrigin())
                .destinationCurrency(request.getDestinationCurrency())
                .exchangeRate(request.getExchangeRate())
                .build();
    }

    private CurrencyExchangeDto buildCurrencyExchangeDtoRequestToUpdate(int exchangeCurrencyId,
                                                                        CurrencyExchangeUpdateRequest request) {
        return CurrencyExchangeDto.builder()
                .id(exchangeCurrencyId)
                .exchangeRate(request.getExchangeRate())
                .build();
    }

}
