package io.github.xmchxup.latticy.mapper;

import io.github.xmchxup.latticy.model.TeacherDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-01-03
 */
public interface TeacherMapper extends BaseMapper<TeacherDO> {

	boolean existsTeacherByTID(String tid);
}
