package io.github.xmchxup.latticy.dto;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class StudentIdsTest {
	@Test
	public void test() {
		String s = "1902411124,1902411199,1902411198";
		System.out.println(s.endsWith(",,"));
		// 1902411124,1902411199,1902411198,
		final String[] split = s.split(",");
		for (String s1 : split) {
			final boolean matches = Pattern.matches("\\d{10}", s1);
			System.out.println(matches);
		}

	}

	@Test
	public void testRemove() {
		String s = "1902411124,1902411199,1902411198,1902411198,";
		System.out.println(s.replaceAll("1902411199,1902411198,1902411198(,)?", ""));
	}
}
