package io.github.xmchxup.latticy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-01-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("teacher")
public class TeacherDO extends BaseModel {


    /**
     * 老师工号
     */
    private String tid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 1:男 0:女
     */
    private Integer sex;

    /**
     * 职位
     */
    private String position;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 学院ID
     */
    @TableField("college_id")
    private Integer collegeId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 简介
     */
    private String summary;
}
