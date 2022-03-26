package io.github.xmchxup.latticy.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class OptionDTO {
	@NotBlank
	@Length(max = 300, message = "{option.ctx}")
	private String content;
	@Range(min = 0, max = 40, message = "{option.score}")
	private Integer score;
}
