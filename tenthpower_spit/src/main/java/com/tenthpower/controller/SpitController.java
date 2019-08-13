package com.tenthpower.controller;

import com.tenthpower.dto.spit.SpitVo;
import com.tenthpower.pojo.Spit;
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
@RequestMapping("/api/spit")
@Api(value="吐槽",tags = "SpitController")
public class SpitController {

    @Autowired
    private SplitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     * @return
     */
    @GetMapping(value = "")
    public Result findAll() throws Exception {

        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    /**
     * 根据ID查询
     * @param spitId
     * @return
     */
    @GetMapping(value = "/{spitId}")
    public Result findById(@PathVariable String spitId){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }

    /**
     * 添加
     * @param spitvo
     * @return
     */
    @PostMapping(value = "")
    public Result save(@RequestBody SpitVo spitvo){
        spitService.save(spitvo);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 修改
     * @param spitId
     * @param spitVo
     * @return
     */
    @PutMapping(value = "/{spitId}")
    public Result update(@PathVariable String spitId, @RequestBody SpitVo spitVo){
        spitVo.set_id(spitId);
        spitService.update(spitVo);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     * @param spitId
     * @return
     */
    @DeleteMapping(value = "/{spitId}")
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
    @GetMapping(value = "/comment/{parentid}/{page}/{size}")
    public Result findByParentid(@PathVariable String parentid, @PathVariable int page, @PathVariable int size) throws Exception {
        Page<SpitVo> pageData = spitService.findByParentid(parentid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<SpitVo>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 点赞
     * @param spitId
     * @return
     */
    @PutMapping(value = "/thumbup/{spitId}")
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
