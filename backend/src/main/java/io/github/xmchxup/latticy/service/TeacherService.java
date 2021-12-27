package io.github.xmchxup.latticy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.xmchxup.latticy.dto.TeacherDTO;
import io.github.xmchxup.latticy.model.TeacherDO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-01-03
 */
public interface TeacherService extends IService<TeacherDO> {
	boolean existsTeacherByTID(String tid);

	void existsTeacherByIds(List<Integer> ids);

	void createTeacher(TeacherDTO dto);

	void deleteTeacher(Integer id);

	void checkTeacherByTID(String teacherTid);

	Integer getTeacherIdByTID(String teacherTid);

}
