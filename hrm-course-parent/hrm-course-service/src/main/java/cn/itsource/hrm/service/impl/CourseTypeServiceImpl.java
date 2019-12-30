package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.RedisClient;
import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.mapper.CourseTypeMapper;
import cn.itsource.hrm.service.ICourseTypeService;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    @Autowired
    private RedisClient redisClient;

    private final String COURSE_TYPE="courseTypes";

    public List<CourseType> loadTreeDataLoop(){
        //初始化一个集合存放一级类型
        List<CourseType> firstLevelTypes = new ArrayList<>();
        //查询数据库中的所有类型
        List<CourseType> courseTypes = baseMapper.selectList(null);
        //创建一个Map，将courseTypes数据存到map中，key使用id，value就是CourseType
        Map<Long, CourseType> map = new HashMap<>();
        for (CourseType courseType:courseTypes) {
            map.put(courseType.getId(), courseType);
        }
        //循环courseTypes，分配一级类型和非一级类型
        for (CourseType courseType:courseTypes) {
            if(courseType.getPid().longValue()==0L){
                firstLevelTypes.add(courseType);
            }else {
                CourseType parent = map.get(courseType.getPid());
                if(parent!=null){
//                    System.out.println(courseType);
                    parent.getChildren().add(courseType);
                }
            }
        }
       return firstLevelTypes;
    }

    @Override
    public List<CourseType> loadTreeData() {
        String courseTypeStr = redisClient.get(COURSE_TYPE);
        List<CourseType> list=null;
        if(StringUtils.isNotEmpty(courseTypeStr)){
            list= JSONObject.parseArray(courseTypeStr, CourseType.class);
        }else{
            list=loadTreeDataLoop();
            String objStr = JSONObject.toJSONString(list);
            redisClient.set(COURSE_TYPE, objStr);
        }
        return list;
    }


    public void synOperate() {
        List<CourseType> list = loadTreeDataLoop();
        String jsonStr = JSONObject.toJSONString(list);
        redisClient.set(COURSE_TYPE, jsonStr);
    }

    public CourseTypeServiceImpl() {
        super();
    }

    @Override
    public boolean save(CourseType entity) {
        super.save(entity);
        synOperate();
        return true;

    }

    @Override
    public boolean removeById(Serializable id) {
        super.removeById(id);
        synOperate();
        return true;
    }

    @Override
    public boolean updateById(CourseType entity) {
        super.updateById(entity);
        synOperate();
        return true;
    }
}
