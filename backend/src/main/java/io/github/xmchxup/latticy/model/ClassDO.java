package io.github.xmchxup.latticy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("class")
public class ClassDO extends BaseModel {


    /**
     * 专业名称
     */
    private String name;

    /**
     * 所属学院ID
     */
    @TableField("college_id")
    private Integer collegeId;

    /**
     * 所属专业ID
     */
    @TableField("major_id")
    private Integer majorId;
}
