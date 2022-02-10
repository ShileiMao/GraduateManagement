package io.github.xmchxup.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.xmchxup.latticy.model.TopicSelectTableDO;
import io.github.xmchxup.latticy.vo.TopicSelectVO;

import java.util.List;

public interface TopicSelectTableMapper extends BaseMapper<TopicSelectTableDO> {
    List<TopicSelectVO> selectTopicsByYear(Integer graduateId, Integer collegeId, Integer teacherId);

    TopicSelectVO selectTopicById(Integer topicId);

    int deleteByTopicId(Integer id);
}
