package io.github.xmchxup.latticy.dto;

import io.github.xmchxup.latticy.dto.validators.StudentIdsPattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class TeamDTO {
	@NotBlank
	@Length(max = 10)
	private String name;
	@NotNull
	@Positive
	private Integer teacherId;
	@NotBlank
	@Length(max = 20)
	private String judges;
	@StudentIdsPattern(message = "{team.sids}")
	private String studentIds;
	@NotBlank
	@Pattern(regexp = "\\d{4}", message = "{team.year}")
	private String years;
	@NotNull
	@Positive
	private Integer collegeId;
}
