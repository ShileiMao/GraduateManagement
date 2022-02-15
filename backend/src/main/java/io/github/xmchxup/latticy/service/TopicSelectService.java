package io.github.xmchxup.latticy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.xmchxup.latticy.dto.TopicAssignDTO;
import io.github.xmchxup.latticy.model.TopicSelectTableDO;
import io.github.xmchxup.latticy.vo.TopicAssignVO;
import io.github.xmchxup.latticy.vo.TopicSelectVO;

import java.util.List;

public interface TopicSelectService extends IService<TopicSelectTableDO> {
    List<TopicSelectVO> allTopics(Integer graduateInfoId, Integer collegeID, Integer teacherId);

    List<TopicSelectVO> allAssigned(Integer taskId);

    List<TopicAssignVO> allSelections(Integer graduateInfoId, Integer teacherId, Integer topicId);

    boolean deleteTopicById(Integer id);

    TopicSelectVO insert(TopicSelectTableDO obj);

    TopicSelectVO update(TopicSelectTableDO obj);

    TopicAssignVO assignTopicToStudent(Integer topicId, Integer studentId);

    TopicAssignVO getAssignByStudent(Integer studentId);

    void deleteAssignById(Integer assignId);

    void updateAssignStatus(TopicAssignDTO assignDTO);
}
