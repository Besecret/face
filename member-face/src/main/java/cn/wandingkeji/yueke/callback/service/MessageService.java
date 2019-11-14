

package cn.wandingkeji.yueke.callback.service;

import cn.wandingkeji.yueke.callback.model.EventModel;


/**
 * 推送 微信消息service
 * @author  w.d
 */

public interface MessageService {

	/**
	 *  推送消息
	 * @param eventModel 消息体
	 * @return 推送结果
	 */
	Object pushMessage(EventModel eventModel);

	/**
	 * 同步顾客信息
	 * @param event 事件
	 * @return 数据库变化行数
	 */
	int synCustomerInfo(EventModel event);
}
