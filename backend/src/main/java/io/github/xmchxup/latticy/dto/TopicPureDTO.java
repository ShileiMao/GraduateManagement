package io.github.xmchxup.latticy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Data
public class TopicPureDTO {
	@Positive(message = "{id.positive}")
	private Integer id;
	@Positive
	private Integer status;
	@NotBlank
	private String statusName;
}
