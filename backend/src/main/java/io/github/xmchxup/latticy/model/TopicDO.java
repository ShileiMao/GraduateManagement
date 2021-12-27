package io.github.xmchxup.latticy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("topic")
public class TopicDO extends BaseModel {


    /**
     * 名称
     */
    private String name;

    /**
     * 注意事项
     */
    private String description;

    /**
     * 0:为老师发布 1:学生自主申请
     */
    private Integer type;

    /**
     * 发布年份
     */
    @TableField("publish_year")
    private String publishYear;

    /**
     * 学生学号
     */
    @TableField("student_id")
    private String studentId;

    /**
     * 老师id
     */
    @TableField("teacher_id")
    private Integer teacherId;


    /**
     * 所属学院
     */
    @TableField("college_id")
    private Integer collegeId;

    @TableField("graduate_info_id")
    private Integer graduateInfoId;

    /**
     * 课题状态
     */
    private Integer status;


}
