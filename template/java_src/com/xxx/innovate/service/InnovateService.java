package com.xxx.innovate.service;


/**
 * @author 代码自动生成工具
 * @version 1.0
 * @since  
 */
 
import java.util.*;
import com.xxx.innovate.model.*;
import com.xxx.innovate.dao.*;
import com.xxx.innovate.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xxx.framework.service.CrudService;

@Service
public class InnovateService extends CrudService<InnovateDao, InnovateEntity> {
	@Autowired
	//Innovate对应的DAO类,主要用于数据库的增删改查等操作
	private InnovateDao innovateDao;
	

}
