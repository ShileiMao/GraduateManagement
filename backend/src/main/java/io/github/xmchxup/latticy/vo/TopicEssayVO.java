package io.github.xmchxup.latticy.vo;

import io.github.xmchxup.latticy.model.TopicEssayDO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicEssayVO extends TopicEssayDO {
    private String topicName;

    private String topicDesc;

    private String studentName;

    private String studentSID;

    private String teacherName;

    private String downloadUrl;
}
