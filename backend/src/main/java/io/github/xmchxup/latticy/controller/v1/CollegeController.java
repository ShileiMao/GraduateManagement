package io.github.xmchxup.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.xmchxup.latticy.common.mybatis.Page;
import io.github.xmchxup.latticy.common.util.PageUtil;
import io.github.xmchxup.latticy.dto.CollegeDTO;
import io.github.xmchxup.latticy.model.CollegeDO;
import io.github.xmchxup.latticy.service.CollegeService;
import io.github.xmchxup.latticy.service.MajorService;
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
@RequestMapping("/v1//college")
@Validated
@PermissionModule(value = "学院")
@Api(tags = "学院管理")
public class CollegeController {

	@Autowired
	private CollegeService collegeService;

	@Autowired
	private MajorService majorService;


	@PostMapping("")
	@GroupRequired
	@PermissionMeta(value = "创建学院")
	@ApiOperation(value = "新增学院", notes = "新增学院")
	public CreatedVO create(@RequestBody @Validated CollegeDTO dto) {
		this.collegeService.create(dto);
		return new CreatedVO();
	}

	@PutMapping("/{id}")
	@GroupRequired
	@PermissionMeta(value = "更新学院")
	@ApiOperation(value = "更新学院", notes = "更新学院")
	public UpdatedVO update(@RequestBody @Validated CollegeDTO dto,
							@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.collegeService.update(dto, id);
		return new UpdatedVO();
	}

	@DeleteMapping("/{id}")
	@GroupRequired
	@PermissionMeta(value = "删除学院")
	@ApiOperation(value = "删除学院", notes = "删除学院")
	public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.majorService.checkMajorByCollegeId(id);
		this.collegeService.removeById(id);
		return new DeletedVO();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "获取学院", notes = "获取学院")
	public CollegeDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
		return this.collegeService.getById(id);
	}

	@GetMapping("/all")
	@ApiOperation(value = "获取所有学院", notes = "获取所有学院")
	@LoginRequired
	public List<CollegeDO> getAllColleges() {
		return collegeService.list();
	}


	@GetMapping("/page")
	@ApiOperation(value = "学院列表", notes = "学院列表")
	public PageResponseVO<CollegeDO> page(
			@RequestParam(name = "count", required = false, defaultValue = "10")
			@Min(value = 1, message = "{page.count.min}")
			@Max(value = 30, message = "{page.count.max}") Integer count,
			@RequestParam(name = "page", required = false, defaultValue = "0")
			@Min(value = 0, message = "{page.number.min}") Integer page
	) {
		Page<CollegeDO> pager = new Page<>(page, count);
		IPage<CollegeDO> paging = this.collegeService
				.getBaseMapper()
				.selectPage(pager, null);
		return PageUtil.build(paging);
	}

}
