package io.github.xmchxup.latticy.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaperCheckResp {
    @Setter
    @Getter
    @AllArgsConstructor
    public static class PaperCheckItem {
        private String name;
        private String path;
    }

    private PaperCheckItem[] outputPaths;
}
