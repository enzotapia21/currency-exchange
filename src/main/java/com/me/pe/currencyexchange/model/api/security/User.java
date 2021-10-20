package com.ibk.pe.currencyexchange.model.api.security;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String user;

    private String token;

}
