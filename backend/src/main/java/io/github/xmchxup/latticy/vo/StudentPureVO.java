package io.github.xmchxup.latticy.vo;

import io.github.xmchxup.latticy.model.StudentDO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * 用于 团队成员
 *
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class StudentPureVO {
	String sid;
	String name;

	public StudentPureVO(StudentDO studentDO) {
		BeanUtils.copyProperties(studentDO, this);
	}
}
