package com.blog.service;

import com.blog.constants.VoConstants;
import com.blog.dao.SpitDao;
import com.blog.controller.dto.spit.SpitVo;
import com.blog.pojo.Spit;
import com.blog.util.BeanCopierEx;
import com.blog.util.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SplitService {

    private final Logger log = LoggerFactory.getLogger(SplitService.class);

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询全部
     * @return
     */
    public List<SpitVo> findAll() throws Exception {
        List<SpitVo> result = new ArrayList<SpitVo>();
        List<Spit> sqlResult = spitDao.findAll();
        result = BeanCopierEx.copy(sqlResult, SpitVo.class);
        return result;
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public SpitVo findById(String id){
        SpitVo result = new SpitVo();
        Spit sqlResult = spitDao.findById(id).get();
        BeanCopierEx.copy(sqlResult,result);
        return result;
    }

    /**
     * 添加
     * @param spitVo
     */
    public void save(SpitVo spitVo){
        Spit spit = new Spit();
        BeanCopierEx.copy(spitVo,spit);
        spit.set_id(idWorker.nextId());
        spit.setPublishTime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.settHumBup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState(VoConstants.SPLIT_STATE_SEE);// 状态:可见
        // 如果当前添加的吐槽，有父节点，那么父节点的吐槽回复数要加一
        if (StringUtils.isNotBlank(spit.getParentId())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentId()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spitDao.save(spit);
    }

    /**
     * 修改
     * @param spitVo
     */
    public void update(SpitVo spitVo){
        Spit spit = new Spit();
        BeanCopierEx.copy(spitVo, spit);
        spitDao.save(spit);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    /**
     * 吐槽
     * @param parentId
     * @param page
     * @param size
     * @return
     */
    public Page<SpitVo> findByParentId(String parentId, int page, int size) throws Exception {
        List<SpitVo> spitVolist = new ArrayList<SpitVo>();
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Spit> sqlResult = spitDao.findByParentId(parentId, pageRequest);
        List<Spit> sqlContent = sqlResult.getContent();
        spitVolist = BeanCopierEx.copy(sqlContent,SpitVo.class);
        Page<SpitVo> result = new PageImpl(spitVolist, sqlResult.getPageable(), sqlResult.getTotalElements());
        return  result;
    }

    /**
     * 点赞
     * @param spitId
     */
    public void thumbup(String spitId) {
        //方式一：效率有问题
        //Spit spit = spitDao.findById(spitId).get();
        //spit.setThumbup((spit.getThumbup()==null ? 0 : spit.getThumbup()) + 1);
        //spitDao.save(spit);

        //方式二：使用原生mongo命令来实现自增 db.spit.update({"_id":"1"},{$inc:{thumbup:NumberInt(1)}})
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is("1"));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }



}
