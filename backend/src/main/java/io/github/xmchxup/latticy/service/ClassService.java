package io.github.xmchxup.latticy.service;

import io.github.xmchxup.latticy.dto.ClassDTO;
import io.github.xmchxup.latticy.model.ClassDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-01-15
 */
public interface ClassService extends IService<ClassDO> {

	void create(ClassDTO dto);

	void update(ClassDTO dto, Integer id);

	void checkClassByMajorId(Integer majorId);
}
