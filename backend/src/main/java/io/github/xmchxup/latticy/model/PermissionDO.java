package io.github.xmchxup.latticy.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@Data
@Builder
@TableName("lin_permission")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDO extends BaseModel implements Serializable {

    private static final long serialVersionUID = -2400022443732120128L;

    /**
     * 权限名称，例如：访问首页
     */
    private String name;

    /**
     * 权限所属模块，例如：人员管理
     */
    private String module;

    /**
     * 0：关闭 1：开启
     */
    private Boolean mount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Boolean getMount() {
        if(null == mount)
            mount  = false;
        return mount;
    }

    public void setMount(Boolean mount) {
        this.mount = mount;
    }
}
