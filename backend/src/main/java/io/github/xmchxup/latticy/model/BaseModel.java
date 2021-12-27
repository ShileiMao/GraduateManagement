package io.github.xmchxup.latticy.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @author Juzi@TaleLin
 */
@Data
public class BaseModel {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @JsonIgnore
    @TableField("create_time")
    private Date createTime;

    @JsonIgnore
    @TableField("update_time")
    private Date updateTime;

    @TableLogic
    @JsonIgnore
    @TableField("delete_time")
    private Date deleteTime;
}
