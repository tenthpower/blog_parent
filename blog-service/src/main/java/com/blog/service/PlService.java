package com.blog.service;

import com.blog.dao.PlDao;
import com.blog.controller.dto.qa.PlVo;
import com.blog.pojo.Pl;
import com.blog.util.BeanCopierEx;
import com.blog.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlService {

    @Autowired
    private PlDao plDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有的
     */
    public List<PlVo> findAll() throws Exception {
        List<PlVo> result = new ArrayList<PlVo>();
        List<Pl> sqlResult = plDao.findAll();
        result = BeanCopierEx.copy(sqlResult,PlVo.class);
        return result;
    }

    /**
     * 通过id 查询
     */
    public PlVo findById(String id){
        PlVo result = new PlVo();
        Pl sqlResult = plDao.findById(id).get();
        BeanCopierEx.copy(sqlResult,result);
        return result;
    }

    /**
     * 添加
     */
    public void add(PlVo plVo){
        Pl pl = new Pl();
        BeanCopierEx.copy(plVo, pl);
        pl.setId(idWorker.nextId());//设置ID
        plDao.save(pl);
    }

    /**
     * 更新
     */
    public void update(PlVo plVo){
        Pl pl = new Pl();
        BeanCopierEx.copy(plVo, pl);
        plDao.save(pl);
    }

    /**
     * 删除
     */
    public void deleteById(String id){
        plDao.deleteById(id);
    }

}
