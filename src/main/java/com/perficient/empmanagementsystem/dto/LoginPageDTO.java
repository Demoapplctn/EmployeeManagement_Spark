package com.perficient.empmanagementsystem.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static com.perficient.empmanagementsystem.common.CignaConstantUtils.PROVIDE_EMAIL;
import static com.perficient.empmanagementsystem.common.CignaConstantUtils.PROVIDE_PASSWORD;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginPageDTO implements Serializable {
    private static final long serialVersionUID = -5633128091905628323L;

    @NotNull(message = PROVIDE_EMAIL)
    private String email;
    @NotNull(message = PROVIDE_PASSWORD)
    private String password;

}
