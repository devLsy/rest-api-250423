package com.test.lsy.restapi250423.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class UserDto {

    @JsonIgnore @Schema(hidden = true)
    private Long id;

    @NotNull @NotEmpty
    private String name;

    @NotNull @NotEmpty
    private String email;

    @Builder
    public UserDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
