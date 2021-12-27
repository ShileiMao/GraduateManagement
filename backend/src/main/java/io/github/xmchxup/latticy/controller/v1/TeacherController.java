package io.github.xmchxup.latticy.controller.v1;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.core.annotation.AdminRequired;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.xmchxup.latticy.common.mybatis.Page;
import io.github.xmchxup.latticy.common.util.PageUtil;
import io.github.xmchxup.latticy.dto.TeacherDTO;
import io.github.xmchxup.latticy.model.TeacherDO;
import io.github.xmchxup.latticy.service.TeacherService;
import io.github.xmchxup.latticy.vo.CreatedVO;
import io.github.xmchxup.latticy.vo.DeletedVO;
import io.github.xmchxup.latticy.vo.PageResponseVO;
import io.github.xmchxup.latticy.vo.UpdatedVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author generator@TaleLin
 * @since 2021-01-03
 */

@RestController
@RequestMapping("/v1/teacher")
@Api(tags = "教师管理")
@Validated
public class TeacherController {

	@Autowired
	private TeacherService teacherService;

	@PostMapping("")
	@GroupRequired
	@ApiOperation(value = "新增教师", notes = "新增教师")
	@AdminRequired
	public CreatedVO create(@RequestBody @Validated TeacherDTO dto) {
		this.teacherService.createTeacher(dto);
		return new CreatedVO();
	}

	@PutMapping("/{id}")
	@GroupRequired
	@ApiOperation(value = "更新教师", notes = "更新教师")
	@AdminRequired
	public UpdatedVO update(@RequestBody @Validated TeacherDTO dto,
							@PathVariable @Positive(message = "{id.positive}") Integer id) {
		dto.setTid(null);
		dto.setEmail(null);

		TeacherDO teacherDO = this.teacherService.getById(id);
		if (null == teacherDO) {
			throw new ParameterException(2430);
		}
		BeanUtils.copyProperties(dto, teacherDO);
		this.teacherService.updateById(teacherDO);
		return new UpdatedVO();
	}

	@DeleteMapping("/{id}")
	@GroupRequired
	@ApiOperation(value = "删除教师", notes = "删除教师")
	@AdminRequired
	public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.teacherService.deleteTeacher(id);
		return new DeletedVO();
	}

	@GetMapping("/all")
	@ApiOperation(value = "获取所有教师", notes = "获取所有教师")
	public List<TeacherDO> getAll() {
		return this.teacherService.list();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "获取教师", notes = "获取教师")
	public TeacherDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
		return this.teacherService.getById(id);
	}

	@GetMapping("/page")
	@ApiOperation(value = "教师列表", notes = "教师列表")
	public PageResponseVO<TeacherDO> page(
			@RequestParam(name = "keyword", required = false)
					String teacherName,
			@RequestParam(name = "count", required = false, defaultValue = "10")
			@Min(value = 1, message = "{page.count.min}")
			@Max(value = 30, message = "{page.count.max}") Integer count,
			@RequestParam(name = "page", required = false, defaultValue = "0")
			@Min(value = 0, message = "{page.number.min}") Integer page
	) {
		Page<TeacherDO> pager = new Page<>(page, count);

		final QueryWrapper<TeacherDO> queryWrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(teacherName)) {
			queryWrapper.lambda().like(TeacherDO::getName, teacherName);
		}

		Page<TeacherDO> paging = this.teacherService
				.getBaseMapper()
				.selectPage(pager, queryWrapper);
		return PageUtil.build(paging);
	}

}
