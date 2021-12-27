package io.github.xmchxup.latticy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("`option`")
public class OptionDO extends BaseModel {


    /**
     * 内容
     */
    private String content;

    private Integer score;


}
