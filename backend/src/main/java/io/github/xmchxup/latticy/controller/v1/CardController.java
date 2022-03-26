package io.github.xmchxup.latticy.controller.v1;


import io.github.talelin.core.annotation.GroupRequired;
import io.github.xmchxup.latticy.dto.CardDTO;
import io.github.xmchxup.latticy.model.CardDO;
import io.github.xmchxup.latticy.service.CardService;
import io.github.xmchxup.latticy.service.ScorecardService;
import io.github.xmchxup.latticy.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author generator@TaleLin
 * @since 2021-02-27
 */
//@Api(tags = "评分表模板管理")
@Validated
@RestController
@RequestMapping("/v1/card")
//@PermissionModule(value = "评分表模板")
public class CardController {
	@Autowired
	private CardService cardService;

	@Autowired
	private ScorecardService scorecardService;

	@PostMapping("")
	@GroupRequired
//	@PermissionMeta(value = "指导教师")
//	@ApiOperation(value = "创建评分表模板", notes = "创建评分表模板")
	public CreatedVO create(@Validated @RequestBody CardDTO dto) {
		CardDO cardDO = new CardDO();
		BeanUtils.copyProperties(dto, cardDO);
		this.cardService.save(cardDO);
		return new CreatedVO();
	}

	@PutMapping("/{id}")
	public UpdatedVO update(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		return new UpdatedVO();
	}

	@GroupRequired
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除评分表模板", notes = "删除评分表模板")
	public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
		this.scorecardService.checkNotUseByCardId(id);

		this.cardService.removeById(id);
		return new DeletedVO();
	}

	@GetMapping("/{id}")
	public CardDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
		return null;
	}

	@GetMapping("/options")
	@ApiOperation(value = "获取所有评分表模板，带有选项信息", notes = "获取所有评分表模板，带有选项信息")
	public List<CardVO> getAllCard() {
		return this.cardService.getAllCardWithOptions();
	}

	@GetMapping("/page")
	public PageResponseVO<CardDO> page(
			@RequestParam(name = "count", required = false, defaultValue = "10")
			@Min(value = 1, message = "{page.count.min}")
			@Max(value = 30, message = "{page.count.max}") Integer count,
			@RequestParam(name = "page", required = false, defaultValue = "0")
			@Min(value = 0, message = "{page.number.min}") Integer page
	) {
		return null;
	}

}
