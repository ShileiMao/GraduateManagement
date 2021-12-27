package io.github.xmchxup.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.xmchxup.latticy.model.GroupPermissionDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@Repository
public interface GroupPermissionMapper extends BaseMapper<GroupPermissionDO> {

    int insertBatch(@Param("relations") List<GroupPermissionDO> relations);

    int deleteBatchByGroupIdAndPermissionId(@Param("groupId") Integer groupId, @Param("permissionIds") List<Integer> permissionIds);
}
