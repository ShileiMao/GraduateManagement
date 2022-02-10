package io.github.xmchxup.latticy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("topic_type")
public class TopicTypeDO extends BaseModel {
    private String name;

    private String description;

    private int status;
}
