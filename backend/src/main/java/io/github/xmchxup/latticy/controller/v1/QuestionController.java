package io.github.xmchxup.latticy.controller.v1;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.xmchxup.latticy.common.mybatis.Page;
import io.github.xmchxup.latticy.common.util.PageUtil;
import io.github.xmchxup.latticy.dto.QuestionDTO;
import io.github.xmchxup.latticy.model.OptionDO;
import io.github.xmchxup.latticy.model.QuestionDO;
import io.github.xmchxup.latticy.service.OptionService;
import io.github.xmchxup.latticy.service.QuestionService;
import io.github.xmchxup.latticy.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

/**
 * @author generator@TaleLin
 * @since 2021-04-14
 */
@Validated
@Api(tags = "评分准则管理")
@RestController
@RequestMapping("/v1//question")
@PermissionModule(value = "评分准则")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private OptionService optionService;

	@PostMapping("")
	@GroupRequired
	@PermissionMeta(value = "指导教师")
	@ApiOperation(value = "新增评分准则", notes = "新增评分准则")
	public CreatedVO create(@Validated @RequestBody QuestionDTO dto) {
		questionService.create(dto);
		return new CreatedVO();
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "更新评分准则", notes = "更新评分准则")
	public UpdatedVO update(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		return new UpdatedVO();
	}

	@DeleteMapping("/{id}")
	@GroupRequired
	@PermissionMeta(value = "指导教师")
	@ApiOperation(value = "删除评分准则", notes = "删除评分准则")
	public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.questionService.deleteById(id);
		return new DeletedVO();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "获取评分准则", notes = "获取评分准则")
	public QuestionDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
		return this.questionService.getById(id);
	}

	@GetMapping("/options")
	@ApiOperation(value = "通过ids获取选项", notes = "通过ids获取选项")
	public List<OptionDO> getOptionsByQuestion(
			@Validated @Pattern(regexp = "^\\d+,\\d+,\\d+,\\d+", message = "{option.ids}")
			@RequestParam(name = "optionIds") String optionIds) {

		QueryWrapper<OptionDO> wrapper = new QueryWrapper<>();
		wrapper.lambda().inSql(OptionDO::getId, optionIds);
		return this.optionService.getBaseMapper().selectList(wrapper);
	}

	@GetMapping("")
	@ApiOperation(value = "获取所有评分准则", notes = "获取所有评分准则")
	public List<QuestionPureVO> getAllQuestion() {
		ArrayList<QuestionPureVO> result = new ArrayList<>();

		List<QuestionDO> list = this.questionService.list();

		list.forEach(questionDO -> {
			QuestionPureVO questionPureVO = new QuestionPureVO();
			BeanUtils.copyProperties(questionDO, questionPureVO);
			result.add(questionPureVO);
		});

		return result;
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取评分准则分页列表", notes = "获取评分准则分页列表")
	public PageResponseVO<QuestionDO> page(
			@RequestParam(name = "count", required = false, defaultValue = "10")
			@Min(value = 1, message = "{page.count.min}")
			@Max(value = 30, message = "{page.count.max}") Integer count,
			@RequestParam(name = "page", required = false, defaultValue = "0")
			@Min(value = 0, message = "{page.number.min}") Integer page
	) {
		Page<QuestionDO> pager = new Page<>(page, count);


		IPage<QuestionDO> paging = this.questionService
				.getBaseMapper()
				.selectPage(pager, null);
		return PageUtil.build(paging);
	}

}
