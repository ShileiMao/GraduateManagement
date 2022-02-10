package io.github.xmchxup.latticy.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaperCmpVO {
    /**
     * 论文地址
     */
    private String paperText;
    /**
     * 参考论文所在 目录 的地址
     */
    private String refText;

//    public String getPaperText() {
//        return paperText;
//    }
//
//    public String getRefText() {
//        return refText;
//    }
//
//    public void setPaperText(String paperText) {
//        this.paperText = paperText;
//    }
//
//    public void setRefText(String refText) {
//        this.refText = refText;
//    }
}
