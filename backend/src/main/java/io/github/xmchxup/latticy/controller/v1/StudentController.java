package io.github.xmchxup.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.xmchxup.latticy.common.mybatis.Page;
import io.github.xmchxup.latticy.common.util.PageUtil;
import io.github.xmchxup.latticy.dto.StudentDTO;
import io.github.xmchxup.latticy.model.StudentDO;
import io.github.xmchxup.latticy.service.StudentService;
import io.github.xmchxup.latticy.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * @author generator@TaleLin
 * @since 2021-01-03
 */
@RestController
@RequestMapping("/v1/student")
@Api(tags = "学生管理")
@Validated
@PermissionModule(value = "学生")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@PostMapping("")
	@ApiOperation(value = "新增学生", notes = "新增学生")
	@GroupRequired
	@PermissionMeta(value = "创建学生")
	public CreatedVO create(@RequestBody @Validated StudentDTO dto) {
		this.studentService.createStudent(dto);
		return new CreatedVO();
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "更新学生", notes = "更新学生")
	@GroupRequired
	@PermissionMeta(value = "更新学生")
	public UpdatedVO update(@RequestBody @Validated StudentDTO dto,
							@PathVariable @Positive(message = "{id.positive}") Integer id) {
		dto.setSid(null);
		dto.setEmail(null);
		dto.setClassId(null);

		StudentDO studentDO = this.studentService.getById(id);
		if (null == studentDO) {
			throw new NotFoundException(2400);
		}
		BeanUtils.copyProperties(dto, studentDO);
		this.studentService.updateById(studentDO);
		return new UpdatedVO();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除学生", notes = "删除学生")
	@GroupRequired
	@PermissionMeta(value = "删除学生")
	public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {

		this.studentService.deleteStudent(id);
		return new DeletedVO();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "获取学生", notes = "获取学生")
	public StudentVO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
		return this.studentService.getStudentById(id);
	}

	@GetMapping("/page")
	@ApiOperation(value = "学生列表", notes = "学生列表")
	public PageResponseVO<StudentVO> page(
			@RequestParam(name = "keyword", required = false)
					String studentName,
			@RequestParam(name = "count", required = false, defaultValue = "10")
			@Min(value = 1, message = "{page.count.min}")
			@Max(value = 30, message = "{page.count.max}") Integer count,
			@RequestParam(name = "page", required = false, defaultValue = "0")
			@Min(value = 0, message = "{page.number.min}") Integer page
	) {
		Page<StudentVO> pager = new Page<>(page, count);

		IPage<StudentVO> paging = this.studentService.selectPageVO(pager, studentName);
		return PageUtil.build(paging);
	}

}
