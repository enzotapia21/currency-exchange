package com.ibk.pe.currencyexchange.model.thirdparty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchange {

    @Id
    @Column
    private int id;

    @Column
    private String originCurrency;

    @Column
    private String destinationCurrency;

    @Column
    private BigDecimal exchangeRate;

}
