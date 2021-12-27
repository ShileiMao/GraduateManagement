package io.github.xmchxup.latticy.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class MajorDTO {
	@NotBlank
	private String name;
	@NotNull
	@Positive
	private Integer collegeId;
}
