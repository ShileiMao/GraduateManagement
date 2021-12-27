package io.github.xmchxup.latticy.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author xmchx (sunhuayangak47@gmail.com)
 */
@Getter
@Setter
@AllArgsConstructor
public class CardVO {
	private Integer id;
	private String title;
	private List<QuestionVO> questions;
}
