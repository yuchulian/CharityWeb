package com.jlqr.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.PersonnelContract;
import com.jlqr.common.model.PersonnelContractView;

public class PersonnelContractService extends ServiceUtil {

	public PersonnelContract personnelContractById(int id) {
		// TODO Auto-generated method stub
		return PersonnelContract.dao.findById(id);
	}

	public Page<PersonnelContractView> personnelContractPaginate(Controller controller) throws Exception {
		// TODO Auto-generated method stub
		return this.paginate(PersonnelContractView.class, controller);
	}

	public void personnelContractSave(PersonnelContract personnelContract) throws Exception{
		SimpleDateFormat df = null;
		//进行设置使用的天数
		df = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = personnelContract.getTrialStart();
		Date end = personnelContract.getTrialEnd();			
		Date beginDate = df.parse(df.format(begin));
		Date endDage = df.parse(df.format(end));
		Calendar cal = Calendar.getInstance();    
        cal.setTime(beginDate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(endDage);    
        long time2 = cal.getTimeInMillis();         
        int between_days=(int) ((time2-time1)/(1000*3600*24));
        personnelContract.setTrialDays(between_days);		
		if(personnelContract.getId()==null){
			Integer id = getMaxColumn(PersonnelContract.class,"id")+1;
			personnelContract.setId(id);
			//进行设置合同编号,可能需要改进
			Date date = new Date();
			df = new SimpleDateFormat("yyyyMMddHHmmss");
			String contract_number = "MZ"+df.format(date);
			personnelContract.setContractNumber(contract_number);
	        personnelContract.save();
		}else{
			personnelContract.update();
		}	
	}

	public PersonnelContractView personnelContractViewById(int id) {
		return PersonnelContractView.dao.findById(id);
	}

	public void personnelContractDelete(Integer id) {
		PersonnelContract.dao.deleteById(id);
	}

}
