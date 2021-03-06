package cn.itsource.hrm.service;


import cn.itsource.hrm.domain.CourseType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程目录 服务类
 * </p>
 *
 * @author solargen
 * @since 2019-12-26
 */
public interface ICourseTypeService extends IService<CourseType> {
    List<CourseType> loadTreeDataLoop();
    List<CourseType> loadTreeData();
    void synOperate();
}
