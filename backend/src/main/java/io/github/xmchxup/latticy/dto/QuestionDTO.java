package io.github.xmchxup.latticy.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class QuestionDTO {
	@NotBlank
	@Length(max = 40, message ="{question.title}")
	private String title;
	@Pattern(regexp = "^\\d+-\\d+-\\d+-\\d+", message = "{option.ids}")
	private String optionIds;
	private Integer answerOptionId;
	@Valid
	@Size(min = 4, max = 4, message = "{question.options}")
	private List<OptionDTO> options;
}
