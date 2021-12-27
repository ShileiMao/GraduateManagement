package io.github.xmchxup.latticy;

import org.junit.Test;

import java.time.LocalDate;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class LocalDateTest {
	@Test
	public void test() {
		final int month = LocalDate.now().getMonth().getValue();
		final LocalDate now = LocalDate.now();
		System.out.println(month);
	}
}
