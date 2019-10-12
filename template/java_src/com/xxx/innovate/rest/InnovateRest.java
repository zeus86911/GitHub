package com.xxx.innovate.rest;


/**
 * @author 代码自动生成工具
 * @version 1.0
 * @since  
 */
 
import java.util.*;
import com.xxx.innovate.model.*;
import com.xxx.innovate.dao.*;
import com.xxx.innovate.service.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.xxx.framework.model.ServiceResponse;
import com.xxx.framework.model.Page;


@Api(value = "innovate")
@RestController
@RequestMapping(value = "/innovate-api/innovate")
public class InnovateRest {
	@Autowired
	private InnovateService innovateService;
	
	
	/** 
	 * 搜索对象 
	 **/
	@RequestMapping(value = "",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
	@ResponseBody
	@CrossOrigin
	public ServiceResponse<Page<InnovateEntity>> list(InnovateEntity innovateEntity) {
		Page<InnovateEntity> page = this.innovateService.findPage(innovateEntity);
		return new ServiceResponse<Page<InnovateEntity>>(page,ServiceResponse.RESULT_CODE_SUCCESS_MSG,ServiceResponse.RESULT_CODE_SUCCESS_CODE);
	}

	
	/** 
	 * 查看对象
	 **/
	@RequestMapping(value = "/{id}",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
	@ResponseBody
	@CrossOrigin
	public ServiceResponse<InnovateEntity> view(@PathVariable String id)  {
		InnovateEntity innovateEntity  = innovateService.get(id);
		return new ServiceResponse<InnovateEntity>(innovateEntity,ServiceResponse.RESULT_CODE_SUCCESS_MSG,ServiceResponse.RESULT_CODE_SUCCESS_CODE);
	}


	/** 
	 * 查看对象（支持多条件查询）
	 **/
	@RequestMapping(value = "get",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
	@ResponseBody
	@CrossOrigin
	public ServiceResponse<InnovateEntity> get(InnovateEntity model)  {
		InnovateEntity innovateEntity  = innovateService.get(model);
		return new ServiceResponse<InnovateEntity>(innovateEntity,ServiceResponse.RESULT_CODE_SUCCESS_MSG,ServiceResponse.RESULT_CODE_SUCCESS_CODE);
	}
	
	
	/** 
	 * 新增对象
	 **/
	@RequestMapping(value = "",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	@CrossOrigin
	public ServiceResponse<InnovateEntity> save(@RequestBody InnovateEntity model)  {
		innovateService.save(model);
		return new ServiceResponse<InnovateEntity>(model,ServiceResponse.RESULT_CODE_SUCCESS_MSG,ServiceResponse.RESULT_CODE_SUCCESS_CODE);
	}
	
	
	/**
	 * 更新对象
	 * @return 
	 **/
	@RequestMapping(value = "",method = RequestMethod.PUT,produces = "application/json;charset=UTF-8")
	@ResponseBody
	@CrossOrigin
	public ServiceResponse<InnovateEntity> update(@RequestBody InnovateEntity model)  {
		innovateService.update(model);
		return new ServiceResponse<InnovateEntity>(ServiceResponse.RESULT_CODE_SUCCESS_MSG,ServiceResponse.RESULT_CODE_SUCCESS_CODE);
	}
	
	/**
	 * 删除对象
	 * @return 
	 **/
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE,produces = "application/json;charset=UTF-8")
	@ResponseBody
	@CrossOrigin
	public ServiceResponse<InnovateEntity> delete(@PathVariable String id) {
		innovateService.delete(id);
		return new ServiceResponse<InnovateEntity>(ServiceResponse.RESULT_CODE_SUCCESS_MSG,ServiceResponse.RESULT_CODE_SUCCESS_CODE);
	}
	
	/**
	 *  批量删除对象
	 * @return 
	 **/
	@RequestMapping(value = "deleteBatch",method = RequestMethod.DELETE,produces = "application/json;charset=UTF-8")
	@ResponseBody
	@CrossOrigin
	public ServiceResponse<InnovateEntity> deleteBatch(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			innovateService.delete(ids[i]);
		}
		return new ServiceResponse<InnovateEntity>(ServiceResponse.RESULT_CODE_SUCCESS_MSG,ServiceResponse.RESULT_CODE_SUCCESS_CODE);
	}
	
}



