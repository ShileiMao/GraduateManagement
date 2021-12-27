package io.github.xmchxup.latticy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("team")
public class TeamDO extends BaseModel {


    /**
     * 团队名
     */
    private String name;

    /**
     * 负责人id
     */
    @TableField("teacher_id")
    private Integer teacherId;

    /**
     * 评委团队名称
     */
    private String judges;

    /**
     * 成员学生id
     */
    @TableField("student_ids")
    private String studentIds;

    /**
     * 届数
     */
    private String years;

    /**
     * 所属学院
     */
    @TableField("college_id")
    private Integer collegeId;
}
