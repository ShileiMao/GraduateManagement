package io.github.xmchxup.latticy.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class GraduateInfoDTO {
	@NotBlank
	private String title;
	@NotBlank
	@Pattern(regexp = "\\d{4}", message = "{team.year}")
	private String year;
	private List<Integer> teacherIds;
}