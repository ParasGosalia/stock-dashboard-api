package com.sample.payconiq.stocks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class AuthenticationResponse {

    private final String jwtToken;
}
