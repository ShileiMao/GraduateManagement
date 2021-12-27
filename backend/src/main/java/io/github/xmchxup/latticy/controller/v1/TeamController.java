package io.github.xmchxup.latticy.controller.v1;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.core.annotation.GroupRequired;
import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.xmchxup.latticy.common.mybatis.Page;
import io.github.xmchxup.latticy.common.util.PageUtil;
import io.github.xmchxup.latticy.dto.TeamDTO;
import io.github.xmchxup.latticy.dto.validators.StudentIdsPattern;
import io.github.xmchxup.latticy.model.TeamDO;
import io.github.xmchxup.latticy.service.StudentService;
import io.github.xmchxup.latticy.service.TeamService;
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
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author generator@TaleLin
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/v1//team")
@Api(tags = "团队管理")
@Validated
@PermissionModule(value = "团队")
public class TeamController {
	@Autowired
	private TeamService teamService;

	@Autowired
	private StudentService studentService;

	@PostMapping("")
	@ApiOperation(value = "新增团队", notes = "新增团队")
	@GroupRequired
	@PermissionMeta(value = "创建团队")
	public CreatedVO create(@RequestBody @Validated TeamDTO dto) {
		this.teamService.existsTeamByTeacherIdAndYears(dto.getTeacherId(), dto.getYears());
		final TeamDO teamDO = new TeamDO();
		BeanUtils.copyProperties(dto, teamDO);
		this.teamService.save(teamDO);
		return new CreatedVO();
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "更新团队", notes = "更新团队")
	@GroupRequired
	@PermissionMeta(value = "更新团队")
	public UpdatedVO update(@RequestBody @Validated TeamDTO dto,
							@PathVariable @Positive(message = "{id.positive}") Integer id) {
		final TeamDO teamDO = new TeamDO();
		BeanUtils.copyProperties(dto, teamDO);
		teamDO.setId(id);
		this.teamService.updateById(teamDO);
		return new UpdatedVO();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除团队", notes = "删除团队")
	@GroupRequired
	@PermissionMeta(value = "删除团队")
	public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
//		检测是否有所属信息
		final TeamDO teamDO = this.teamService.getById(id);
		if (!StringUtils.isEmpty(teamDO.getStudentIds())) {
			throw new ParameterException(2399);
		}
		this.teamService.removeById(id);
		return new DeletedVO();
	}

	@GetMapping("/students")
	@ApiOperation(value = "获取团队成员", notes = "获取团队成员")
	public List<StudentPureVO> getStudents(@RequestParam @StudentIdsPattern String studentSids) {
		return this.studentService.getStudentsByIds(studentSids);
	}

	@GetMapping("/studentsInMajor")
	public List<StudentVO>getStudentsByMajor(@RequestParam String majorId, @RequestParam String classId) {
		return this.studentService.getStudents(majorId, classId);
	}

	@GetMapping("studentsInCollege")
	public List<StudentVO>getStudentsByCollge(@RequestParam String collegeId) {
		Integer value = Integer.parseInt(collegeId);
		return this.studentService.getStudentsInCollege(value);
	}

	@PostMapping("/students")
	@ApiOperation(value = "新增团队成员", notes = "新增团队成员")
	public CreatedVO addStudents(@RequestParam @Positive(message = "{id.positive}") Integer teamId,
								 @RequestParam @StudentIdsPattern String studentSids) {
		if (!studentSids.endsWith(",")) {
			studentSids = studentSids + ",";
		}


		final TeamDO updateDo = new TeamDO();

		final TeamDO teamDO = this.teamService.getById(teamId);

		String original = teamDO.getStudentIds();
		if (!original.endsWith(",")) {
			original = original + ",";
		}

		updateDo.setId(teamId);
		updateDo.setStudentIds(
				original + studentSids
		);
		this.teamService.updateById(updateDo);

		return new CreatedVO();
	}

	@DeleteMapping("/students")
	@ApiOperation(value = "删除团队成员", notes = "删除团队成员")
	public DeletedVO deleteStudent(@RequestParam @Positive(message = "{id.positive}") Integer teamId,
								   @RequestParam @StudentIdsPattern String studentSids) {
		final TeamDO updateDo = new TeamDO();

		final TeamDO teamDO = this.teamService.getById(teamId);


		updateDo.setId(teamId);
		updateDo.setStudentIds(teamDO
				.getStudentIds()
				.replaceAll(studentSids + "(,)?",
						""
				));
		this.teamService.updateById(updateDo);

		return new DeletedVO();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "获取团队", notes = "获取团队")
	public TeamDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
		return this.teamService.getById(id);
	}

	@GetMapping("/page")
	@ApiOperation(value = "获取团队分页列表", notes = "获取团队分页列表")
	public PageResponseVO<TeamDO> page(
			@RequestParam(name = "keyword", required = false)
					String teamName,
			@RequestParam(name = "count", required = false, defaultValue = "10")
			@Min(value = 1, message = "{page.count.min}")
			@Max(value = 30, message = "{page.count.max}") Integer count,
			@RequestParam(name = "page", required = false, defaultValue = "0")
			@Min(value = 0, message = "{page.number.min}") Integer page
	) {
		Page<TeamDO> pager = new Page<>(page, count);

		final QueryWrapper<TeamDO> wrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(teamName))
			wrapper.lambda().like(TeamDO::getName, teamName);


		IPage<TeamDO> paging = this.teamService
				.getBaseMapper()
				.selectPage(pager, wrapper);
		return PageUtil.build(paging);
	}

}
