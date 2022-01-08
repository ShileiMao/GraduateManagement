package io.github.xmchxup.latticy.controller.v1;

import io.github.xmchxup.latticy.service.PaperCheckService;
import io.github.xmchxup.latticy.vo.PathVO;
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
    public  void  check(@RequestBody PathVO pathVO){
        String filePath1 = pathVO.getFilePath1();
        String filePath2 = pathVO.getFilePath2();
        String filePath3 = pathVO.getFilePath3();
        paperCheckService.paperCheck(filePath1, filePath2, filePath3);
    }
}
