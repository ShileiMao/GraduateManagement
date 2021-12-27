package io.github.xmchxup.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.xmchxup.latticy.dto.StudentDTO;
import io.github.xmchxup.latticy.dto.user.RegisterDTO;
import io.github.xmchxup.latticy.mapper.StudentMapper;
import io.github.xmchxup.latticy.model.StudentDO;
import io.github.xmchxup.latticy.model.UserDO;
import io.github.xmchxup.latticy.model.UserIdentityDO;
import io.github.xmchxup.latticy.service.StudentService;
import io.github.xmchxup.latticy.service.UserIdentityService;
import io.github.xmchxup.latticy.service.UserService;
import io.github.xmchxup.latticy.vo.StudentPureVO;
import io.github.xmchxup.latticy.vo.StudentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-01-03
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, StudentDO> implements StudentService {

	@Autowired
	private StudentMapper studentMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserIdentityService userIdentityService;


	@Override
	public boolean existsStudentBySID(String sid) {
		return this.studentMapper.existsStudentBySID(sid) != 0;
	}

	@Override
	public void checkStudentByClassId(Integer classId) {
		final List<StudentDO> list = new LambdaQueryChainWrapper<>(this.baseMapper)
				.eq(StudentDO::getClassId, classId).list();
		if (!list.isEmpty()) {
			throw new ParameterException(2399);
		}
	}

	@Override
	public StudentVO getStudentById(Integer id) {
		return this.studentMapper.getStudentById(id);
	}

	@Override
	public IPage<StudentVO> selectPageVO(IPage<StudentVO> page, String studentName) {
		return this.baseMapper.selectPageVO(page, studentName);
	}

	@Transactional
	@Override
	public void createStudent(StudentDTO dto) {
		if (this.existsStudentBySID(dto.getSid())) {
			throw new ParameterException(2401);
		}

		StudentDO studentDO = new StudentDO();
		BeanUtils.copyProperties(dto, studentDO);
		this.save(studentDO);

		RegisterDTO registerDTO = new RegisterDTO();
		registerDTO.setEmail(dto.getEmail());
		registerDTO.setUsername(dto.getSid());
		registerDTO.setGroupIds(Collections.singletonList(3));
		registerDTO.setPassword("123456");
		registerDTO.setConfirmPassword("123456");
		this.userService.createUser(registerDTO);
	}

	@Transactional
	@Override
	public void deleteStudent(Integer id) {
		StudentDO studentDO = this.getById(id);
		if (null == studentDO) return;

		this.getBaseMapper().deleteById(id);

		UserDO userDO = this.userService.getUserByUsername(studentDO.getSid());

		QueryWrapper<UserDO> userWrapper = new QueryWrapper<>();
		userWrapper.lambda().eq(UserDO::getUsername, studentDO.getSid());
		this.userService.remove(userWrapper);

		QueryWrapper<UserIdentityDO> userIdentityWrapper = new QueryWrapper<>();
		userIdentityWrapper.lambda().eq(UserIdentityDO::getUserId, userDO.getId());
		this.userIdentityService.remove(userIdentityWrapper);
	}

	@Override
	public List<StudentPureVO> getStudentsByIds(String studentSids) {
		//1902411124,19002411124
		if (studentSids.endsWith(",")) {
			studentSids = studentSids.substring(0, studentSids.length() - 1);
		}

		final QueryWrapper<StudentDO> wrapper = new QueryWrapper<>();
		wrapper.lambda().inSql(StudentDO::getSid, studentSids);

		final List<StudentDO> result = this.baseMapper.selectList(wrapper);

		return result.stream().map(StudentPureVO::new).collect(Collectors.toList());
	}

	@Override
	public List<StudentVO>getStudents(String majorId, String classId) {
		return this.studentMapper.allStudentsInMajor(majorId, classId);
	}

	@Override
	public List<StudentVO>getStudentsInCollege(Integer collegeId) {
		return this.studentMapper.allStudentsInCollege(collegeId);
	}
}
