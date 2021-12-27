package io.github.xmchxup.latticy.enumeration;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public enum ScoreLevel {
	EXCELLENT("优秀", "100-90分"),
	GOOD("良好", "89-80分"),
	MEDIUM("中等", "79-70分"),
	PASS("及格", "69-60分"),
	FAIL("不及格", "60分以下");
	private final String value;

	ScoreLevel(String value, String description) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
