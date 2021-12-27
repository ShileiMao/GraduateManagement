package io.github.xmchxup.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.xmchxup.latticy.model.StudentDO;
import io.github.xmchxup.latticy.vo.StudentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-01-03
 */
public interface StudentMapper extends BaseMapper<StudentDO> {

	int existsStudentBySID(String sid);

	StudentVO getStudentById(Integer id);

	IPage<StudentVO> selectPageVO(IPage<StudentVO> page, @Param("name") String studentName);

	List<StudentVO> allStudentsInMajor(@Param("majorId") String majorId, @Param("classId") String classId);

	List<StudentVO> allStudentsInCollege(@Param("collegeId") Integer collegeId);
}
