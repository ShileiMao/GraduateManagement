package io.github.xmchxup.latticy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("topic_select_table")
public class TopicSelectTableDO extends BaseModel {
    private String name;

    private String description;

    @TableField("topic_type")
    private Integer topicType;

    @TableField("teacher_id")
    private Integer teacherId;


    @TableField("college_id")
    private Integer collegeId;

    @TableField("graduate_info_id")
    private Integer graduateInfoId;

    private int status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getTopicType() {
        return topicType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    public Integer getGraduateInfoId() {
        return graduateInfoId;
    }

    public void setGraduateInfoId(Integer graduateInfoId) {
        this.graduateInfoId = graduateInfoId;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }
}
