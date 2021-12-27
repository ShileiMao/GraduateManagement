package io.github.xmchxup.latticy.controller.v1;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.xmchxup.latticy.common.mybatis.Page;
import io.github.xmchxup.latticy.common.util.PageUtil;
import io.github.xmchxup.latticy.dto.GraduateInfoDTO;
import io.github.xmchxup.latticy.model.GraduateInfoDO;
import io.github.xmchxup.latticy.model.TeacherDO;
import io.github.xmchxup.latticy.service.GraduateInfoService;
import io.github.xmchxup.latticy.service.TeacherService;
import io.github.xmchxup.latticy.vo.CreatedVO;
import io.github.xmchxup.latticy.vo.DeletedVO;
import io.github.xmchxup.latticy.vo.PageResponseVO;
import io.github.xmchxup.latticy.vo.UpdatedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

/**
 * @author generator@TaleLin
 * @since 2021-04-27
 */
@Validated
@RestController
@RequestMapping("/v1//graduate-info")
public class GraduateInfoController {

	@Autowired
	private GraduateInfoService graduateInfoService;

	@Autowired
	private TeacherService teacherService;

	@PostMapping("")
	public CreatedVO create(@RequestBody @Validated GraduateInfoDTO dto) {
		this.graduateInfoService.create(dto);
		return new CreatedVO();
	}

	@PutMapping("/{id}")
	public UpdatedVO update(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		return new UpdatedVO();
	}

	@DeleteMapping("/{id}")
	public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		return new DeletedVO();
	}

	@GetMapping("/{id}")
	public GraduateInfoDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
		return null;

	}

	@GetMapping("/all")
	public List<GraduateInfoDO> getAll() {
		return this.graduateInfoService.list();
	}

	@GetMapping("/{id}/guide/teachers")
	public List<TeacherDO> getGuideTeacherByGraduateId(@PathVariable(value = "id")
													   @Positive(message = "{id.positive}") Integer id) {
		GraduateInfoDO graduateInfoDO = this.graduateInfoService.getById(id);
		if (graduateInfoDO == null) {
			throw new ParameterException(2460);
		}
		ArrayList<TeacherDO> list = new ArrayList<>();
		String[] teacherIds = graduateInfoDO.getTeacherIds().split(",");
		for (String teacherId : teacherIds) {
			TeacherDO teacherDO = this.teacherService.getById(Integer.parseInt(teacherId));
			list.add(teacherDO);
		}
		return list;
	}

	@GetMapping("/page")
	public PageResponseVO<GraduateInfoDO> page(
			@RequestParam(name = "count", required = false, defaultValue = "10")
			@Min(value = 1, message = "{page.count.min}")
			@Max(value = 30, message = "{page.count.max}") Integer count,
			@RequestParam(name = "page", required = false, defaultValue = "0")
			@Min(value = 0, message = "{page.number.min}") Integer page
	) {
		Page<GraduateInfoDO> pager = new Page<>(page, count);

		IPage<GraduateInfoDO> paging = this.graduateInfoService
				.getBaseMapper()
				.selectPage(pager, null);
		return PageUtil.build(paging);
	}

}
