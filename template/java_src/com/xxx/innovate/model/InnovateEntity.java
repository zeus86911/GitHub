package com.xxx.innovate.model;

import java.util.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.xxx.framework.model.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
@Data @EqualsAndHashCode(callSuper=false)
public class InnovateEntity extends BaseEntity<InnovateEntity>{
	private static final long serialVersionUID = 5454155825314635342L;
	//alias
	public static final String TABLE_ALIAS = "创意";
	
		
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	/**
	* 主键ID       db_column: ID 
	*/	
	@Length(max=50)
	private String id;
	/**
	* 标题       db_column: TITLE 
	*/	
	@Length(max=200)
	private String title;
	/**
	* 必要性分析       db_column: ANALYSE1 
	*/	
	@Length(max=4000)
	private String analyse1;
	/**
	* 关键字       db_column: KEYWORD 
	*/	
	@Length(max=50)
	private String keyword;
	/**
	* 类型       db_column: TYPE 
	*/	
	
	private Integer type;
	/**
	* 父级分类ID       db_column: PARENT_CATEGORY_ID 
	*/	
	@Length(max=50)
	private String parentCategoryId;
	/**
	* 父级分类名称       db_column: PARENT_CATEGORY_NAME 
	*/	
	@Length(max=50)
	private String parentCategoryName;
	/**
	* 分类ID       db_column: CATEGORY_ID 
	*/	
	@Length(max=50)
	private String categoryId;
	/**
	* 分类名称       db_column: CATEGORY_NAME 
	*/	
	@Length(max=50)
	private String categoryName;
	/**
	* 联系人名       db_column: NAME 
	*/	
	@Length(max=50)
	private String name;
	/**
	* 联系手机       db_column: TELEPHONE 
	*/	
	@Length(max=50)
	private String telephone;
	/**
	* 邮箱地址       db_column: EMAIL 
	*/	
	@Length(max=50)
	private String email;
	/**
	* 是否公开       db_column: IS_PUBLIC 
	*/	
	
	private Integer isPublic;
	/**
	* 评分       db_column: SCORE 
	*/	
	
	private Integer score;
	/**
	* 所属层级       db_column: RANK 
	*/	
	
	private Integer rank;
	/**
	* 点赞统计       db_column: GOOD_COUNT 
	*/	
	
	private Integer goodCount;
	/**
	* 评论统计       db_column: TALK_COUNT 
	*/	
	
	private Integer talkCount;
	/**
	* 阅知统计       db_column: VIEW_COUNT 
	*/	
	
	private Integer viewCount;
	/**
	* 收藏统计       db_column: COLLECT_COUNT 
	*/	
	
	private Integer collectCount;
	/**
	* 整合次数       db_column: INTEGRATED_NUM 
	*/	
	
	private Integer integratedNum;
	/**
	*  状态       db_column: STATUS 
	*/	
	@Length(max=50)
	private String status;
	/**
	* 来源类型       db_column: SOURCE_TYPE 
	*/	
	
	private Integer sourceType;
	/**
	* 来源       db_column: SOURCE_SYS 
	*/	
	
	private Integer sourceSys;
	/**
	* 来源名称ID       db_column: SOURCE_ID 
	*/	
	@Length(max=50)
	private String sourceId;
	/**
	* 来源名称        db_column: SOURCE_NAME 
	*/	
	@Length(max=200)
	private String sourceName;
	/**
	* 备注       db_column: REMARK 
	*/	
	@Length(max=200)
	private String remark;
	/**
	* 是否已删除 N 未删除 Y 已删除       db_column: IS_DELETE 
	*/	
	@Length(max=50)
	private String isDelete;
	/**
	* 创建人编码       db_column: CREATE_USER_CODE 
	*/	
	@Length(max=50)
	private String createUserCode;
	/**
	* 创建人姓名       db_column: CREATE_USER_NAME 
	*/	
	@Length(max=50)
	private String createUserName;
	/**
	* 创建人组织ID       db_column: CREATE_ORG_ID 
	*/	
	@Length(max=50)
	private String createOrgId;
	/**
	* 创建人组织名称       db_column: CREATE_ORG_NAME 
	*/	
	@Length(max=50)
	private String createOrgName;
	/**
	* 创建人部门ID       db_column: CREATE_DEPT_ID 
	*/	
	@Length(max=50)
	private String createDeptId;
	/**
	* 创建人部门名称       db_column: CREATE_DEPT_NAME 
	*/	
	@Length(max=50)
	private String createDeptName;
	/**
	* 创建人单位ID       db_column: CREATE_COMPANY_ID 
	*/	
	@Length(max=50)
	private String createCompanyId;
	/**
	* 创建人单位名称       db_column: CREATE_COMPANY_NAME 
	*/	
	@Length(max=50)
	private String createCompanyName;
	/**
	* 创建时间       db_column: CREATE_TIME 
	*/	
	
	private Date createTime;
	/**
	* 最后更新人       db_column: UPDATE_USER_CODE 
	*/	
	@Length(max=50)
	private String updateUserCode;
	/**
	* 最后更新人组织ID       db_column: UPDATE_ORG_ID 
	*/	
	@Length(max=50)
	private String updateOrgId;
	/**
	* 最后更新时间       db_column: UPDATE_TIME 
	*/	
	
	private Date updateTime;
	/**
	* 转派接收人编码       db_column: RECEIVER_CODE 
	*/	
	@Length(max=50)
	private String receiverCode;
	/**
	* 转派接收人名称       db_column: RECEIVER_NAME 
	*/	
	@Length(max=50)
	private String receiverName;
	/**
	* 转派组织ID       db_column: RECEIVER_ORG_ID 
	*/	
	@Length(max=50)
	private String receiverOrgId;
	/**
	* 转派组织名称       db_column: RECEIVER_ORG_NAME 
	*/	
	@Length(max=50)
	private String receiverOrgName;
	/**
	* 转派部门ID       db_column: RECEIVER_DEPT_ID 
	*/	
	@Length(max=50)
	private String receiverDeptId;
	/**
	* 转派部门名称       db_column: RECEIVER_DEPT_NAME 
	*/	
	@Length(max=50)
	private String receiverDeptName;
	/**
	* 转派状态       db_column: TRUN_STATUS 
	*/	
	@Length(max=50)
	private String trunStatus;
	/**
	* 及时改进ID       db_column: INTIME_ID 
	*/	
	@Length(max=50)
	private String intimeId;
	/**
	* 及时改进反馈时间       db_column: INTIME_DATE 
	*/	
	
	private Date intimeDate;
	/**
	* 主要内容       db_column: CONTENT 
	*/	
	@Length(max=4000)
	private String content;
	/**
	* 是否推荐       db_column: IS_RECOMMEND 
	*/	
	
	private Integer isRecommend;
	/**
	* 推荐人       db_column: RECOMMEND_USER_CODE 
	*/	
	@Length(max=50)
	private String recommendUserCode;
	/**
	* 推荐时间       db_column: RECOMMEND_TIME 
	*/	
	
	private Date recommendTime;
	/**
	* 定性目标       db_column: AIM 
	*/	
	@Length(max=4000)
	private String aim;
	/**
	* 可行性分析       db_column: ANALYSE2 
	*/	
	@Length(max=4000)
	private String analyse2;
	//columns END

	public InnovateEntity(){
	}

	public InnovateEntity(
		String id
	){
		this.id = id;
	}

	
	@Override
	public void preInsert() {
		this.setId(java.util.UUID.randomUUID().toString());
	}

	@Override
	public void preUpdate() {
	// TODO Auto-generated method stub
	}
	
		
}

