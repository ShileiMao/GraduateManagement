package io.github.xmchxup.latticy.enumeration;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public enum SexEnum {
	WOMEN(0, "女"),
	MEN(1, "男");
	private final Integer value;

	SexEnum(Integer value, String description) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
