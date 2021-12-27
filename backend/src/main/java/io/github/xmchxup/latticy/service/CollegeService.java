package io.github.xmchxup.latticy.service;

import io.github.xmchxup.latticy.dto.CollegeDTO;
import io.github.xmchxup.latticy.model.CollegeDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-01-15
 */
public interface CollegeService extends IService<CollegeDO> {

	void create(CollegeDTO dto);

	void update(CollegeDTO dto, Integer id);

	void existsCollegeById(Integer id);

	void existsCollegeByName(String name);
}
