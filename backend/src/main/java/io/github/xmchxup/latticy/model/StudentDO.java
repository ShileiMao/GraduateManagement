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
@TableName("student")
public class StudentDO extends BaseModel {


    /**
     * 学号
     */
    private String sid;

    /**
     * 班级id
     */
    @TableField("class_id")
    private Integer classId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 1:男 0:女
     */
    private Integer sex;


    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;


}
