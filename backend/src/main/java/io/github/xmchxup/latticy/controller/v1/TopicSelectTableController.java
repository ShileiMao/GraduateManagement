package io.github.xmchxup.latticy.controller.v1;

import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.xmchxup.latticy.dto.TopicAssignDTO;
import io.github.xmchxup.latticy.model.TopicSelectTableDO;
import io.github.xmchxup.latticy.model.TopicTypeDO;
import io.github.xmchxup.latticy.service.TopicSelectService;
import io.github.xmchxup.latticy.service.TopicTypeService;
import io.github.xmchxup.latticy.vo.TopicAssignVO;
import io.github.xmchxup.latticy.vo.TopicSelectVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@PermissionModule(value = "论文")
@RestController
@RequestMapping("/v1/topicSelect")
public class TopicSelectTableController {
    @Autowired
    private TopicSelectService topicSelectService;

    @Autowired
    private TopicTypeService topicTypeService;

    @PermissionMeta(value = "论文选题")
    @ApiOperation(value = "论文选题", notes = "论文选题")
    @PostMapping("/loadAll")
    List<TopicSelectVO>loadAll(@RequestParam(required = true) Integer graduateInfoId, @RequestParam(required = false) Integer collegeId, @RequestParam(required = false) Integer teacherId) {
        List<TopicSelectVO> topicSelectVOS = topicSelectService.allTopics(graduateInfoId, collegeId, teacherId);
        return topicSelectVOS;
    }

//    @PostMapping("/loadAllByTask")
//    List<TopicSelectVO>loadAllByTask(@RequestParam Integer taskId) {
//        List<TopicSelectVO> topicSelectVOS = topicSelectService.allTopics(graduateInfoId, collegeId, teacherId);
//        return topicSelectVOS;
//    }
    @ApiOperation(value = "论文选题", notes = "论文选题")
    @PermissionMeta(value = "论文选题")
    @GetMapping("/getAllSelection")
    List<TopicAssignVO>getAllSelection(@RequestParam(required = true) Integer graduateInfoId,
                                       @RequestParam(name = "teacherId", required = false) Integer teacherId,
                                       @RequestParam(required = false)Integer studentId,
                                       @RequestParam(required = false) Integer topicId) {
        List<TopicAssignVO> allSelection = topicSelectService.allSelections(graduateInfoId, teacherId, topicId);
        return allSelection;
    }

    @PermissionMeta(value = "发布选题")
    @ApiOperation(value = "发布选题", notes = "发布选题")
    @PostMapping("/delete")
    Boolean deleteTopicById(@RequestParam Integer topicId) {
        boolean deleted = topicSelectService.deleteTopicById(topicId);
        if (deleted) {
            return true;
        }

        return false;
    }

    @PermissionMeta(value = "发布选题")
    @ApiOperation(value = "发布选题", notes = "发布选题")
    @PostMapping("/createNewTask")
    TopicSelectVO createNewTask(@RequestBody TopicSelectTableDO object) {
        TopicSelectVO topicSelectVO = topicSelectService.insert(object);
        return topicSelectVO;
    }

    @PermissionMeta(value = "发布选题")
    @PostMapping("/updateTask")
    @ApiOperation(value = "发布选题", notes = "发布选题")
    TopicSelectVO updateTask(@RequestBody TopicSelectTableDO object) {
        TopicSelectVO topicSelectVO = topicSelectService.update(object);
        return topicSelectVO;
    }

    @GetMapping("/allTopicType")
    List<TopicTypeDO> allTopicTypes() {
        List<TopicTypeDO> types = topicTypeService.list();
        return types;
    }

    @PostMapping("/assignTopic")
    TopicAssignVO assignTopic(@RequestParam Integer topicId, @RequestParam Integer studentId) {
        TopicAssignVO topicAssignVO = topicSelectService.topicAssign(topicId, studentId);
        return topicAssignVO;
    }

    @ApiOperation(value = "论文选题", notes = "论文选题")
    @PermissionMeta(value = "论文选题")
    @GetMapping("/getAssign")
    TopicAssignVO getAssignByStudnet(@RequestParam Integer studentId) {
        TopicAssignVO topicAssignVO = topicSelectService.getAssignByStudent(studentId);
        return topicAssignVO;
    }

    @PostMapping("/deleteAssign")
    void deleteAssignById(@RequestParam Integer assignId) {
        topicSelectService.deleteAssignById(assignId);
    }

    @PostMapping("/updateAssign")
    void updateAssign(@RequestBody TopicAssignDTO dto) {
        topicSelectService.updateAssignStatus(dto);
    }

}
