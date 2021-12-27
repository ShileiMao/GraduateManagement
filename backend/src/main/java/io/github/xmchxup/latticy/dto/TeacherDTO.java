package io.github.xmchxup.latticy.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class TeacherDTO {
	@NotBlank
	@Length(min = 4, max = 4)
	@Pattern(regexp="\\d{4}",message = "{teacher.tid}")
	private String tid;
	@NotBlank
	private String name;
	@Range(min = 0, max = 1, message = "{user.sex}")
	private Integer sex;
	private Integer collegeId;
	private String position;
	@NotBlank
	@Email(message = "{email}")
	private String email;
	private String mobile;
	private String summary;
}
