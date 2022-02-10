package io.github.xmchxup.latticy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.xmchxup.latticy.mapper.TopicTypeMapper;
import io.github.xmchxup.latticy.model.TopicTypeDO;
import io.github.xmchxup.latticy.service.TopicTypeService;
import org.springframework.stereotype.Service;

@Service
public class TopicTypeServiceImp extends ServiceImpl<TopicTypeMapper, TopicTypeDO> implements TopicTypeService {
}
