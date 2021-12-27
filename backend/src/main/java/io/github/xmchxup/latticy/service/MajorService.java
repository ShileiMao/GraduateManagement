package io.github.xmchxup.latticy.service;

import io.github.xmchxup.latticy.dto.MajorDTO;
import io.github.xmchxup.latticy.model.MajorDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-01-15
 */
public interface MajorService extends IService<MajorDO> {

	void create(MajorDTO dto);

	void update(MajorDTO dto, Integer id);

	void existsMajorByName(String name);

	void existsMajorById(Integer id);

	void checkMajorByCollegeId(Integer collegeId);
}
