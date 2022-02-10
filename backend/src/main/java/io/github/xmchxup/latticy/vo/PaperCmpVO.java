package io.github.xmchxup.latticy.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaperCmpVO {
    /**
     * 文档名称
     */
    @JsonProperty("fileName")
    private String fileName;

    /**
     * 文档内容 （base64 编码）
    * */
    @JsonProperty("fileContent")
    private String fileContent;
}
