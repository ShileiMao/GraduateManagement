package io.github.xmchxup.latticy.service.impl;


import io.github.xmchxup.latticy.common.util.HammingUtils;
import io.github.xmchxup.latticy.common.util.SimHashUtils;
import io.github.xmchxup.latticy.common.util.TxtIOUtils;
import io.github.xmchxup.latticy.service.PaperCheckService;
import org.springframework.stereotype.Service;

@Service
public  class PaperCheckServiceImpl implements PaperCheckService {


    @Override
    public void paperCheck(String path1, String path2, String path3) {
        String str0 = TxtIOUtils.readTxt(path1);
        String str1 = TxtIOUtils.readTxt(path2);
        String resultFileName = path3;
        // 由字符串得出对应的 simHash值
        String simHash0 = SimHashUtils.getSimHash(str0);
        String simHash1 = SimHashUtils.getSimHash(str1);
        // 由 simHash值求出相似度
        double similarity = HammingUtils.getSimilarity(simHash0, simHash1);
        // 把相似度写入最后的结果文件中
        TxtIOUtils.writeTxt(similarity, resultFileName);
    }


}
