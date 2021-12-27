package io.github.xmchxup.latticy.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class StudentDTO {
	@NotBlank
	@Length(min = 10, max = 10)
	@Pattern(regexp="\\d{10}",message = "{student.sid}")
	private String sid;
	@NotNull
	@Positive
	private Integer classId;
	@NotBlank
	private String name;
	@Range(min = 0, max = 1, message = "{user.sex}")
	private Integer sex;
	@NotBlank
	@Email(message = "{email}")
	private String email;
	private String mobile;
}
