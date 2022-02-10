package io.github.xmchxup.latticy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.xmchxup.latticy.model.TopicDO;
import io.github.xmchxup.latticy.vo.TopicAssignVO;
import io.github.xmchxup.latticy.vo.TopicPureVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator@TaleLin
 * @since 2021-02-04
 */
public interface TopicService extends IService<TopicDO> {

	IPage<TopicAssignVO> selectPageVO(IPage<TopicAssignVO> pager, String name);

	TopicDO getTopicByStudentSid(String sid);

	List<TopicDO> getTopicsByTeacherId(Integer teacherId, boolean isSupplement);

	void checkTopicStatusBySID(String studentId);

	List<TopicPureVO> getByThisYear(boolean isSupplement);
}
