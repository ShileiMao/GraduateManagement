package io.github.xmchxup.latticy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Map;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class ScorecardDTO {
	@NotEmpty(message = "{scorecard.scores}")
	private Map<String, Double> scores;
	@Positive
	@NotNull
	private Integer cardId;
	@Positive
	@NotNull
	private Integer topicId;
//	isXXX 有坑
	@NotNull
	@JsonProperty("is_supplement")
	private boolean isSupplement;
}
