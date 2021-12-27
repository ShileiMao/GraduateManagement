package io.github.xmchxup.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.xmchxup.latticy.dto.StudentDTO;
import io.github.xmchxup.latticy.model.StudentDO;
import io.github.xmchxup.latticy.vo.StudentPureVO;
import io.github.xmchxup.latticy.vo.StudentVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-01-03
 */
public interface StudentService extends IService<StudentDO> {
	boolean existsStudentBySID(String sid);

	void checkStudentByClassId(Integer classId);

	void createStudent(StudentDTO dto);

	void deleteStudent(Integer id);

	StudentVO getStudentById(Integer id);

	IPage<StudentVO> selectPageVO(IPage<StudentVO> page, String studentName);

	List<StudentPureVO> getStudentsByIds(String studentIds);

	public List<StudentVO>getStudents(String majorId, String classId);

	public List<StudentVO>getStudentsInCollege(Integer collegeId);
}
