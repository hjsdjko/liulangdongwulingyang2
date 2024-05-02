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

import com.entity.ChongwuOrderEntity;

import com.service.ChongwuOrderService;
import com.entity.view.ChongwuOrderView;
import com.service.ChongwuService;
import com.entity.ChongwuEntity;
import com.service.YonghuService;
import com.entity.YonghuEntity;

import com.utils.PageUtils;
import com.utils.R;

/**
 * 宠物领养订单
 * 后端接口
 * @author
 * @email
 * @date 2021-04-26
*/
@RestController
@Controller
@RequestMapping("/chongwuOrder")
public class ChongwuOrderController {
    private static final Logger logger = LoggerFactory.getLogger(ChongwuOrderController.class);

    @Autowired
    private ChongwuOrderService chongwuOrderService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;



    //级联表service
    @Autowired
    private ChongwuService chongwuService;
    @Autowired
    private YonghuService yonghuService;


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
        PageUtils page = chongwuOrderService.queryPage(params);

        //字典表数据转换
        List<ChongwuOrderView> list =(List<ChongwuOrderView>)page.getList();
        for(ChongwuOrderView c:list){
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
        ChongwuOrderEntity chongwuOrder = chongwuOrderService.selectById(id);
        if(chongwuOrder !=null){
            //entity转view
            ChongwuOrderView view = new ChongwuOrderView();
            BeanUtils.copyProperties( chongwuOrder , view );//把实体数据重构到view中

            //级联表
            ChongwuEntity chongwu = chongwuService.selectById(chongwuOrder.getChongwuId());
            if(chongwu != null){
                BeanUtils.copyProperties( chongwu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setChongwuId(chongwu.getId());
            }
            //级联表
            YonghuEntity yonghu = yonghuService.selectById(chongwuOrder.getYonghuId());
            if(yonghu != null){
                BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setYonghuId(yonghu.getId());
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
    public R save(@RequestBody ChongwuOrderEntity chongwuOrder, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,chongwuOrder:{}",this.getClass().getName(),chongwuOrder.toString());
        chongwuOrder.setInsertTime(new Date());
        chongwuOrder.setCreateTime(new Date());
        chongwuOrderService.insert(chongwuOrder);
        return R.ok();
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ChongwuOrderEntity chongwuOrder, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,chongwuOrder:{}",this.getClass().getName(),chongwuOrder.toString());
        chongwuOrderService.updateById(chongwuOrder);//根据id更新
        return R.ok();
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        chongwuOrderService.deleteBatchIds(Arrays.asList(ids));
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
        PageUtils page = chongwuOrderService.queryPage(params);

        //字典表数据转换
        List<ChongwuOrderView> list =(List<ChongwuOrderView>)page.getList();
        for(ChongwuOrderView c:list){
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
        ChongwuOrderEntity chongwuOrder = chongwuOrderService.selectById(id);
            if(chongwuOrder !=null){
                //entity转view
        ChongwuOrderView view = new ChongwuOrderView();
                BeanUtils.copyProperties( chongwuOrder , view );//把实体数据重构到view中

                //级联表
                    ChongwuEntity chongwu = chongwuService.selectById(chongwuOrder.getChongwuId());
                if(chongwu != null){
                    BeanUtils.copyProperties( chongwu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setChongwuId(chongwu.getId());
                }
                //级联表
                    YonghuEntity yonghu = yonghuService.selectById(chongwuOrder.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
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
    public R add(@RequestBody ChongwuOrderEntity chongwuOrder, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,chongwuOrder:{}",this.getClass().getName(),chongwuOrder.toString());
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if("用户".equals(role)){
            ChongwuEntity chongwuEntity = chongwuService.selectById(chongwuOrder.getChongwuId());
            if(chongwuEntity == null){
                return R.error(511,"查不到宠物");
            }
            if(chongwuEntity.getChongwuYesnoTypes() == 1){
                return R.error(511,"该宠物已被领养");
            }
//            Double chongwuNewMoney = chongwuEntity.getChongwuNewMoney();
//            if(chongwuNewMoney == null){
//                return R.error(511,"商品价格不能为空");
//            }
//
//            Integer userId = (Integer) request.getSession().getAttribute("userId");
//            YonghuEntity yonghuEntity = yonghuService.selectById(userId);
//            if(yonghuEntity == null){
//                return R.error(511,"用户不能为空");
//            }
//            if(yonghuEntity.getNewMoney() == null){
//                return R.error(511,"用户金额不能为空");
//            }
//            double balance = yonghuEntity.getNewMoney() - chongwuEntity.getChongwuNewMoney();//余额
//            if(balance<0){
//                return R.error(511,"余额不够支付");
//            }
//            chongwuOrder.set();
            chongwuOrder.setCreateTime(new Date());
            chongwuOrder.setInsertTime(new Date());
            chongwuOrderService.insert(chongwuOrder);//根据id更新

            chongwuEntity.setChongwuYesnoTypes(1);
            chongwuService.updateById(chongwuEntity);
//            yonghuEntity.setNewMoney(balance);
//            yonghuService.updateById(yonghuEntity);
            return R.ok();
        }else{
            return R.error(511,"您没有权限支付订单");
        }
    }





}

