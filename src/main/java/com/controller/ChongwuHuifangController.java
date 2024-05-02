package com.controller;


import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.StringUtil;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import com.entity.ChongwuHuifangEntity;

import com.service.ChongwuHuifangService;
import com.entity.view.ChongwuHuifangView;
import com.service.ChongwuOrderService;
import com.entity.ChongwuOrderEntity;

import com.utils.PageUtils;
import com.utils.R;

/**
 * 宠物回访
 * 后端接口
 * @author
 * @email
 * @date 2021-04-26
*/
@RestController
@Controller
@RequestMapping("/chongwuHuifang")
public class ChongwuHuifangController {
    private static final Logger logger = LoggerFactory.getLogger(ChongwuHuifangController.class);

    @Autowired
    private ChongwuHuifangService chongwuHuifangService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;



    //级联表service
    @Autowired
    private ChongwuOrderService chongwuOrderService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
     
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isNotEmpty(role) && "用户".equals(role)){
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        }
        params.put("orderBy","id");
        PageUtils page = chongwuHuifangService.queryPage(params);

        //字典表数据转换
        List<ChongwuHuifangView> list =(List<ChongwuHuifangView>)page.getList();
        for(ChongwuHuifangView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ChongwuHuifangEntity chongwuHuifang = chongwuHuifangService.selectById(id);
        if(chongwuHuifang !=null){
            //entity转view
            ChongwuHuifangView view = new ChongwuHuifangView();
            BeanUtils.copyProperties( chongwuHuifang , view );//把实体数据重构到view中

            //级联表
            ChongwuOrderEntity chongwuOrder = chongwuOrderService.selectById(chongwuHuifang.getChongwuOrderId());
            if(chongwuOrder != null){
                BeanUtils.copyProperties( chongwuOrder , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setChongwuOrderId(chongwuOrder.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody ChongwuHuifangEntity chongwuHuifang, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,chongwuHuifang:{}",this.getClass().getName(),chongwuHuifang.toString());
//        Wrapper<ChongwuHuifangEntity> queryWrapper = new EntityWrapper<ChongwuHuifangEntity>()
//            .eq("chongwu_order_id", chongwuHuifang.getChongwuOrderId())
//            ;
//        logger.info("sql语句:"+queryWrapper.getSqlSegment());
//        ChongwuHuifangEntity chongwuHuifangEntity = chongwuHuifangService.selectOne(queryWrapper);
//        if(chongwuHuifangEntity==null){
            chongwuHuifang.setInsertTime(new Date());
            chongwuHuifang.setCreateTime(new Date());
        //  String role = String.valueOf(request.getSession().getAttribute("role"));
        //  if("".equals(role)){
        //      chongwuHuifang.set
        //  }
            chongwuHuifangService.insert(chongwuHuifang);
            return R.ok();
//        }else {
//            return R.error(511,"表中有相同数据");
//        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ChongwuHuifangEntity chongwuHuifang, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,chongwuHuifang:{}",this.getClass().getName(),chongwuHuifang.toString());
//        //根据字段查询是否有相同数据
//        Wrapper<ChongwuHuifangEntity> queryWrapper = new EntityWrapper<ChongwuHuifangEntity>()
//            .notIn("id",chongwuHuifang.getId())
//            .andNew()
//            .eq("chongwu_order_id", chongwuHuifang.getChongwuOrderId())
//            ;
//        logger.info("sql语句:"+queryWrapper.getSqlSegment());
//        ChongwuHuifangEntity chongwuHuifangEntity = chongwuHuifangService.selectOne(queryWrapper);
//        if(chongwuHuifangEntity==null){
            //  String role = String.valueOf(request.getSession().getAttribute("role"));
            //  if("".equals(role)){
            //      chongwuHuifang.set
            //  }
            chongwuHuifangService.updateById(chongwuHuifang);//根据id更新
            return R.ok();
//        }else {
//            return R.error(511,"表中有相同数据");
//        }
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        chongwuHuifangService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }



    /**
    * 前端列表
    */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isNotEmpty(role) && "用户".equals(role)){
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        }
        // 没有指定排序字段就默认id倒序
        if(StringUtil.isEmpty(String.valueOf(params.get("orderBy")))){
            params.put("orderBy","id");
        }
        PageUtils page = chongwuHuifangService.queryPage(params);

        //字典表数据转换
        List<ChongwuHuifangView> list =(List<ChongwuHuifangView>)page.getList();
        for(ChongwuHuifangView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c);
        }
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ChongwuHuifangEntity chongwuHuifang = chongwuHuifangService.selectById(id);
            if(chongwuHuifang !=null){
                //entity转view
        ChongwuHuifangView view = new ChongwuHuifangView();
                BeanUtils.copyProperties( chongwuHuifang , view );//把实体数据重构到view中

                //级联表
                    ChongwuOrderEntity chongwuOrder = chongwuOrderService.selectById(chongwuHuifang.getChongwuOrderId());
                if(chongwuOrder != null){
                    BeanUtils.copyProperties( chongwuOrder , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setChongwuOrderId(chongwuOrder.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody ChongwuHuifangEntity chongwuHuifang, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,chongwuHuifang:{}",this.getClass().getName(),chongwuHuifang.toString());
        Wrapper<ChongwuHuifangEntity> queryWrapper = new EntityWrapper<ChongwuHuifangEntity>()
            .eq("chongwu_order_id", chongwuHuifang.getChongwuOrderId())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
    ChongwuHuifangEntity chongwuHuifangEntity = chongwuHuifangService.selectOne(queryWrapper);
        if(chongwuHuifangEntity==null){
            chongwuHuifang.setInsertTime(new Date());
            chongwuHuifang.setCreateTime(new Date());
        //  String role = String.valueOf(request.getSession().getAttribute("role"));
        //  if("".equals(role)){
        //      chongwuHuifang.set
        //  }
        chongwuHuifangService.insert(chongwuHuifang);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }





}

