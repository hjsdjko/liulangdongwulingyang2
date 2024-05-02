package com.entity.vo;

import com.entity.ChongwuHuifangEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 宠物回访
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 * @author 
 * @email
 * @date 2021-04-26
 */
@TableName("chongwu_huifang")
public class ChongwuHuifangVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 宠物领养订单
     */

    @TableField(value = "chongwu_order_id")
    private Integer chongwuOrderId;


    /**
     * 回访时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "huifang_time")
    private Date huifangTime;


    /**
     * 回访详情
     */

    @TableField(value = "chongwu_huifang_content")
    private String chongwuHuifangContent;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "insert_time")
    private Date insertTime;


    /**
     * 创建时间 show3
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：宠物领养订单
	 */
    public Integer getChongwuOrderId() {
        return chongwuOrderId;
    }


    /**
	 * 获取：宠物领养订单
	 */

    public void setChongwuOrderId(Integer chongwuOrderId) {
        this.chongwuOrderId = chongwuOrderId;
    }
    /**
	 * 设置：回访时间
	 */
    public Date getHuifangTime() {
        return huifangTime;
    }


    /**
	 * 获取：回访时间
	 */

    public void setHuifangTime(Date huifangTime) {
        this.huifangTime = huifangTime;
    }
    /**
	 * 设置：回访详情
	 */
    public String getChongwuHuifangContent() {
        return chongwuHuifangContent;
    }


    /**
	 * 获取：回访详情
	 */

    public void setChongwuHuifangContent(String chongwuHuifangContent) {
        this.chongwuHuifangContent = chongwuHuifangContent;
    }
    /**
	 * 设置：创建时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：创建时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 设置：创建时间 show3
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间 show3
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
