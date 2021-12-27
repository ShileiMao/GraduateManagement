package io.github.xmchxup.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.xmchxup.latticy.model.TeamDO;
import io.github.xmchxup.latticy.mapper.TeamMapper;
import io.github.xmchxup.latticy.service.TeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-01-28
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, TeamDO> implements TeamService {

	@Override
	public void existsTeamByTeacherIdAndYears(Integer tid, String years) {
		final QueryWrapper<TeamDO> wrapper = new QueryWrapper<>();
		wrapper.lambda()
				.eq(TeamDO::getTeacherId, tid)
				.eq(TeamDO::getYears, years);
		final Integer count = this.baseMapper.selectCount(wrapper);
		if (count > 0) {
			throw new ParameterException(2426);
		}
	}
}
