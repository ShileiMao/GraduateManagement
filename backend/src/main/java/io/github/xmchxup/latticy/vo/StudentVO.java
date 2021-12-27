package io.github.xmchxup.latticy.vo;

import io.github.xmchxup.latticy.model.StudentDO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class StudentVO extends StudentDO {
//	@TableField("class_name")
	private String className;
//	@TableField("college_name")
	private String collegeName;
//	@TableField("major_name")
	private String majorName;

}
