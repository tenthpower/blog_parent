package com.blog.service;

import com.blog.dao.UlDao;
import com.blog.dto.lable.UlVo;
import com.blog.pojo.Ul;
import com.blog.util.BeanCopierEx;
import com.blog.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UlService {

    @Autowired
    private UlDao ulDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有的
     */
    public List<UlVo> findAll() throws Exception {
        List<UlVo> result = new ArrayList<UlVo>();
        List<Ul> sqlResult = ulDao.findAll();
        result = BeanCopierEx.copy(sqlResult,UlVo.class);
        return result;
    }

    /**
     * 通过id 查询
     */
    public UlVo findById(String id){
        UlVo result = new UlVo();
        Ul sqlResult = ulDao.findById(id).get();
        BeanCopierEx.copy(sqlResult,result);
        return result;
    }

    /**
     * 添加
     */
    public void add(UlVo ulVo){
        Ul ul = new Ul();
        BeanCopierEx.copy(ulVo, ul);
        ul.setId(idWorker.nextId());//设置ID
        ulDao.save(ul);
    }

    /**
     * 更新
     */
    public void update(UlVo ulVo){
        Ul ul = new Ul();
        BeanCopierEx.copy(ulVo, ul);
        ulDao.save(ul);
    }

    /**
     * 删除
     */
    public void deleteById(String id){
        ulDao.deleteById(id);
    }
}
