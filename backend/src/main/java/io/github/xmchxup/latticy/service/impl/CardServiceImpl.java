package io.github.xmchxup.latticy.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.MethodNotAllowedException;
import io.github.xmchxup.latticy.mapper.CardMapper;
import io.github.xmchxup.latticy.model.CardDO;
import io.github.xmchxup.latticy.model.OptionDO;
import io.github.xmchxup.latticy.model.QuestionDO;
import io.github.xmchxup.latticy.service.CardService;
import io.github.xmchxup.latticy.service.OptionService;
import io.github.xmchxup.latticy.service.QuestionService;
import io.github.xmchxup.latticy.vo.CardVO;
import io.github.xmchxup.latticy.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-02-27
 */
@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, CardDO> implements CardService {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private OptionService optionService;


	@Override
	public List<CardVO> getAllCardWithOptions() {
		ArrayList<CardVO> result = new ArrayList<>();

		List<CardDO> cards = this.list();
		for (CardDO card : cards) {
			result.add(createCardVO(card));
		}

		return result;
	}

	@Override
	public CardVO getCardWithOptionsById(Integer id) {
		return createCardVO(this.getById(id));
	}

	@Override
	public void checkNotUseByQID(Integer questionId) {
		Integer count = new LambdaQueryChainWrapper<>(this.baseMapper)
				.like(CardDO::getQuestionIds, questionId)
				.count();
		if (count > 0) {
			throw new MethodNotAllowedException(2398);
		}
	}

	private CardVO createCardVO(CardDO card) {
		String title = card.getTitle();
		List<QuestionVO> questionVOS = new ArrayList<>();

		List<QuestionDO> questions = new LambdaQueryChainWrapper<>(this.questionService.getBaseMapper())
				.inSql(QuestionDO::getId, card.getQuestionIds())
				.list();

		questions.forEach(question -> {
			QuestionVO questionVO = new QuestionVO();

			List<OptionDO> optionList =
					new LambdaQueryChainWrapper<>(this.optionService.getBaseMapper())
							.inSql(OptionDO::getId, question.getOptionIds())
							.list();

			int maxScore = 0;
			float totalScore = 0;
			for (OptionDO option : optionList) {
				maxScore = Math.max(maxScore, option.getScore());
				totalScore += option.getScore();
			}

			questionVO.setId(question.getId());
			questionVO.setTitle(question.getTitle());
			questionVO.setOptions(optionList);
			questionVO.setMaxScore(maxScore);
			questionVO.setTotalScore((int) totalScore);

			questionVOS.add(questionVO);
		});

		return new CardVO(card.getId(), title, questionVOS);
	}
}
