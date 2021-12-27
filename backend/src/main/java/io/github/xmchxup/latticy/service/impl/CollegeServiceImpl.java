package io.github.xmchxup.latticy.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.xmchxup.latticy.dto.CollegeDTO;
import io.github.xmchxup.latticy.mapper.CollegeMapper;
import io.github.xmchxup.latticy.model.CollegeDO;
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
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, CollegeDO> implements CollegeService {
	@Autowired
	private CollegeMapper collegeMapper;

	@Autowired
	private MajorService majorServicel;

	@Override
	public void create(CollegeDTO dto) {
		this.existsCollegeByName(dto.getName());

		CollegeDO collegeDO = new CollegeDO();
		BeanUtils.copyProperties(dto, collegeDO);
		this.save(collegeDO);
	}

	@Override
	public void update(CollegeDTO dto, Integer id) {
		this.existsCollegeById(id);

		CollegeDO collegeDO = new CollegeDO();
		collegeDO.setId(id);
		BeanUtils.copyProperties(dto, collegeDO);
		this.updateById(collegeDO);
	}

	@Override
	public void existsCollegeById(Integer id) {
		final CollegeDO collegeDO = new LambdaQueryChainWrapper<>(collegeMapper)
				.eq(CollegeDO::getId, id)
				.one();
		if (collegeDO == null) {
			throw new ParameterException(2420);
		}
	}

	@Override
	public void existsCollegeByName(String name) {
		List<CollegeDO> list = new LambdaQueryChainWrapper<>(collegeMapper)
				.eq(CollegeDO::getName, name)
				.list();
		if (!list.isEmpty()) {
			throw new ParameterException(2421);
		}
	}
}
