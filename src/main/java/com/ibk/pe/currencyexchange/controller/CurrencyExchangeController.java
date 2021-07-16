package com.ibk.pe.currencyexchange.controller;

import com.ibk.pe.currencyexchange.business.CurrencyExchangeService;
import com.ibk.pe.currencyexchange.model.api.calculate.CurrencyExchangeRequest;
import com.ibk.pe.currencyexchange.model.api.calculate.CurrencyExchangeResponse;
import com.ibk.pe.currencyexchange.model.api.save.CurrencyExchangeRegisterRequest;
import com.ibk.pe.currencyexchange.model.dto.CurrencyExchangeDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class CurrencyExchangeController {

    private final CurrencyExchangeService service;

    @GetMapping("/currency-exchange/calculate")
    public CurrencyExchangeResponse calculate(@RequestBody CurrencyExchangeRequest request) {
        CurrencyExchangeDto currencyExchangeDto =
                service.findByOriginCurrencyAndDestinationCurrency(buildCurrencyExchangeDtoRequest(request));
        return buildCurrencyExchangeResponse(request, currencyExchangeDto);
    }

    @PostMapping("/currency-exchange/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody CurrencyExchangeRegisterRequest request) {
        service.save(buildCurrencyExchangeDtoRequest(request));
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

}
