package io.github.xmchxup.latticy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.talelin.autoconfigure.bean.Code;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.xmchxup.latticy.dto.TopicAssignDTO;
import io.github.xmchxup.latticy.mapper.TopicAssignMapper;
import io.github.xmchxup.latticy.mapper.TopicSelectTableMapper;
import io.github.xmchxup.latticy.model.TopicAssignDO;
import io.github.xmchxup.latticy.model.TopicSelectTableDO;
import io.github.xmchxup.latticy.service.TopicSelectService;
import io.github.xmchxup.latticy.vo.TopicAssignVO;
import io.github.xmchxup.latticy.vo.TopicSelectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicSelectServiceImp extends ServiceImpl<TopicSelectTableMapper, TopicSelectTableDO> implements TopicSelectService {
    @Autowired
    private TopicSelectTableMapper topicSelectTableMapper;

    @Autowired
    private TopicAssignMapper topicAssignMapper;

    @Override
    public List<TopicSelectVO> allTopics(Integer graduateInfoId, Integer collegeID, Integer teacherId) {
        if (graduateInfoId == null) {
            throw new ParameterException(Code.PARAMETER_ERROR.getCode());
        }
        return topicSelectTableMapper.selectTopicsByYear(graduateInfoId, collegeID, teacherId);
    }

    @Override
    public List<TopicSelectVO> allAssigned(Integer taskId) {
        return null;
    }

    @Override
    public List<TopicAssignVO> allSelections(Integer graduateInfoId, Integer teacherId, Integer topicId) {
        List<TopicAssignVO> allListing = topicAssignMapper.allAssigns(graduateInfoId, null, teacherId, null, topicId);

        return allListing;
    }

    @Override
    public boolean deleteTopicById(Integer id) {
//        QueryWrapper<TopicAssignDO> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(TopicAssignDO::getTopicId, id);
//        if(topicAssignMapper.)
        List<TopicAssignVO> existingAssigns = topicAssignMapper.getAssignes(id);
        if(existingAssigns.isEmpty()) {
            topicSelectTableMapper.deleteByTopicId(id);
            return true;
        }
        return false;
    }

    @Override
    public TopicSelectVO insert(TopicSelectTableDO obj) {
        int itemId = topicSelectTableMapper.insert(obj);
        if (itemId > 0 ) {
            TopicSelectVO topicSelectVO = topicSelectTableMapper.selectTopicById(itemId);
            return topicSelectVO;
        }

        return null;
    }

    @Override
    public TopicSelectVO update(TopicSelectTableDO obj) {
        topicSelectTableMapper.updateById(obj);
        TopicSelectVO topicSelectVO = topicSelectTableMapper.selectTopicById(obj.getId());
        return topicSelectVO;
    }

    @Override
    public TopicAssignVO topicAssign(Integer topicId, Integer studentId) {
        TopicAssignDO topicAssignDO = new TopicAssignDO();
        topicAssignDO.setStudentId(studentId);
        topicAssignDO.setTopicId(topicId);

        topicAssignMapper.insert(topicAssignDO);

        TopicAssignVO topicAssignVO = topicAssignMapper.getAssigneById(topicAssignDO.getId());
        return topicAssignVO;
    }

    @Override
    public TopicAssignVO getAssignByStudent(Integer studentId) {
        List<TopicAssignVO> topicAssignVO = topicAssignMapper.getAssignByStudent(studentId);
        if(topicAssignVO.isEmpty()) {
            return null;
        }

        return topicAssignVO.get(0);
    }

    @Override
    public void deleteAssignById(Integer assignId) {
        topicAssignMapper.deleteAssignById(assignId);
    }

    @Override
    public void updateAssignStatus(TopicAssignDTO assignDTO) {

        this.topicAssignMapper.updateTopicStatus(assignDTO.getId(), assignDTO.getStatus());
    }
}
