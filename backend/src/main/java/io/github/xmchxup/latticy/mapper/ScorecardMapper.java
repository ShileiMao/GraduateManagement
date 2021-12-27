package io.github.xmchxup.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.xmchxup.latticy.common.mybatis.Page;
import io.github.xmchxup.latticy.model.ScorecardDO;
import io.github.xmchxup.latticy.query.ScorecardQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-04-17
 */
public interface ScorecardMapper extends BaseMapper<ScorecardDO> {


	ScorecardDO getBySid(String sid);

	IPage<ScorecardDO> customSelectPage(Page<ScorecardDO> pager,
										@Param("keywords") ScorecardQuery query);
}
