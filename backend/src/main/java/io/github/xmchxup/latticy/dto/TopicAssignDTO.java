package io.github.xmchxup.latticy.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TopicAssignDTO {
    @NotBlank
    private int id;

    @NotBlank
    private int status;
}
