package io.github.xmchxup.latticy.bo;

import cn.hutool.core.bean.BeanUtil;
import io.github.xmchxup.latticy.model.GroupDO;
import io.github.xmchxup.latticy.model.PermissionDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupPermissionBO {
    private Integer id;

    private String name;

    private String info;

    private List<PermissionDO> permissions;

    public GroupPermissionBO(GroupDO group, List<PermissionDO> permissions) {
        BeanUtil.copyProperties(group, this);
        this.permissions = permissions;
    }
}
