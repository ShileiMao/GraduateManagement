package io.github.xmchxup.latticy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreCardRequestDTD {
    @JsonProperty("graduate_info_id")
    private Integer graduateInfoId;

    @JsonProperty("card_id")
    private Integer cardId;


    @JsonProperty("teacher_id")
    private Integer teacherId;

    @JsonProperty("student_id")
    private Integer studentId;
}
