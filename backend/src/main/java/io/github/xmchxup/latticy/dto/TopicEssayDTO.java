package io.github.xmchxup.latticy.dto;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.xmchxup.latticy.common.util.FileSaver;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Base64;

@Getter
@Setter
public class TopicEssayDTO {
    @JsonProperty("topicAssignId")
    private Integer topicAssignId;

    @JsonProperty("essayTitle")
    private String essayTitle;

    @JsonProperty("studentNote")
    private String studentNote;

    @JsonProperty("teacherNote")
    private String teacherNote;

    @JsonProperty("fileContent")
    private String fileContent;

    @JsonProperty("fileExt")
    private String fileExt;

    private Integer status;

    public String saveContentToPath(String dirPath) throws IOException {
        FileSaver fileSaver = new FileSaver();


        String fileName = new DateTime().getTime() + "." + fileExt;
        byte[] decoded = Base64.getDecoder().decode(fileContent);

        return fileSaver.saveContentToPath(dirPath, decoded, fileName);
    }
}
