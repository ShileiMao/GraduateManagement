package io.github.xmchxup.latticy.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class TopicDTO {
	@NotBlank
	@Length(max = 100)
	private String name;
	@NotBlank
	@Length(max = 100)
	private String description;
	@NotNull
	@Positive
	private Integer graduateInfoId;
	@NotNull
	@Positive
	private Integer teacherId;
	@NotNull
	@Range(min = 0,max = 1,message = "{topic.type}")
	private Integer type;
	@Pattern(regexp="\\d{10}",message = "{student.sid}")
	private String studentId;
	@NotBlank
	@Pattern(regexp = "\\d{4}", message = "{team.year}")
	private String publishYear;
	@NotNull
	@Positive
	private Integer collegeId;
}
