package com.ibk.pe.currencyexchange.model.thirdparty;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyExchange {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String originCurrency;

    @Column
    private String destinationCurrency;

    @Column
    private BigDecimal exchangeRate;

}
