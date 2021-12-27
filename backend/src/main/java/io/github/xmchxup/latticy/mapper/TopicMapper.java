package io.github.xmchxup.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.xmchxup.latticy.model.TopicDO;
import io.github.xmchxup.latticy.vo.TopicVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-02-04
 */
public interface TopicMapper extends BaseMapper<TopicDO> {

	IPage<TopicVO> selectPageVO(IPage<TopicVO> pager, @Param("name") String name);
}
