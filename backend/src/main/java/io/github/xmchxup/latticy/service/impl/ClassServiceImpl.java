package io.github.xmchxup.latticy.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.xmchxup.latticy.dto.ClassDTO;
import io.github.xmchxup.latticy.mapper.ClassMapper;
import io.github.xmchxup.latticy.model.ClassDO;
import io.github.xmchxup.latticy.model.MajorDO;
import io.github.xmchxup.latticy.service.ClassService;
import io.github.xmchxup.latticy.service.MajorService;
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
 * @since 2021-01-15
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, ClassDO> implements ClassService {

	@Autowired
	private ClassMapper classMapper;

	@Autowired
	private MajorService majorService;


	@Override
	public void create(ClassDTO dto) {
		this.majorService.existsMajorById(dto.getMajorId());
		final MajorDO major = this.majorService.getById(dto.getMajorId());


		final ClassDO classDO = new ClassDO();
		classDO.setCollegeId(major.getCollegeId());
		BeanUtils.copyProperties(dto, classDO);

		this.save(classDO);
	}

	@Override
	public void update(ClassDTO dto, Integer id) {
		this.majorService.existsMajorById(dto.getMajorId());
		final MajorDO major = this.majorService.getById(dto.getMajorId());

		final ClassDO classDO = new ClassDO();
		BeanUtils.copyProperties(dto, classDO);
		classDO.setId(id);
		classDO.setCollegeId(major.getCollegeId());
		this.updateById(classDO);
	}

	@Override
	public void checkClassByMajorId(Integer majorId) {
		final List<ClassDO> list = new LambdaQueryChainWrapper<>(this.baseMapper)
				.eq(ClassDO::getMajorId, majorId)
				.list();
		if (!list.isEmpty()) {
			throw new ParameterException(2399);
		}
	}
}
