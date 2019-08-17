package com.blog.service;

import com.blog.dao.FollowDao;
import com.blog.dto.user.FollowVo;
import com.blog.pojo.Follow;
import com.blog.util.BeanCopierEx;
import com.blog.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowService {

    @Autowired
    private FollowDao followDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有的
     */
    public List<FollowVo> findAll() throws Exception {
        List<FollowVo> result = new ArrayList<FollowVo>();
        List<Follow> sqlResult = followDao.findAll();
        result = BeanCopierEx.copy(sqlResult,FollowVo.class);
        return result;
    }

    /**
     * 通过id 查询
     */
    public FollowVo findById(String id){
        FollowVo result = new FollowVo();
        Follow sqlResult = followDao.findById(id).get();
        BeanCopierEx.copy(sqlResult,result);
        return result;
    }

    /**
     * 添加
     */
    public void add(FollowVo followVo){
        Follow follow = new Follow();
        BeanCopierEx.copy(followVo, follow);
        follow.setId(idWorker.nextId());//设置ID
        followDao.save(follow);
    }

    /**
     * 更新
     */
    public void update(FollowVo followVo){
        Follow follow = new Follow();
        BeanCopierEx.copy(followVo, follow);
        followDao.save(follow);
    }

    /**
     * 删除
     */
    public void deleteById(String id){
        followDao.deleteById(id);
    }
}
