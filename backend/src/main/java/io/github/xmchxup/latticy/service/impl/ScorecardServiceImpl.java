package io.github.xmchxup.latticy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import io.github.talelin.autoconfigure.exception.FailedException;
import io.github.talelin.autoconfigure.exception.MethodNotAllowedException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.xmchxup.latticy.bo.ScoreTableBO;
import io.github.xmchxup.latticy.common.mybatis.Page;
import io.github.xmchxup.latticy.common.util.AsposeUtil;
import io.github.xmchxup.latticy.common.util.GenericAndJson;
import io.github.xmchxup.latticy.dto.ScorecardDTO;
import io.github.xmchxup.latticy.enumeration.ScoreLevel;
import io.github.xmchxup.latticy.mapper.ScorecardMapper;
import io.github.xmchxup.latticy.model.GraduateInfoDO;
import io.github.xmchxup.latticy.model.ScorecardDO;
import io.github.xmchxup.latticy.model.TeacherDO;
import io.github.xmchxup.latticy.model.TopicDO;
import io.github.xmchxup.latticy.query.ScorecardQuery;
import io.github.xmchxup.latticy.service.*;
import io.github.xmchxup.latticy.vo.CardVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-04-17
 */
@Service
public class ScorecardServiceImpl extends ServiceImpl<ScorecardMapper, ScorecardDO> implements ScorecardService {
	private static final String GUIDE_TEACHER = "guide_teacher";
	private static final String JUDGE_TEAM = "judge_team";

	@Autowired
	private TopicService topicService;

	@Autowired
	private CardService cardService;

	@Autowired
	private GraduateInfoService graduateInfoService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private ScorecardMapper scorecardMapper;

	@Autowired
	private StudentService studentService;


	@Override
	public void createByGuideTeacher(ScorecardDTO dto) {
		// 指导老师评分
		if (!dto.isSupplement())
			checkNullByTopicId(dto.getTopicId());

		// 计算得分
		Map<String, Double> scores = dto.getScores();
		double guideScore = scores.values().stream().mapToDouble(i -> i).sum();

		ScorecardDO scorecardDO = new ScorecardDO();

		TopicDO topicDO = this.topicService.getById(dto.getTopicId());
		StringBuilder sb = new StringBuilder();
		sb.append(topicDO.getName())
				.append("的评分表");

		// 补答辩
		if (dto.isSupplement()) {
			scorecardDO = this.getByTopicId(dto.getTopicId());

			sb.append("-补答辩");

			scorecardDO.setSupplement(handleSupplement(scorecardDO, GUIDE_TEACHER));
			scorecardDO.setScore(guideScore + scorecardDO.getJudgeScore());
			scorecardDO.setLevel(calculateScoreLevel(scorecardDO.getScore()));
		}

		scorecardDO.setTitle(sb.toString());
		scorecardDO.setTopicId(dto.getTopicId());
		scorecardDO.setGuideCardId(dto.getCardId());
		scorecardDO.setGuideScore(guideScore);
		scorecardDO.setGuideAnswers(createQuestionScoreString(dto.getScores()));

		this.saveOrUpdate(scorecardDO);
	}

	@Override
	public void updateByJudgeTeam(Integer scorecardId, ScorecardDTO dto) {
//		答辩组评分
		// 检验 topicId
		ScorecardDO scorecardDO = this.getById(scorecardId);
		if (!scorecardDO.getTopicId().equals(dto.getTopicId())) {
			throw new ParameterException(2452);
		}

		ScorecardDO originalData = new ScorecardDO();
		BeanUtils.copyProperties(scorecardDO, originalData);

		double judgeScore = dto.getScores().values().stream().mapToDouble(i -> i).sum();
		scorecardDO.setJudgeScore(judgeScore);
		scorecardDO.setScore(judgeScore + scorecardDO.getGuideScore());
		scorecardDO.setJudgeCardId(dto.getCardId());
		scorecardDO.setJudgeAnswers(createQuestionScoreString(dto.getScores()));

		String level = calculateScoreLevel(scorecardDO.getScore());
		scorecardDO.setLevel(level);


		// 补答辩
		if (dto.isSupplement()) {
			String title = scorecardDO.getTitle();
			if (!title.contains("-补答辩")) {
				scorecardDO.setTitle(title + "-补答辩");
			}
			scorecardDO.setSupplement(handleSupplement(originalData, JUDGE_TEAM));
		}

		this.updateById(scorecardDO);
	}


	@Override
	public void guideComment(Integer id, String comment) {
		this.existsById(id);
		ScorecardDO scorecardDO = this.getById(id);
		scorecardDO.setGuideAdvise(comment);
		this.updateById(scorecardDO);
	}

	@Override
	public void judgeComment(Integer id, String comment) {
		this.existsById(id);
		ScorecardDO scorecardDO = this.getById(id);
		scorecardDO.setJudgeAdvise(comment);
		this.updateById(scorecardDO);
	}

	@Override
	public void committeeComment(Integer id, String comment) {
		this.existsById(id);
		ScorecardDO scorecardDO = this.getById(id);
		scorecardDO.setComment(comment);
		this.updateById(scorecardDO);
	}

	@Override
	public void existsById(Integer id) {
		Integer count = new LambdaQueryChainWrapper<>(this.baseMapper)
				.eq(ScorecardDO::getId, id)
				.count();
		if (count == 0) {
			throw new ParameterException(2454);
		}
	}

	@Override
	public void checkNullByTopicId(Integer topicId) {
		Integer count = new LambdaQueryChainWrapper<>(this.baseMapper)
				.eq(ScorecardDO::getTopicId, topicId)
				.count();
		if (count != 0) {
			throw new ParameterException(2453);
		}
	}

	@Override
	public ResponseEntity<byte[]> exportScoreTable(Integer id) {
		this.existsById(id);
		ScorecardDO data = this.getById(id);
		try {
			return AsposeUtil.scorecardToWord(this.createScoreTableBO(data));
		} catch (Exception exception) {
			throw new FailedException(2455);
		}
	}

	@Override
	public ResponseEntity<byte[]> exportScoreTableBySup(String supplement) {
		ScorecardDO data = GenericAndJson.jsonToObject(supplement, new TypeReference<ScorecardDO>() {
		});
		try {
			return AsposeUtil.scorecardToWord(this.createScoreTableBO(data));
		} catch (Exception exception) {
			throw new FailedException(2455);
		}
	}

	@Override
	public Integer getIdBySid(String sid) {
		if (!this.studentService.existsStudentBySID(sid)) {
			throw new ParameterException(2400);
		}
		ScorecardDO scorecardDO = this.scorecardMapper.getBySid(sid);
		if (scorecardDO == null) {
			throw new ParameterException(2454);
		}
		return scorecardDO.getId();
	}

	@Override
	public List<Integer> getAllSupTopicId() {
//		数据量可能太大，只需要获取一年的数量，一年一答辩
		int year = LocalDate.now().getYear();
		LocalDate beginDate = LocalDate.ofYearDay(year, 1);
		LocalDate endDate = LocalDate.ofYearDay(year + 1, 1);

		return new LambdaQueryChainWrapper<>(this.baseMapper)
//				.isNull(ScorecardDO::getSupplement)
				.isNotNull(ScorecardDO::getScore)
				.lt(ScorecardDO::getScore, 60.0)
				.between(ScorecardDO::getCreateTime, beginDate, endDate)
				.list().stream()
				.map(ScorecardDO::getTopicId)
				.collect(Collectors.toList());
	}

	@Override
	public ScorecardDO getByTopicId(Integer topicId) {
		return new LambdaQueryChainWrapper<>(this.baseMapper)
				.eq(ScorecardDO::getTopicId, topicId)
				.one();
	}

	@Override
	public IPage<ScorecardDO> selectPage(Page<ScorecardDO> pager, ScorecardQuery query) {
		return this.baseMapper.customSelectPage(pager, query);
	}

	@Override
	public void checkNotUseByCardId(Integer id) {
		Integer count = new LambdaQueryChainWrapper<>(this.baseMapper)
				.eq(ScorecardDO::getJudgeCardId, id)
				.or()
				.eq(ScorecardDO::getGuideCardId, id)
				.count();
		if (count > 0) {
			throw new MethodNotAllowedException(2398);
		}
	}

	private String calculateScoreLevel(Double score) {
		if (score <= 100 && score >= 90) {
			return ScoreLevel.EXCELLENT.getValue();
		} else if (score <= 89 && score >= 80) {
			return ScoreLevel.GOOD.getValue();
		} else if (score <= 79 && score >= 70) {
			return ScoreLevel.MEDIUM.getValue();
		} else if (score <= 69 && score >= 60) {
			return ScoreLevel.PASS.getValue();
		} else {
			return ScoreLevel.FAIL.getValue();
		}
	}

	private ScoreTableBO createScoreTableBO(ScorecardDO scorecardDO) {
		Integer judgeCardId = scorecardDO.getJudgeCardId();
		Integer guideCardId = scorecardDO.getGuideCardId();


		CardVO guideCard = this.cardService.getCardWithOptionsById(guideCardId);
		CardVO judgeCard = this.cardService.getCardWithOptionsById(judgeCardId);

		TopicDO topicDO = this.topicService.getById(scorecardDO.getTopicId());
		GraduateInfoDO graduateInfoDO = new LambdaQueryChainWrapper<>(this.graduateInfoService.getBaseMapper())
				.eq(GraduateInfoDO::getYear, topicDO.getPublishYear())
				.one();
		if (graduateInfoDO == null) {
			throw new ParameterException(2455);
		}

//		指导老师名称
		String teacherIds = graduateInfoDO.getTeacherIds();
		String[] chars = teacherIds.split(",");
		List<TeacherDO> teacherDOS = this.teacherService.listByIds(Arrays.asList(chars));
		List<String> teacherNames = new ArrayList<>();
		teacherDOS.forEach(teacherDO -> teacherNames.add(teacherDO.getName()));

//		选项得分情况
		Map<String, String> guideAnswers = createQuestionScoreMap(scorecardDO.getGuideAnswers());
		Map<String, String> judgeAnswers = createQuestionScoreMap(scorecardDO.getJudgeAnswers());


		return ScoreTableBO.builder()
				.scorecardDO(scorecardDO)
				.guideCard(guideCard)
				.judgeCard(judgeCard)
				.guideTeacherNames(teacherNames)
				.guideAnswers(guideAnswers)
				.judgeAnswers(judgeAnswers)
				.build();
	}

	private String createQuestionScoreString(Map<String, Double> scores) {
		StringBuilder sb = new StringBuilder();
		scores.forEach((qId, score) -> {
			sb.append(qId).append(":");
//			处理 8.0 => 8
			if (score % 1 == 0) {
				sb.append(score.intValue());
			} else {
				sb.append(score);
			}
			sb.append(",");
		});
		return sb.toString();
	}

	/**
	 * 生成关于问题得分的键值对 QuestionId:Score
	 * 1:3,2:2,3:3,4:2,5:4,6:4,
	 *
	 * @param answers
	 * @return
	 */
	private Map<String, String> createQuestionScoreMap(String answers) {
		Map<String, String> map = new HashMap<>();
		String[] pairs = answers.split(",");
		for (String pair : pairs) {
			if (StringUtils.isEmpty(pair)) {
				continue;
			}
			String[] keyValue = pair.split(":");
			map.put(keyValue[0], keyValue[1]);
		}
		return map;
	}

	/**
	 * 进行supplement字段处理数据，防止丢失
	 *
	 * @param originalData 数据库中即将要改变的数据
	 * @param type
	 * @return
	 */
	private String handleSupplement(ScorecardDO originalData, String type) {

		if (StringUtils.isEmpty(originalData.getSupplement())) {
			return GenericAndJson.objectToJson(originalData);
		}

		ScorecardDO supData = GenericAndJson.jsonToObject(originalData.getSupplement(),
				new TypeReference<ScorecardDO>() {
				});

		originalData.setSupplement("");
		if (GUIDE_TEACHER.equals(type)) {
			// 只更新 GUIDE_TEACHER相关信息
			supData.setGuideAnswers(originalData.getGuideAnswers());
			supData.setGuideAdvise(originalData.getGuideAdvise());
			supData.setGuideScore(originalData.getGuideScore());
			supData.setGuideCardId(originalData.getGuideCardId());
			supData.setScore(supData.getJudgeScore() + originalData.getGuideScore());
		} else if (JUDGE_TEAM.equals(type)) {
			// 只更新 JUDGE_TEAM相关信息
			supData.setJudgeAnswers(originalData.getJudgeAnswers());
			supData.setJudgeAdvise(originalData.getJudgeAdvise());
			supData.setJudgeScore(originalData.getJudgeScore());
			supData.setJudgeCardId(originalData.getJudgeCardId());
			supData.setScore(supData.getGuideScore() + originalData.getJudgeScore());
		}

		supData.setLevel(calculateScoreLevel(supData.getScore()));

		return GenericAndJson.objectToJson(supData);
	}
}
