package io.github.xmchxup.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.xmchxup.latticy.model.TopicEssayDO;
import io.github.xmchxup.latticy.vo.TopicEssayVO;

import java.util.List;

public interface TopicEssayMapper extends BaseMapper<TopicEssayDO>  {
    List<TopicEssayVO>listOfEssayByPeopleId(Integer graduateInfoId, Integer teacherId, Integer studentId);
    TopicEssayVO getEssayById(Integer essayId);
}
