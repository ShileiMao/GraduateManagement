package io.github.xmchxup.latticy.service;

import io.github.xmchxup.latticy.model.CardDO;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.xmchxup.latticy.vo.CardVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-02-27
 */
public interface CardService extends IService<CardDO> {

	List<CardVO> getAllCardWithOptions();

	CardVO getCardWithOptionsById(Integer id);

	void checkNotUseByQID(Integer questionId);
}
