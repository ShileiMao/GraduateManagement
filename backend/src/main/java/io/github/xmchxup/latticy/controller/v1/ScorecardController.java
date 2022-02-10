package io.github.xmchxup.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import io.github.talelin.autoconfigure.exception.MethodNotAllowedException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.xmchxup.latticy.common.mybatis.Page;
import io.github.xmchxup.latticy.common.util.PageUtil;
import io.github.xmchxup.latticy.dto.ScorecardDTO;
import io.github.xmchxup.latticy.dto.validators.StudentIdsPattern;
import io.github.xmchxup.latticy.model.ScorecardDO;
import io.github.xmchxup.latticy.service.ScorecardService;
import io.github.xmchxup.latticy.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author generator@TaleLin
 * @since 2021-04-17
 */

@Api(tags = "评分表管理")
@Validated
@PermissionModule(value = "评分表")
@RestController
@RequestMapping("/v1/scorecard")
public class ScorecardController {
	@Autowired
	private ScorecardService scorecardService;


	@GetMapping("/download/{id}")
	@ApiOperation(value = "下载评分表", notes = "下载评分表")
	public ResponseEntity<byte[]> downloadScoreTable(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		return scorecardService.exportScoreTable(id);
	}

	@GetMapping("/download/{id}/sup")
	@ApiOperation(value = "下载旧评分表", notes = "下载旧评分表")
	public ResponseEntity<byte[]> downloadOldScoreTable(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		ScorecardDO data = this.scorecardService.getById(id);
		return scorecardService.exportScoreTableBySup(data.getSupplement());
	}

	@GetMapping("/by/student/{sid}")
	@ApiOperation(value = "通过sid,获取评分表id", notes = "通过sid,获取评分表id")
	public Integer getScorecardIdBySid(@Validated @StudentIdsPattern @PathVariable String sid) {
		return scorecardService.getIdBySid(sid);
	}

	@PostMapping("")
	@GroupRequired
	@PermissionMeta(value = "指导教师")
	@ApiOperation(value = "新增评分表", notes = "指导教师评分，并创建表")
	public CreatedVO createByGuideTeacher(@Validated @RequestBody ScorecardDTO dto) {
		this.scorecardService.createByGuideTeacher(dto);
		return new CreatedVO();
	}


	@PutMapping("/{id}")
	@GroupRequired
	@PermissionMeta(value = "答辩小组")
	@ApiOperation(value = "更新评分表", notes = "答辩小组评分，并评分表")
	public UpdatedVO update(
			@Validated @RequestBody ScorecardDTO dto,
			@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.scorecardService.updateByJudgeTeam(id, dto);
		return new UpdatedVO();
	}

	@DeleteMapping("/{id}")
	@GroupRequired
	@PermissionMeta(value = "答辩小组")
	@ApiOperation(value = "删除评分表", notes = "删除评分表")
	public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.scorecardService.removeById(id);
		return new DeletedVO();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "通过id获取评分表", notes = "通过id获取评分表")
	public ScorecardDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
		return this.scorecardService.getById(id);
	}

	@GetMapping("/getScoreCard")
	public List<ScoreCardVO> getScoreCard(@RequestParam(required = true, name = "graduate_info_id") Integer graduateInfoId,
									@RequestParam(required = false, name = "card_id") Integer cardId,
									@RequestParam(required = false, name = "teacher_id")Integer teacherId,
									@RequestParam(required = false, name = "student_id")Integer studentId) {
		List<ScoreCardVO> scoreCardVO = this.scorecardService.getBy(graduateInfoId, cardId, teacherId, studentId);
		return scoreCardVO;
	}

	@GetMapping("/page")
	@ApiOperation(value = "分页带搜索", notes = "分页带搜索")
	public PageResponseVO<ScorecardDO> page(
			@RequestParam Integer graduateInfoId,
			@RequestParam(required = false) Integer teacherId,
			@RequestParam(name = "count", required = false, defaultValue = "10")
			@Min(value = 1, message = "{page.count.min}")
			@Max(value = 30, message = "{page.count.max}") Integer count,
			@RequestParam(name = "page", required = false, defaultValue = "0")
			@Min(value = 0, message = "{page.number.min}") Integer page
	) {
		Page<ScorecardDO> pager = new Page<>(page, count);
		IPage<ScorecardDO> paging = this.scorecardService.selectPage(pager, graduateInfoId, teacherId);
		return PageUtil.build(paging);
	}

	@GetMapping("/by/topic/{id}")
	@ApiOperation(value = "通过课题ID获取评分表", notes = "通过课题ID获取评分表")
	public ScorecardDO getByTopicId(
			@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
		return new LambdaQueryChainWrapper<>(this.scorecardService.getBaseMapper())
				.eq(ScorecardDO::getAssignId, id)
				.one();
	}

	@GetMapping("/exists/by/topic/{id}")
	public void existsByTopicId(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
		Integer count = new LambdaQueryChainWrapper<>(this.scorecardService.getBaseMapper())
				.eq(ScorecardDO::getAssignId, id)
				.count();
		if (count > 0) {
			throw new MethodNotAllowedException(2453);
		}
	}

	@GetMapping("/all/guide/complete")
	@ApiOperation(value = "获取指导老师评分完成的课题ID", notes = "获取指导老师评分完成的课题ID")
	public List<Integer> getAllGuideComplete() {
		return new LambdaQueryChainWrapper<>(this.scorecardService.getBaseMapper())
				.isNotNull(ScorecardDO::getGuideCardId)
				.list().stream()
				.map(ScorecardDO::getAssignId)
				.collect(Collectors.toList());
	}

	@GetMapping("/all/guide/advise/complete")
	@ApiOperation(value = "获取指导老师留言完成的课题ID,答辩小组未留言", notes = "获取指导老师留言完成的课题ID,答辩小组未留言")
	public List<Integer> getAllGuideAdviseComplete() {
		return new LambdaQueryChainWrapper<>(this.scorecardService.getBaseMapper())
				.isNotNull(ScorecardDO::getGuideAdvise)
				.isNull(ScorecardDO::getJudgeAdvise)
				.list().stream()
				.map(ScorecardDO::getAssignId)
				.collect(Collectors.toList());
	}

	@GetMapping("/all/complete")
	@ApiOperation(value = "所有工作（评分，留言）完成的课题ID", notes = "所有工作（评分，留言）完成的课题ID")
	public List<Integer> getAllCompleteTopicId() {
		return new LambdaQueryChainWrapper<>(this.scorecardService.getBaseMapper())
				.isNotNull(ScorecardDO::getJudgeCardId)
				.isNotNull(ScorecardDO::getJudgeAdvise)
				.isNotNull(ScorecardDO::getGuideCardId)
				.isNotNull(ScorecardDO::getGuideAdvise)
				.list().stream()
				.map(ScorecardDO::getAssignId)
				.collect(Collectors.toList());
	}


	@PutMapping("/{id}/guide/comment")
	@GroupRequired
	@PermissionMeta(value = "指导教师")
	@ApiOperation(value = "指导教师留言", notes = "指导教师留言")
	public UpdatedVO guideTeacherComment(
			@RequestParam(name = "comment", defaultValue = " ") String comment,
			@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.scorecardService.guideComment(id, comment);
		return new UpdatedVO();
	}

	@PutMapping("/{id}/committee/comment")
	@GroupRequired
	@PermissionMeta(value = "答辩委员会留言")
	@ApiOperation(value = "答辩委员会意见", notes = "答辩委员会意见")
	public UpdatedVO committeeComment(
			@RequestParam(name = "comment", defaultValue = " ") String comment,
			@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.scorecardService.committeeComment(id, comment);
		return new UpdatedVO();
	}

	@PutMapping("/{id}/judge/comment")
	@GroupRequired
	@PermissionMeta(value = "答辩小组")
	@ApiOperation(value = "答辩小组留言", notes = "答辩小组留言")
	public UpdatedVO judgeTeamComment(
			@RequestParam(name = "comment", defaultValue = " ") String comment,
			@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.scorecardService.judgeComment(id, comment);
		return new UpdatedVO();
	}


}
