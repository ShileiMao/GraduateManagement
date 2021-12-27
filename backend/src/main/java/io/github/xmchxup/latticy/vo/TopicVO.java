package io.github.xmchxup.latticy.vo;

import io.github.xmchxup.latticy.model.TopicDO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@Builder
public class TopicVO extends TopicDO {
	private String collegeName;
	private String studentName;
	private String teacherName;
}
