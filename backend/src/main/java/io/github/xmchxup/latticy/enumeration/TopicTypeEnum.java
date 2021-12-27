package io.github.xmchxup.latticy.enumeration;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public enum TopicTypeEnum {
	STUDENT_SELECT(1, "学生自主申请"),
	TEACHER_SELECT(0, "老师制定");
	private final Integer value;

	TopicTypeEnum(Integer value, String description) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
