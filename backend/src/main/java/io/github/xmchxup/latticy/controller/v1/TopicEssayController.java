package io.github.xmchxup.latticy.controller.v1;

import io.github.talelin.core.annotation.PermissionMeta;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.xmchxup.latticy.dto.TopicEssayDTO;
import io.github.xmchxup.latticy.mapper.TopicEssayMapper;
import io.github.xmchxup.latticy.model.TopicEssayDO;
import io.github.xmchxup.latticy.module.file.FileHelper;
import io.github.xmchxup.latticy.vo.TopicEssayVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/v1/topicEssay")
@PermissionModule("论文")
public class TopicEssayController {

    @Value("${serverDomain}")
    private String serverDomain;

    @Autowired
    TopicEssayMapper topicEssayMapper;

    @Autowired
    ServletContext servletContext;

    @ApiOperation(value = "论文上传下载", notes = "论文上传下载")
    @PermissionMeta(value = "论文", mount = true)
    @GetMapping("/getEssaies")
    public List<TopicEssayVO> getEssaies(@RequestParam Integer graduateInfoId, @RequestParam(required = false) Integer teacherId, @RequestParam(required = false) Integer studentId) {
        List<TopicEssayVO> topicEssayVOList = this.topicEssayMapper.listOfEssayByPeopleId(graduateInfoId, teacherId, studentId);
        for(TopicEssayVO topicEssayVO : topicEssayVOList) {
            updateDownloadLink(topicEssayVO);
        }
        return topicEssayVOList;
    }

    @ApiOperation(value = "论文上传下载", notes = "论文上传下载")
    @PermissionMeta(value = "论文")
    @GetMapping("/getEssay")
    public TopicEssayVO getEssayById(@RequestParam Integer essayId) {
        TopicEssayVO topicEssayVO = this.topicEssayMapper.getEssayById(essayId);
        updateDownloadLink(topicEssayVO);
        return topicEssayVO;
    }

    private void updateDownloadLink(TopicEssayVO topicEssayVO) {

        String filePath = topicEssayVO.getFilePath();
        FileHelper fileHelper = new FileHelper();
        String urlStr = this.serverDomain + "/download?filePath=" + URLEncoder.encode(filePath);
        topicEssayVO.setDownloadUrl(urlStr);
    }

    @ApiOperation(value = "论文上传下载", notes = "论文上传下载")
    @PermissionMeta(value = "论文")
    @PostMapping("/uploadEssay")
    public void uploadEssay(@RequestBody TopicEssayDTO topicEssayDTO) throws IOException {
        String dirPath = "essay/upload";
        String path = topicEssayDTO.saveContentToPath(dirPath);
        if(path == null) {
            throw new IOException("上传文件失败");
        }

        TopicEssayDO essayDO = new TopicEssayDO();
        BeanUtils.copyProperties(topicEssayDTO, essayDO);
        essayDO.setFilePath(path);
        this.topicEssayMapper.insert(essayDO);
    }

    @ApiOperation(value = "论文上传下载", notes = "论文上传下载")
    @PostMapping("/modifyEssay")
    @PermissionMeta(value = "论文")
    public void modifyEssay(@RequestBody TopicEssayDTO topicEssayDTO) throws IOException {
        String dirPath = "essay/upload";

        TopicEssayDO essayDO = new TopicEssayDO();
        BeanUtils.copyProperties(topicEssayDTO, essayDO);

        if(!StringUtils.isEmpty(topicEssayDTO.getFileContent())) {
            String path = topicEssayDTO.saveContentToPath(dirPath);
            if(path == null) {
                throw new IOException("上传文件失败");
            }
            essayDO.setFilePath(path);
        }

        this.topicEssayMapper.updateById(essayDO);
    }



}
