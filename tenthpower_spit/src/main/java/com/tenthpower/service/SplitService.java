package com.tenthpower.service;

import com.tenthpower.dao.SplitDao;
import com.tenthpower.pojo.Split;
import com.tenthpower.util.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class SplitService {
    private final Logger log = LoggerFactory.getLogger(SplitService.class);

    @Autowired
    private SplitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询全部
     * @return
     */
    public List<Split> findAll(){
        return spitDao.findAll();
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public Split findById(String id){
        return spitDao.findById(id).get();
    }

    /**
     * 添加
     * @param spit
     */
    public void save(Split spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        //如果当前添加的吐槽，有父节点，那么父节点的吐槽回复数要加一
        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }

        spitDao.save(spit);
    }

    /**
     * 修改
     * @param spit
     */
    public void update(Split spit){
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
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Split> findByParentid(String parentid, int page, int size){
        Pageable pageable = PageRequest.of(page-1, size);
        return spitDao.findByParentid(parentid, pageable);
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
