package io.github.xmchxup.latticy.service;

import io.github.xmchxup.latticy.dto.QuestionDTO;
import io.github.xmchxup.latticy.model.QuestionDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-04-14
 */
public interface QuestionService extends IService<QuestionDO> {

	void create(QuestionDTO dto);

	void deleteById(Integer id);
}
