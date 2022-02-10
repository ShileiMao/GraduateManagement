package io.github.xmchxup.latticy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author generator@TaleLin
 * @since 2021-04-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scorecard")
public class ScorecardDO extends BaseModel {


	/**
	 * 评分表标题
	 */
	private String title;

	/**
	 * 评分表留言
	 */
	private String comment;

	/**
	 * 优秀、良好、中等、及格和不及格
	 */
	private String level;

	/**
	 * 得分
	 */
	private Double score;

	/**
	 * 指导老师评分表模板id
	 */
	@TableField("guide_card_id")
	private Integer guideCardId;

	/**
	 * 答辩小组评分表模板id
	 */
	@TableField("judge_card_id")
	private Integer judgeCardId;

	@TableField("assign_id")
	private Integer assignId;


	/**
	 * 指导老师评分
	 */
	@TableField("guide_score")
	private Double guideScore;

	/**
	 * 指导老师建议
	 */
	@TableField("guide_advise")
	private String guideAdvise;

	/**
	 * 评委团队评分
	 */
	@TableField("judge_score")
	private Double judgeScore;

	/**
	 * 评委团队建议
	 */
	@TableField("judge_advise")
	private String judgeAdvise;

	@TableField("judge_answers")
	private String judgeAnswers;
	@TableField("guide_answers")
	private String guideAnswers;


	/**
	 * 补答辩记录原先答辩信息
	 */
	private String supplement;
}
