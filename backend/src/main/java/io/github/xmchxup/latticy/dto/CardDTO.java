package io.github.xmchxup.latticy.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class CardDTO {
	@NotBlank
	private String title;
	@Pattern(regexp = "\\d+(,\\d+)+", message = "{card.questionIds}")
	private String questionIds;
}
