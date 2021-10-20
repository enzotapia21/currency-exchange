package com.me.pe.currencyexchange.model.api.update;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyExchangeUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal exchangeRate;

}
