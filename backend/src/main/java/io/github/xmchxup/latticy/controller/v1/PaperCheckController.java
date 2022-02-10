package io.github.xmchxup.latticy.controller.v1;

import cn.hutool.core.date.DateTime;
import io.github.xmchxup.latticy.common.util.FileSaver;
import io.github.xmchxup.latticy.module.file.FileHelper;
import io.github.xmchxup.latticy.service.PaperCheckService;
import io.github.xmchxup.latticy.vo.PaperCheckResp;
import io.github.xmchxup.latticy.vo.PaperCmpVO;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Base64;

@RestController
@RequestMapping("/v1/papercheck")
@Validated
public class PaperCheckController {
    @Autowired
    private PaperCheckService paperCheckService;
    @PostMapping("/check")
    @ResponseBody
    public PaperCheckResp check(@RequestBody PaperCmpVO cmpVO) throws Exception {
       // double similarity = paperCheckService.paperCheck(cmpVO.getPaperText(), cmpVO.getRefText());
        //获取机器指纹
        System.out.println("机器码：" + CheckManager.INSTANCE.getMachineCode());
        //设置授权许可证（免费获取评估许可证：https://xincheck.com/?id=7）。授权许可证只需要设置1次，整个程序运行周期内均有效
        //根据上一句输出的机器指纹，在评估许可证网站中获取RegCode,填写至下行代码
        CheckManager.INSTANCE.setRegCode("abY3xKEdjCvkLLIcbw4JlaO7I1kqejSUxatt8G0nohEvIOTScu4B778brp6AK3mLVp/ced7330EzOhKXnA01pto+Df8rEJKI87YkzOn8Y1a2Ht6Mkdy3ZZAUhL9zXF9CN8VU8e60ix6KgOdWzhiDYtxaZEysK6NmQqeHwTJ0AKiN4E/hM0YSe9nKbZ4Cl72odMsIWVxeU62nMu4pV0VZgXdmau/Q/ADxoragS9ziFL3QZGsYaqqiAV/E5yoSOJfoFtkdEFVFGcKkhNmW5COCkyZMsvrJ/uQGfk7MIttZaQ0ABpylBhC1dSuwipYkq78Wiy5cglv6tdOe6u0VgrrtZm4o3wpAzDs8h+dU/YN9Z1uwPqo/r86Vwa0f8L8UKIB3d0lJGz250TghC1405qP0vfmWJGBOFTrf3wpneWFE5gNw7wceHhhgjemZN1WpKft8gu2QqcHvHZ1N9qB8iMHZcCeuT/JZpoi07NrEulkFh5A=");

        FileSaver fileSaver = new FileSaver();
        FileHelper fileHelper = new FileHelper();

        byte[] decoded = Base64.getDecoder().decode(cmpVO.getFileContent());

        String fileName = new DateTime().getTime() + "_" + cmpVO.getFileName();
        String essayRelativePath = fileSaver.saveContentToPath("essay/check", decoded, fileName);
        String essayPath = fileHelper.getDirPath(essayRelativePath);
        //通过<文件夹>加载本地比对库（支持pdf、txt、doc、docx）
        String refDirPath = fileHelper.getDirPath("essay/reference");
        LocalPaperLibrary paperLibrary = LocalPaperLibrary.load(new File(refDirPath));//初始化对比库对象

        //读取待查重的<文件>（支持pdf、txt、doc、docx）
        Paper paper = Paper.load(new File(essayPath)); //读取本地<文件>

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
        String outputDir = fileHelper.getDirPath("essay/checkOutput");
        String fileOutStamp = new DateTime().getTime() + "";

        String path1 = outputDir + File.separator + fileOutStamp + "_report1.html";
        String path2 = outputDir + File.separator + fileOutStamp + "_report2.html";
        String path3 = outputDir + File.separator + fileOutStamp + "_report3.html";

        String relPath1 = "assets" + fileHelper.getRelativePath(path1);
        String relPath2 = "assets" + fileHelper.getRelativePath(path2);
        String relPath3 = "assets" + fileHelper.getRelativePath(path3);

        checkTask.getReporters().get(0).saveAsFile(path1, ReportType.TEXT_WITH_CITATION); //保存查重报告（全文标红）
        checkTask.getReporters().get(0).saveAsFile(path2, ReportType.TEXT_WITH_ORIGINAL); //保存查重报告（原文对照）
        checkTask.getReporters().get(0).saveAsFile(path3, ReportType.SAMPLE); //保存查重报告（简洁）

        PaperCheckResp resp = new PaperCheckResp();

        PaperCheckResp.PaperCheckItem item1 = new PaperCheckResp.PaperCheckItem("全文报告", relPath1);
        PaperCheckResp.PaperCheckItem item2 = new PaperCheckResp.PaperCheckItem("原文对照", relPath2);
        PaperCheckResp.PaperCheckItem item3 = new PaperCheckResp.PaperCheckItem("概述", relPath3);

        resp.setOutputPaths(new PaperCheckResp.PaperCheckItem[] {
            item1, item2, item3
        });

        return resp;
    }
}
