package cn.wandingkeji.yueke.callback.controller;

import cn.wandingkeji.utils.constant.ConstantUtils;
import cn.wandingkeji.yueke.callback.model.EventModel;
import cn.wandingkeji.yueke.callback.service.MessageService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 约客回调controller  配置路径 https://mp.wandingkeji.cn/member-face/message/push
 *
 * @author w.d
 */
@RestController
@RequestMapping("/message")
@Slf4j
public class EventCallbackController {

    @Resource
    private MessageService messageSerivce;

    @RequestMapping("/push")
    public Object pushMessage(HttpServletRequest request) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String acceptJson = StreamUtils.copyToString(inputStream, Charset.forName("utf-8"));
        acceptJson = StringEscapeUtils.unescapeXml(acceptJson);
        log.info(" -------- 约科返回 json 数据 -------- " + "\n" + acceptJson);

        EventModel eventModel;
        try {

            JSONObject json = JSON.parseObject(acceptJson);
            log.info("json is :" + json);
            String event = json.getString("event") == null ? "" : json.getString("event");

            eventModel = JSON.parseObject(event, EventModel.class);

        } catch (Exception e) {

            log.error(" JSON　解析失败 !");
            return ConstantUtils.printErrorMessage(" JSON　解析失败 !");
        }
        log.info("eventModel is :" + eventModel);

        if (eventModel != null && eventModel.getCustomer().getIsVip()) {
            // 已注册用户 推送消息
            return messageSerivce.pushMessage(eventModel);
        } else {
            //普通用户
            log.info(" 未注册用户同步入库 ! ");
            int num = messageSerivce.synCustomerInfo(eventModel);
            if (num > 0) {
                log.info("同步成功");
            }
            return "同步成功";
        }


    }
}
