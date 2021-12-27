package io.github.xmchxup.latticy.service;

import io.github.xmchxup.latticy.dto.GraduateInfoDTO;
import io.github.xmchxup.latticy.model.GraduateInfoDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-04-27
 */
public interface GraduateInfoService extends IService<GraduateInfoDO> {

	void create(GraduateInfoDTO dto);
}
