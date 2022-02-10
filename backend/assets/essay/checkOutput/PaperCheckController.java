package io.github.xmchxup.latticy.controller.v1;

import io.github.xmchxup.latticy.service.PaperCheckService;
import io.github.xmchxup.latticy.vo.PaperCmpVO;
import io.github.xmchxup.latticy.vo.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cn.textcheck.CheckManager;
import cn.textcheck.engine.algorithm.ContinuityCheck;
import cn.textcheck.engine.checker.CheckTask;
import cn.textcheck.engine.pojo.LocalPaperLibrary;
import cn.textcheck.engine.pojo.Paper;
import cn.textcheck.engine.type.CheckLevel;
import cn.textcheck.engine.type.ReportType;

import java.io.File;

@RestController
@RequestMapping("/v1/papercheck")
@Validated
public class PaperCheckController {
    @Autowired
    private PaperCheckService paperCheckService;
    @Value("${upload.paths}")
    private String filePaths;

    @PostMapping("/check")
    @ResponseBody
    public ReportVo check(@RequestBody PaperCmpVO cmpVO) throws Exception {
        ReportVo reportVo = new ReportVo();
        //double similarity = paperCheckService.paperCheck(cmpVO.getPaperText(), cmpVO.getRefText());
        //获取机器指纹
        System.out.println(CheckManager.INSTANCE.getMachineCode());
        //设置授权许可证（免费获取评估许可证：https://xincheck.com/?id=7）。授权许可证只需要设置1次，整个程序运行周期内均有效
        //根据上一句输出的机器指纹，在评估许可证网站中获取RegCode,填写至下行代码
        CheckManager.INSTANCE.setRegCode("l5ItOHnwi9rO+DIGUt5zgj+243P6bZbQyDB23Dzd78CxlYNl56PmpsG97wTp6GvmWDc2dQMIw2CAZ+FIuhsewrCQIg/kFW0vgSlwwfo9DUKjgWgr6p053mM6wmHKFFkEertryhmn7k+Gm8hs+7K4hm07Hz+eJNTOx5fA+0ri4n78TzhskgwPESnOcOR4P1Wbh+kjH7bZkmuY2gwCWP7zKT32dsGK0eJCATxOONKxyzIA0C3JDMQ8gsKhj9BxsuC9CHGDAijUX0gZhxSlOLzGSoo2An8yIiLuc5HOnfsYv9K6pDWDDkK0bTH0C0UbxFjTh9EnbtT4J9tR6IWE8ez9kJW+RdbePIfT49W3xkO9zid3DFu15f0gsB9zaLLma1d55M69GnLLdEeFqPxGQHandPEtiAVAAY2xp+7zVQzXOtF/tNu32WooEfPgKnVGmm2jfs8nli1CtKe0EeDT71u1gylyM1ewuldOlnJ6qHcUopQ=");
        //通过<文件夹>加载本地比对库（支持pdf、txt、doc、docx）
        LocalPaperLibrary paperLibrary = LocalPaperLibrary.load(new File(cmpVO.getRefText()));//初始化对比库对象

        //读取待查重的<文件>（支持pdf、txt、doc、docx）
        Paper paper = Paper.load(new File(cmpVO.getPaperText())); //读取本地<文件>

        //注意：待查文本和比对库中的文本如果完全相同，将会自动跳过，不进行查重比对。测试时请不要使用完全相同的两个文本进行查重。

        //构建并启动任务
        CheckTask checkTask = CheckManager.INSTANCE
                .getCheckTaskBuilder() //获取查重任务构造器
                //.addLibrary(paperLibrary) //添加比对库。可以添加多个
                .addCheckPaper(paper) //添加待查Paper。可以添加多个
                .addCheckCore(new ContinuityCheck(CheckLevel.STRICT))
                .build(); //构建任务，返回checkTask对象
        checkTask.start(); //启动任务
        checkTask.join(); //等待查重结束（阻塞）

        //保存查重报告
        String report1 = filePaths+"report1.html" ;
        String report2 = filePaths+"report2.html";
        String report3 = filePaths+"report3.html";

        checkTask.getReporters().get(0).saveAsFile(report1, ReportType.TEXT_WITH_CITATION); //保存查重报告（全文标红）
        checkTask.getReporters().get(0).saveAsFile(report2, ReportType.TEXT_WITH_ORIGINAL); //保存查重报告（原文对照）
        checkTask.getReporters().get(0).saveAsFile(report3, ReportType.SAMPLE); //保存查重报告（简洁）
        reportVo.setReport1(report1);
        reportVo.setReport2(report2);
        reportVo.setReport3(report3);
        return reportVo;

    }
}
