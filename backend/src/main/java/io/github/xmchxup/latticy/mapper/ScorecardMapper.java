package io.github.xmchxup.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.xmchxup.latticy.common.mybatis.Page;
import io.github.xmchxup.latticy.model.ScorecardDO;
import io.github.xmchxup.latticy.vo.ScoreCardVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

	IPage<ScoreCardVO> customSelectPage(Page<ScoreCardVO> pager,
										Integer graduateInfoId,
										Integer teacherId);

	List<ScoreCardVO> selectScoreCardById(@Param("graduateInfoId")Integer graduateInfoId, @Param("cardId") Integer cardId, @Param("teacherId")Integer teacherId, @Param("studentId")Integer studentId);
}
