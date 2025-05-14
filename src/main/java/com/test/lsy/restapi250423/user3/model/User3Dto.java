package com.test.lsy.restapi250423.user3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class User3Dto {

    @JsonIgnore @Schema(hidden = true)
    private Long id;

    @NotBlank(message = "{name.notblank}")
    private String name;

    @NotBlank(message = "{email.notblank}") @Email(message = "{email.invalid}")
    private String email;

    @Builder
    public User3Dto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
