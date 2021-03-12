package com.sample.payconiq.stocks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
@Getter
@AllArgsConstructor
public class AuthenticationResponse {

    private final String jwtToken;
}
