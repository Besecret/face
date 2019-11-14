/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @Title:  ConstantUtils.java   
 * @Package cn.wandingkeji.utils.constant   
 * @Description:    TODO
 * @author: 薛展峰    
 * @date:   2019年6月24日 上午9:42:22   
 * @version V1.0 
 */
package cn.wandingkeji.utils.constant;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.wandingkeji.common.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import cn.wandingkeji.utils.http.ReturnData;

/**   
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName:  ConstantUtils   
 * @Description:TODO   
 * @author: 薛展峰
 * @date:   2019年6月24日 上午9:42:22   
 */
public class ConstantUtils {
	
	private static final Logger log = Logger.getLogger(ConstantUtils.class);

	
	
	/**
	 * @Title: getUUID   
	 * @Description: 生成UUID
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public final static String COMPANY_MEMBER_NAME = "客服";

	public static <T> ReturnData<T>  printErrorMessage() {
		ReturnData<T> returnData = new ReturnData<>();
		returnData.setCode(Constant.ERROR_CODE);
		returnData.setMsg("网络连接超时");
		returnData.setSubCode(Constant.ERROR_CODE);
		returnData.setSubMsg("网络连接超时");
		returnData.setTimestamp(System.currentTimeMillis());
		return returnData;
	}
	
	public static <T> ReturnData<T> printErrorMessage(String message) {
		ReturnData<T> returnData = new ReturnData<T>();
		returnData.setCode(Constant.ERROR_CODE);
		returnData.setMsg(Constant.ERROR_MESSAGE);
		returnData.setSubCode(Constant.ERROR_CODE);
		returnData.setSubMsg(message);
		returnData.setTimestamp(System.currentTimeMillis());
		return returnData;
	}
	
	public static <T> ReturnData<T> printErrorMessage(String code,String message) {
		ReturnData<T> returnData = new ReturnData<T>();
		returnData.setCode(Constant.ERROR_CODE);
		returnData.setMsg(Constant.ERROR_MESSAGE);
		returnData.setSubCode(code);
		returnData.setSubMsg(message);
		returnData.setTimestamp(System.currentTimeMillis());
		return returnData;
	}
	
	public static <T> ReturnData<T> printSuccessMessage(String message,T t) {
		ReturnData<T> returnData = new ReturnData<T>();
		returnData.setCode(Constant.SCCUESS_CODE);
		returnData.setMsg(Constant.SUCCESS_MESSAGE);
		returnData.setSubCode(Constant.SCCUESS_CODE);
		returnData.setSubMsg(message);
		returnData.setTimestamp(System.currentTimeMillis());
		return returnData;
	}
	
	/**   
	 * @Title: formatStringToDate   
	 * @Description: TODO
	 * @param: @param year
	 * @param: @param month
	 * @param: @param day      
	 * @return: void      
	 * @throws   
	 */
	public static String formatStringToDate(String year, String month, String day, String pattern) {
		if(year.equals("0")) {
			year = "2000";
		}
		Integer intMonth = Integer.valueOf(month);
		Integer intDay = Integer.valueOf(day);
		if(intMonth < 10 && intMonth > 0) {
			month = String.valueOf("0" + intMonth);
		} else {
			month = "01";
		}
		if(intDay < 10 && intDay > 0) {
			day = String.valueOf("0" + intDay);
		} else {
			day = "01";
		}
		if(pattern == null || pattern.equals("")) {
			pattern = "-";
		}
		return year + pattern + month + pattern + day;
	}
	

}
