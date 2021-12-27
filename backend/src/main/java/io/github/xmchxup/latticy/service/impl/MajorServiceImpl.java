package io.github.xmchxup.latticy.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.xmchxup.latticy.dto.MajorDTO;
import io.github.xmchxup.latticy.mapper.MajorMapper;
import io.github.xmchxup.latticy.model.MajorDO;
import io.github.xmchxup.latticy.service.CollegeService;
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
public class MajorServiceImpl extends ServiceImpl<MajorMapper, MajorDO> implements MajorService {

	@Autowired
	private MajorMapper majorMapper;


	@Autowired
	private CollegeService collegeService;

	@Override
	public void create(MajorDTO dto) {
		this.existsMajorByName(dto.getName());
		collegeService.existsCollegeById(dto.getCollegeId());

		MajorDO majorDO = new MajorDO();
		BeanUtils.copyProperties(dto, majorDO);
		this.save(majorDO);
	}

	@Override
	public void existsMajorByName(String name) {
		final List<MajorDO> list = new LambdaQueryChainWrapper<>(majorMapper)
				.eq(MajorDO::getName, name).list();
		if (!list.isEmpty()) {
			throw new ParameterException(2422);
		}
	}

	@Override
	public void existsMajorById(Integer id) {
		final MajorDO majorDO = this.majorMapper.selectById(id);
		if (majorDO == null) {
			throw new ParameterException(2422);
		}
	}

	@Override
	public void update(MajorDTO dto, Integer id) {
		this.collegeService.existsCollegeById(dto.getCollegeId());

		final MajorDO majorDO = new MajorDO();
		BeanUtils.copyProperties(dto,majorDO);
		majorDO.setId(id);
		this.updateById(majorDO);
	}

	@Override
	public void checkMajorByCollegeId(Integer collegeId) {
		final List<MajorDO> list = new LambdaQueryChainWrapper<>(this.baseMapper)
				.eq(MajorDO::getCollegeId, collegeId)
				.list();
		if (!list.isEmpty()) {
			throw new ParameterException(2399);
		}
	}
}
