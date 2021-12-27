package io.github.xmchxup.latticy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@Data
@TableName("lin_user_group")
public class UserGroupDO implements Serializable {

    private static final long serialVersionUID = -7219009955825484511L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 分组id
     */
    @TableField("group_id")
    private Integer groupId;

    public UserGroupDO(Integer userId, Integer groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }
}
