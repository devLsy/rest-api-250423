package com.test.lsy.restapi250423.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {

    @JsonIgnore // JSON 요청으로 받을 때 무시
    @Schema(hidden = true) // Swagger 문서에서 숨김
    private Long id;
    private String name;
    private String email;

    @Builder
    public UserDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
