package io.github.xmchxup.latticy.controller.v1;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.xmchxup.latticy.common.mybatis.Page;
import io.github.xmchxup.latticy.common.util.PageUtil;
import io.github.xmchxup.latticy.dto.TopicDTO;
import io.github.xmchxup.latticy.dto.TopicPureDTO;
import io.github.xmchxup.latticy.dto.validators.StudentIdsPattern;
import io.github.xmchxup.latticy.enumeration.TopicStatusEnum;
import io.github.xmchxup.latticy.enumeration.TopicTypeEnum;
import io.github.xmchxup.latticy.model.TopicDO;
import io.github.xmchxup.latticy.service.TeacherService;
import io.github.xmchxup.latticy.service.TopicService;
import io.github.xmchxup.latticy.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author generator@TaleLin
 * @since 2021-02-04
 */
@Api(tags = "课题管理")
@Validated
@PermissionModule(value = "课题")
@RestController
@RequestMapping("/v1/topic")
public class TopicController {

	@Autowired
	private TopicService topicService;

	@Autowired
	private TeacherService teacherService;

	@PostMapping("")
	@ApiOperation(value = "新增课题", notes = "新增课题")
	@GroupRequired
	@PermissionMeta(value = "创建课题")
	public CreatedVO create(@RequestBody @Validated TopicDTO dto) {
//		检测是否已经有课题进行审核中
		topicService.checkTopicStatusBySID(dto.getStudentId());
		final TopicDO topicDO = new TopicDO();
		BeanUtils.copyProperties(dto, topicDO);

		if (dto.getType().equals(TopicTypeEnum.STUDENT_SELECT.getValue())) {
			topicDO.setStatus(TopicStatusEnum.SUBMIT.getValue());
		} else if (dto.getType().equals(TopicTypeEnum.TEACHER_SELECT.getValue())) {
			if (StringUtils.isEmpty(dto.getStudentId())) {
				topicDO.setStatus(TopicStatusEnum.TOPIC_VACANT.getValue());
			} else {
				topicDO.setStatus(TopicStatusEnum.TOPIC_SELECTED.getValue());
			}
		}

		this.topicService.save(topicDO);
		return new CreatedVO();
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "更新课题", notes = "更新课题")
	@GroupRequired
	@PermissionMeta(value = "更新课题")
	public UpdatedVO update(@RequestBody @Validated TopicDTO dto,
							@PathVariable @Positive(message = "{id.positive}") Integer id) {
		final TopicDO topicDO = new TopicDO();
		BeanUtils.copyProperties(dto, topicDO);
		topicDO.setId(id);
		this.topicService.updateById(topicDO);
		return new UpdatedVO();
	}

	@PutMapping("/status")
	@ApiOperation(value = "审核课题", notes = "审核课题")
	@GroupRequired
	@PermissionMeta(value = "审核课题")
	public UpdatedVO updateStatus(@RequestBody @Validated TopicPureDTO dto) {
		final TopicDO topicDO = new TopicDO();
		BeanUtils.copyProperties(dto, topicDO);
		final TopicStatusEnum statusEnum = TopicStatusEnum.toType(dto.getStatusName());
		if (statusEnum == null) {
			throw new ParameterException(2451);
		}
		topicDO.setStatus(statusEnum.getValue());
		this.topicService.updateById(topicDO);
		return new UpdatedVO();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除课题", notes = "删除课题")
	@GroupRequired
	@PermissionMeta(value = "删除课题")
	public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.topicService.removeById(id);
		return new DeletedVO();
	}

	@GetMapping("/by/year")
	@ApiOperation(value = "获取今年的课题", notes = "获取今年的课题")
	public List<TopicPureVO> getByThisYear() {
//		答辩组账号调用
		return this.topicService.getByThisYear(false);
	}

	@GetMapping("/sup/by/year")
	@ApiOperation(value = "获取今年的补答辩课题", notes = "获取今年的补答辩课题")
	public List<TopicPureVO> getSupByThisYear() {
//		答辩组账号调用
		return this.topicService.getByThisYear(true);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "获取课题", notes = "获取课题")
	public TopicDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
		return this.topicService.getById(id);
	}

	@GetMapping("/getTopicByStudentSid")
	@ApiOperation(value = "通过学生SID获取课题", notes = "通过学生SID获取课题")
	public TopicDO getTopicByStudentSid(@RequestParam @Validated @StudentIdsPattern String sid) {
		return this.topicService.getTopicByStudentSid(sid);
	}

	@GetMapping("/getTopicsByTeacherId")
	@ApiOperation(value = "通过教师TID获取课题", notes = "通过教师TID获取课题")
	public List<TopicDO> getTopicsByTeacherId(@RequestParam("teacherId") @Validated
											  @Pattern(regexp = "\\d{4}", message = "{teacher.tid}")
													  String teacherTid) {
		return this.topicService.getTopicsByTeacherId(
				this.teacherService.getTeacherIdByTID(teacherTid), false);
	}

	@GetMapping("/getSupTopicsByTeacherId")
	@ApiOperation(value = "通过教师TID获取补答辩课题", notes = "通过教师TID获取补答辩课题")
	public List<TopicDO> getSupTopicsByTeacherId(@RequestParam("teacherId") @Validated
												 @Pattern(regexp = "\\d{4}", message = "{teacher.tid}")
														 String teacherTid) {
		return this.topicService.getTopicsByTeacherId(
				this.teacherService.getTeacherIdByTID(teacherTid), true);
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取课题分页列表", notes = "获取课题分页列表")
	public PageResponseVO<TopicAssignVO> page(
			@RequestParam(name = "keyword", required = false)
					String name,
			@RequestParam(name = "count", required = false, defaultValue = "10")
			@Min(value = 1, message = "{page.count.min}")
			@Max(value = 30, message = "{page.count.max}") Integer count,
			@RequestParam(name = "page", required = false, defaultValue = "0")
			@Min(value = 0, message = "{page.number.min}") Integer page
	) {
		Page<TopicAssignVO> pager = new Page<>(page, count);

		final QueryWrapper<TopicDO> wrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(name))
			wrapper.lambda().like(TopicDO::getName, name);

		IPage<TopicAssignVO> paging = this.topicService.selectPageVO(pager, name);
		return PageUtil.build(paging);
	}

}
