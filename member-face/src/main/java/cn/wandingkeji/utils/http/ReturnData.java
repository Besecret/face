/**  
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 *
 * @Title: ReturnDate.java
 * @Package cn.wandingkeji.utils.http
 * @Description: TODO
 * @author: 薛展峰
 * @date: 2019年6月27日 下午1:46:27
 * @version V1.0
 */
package cn.wandingkeji.utils.http;

import lombok.Data;
import lombok.ToString;

/**
 * 西安万鼎网络科技有限公司, http://www.wandingkeji.cn/
 * @ClassName: ReturnDate
 * @Description:TODO
 * @author: 薛展峰
 * @date: 2019年6月27日 下午1:46:27
 */
@Data
@ToString
public class ReturnData<T> {



    private String code;

    private String msg;

    private String subCode;

    private String subMsg;

    private Long timestamp;

    private T data;

}
