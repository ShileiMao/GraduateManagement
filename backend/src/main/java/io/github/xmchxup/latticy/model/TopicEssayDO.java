package io.github.xmchxup.latticy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@TableName("topic_essay")
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TopicEssayDO extends BaseModel {
    @TableField("topic_assign_id")
    private Integer topicAssignId;

    @TableField("essay_title")
    private String essayTitle;

    @TableField("student_note")
    private String studentNote;

    @TableField("teacher_note")
    private String teacherNote;

    @TableField("file_path")
    private String filePath;

    private Integer status;
}
