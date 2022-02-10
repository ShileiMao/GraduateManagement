package io.github.xmchxup.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.MethodNotAllowedException;
import io.github.xmchxup.latticy.enumeration.TopicStatusEnum;
import io.github.xmchxup.latticy.mapper.TopicAssignMapper;
import io.github.xmchxup.latticy.mapper.TopicMapper;
import io.github.xmchxup.latticy.model.TeamDO;
import io.github.xmchxup.latticy.model.TopicDO;
import io.github.xmchxup.latticy.service.ScorecardService;
import io.github.xmchxup.latticy.service.TeamService;
import io.github.xmchxup.latticy.service.TopicService;
import io.github.xmchxup.latticy.vo.TopicAssignVO;
import io.github.xmchxup.latticy.vo.TopicPureVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-02-04
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, TopicDO> implements TopicService {

	@Autowired
	private TeamService teamService;

	@Autowired
	private ScorecardService scorecardService;

	@Autowired
	private TopicAssignMapper topicAssignMapper;

	@Override
	public IPage<TopicAssignVO> selectPageVO(IPage<TopicAssignVO> pager, String name) {
		return this.topicAssignMapper.selectPageVO(pager, name);
//				selectPageVO(pager, name);
	}

	@Override
	public TopicDO getTopicByStudentSid(String sid) {
		final LambdaQueryChainWrapper<TopicDO> wrapper = new LambdaQueryChainWrapper<>(baseMapper);
		final List<TopicDO> list = wrapper.eq(TopicDO::getStudentId, sid).list();
		if (list.isEmpty()) return null;
		return list.get(0);
	}

	@Override
	public List<TopicDO> getTopicsByTeacherId(Integer teacherId, boolean isSupplement) {
		// 获取教师所在团队下的所有学生的Topic
		final QueryWrapper<TeamDO> teamDOQueryWrapper = new QueryWrapper<>();

//		int year = LocalDate.now().getYear();

		teamDOQueryWrapper.lambda()
				.eq(TeamDO::getTeacherId, teacherId);
//				.eq(TeamDO::getYears, Integer.toString(year));
		final TeamDO teamDO = this.teamService.getOne(teamDOQueryWrapper);

		String studentIds = teamDO.getStudentIds();
		if (studentIds.endsWith(",")) {
			studentIds = studentIds.substring(0, studentIds.length() - 1);
		}

		// 要团队年份和主题年份一样
		QueryWrapper<TopicDO> topicDOQueryWrapper = new QueryWrapper<>();
		topicDOQueryWrapper.lambda()
//				.eq(TopicDO::getPublishYear, Integer.toString(year))
				.inSql(TopicDO::getStudentId, studentIds);

		List<TopicDO> topicDOS = this.baseMapper.selectList(topicDOQueryWrapper);

		if (isSupplement) {
			topicDOS = handleSupplementTopic(topicDOS);
		}
		return topicDOS;
	}

	@Override
	public void checkTopicStatusBySID(String studentId) {
		final QueryWrapper<TopicDO> topicDOQueryWrapper = new QueryWrapper<>();
		topicDOQueryWrapper.lambda()
				.eq(TopicDO::getStudentId, studentId);

		final List<TopicDO> topicDOS = this.baseMapper.selectList(topicDOQueryWrapper);
		if (topicDOS.isEmpty()) {
			return;
		}

		topicDOQueryWrapper.lambda().eq(TopicDO::getStatus,
				TopicStatusEnum.SUBMIT_REJECT.getValue());
		final Integer count = this.baseMapper.selectCount(topicDOQueryWrapper);
		if (count > 0) return;

		throw new MethodNotAllowedException(2450);
	}

	@Override
	public List<TopicPureVO> getByThisYear(boolean isSupplement) {

		int year = LocalDate.now().getYear();

		QueryWrapper<TopicDO> wrapper = new QueryWrapper<>();
		wrapper.lambda().eq(TopicDO::getPublishYear, year);

		List<TopicDO> topicDOS = this.list(wrapper);

		if (isSupplement) {
			topicDOS = handleSupplementTopic(topicDOS);
		}

		return topicDOS.stream()
				.map(topicDO -> {
					TopicPureVO topicPureVO = new TopicPureVO();
					BeanUtils.copyProperties(topicDO, topicPureVO);
					return topicPureVO;
				}).collect(Collectors.toList());
	}

	/**
	 * 处理今年补答辩信息
	 */
	private List<TopicDO> handleSupplementTopic(List<TopicDO> topicDOS) {
		// 过滤分数超过60的
		List<Integer> topicIds = this.scorecardService.getAllSupAssignId();
		return topicDOS.stream()
				.filter(topicDO -> topicIds.contains(topicDO.getId()))
				.collect(Collectors.toList());
	}
}
