package io.github.xmchxup.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.xmchxup.latticy.dto.OptionDTO;
import io.github.xmchxup.latticy.dto.QuestionDTO;
import io.github.xmchxup.latticy.mapper.QuestionMapper;
import io.github.xmchxup.latticy.model.OptionDO;
import io.github.xmchxup.latticy.model.QuestionDO;
import io.github.xmchxup.latticy.service.CardService;
import io.github.xmchxup.latticy.service.OptionService;
import io.github.xmchxup.latticy.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-04-14
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, QuestionDO> implements QuestionService {

	@Autowired
	private OptionService optionService;

	@Autowired
	private CardService cardService;

	@Transactional
	@Override
	public void create(QuestionDTO dto) {
		StringBuilder sb = new StringBuilder();

		for (OptionDTO optionDTO : dto.getOptions()) {
			OptionDO optionDO = new OptionDO();

			BeanUtils.copyProperties(optionDTO, optionDO);
			this.optionService.save(optionDO);
			sb.append(optionDO.getId()).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);

		final QuestionDO questionDO = new QuestionDO();
		BeanUtils.copyProperties(dto, questionDO);
		questionDO.setOptionIds(sb.toString());
		this.save(questionDO);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) {
		// 判断是否在使用
		this.cardService.checkNotUseByQID(id);

		QuestionDO questionDO = this.getById(id);

		QueryWrapper<OptionDO> wrapper = new QueryWrapper<>();
		wrapper.lambda().inSql(OptionDO::getId, questionDO.getOptionIds());
		this.optionService.remove(wrapper);

		this.removeById(id);
	}
}
