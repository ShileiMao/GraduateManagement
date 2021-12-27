package io.github.xmchxup.latticy.dto.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = StudentIdsPatternValidator.class)
public @interface StudentIdsPattern {
	String message() default "字段不符合要求";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
