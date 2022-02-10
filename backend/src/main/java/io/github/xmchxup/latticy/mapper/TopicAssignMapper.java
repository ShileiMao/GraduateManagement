package io.github.xmchxup.latticy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.xmchxup.latticy.model.TopicAssignDO;
import io.github.xmchxup.latticy.vo.TopicAssignVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TopicAssignMapper extends BaseMapper<TopicAssignDO> {
    List<TopicAssignVO> allAssigns(Integer graduateId, Integer collegeId, Integer teacherId, Integer studentId, Integer topicId);

    List<TopicAssignVO> getAssignes(Integer topicId);

    TopicAssignVO getAssigneById(Integer assignId);

    List<TopicAssignVO> getAssignByStudent(Integer stuId);

    void deleteAssignById(Integer assignId);

    IPage<TopicAssignVO> selectPageVO(IPage<TopicAssignVO> pager, @Param("name") String name);

    void updateTopicStatus(Integer id, Integer status);

}
