package com.tenthpower.controller;

import com.tenthpower.pojo.Split;
import com.tenthpower.service.SplitService;
import com.tenthpower.entity.PageResult;
import com.tenthpower.entity.Result;
import com.tenthpower.entity.StatusCode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/split")
@Api(value="吐槽",tags = "SplitController")
public class SplitController {
    @Autowired
    private SplitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    /**
     * 根据ID查询
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }

    /**
     * 添加
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Split spit){
        spitService.save(spit);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 修改
     * @param spitId
     * @param spit
     * @return
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId, @RequestBody Split spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 吐槽
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid, @PathVariable int page, @PathVariable int size){
        Page<Split> pageData = spitService.findByParentid(parentid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Split>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 点赞
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/thumbup/{spitId}", method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitId){
        //判断当前用户是否已经点赞，但是现在我们没有做认证，暂时先把userid写死
        String userid = "111";
        //判断当前用户是否已经点赞
        if(redisTemplate.opsForValue().get("thumbup_"+userid)!=null){
            return new Result(false, StatusCode.REPERROR, "不能重复点赞");
        }
        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userid, 1);
        return new Result(true, StatusCode.OK, "点赞成功");
    }

}
