package io.github.xmchxup.latticy.controller.v1;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.xmchxup.latticy.common.mybatis.Page;
import io.github.xmchxup.latticy.common.util.PageUtil;
import io.github.xmchxup.latticy.dto.ClassDTO;
import io.github.xmchxup.latticy.model.ClassDO;
import io.github.xmchxup.latticy.service.ClassService;
import io.github.xmchxup.latticy.service.StudentService;
import io.github.xmchxup.latticy.vo.CreatedVO;
import io.github.xmchxup.latticy.vo.DeletedVO;
import io.github.xmchxup.latticy.vo.PageResponseVO;
import io.github.xmchxup.latticy.vo.UpdatedVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author generator@TaleLin
 * @since 2021-01-15
 */
@RestController
@RequestMapping("/v1//class")
@Validated
@PermissionModule(value = "班级")
@Api(tags = "班级管理")
public class ClassController {


	@Autowired
	private ClassService classService;

	@Autowired
	private StudentService studentService;


	@PostMapping("")
	@GroupRequired
	@PermissionMeta(value = "创建班级")
	@ApiOperation(value = "新增班级", notes = "新增班级")
	public CreatedVO create(@RequestBody @Validated ClassDTO dto) {
		this.classService.create(dto);
		return new CreatedVO();
	}

	@PutMapping("/{id}")
	@GroupRequired
	@PermissionMeta(value = "更新班级")
	@ApiOperation(value = "更新班级", notes = "更新班级")
	public UpdatedVO update(@RequestBody @Validated ClassDTO dto,
							@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.classService.update(dto, id);
		return new UpdatedVO();
	}

	@DeleteMapping("/{id}")
	@GroupRequired
	@PermissionMeta(value = "删除班级")
	@ApiOperation(value = "删除班级", notes = "删除班级")
	public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.studentService.checkStudentByClassId(id);
		this.classService.getBaseMapper().deleteById(id);
		return new DeletedVO();
	}

	@GetMapping("/search")
	@ApiOperation(value = "通过名称搜索班级", notes = "通过名称搜索班级")
	@LoginRequired
	public List<ClassDO> searchMajorByName(@RequestParam(name = "keyword", required = false)
												   String className) {
		return new LambdaQueryChainWrapper<>(this.classService.getBaseMapper())
				.like(ClassDO::getName, className)
				.list();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "获取班级", notes = "获取班级")
	@LoginRequired
	public ClassDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
		return this.classService.getById(id);
	}

	@GetMapping("/all")
	@ApiOperation(value = "获取所有班级", notes = "获取所有班级")
	@LoginRequired
	public List<ClassDO> getAllClass() {
		return classService.list();
	}


	@GetMapping("/page")
	@ApiOperation(value = "班级列表", notes = "班级列表")
	@LoginRequired
	public PageResponseVO<ClassDO> page(
			@RequestParam(name = "keyword", required = false)
					Integer majorId,
			@RequestParam(name = "count", required = false, defaultValue = "10")
			@Min(value = 1, message = "{page.count.min}")
			@Max(value = 30, message = "{page.count.max}") Integer count,
			@RequestParam(name = "page", required = false, defaultValue = "0")
			@Min(value = 0, message = "{page.number.min}") Integer page
	) {
		Page<ClassDO> pager = new Page<>(page, count);

		final QueryWrapper<ClassDO> queryWrapper = new QueryWrapper<>();
		if (majorId != null) {
			queryWrapper.lambda().eq(ClassDO::getMajorId, majorId);
		}

		IPage<ClassDO> paging = this.classService
				.getBaseMapper()
				.selectPage(pager, queryWrapper);
		return PageUtil.build(paging);
	}

}
