import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class DateTest {
	@Test
	void test() {
		int year = LocalDate.now().getYear();
		System.out.println(LocalDate.ofYearDay(year, 1));
		System.out.println(LocalDate.ofYearDay(year + 1, 1));
	}

	@Test
	void doubleTest() {
		double a = 3.0005;
		if (a % 1 == 0) {
			System.out.println((long)a);
		}
	}
}
