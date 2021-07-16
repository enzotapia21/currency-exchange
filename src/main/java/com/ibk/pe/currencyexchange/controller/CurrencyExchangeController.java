package com.ibk.pe.currencyexchange.controller;

import com.ibk.pe.currencyexchange.business.CurrencyExchangeService;
import com.ibk.pe.currencyexchange.model.api.CurrencyExchangeRequest;
import com.ibk.pe.currencyexchange.model.api.CurrencyExchangeResponse;
import com.ibk.pe.currencyexchange.model.dto.CurrencyExchangeDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class CurrencyExchangeController {

    private final CurrencyExchangeService service;

    @GetMapping("/currency-exchange/calculate")
    @ResponseBody
    public CurrencyExchangeResponse calculate(@RequestBody CurrencyExchangeRequest request) {

        CurrencyExchangeDto currencyExchangeDto =
                service.findByOriginCurrencyAndDestinationCurrency(buildCurrencyExchangeDtoRequest(request));

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

    private CurrencyExchangeDto buildCurrencyExchangeDtoRequest(CurrencyExchangeRequest request) {
        return CurrencyExchangeDto.builder()
                .originCurrency(request.getCurrencyOrigin())
                .destinationCurrency(request.getDestinationCurrency())
                .build();
    }

}
