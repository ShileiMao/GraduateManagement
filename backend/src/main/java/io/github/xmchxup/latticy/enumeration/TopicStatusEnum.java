package io.github.xmchxup.latticy.enumeration;

import java.util.stream.Stream;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public enum TopicStatusEnum {
	START(0, "开始"),
	SUBMIT(1, "申请课题"),
	SUBMIT_PASS(2, "申请通过"),
	SUBMIT_REJECT(3, "申请未通过"),
	TOPIC_SELECTED(4, "课题被选"),
	TOPIC_VACANT(5, "课题未被选"),
	FIRST_REJECT(6, "一审通过"),
	FIRST_PASS(7, "一审未通过"),
	SECOND_REJECT(8, "二审通过"),
	SECOND_PASS(9, "二审未通过"),
	THIRD_REJECT(10, "三审通过"),
	THIRD_PASS(11, "三审未通过"),
	END(100, "结束");

	private final Integer value;
	private final String description;

	TopicStatusEnum(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	public Integer getValue() {
		return value;
	}

	public static TopicStatusEnum toType(String description) {
		return Stream.of(TopicStatusEnum.values())
				.filter(t -> t.description.equals(description))
				.findAny()
				.orElse(null);
	}

	public static TopicStatusEnum toType(int value) {
		return Stream.of(TopicStatusEnum.values())
				.filter(t -> t.value == value)
				.findAny()
				.orElse(null);
	}
}
