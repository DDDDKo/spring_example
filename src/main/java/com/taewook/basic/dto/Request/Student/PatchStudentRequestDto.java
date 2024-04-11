package com.taewook.basic.dto.Request.Student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchStudentRequestDto {
    @NotNull
    private Integer studentNumber;
    @NotBlank
    private String address;
}
