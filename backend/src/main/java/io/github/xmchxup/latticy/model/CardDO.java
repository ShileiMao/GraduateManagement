package io.github.xmchxup.latticy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("card")
public class CardDO extends BaseModel {


    /**
     * 标题
     */
    private String title;

    @TableField("question_ids")
    private String questionIds;

}
