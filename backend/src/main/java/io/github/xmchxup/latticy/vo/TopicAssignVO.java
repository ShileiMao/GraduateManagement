package io.github.xmchxup.latticy.vo;

import io.github.xmchxup.latticy.model.TopicAssignDO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TopicAssignVO extends TopicAssignDO {
    private String studentName;

    private Integer collegeId;

    private String collegeName;


    private Integer graduateId;

    private String graduateTitle;

    private String topicTypeName;


    private String topicTypeDesc;



    private String teacherName;
//    private TopicSelectVO topicSelectVO;

    private String name;

    private String description;


    private Integer topicType;


    private Integer teacherId;


    private Integer graduateInfoId;

}
