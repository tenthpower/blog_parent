package com.blog.service;

import com.blog.dao.ReplyDao;
import com.blog.controller.dto.qa.ReplyVo;
import com.blog.pojo.Reply;
import com.blog.util.BeanCopierEx;
import com.blog.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyService {

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有的
     */
    public List<ReplyVo> findAll() throws Exception {
        List<ReplyVo> result = new ArrayList<ReplyVo>();
        List<Reply> sqlResult = replyDao.findAll();
        result = BeanCopierEx.copy(sqlResult,ReplyVo.class);
        return result;
    }

    /**
     * 通过id 查询
     */
    public ReplyVo findById(String id){
        ReplyVo result = new ReplyVo();
        Reply sqlResult = replyDao.findById(id).get();
        BeanCopierEx.copy(sqlResult,result);
        return result;
    }

    /**
     * 添加
     */
    public void add(ReplyVo replyVo){
        Reply reply = new Reply();
        BeanCopierEx.copy(replyVo, reply);
        reply.setId(idWorker.nextId());//设置ID
        replyDao.save(reply);
    }

    /**
     * 更新
     */
    public void update(ReplyVo replyVo){
        Reply reply = new Reply();
        BeanCopierEx.copy(replyVo, reply);
        replyDao.save(reply);
    }

    /**
     * 删除
     */
    public void deleteById(String id){
        replyDao.deleteById(id);
    }
}
