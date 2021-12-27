package io.github.xmchxup.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.xmchxup.latticy.dto.TeacherDTO;
import io.github.xmchxup.latticy.dto.user.RegisterDTO;
import io.github.xmchxup.latticy.mapper.TeacherMapper;
import io.github.xmchxup.latticy.model.TeacherDO;
import io.github.xmchxup.latticy.model.UserDO;
import io.github.xmchxup.latticy.model.UserIdentityDO;
import io.github.xmchxup.latticy.service.TeacherService;
import io.github.xmchxup.latticy.service.UserIdentityService;
import io.github.xmchxup.latticy.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-01-03
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, TeacherDO> implements TeacherService {

	@Autowired
	private TeacherMapper teacherMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserIdentityService userIdentityService;

	@Override
	public boolean existsTeacherByTID(String tid) {
		return teacherMapper.existsTeacherByTID(tid);
	}

	@Override
	public void existsTeacherByIds(List<Integer> ids) {
		if (ids == null || ids.isEmpty()) throw new ParameterException(2430);
		ids.forEach(id -> {
			TeacherDO teacherDO = this.getById(id);
			if (teacherDO == null) {
				throw new ParameterException(2430);
			}
		});
	}

	@Transactional
	@Override
	public void createTeacher(TeacherDTO dto) {
		if (this.existsTeacherByTID(dto.getTid())) {
			throw new ParameterException(2431);
		}
		TeacherDO teacherDO = new TeacherDO();
		BeanUtils.copyProperties(dto, teacherDO);
		this.save(teacherDO);

		RegisterDTO registerDTO = new RegisterDTO();
		registerDTO.setEmail(dto.getEmail());
		registerDTO.setUsername(dto.getTid());
		registerDTO.setGroupIds(Collections.singletonList(4));
		registerDTO.setPassword("123456");
		registerDTO.setConfirmPassword("123456");
		this.userService.createUser(registerDTO);
	}

	@Transactional
	@Override
	public void deleteTeacher(Integer id) {
		TeacherDO teacherDO = this.getById(id);
		if (null == teacherDO) return;

		this.removeById(id);

		UserDO userDO = this.userService.getUserByUsername(teacherDO.getTid());

		QueryWrapper<UserDO> userWrapper = new QueryWrapper<>();
		userWrapper.lambda().eq(UserDO::getUsername, teacherDO.getTid());
		this.userService.remove(userWrapper);

		QueryWrapper<UserIdentityDO> userIdentityWrapper = new QueryWrapper<>();
		userIdentityWrapper.lambda().eq(UserIdentityDO::getUserId, userDO.getId());
		this.userIdentityService.remove(userIdentityWrapper);
	}

	@Override
	public void checkTeacherByTID(String teacherTid) {
		final QueryWrapper<TeacherDO> wrapper = new QueryWrapper<>();
		wrapper.lambda().eq(TeacherDO::getTid, teacherTid);
		final TeacherDO teacher = this.getOne(wrapper);
		if (teacher == null) {
			throw new ParameterException(2430);
		}
	}


	@Override
	public Integer getTeacherIdByTID(String teacherTid) {
		this.checkTeacherByTID(teacherTid);

		final QueryWrapper<TeacherDO> wrapper = new QueryWrapper<>();
		wrapper.lambda().eq(TeacherDO::getTid, teacherTid);
		return this.getOne(wrapper).getId();
	}
}
