package com.sample.payconiq.stocks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

import static com.sample.payconiq.stocks.utils.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class AuthenticationRequest {

    @NotBlank(message = USERNAME_NOT_BLANK)
    private String username;
    @NotBlank(message = PASSWORD_NOT_BLANK)
    private String password;
}