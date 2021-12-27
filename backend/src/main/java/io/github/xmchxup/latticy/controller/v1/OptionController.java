package io.github.xmchxup.latticy.controller.v1;


import io.github.xmchxup.latticy.model.OptionDO;
import io.github.xmchxup.latticy.vo.CreatedVO;
import io.github.xmchxup.latticy.vo.DeletedVO;
import io.github.xmchxup.latticy.vo.PageResponseVO;
import io.github.xmchxup.latticy.vo.UpdatedVO;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
* @author generator@TaleLin
* @since 2021-04-14
*/
@Validated
@Api(tags = "选项管理")
@RestController
@RequestMapping("/v1//option")
public class OptionController {

    @PostMapping("")
    public CreatedVO create() {
        return new CreatedVO();
    }

    @PutMapping("/{id}")
    public UpdatedVO update(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        return new UpdatedVO();
    }

    @DeleteMapping("/{id}")
    public DeletedVO delete(@PathVariable @Positive(message = "{id.positive}") Integer id) {
        return new DeletedVO();
    }

    @GetMapping("/{id}")
    public OptionDO get(@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id) {
        return null;
    }

    @GetMapping("/page")
    public PageResponseVO<OptionDO> page(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{page.count.min}")
            @Max(value = 30, message = "{page.count.max}") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page.number.min}") Integer page
    ) {
        return null;
    }

}
