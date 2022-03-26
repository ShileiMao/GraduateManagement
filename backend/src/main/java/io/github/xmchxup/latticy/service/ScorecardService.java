package io.github.xmchxup.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.xmchxup.latticy.common.mybatis.Page;
import io.github.xmchxup.latticy.dto.ScorecardDTO;
import io.github.xmchxup.latticy.model.ScorecardDO;
import io.github.xmchxup.latticy.vo.ScoreCardVO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-04-17
 */
public interface ScorecardService extends IService<ScorecardDO> {

	void createByGuideTeacher(ScorecardDTO dto);

	void updateByJudgeTeam(Integer scorecardId, ScorecardDTO dto);

	void guideComment(Integer id, String comment);

	void judgeComment(Integer id, String comment);

	void committeeComment(Integer id, String comment);

	void existsById(Integer id);

	void checkNullByAssignId(Integer topicId);

	ResponseEntity<byte[]> exportScoreTable(Integer id);

	ResponseEntity<byte[]> exportScoreTableBySup(String supplement);

	Integer getIdBySid(String sid);

	List<Integer> getAllSupAssignId();

	ScorecardDO getByAssignId(Integer topicId);

	List<ScoreCardVO> getBy(Integer graduateInfoId, Integer cardId, Integer teacherId, Integer studentId);

	IPage<ScoreCardVO> selectPage(Page<ScoreCardVO> pager, Integer graduateInfoId, Integer teacherId);

	void checkNotUseByCardId(Integer id);
}
