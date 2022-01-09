package io.github.xmchxup.latticy.controller.v1;

import io.github.xmchxup.latticy.service.PaperCheckService;
import io.github.xmchxup.latticy.vo.PaperCmpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/papercheck")
@Validated
public class PaperCheckController {
    @Autowired
    private PaperCheckService paperCheckService;
    @PostMapping("/check")
    @ResponseBody
    public String check(@RequestBody PaperCmpVO cmpVO){
        double similarity = paperCheckService.paperCheck(cmpVO.getPaperText(), cmpVO.getRefText());
        System.out.println("similarity: " + similarity);
        return similarity + "";
    }
}
