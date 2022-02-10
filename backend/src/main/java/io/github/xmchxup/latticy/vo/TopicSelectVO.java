package io.github.xmchxup.latticy.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.github.xmchxup.latticy.model.TopicSelectTableDO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicSelectVO extends TopicSelectTableDO {
    @TableField("graduate_id")
    private Integer graduateId;

    @TableField("graduate_title")
    private String graduateTitle;

    @TableField("topic_type_name")
    private String topicTypeName;

    @TableField("topic_type_desc")
    private String topicTypeDesc;

    @TableField("college_name")
    private String collegeName;

    @TableField("teacher_name")
    private String teacherName;
}
