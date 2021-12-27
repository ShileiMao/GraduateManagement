package io.github.xmchxup.latticy.mapper;

import io.github.xmchxup.latticy.model.UserGroupDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pedro@TaleLin
 */
@Repository
public interface UserGroupMapper extends BaseMapper<UserGroupDO> {

    int insertBatch(@Param("relations") List<UserGroupDO> relations);
}
