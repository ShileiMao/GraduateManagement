package io.github.xmchxup.latticy.dto.validators;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
public class StudentIdsPatternValidator implements
		ConstraintValidator<StudentIdsPattern, String> {


	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		if (StringUtils.isEmpty(s)) return false;
		// 1902411124,1902411199,1902411198,
		// 不允许尾部出现,
		if (s.endsWith(",,")) return false;

		final String[] split = s.split(",");
		for (String studentId : split) {
			if (studentId.length() != 10) {
				return false;
			}
			final boolean matches = Pattern.matches("\\d{10}", studentId);
			if (!matches) {
				return false;
			}
		}
		return true;
	}
}
