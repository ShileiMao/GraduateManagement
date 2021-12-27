package io.github.xmchxup.latticy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.xmchxup.latticy.dto.GraduateInfoDTO;
import io.github.xmchxup.latticy.model.GraduateInfoDO;
import io.github.xmchxup.latticy.mapper.GraduateInfoMapper;
import io.github.xmchxup.latticy.service.GraduateInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.xmchxup.latticy.service.TeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-04-27
 */
@Service
public class GraduateInfoServiceImpl extends ServiceImpl<GraduateInfoMapper, GraduateInfoDO> implements GraduateInfoService {

	@Autowired
	private TeacherService teacherService;

	@Override
	public void create(GraduateInfoDTO dto) {
		QueryWrapper<GraduateInfoDO> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(GraduateInfoDO::getYear, dto.getYear());
		if (this.getOne(queryWrapper) != null) {
			throw new ParameterException(2461);
		}

		List<Integer> teacherIds = dto.getTeacherIds();
		this.teacherService.existsTeacherByIds(teacherIds);

//		TODO: 分配指导老师权限

		GraduateInfoDO graduateInfoDO = new GraduateInfoDO();
		BeanUtils.copyProperties(dto, graduateInfoDO);

		StringBuilder builder = new StringBuilder();
		teacherIds.forEach(id -> builder.append(id).append(","));
		graduateInfoDO.setTeacherIds(builder.substring(0, builder.length() - 1));
		this.save(graduateInfoDO);
	}
}
