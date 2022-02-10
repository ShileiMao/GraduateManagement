package io.github.xmchxup.latticy.vo;

import io.github.xmchxup.latticy.model.ScorecardDO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreCardVO extends ScorecardDO {
    private int studentId;

    private int topicId;

    private String topicName;

    private String topicDesc;

    private String teacherName;

    private String studentName;

    private int graduateInfoId;

    private String graduateTitle;
}
