package io.github.xmchxup.latticy.service.impl;


import io.github.xmchxup.latticy.common.util.HammingUtils;
import io.github.xmchxup.latticy.common.util.SimHashUtils;
import io.github.xmchxup.latticy.service.PaperCheckService;
import org.springframework.stereotype.Service;

@Service
public  class PaperCheckServiceImpl implements PaperCheckService {


    @Override
    public double paperCheck(String text1, String text2) {

        // 由字符串得出对应的 simHash值
        String simHash0 = SimHashUtils.getSimHash(text1);
        String simHash1 = SimHashUtils.getSimHash(text2);
        // 由 simHash值求出相似度
        double similarity = HammingUtils.getSimilarity(simHash0, simHash1);

        return similarity;
    }


}
