package io.github.xmchxup.latticy.vo;

import io.github.xmchxup.latticy.model.OptionDO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
public class QuestionVO {
	private int id;
	private String title;
	private Integer maxScore;
	private List<OptionDO> options;
}
