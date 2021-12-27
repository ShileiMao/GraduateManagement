package io.github.xmchxup.latticy.bo;

import io.github.xmchxup.latticy.model.ScorecardDO;
import io.github.xmchxup.latticy.vo.CardVO;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Data
@Builder
public class ScoreTableBO {
	private ScorecardDO scorecardDO;
	private CardVO judgeCard;
	private CardVO guideCard;
	private List<String> guideTeacherNames;
	private Map<String,String> judgeAnswers;
	private Map<String,String> guideAnswers;
}
