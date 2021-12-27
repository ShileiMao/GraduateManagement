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
import io.github.xmchxup.latticy.dto.MajorDTO;
import io.github.xmchxup.latticy.model.MajorDO;
import io.github.xmchxup.latticy.service.ClassService;
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
@RequestMapping("/v1//major")
@Validated
@PermissionModule(value = "专业")
@Api(tags = "专业管理")
public class MajorController {

	@Autowired
	private MajorService majorService;

	@Autowired
	private ClassService classService;


	@PostMapping("")
	@GroupRequired
	@PermissionMeta(value = "创建专业")
	@ApiOperation(value = "新增专业", notes = "新增专业")
	public CreatedVO create(@RequestBody @Validated MajorDTO dto) {
		this.majorService.create(dto);
		return new CreatedVO();
	}

	@PutMapping("/{id}")
	@GroupRequired
	@PermissionMeta(value = "更新专业")
	@ApiOperation(value = "更新专业", notes = "更新专业")
	public UpdatedVO update(@RequestBody @Validated MajorDTO dto,
							@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.majorService.update(dto, id);
		return new UpdatedVO();
	}

	@DeleteMapping("/{id}")
	@GroupRequired
	@PermissionMeta(value = "删除专业")
	@ApiOperation(value = "删除专业", notes = "删除专业")
	public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.classService.checkClassByMajorId(id);
		this.majorService.getBaseMapper().deleteById(id);
		return new DeletedVO();
	}


	@GetMapping("/{id}")
	@ApiOperation(value = "获取专业", notes = "获取专业")
	@LoginRequired
	public MajorDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
		return this.majorService.getById(id);
	}

	@GetMapping("/search")
	@ApiOperation(value = "通过名称搜索专业", notes = "通过名称搜索专业")
	@LoginRequired
	public List<MajorDO> searchMajorByName(@RequestParam(name = "keyword", required = false)
												   String majorName) {
		return new LambdaQueryChainWrapper<>(this.majorService.getBaseMapper())
				.like(MajorDO::getName, majorName)
				.list();
	}

	@GetMapping("/all")
	@ApiOperation(value = "获取所有专业", notes = "获取所有专业")
	@LoginRequired
	public List<MajorDO> getAllMajors() {
		return majorService.list();
	}

	@GetMapping("/page")
	@ApiOperation(value = "专业列表", notes = "专业列表")
	@LoginRequired
	public PageResponseVO<MajorDO> page(
			@RequestParam(name = "keyword", required = false)
					Integer collegeId,
			@RequestParam(name = "count", required = false, defaultValue = "10")
			@Min(value = 1, message = "{page.count.min}")
			@Max(value = 30, message = "{page.count.max}") Integer count,
			@RequestParam(name = "page", required = false, defaultValue = "0")
			@Min(value = 0, message = "{page.number.min}") Integer page
	) {
		Page<MajorDO> pager = new Page<>(page, count);

		final QueryWrapper<MajorDO> queryWrapper = new QueryWrapper<>();
		if (collegeId != null) {
			queryWrapper.lambda().eq(MajorDO::getCollegeId, collegeId);
		}

		IPage<MajorDO> paging = this.majorService
				.getBaseMapper()
				.selectPage(pager, queryWrapper);
		return PageUtil.build(paging);
	}

}
