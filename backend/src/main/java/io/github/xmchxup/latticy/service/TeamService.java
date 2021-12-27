package io.github.xmchxup.latticy.service;

import io.github.xmchxup.latticy.model.TeamDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-01-28
 */
public interface TeamService extends IService<TeamDO> {
	void existsTeamByTeacherIdAndYears(Integer tid, String years);
}
