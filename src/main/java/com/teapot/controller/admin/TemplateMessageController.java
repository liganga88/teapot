package com.teapot.controller.admin;

import com.teapot.bean.JsonResult;
import com.teapot.pojo.TbTemplateMessage;
import com.teapot.service.TemplateMessageService;
import com.weixin.sdk.api.AccessTokenApi;
import com.weixin.sdk.api.TemplateData;
import com.weixin.sdk.api.TemplateMsgApi;
import com.weixin.sdk.api.UserListApi;
import com.weixin.sdk.api.dto.UserList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/5/11.
 */
@Controller
@RequestMapping("/admin/tempMsg")
public class TemplateMessageController {

    @Autowired
    private TemplateMessageService templateMessageService;

    @Value("${templateMessage.receiver}")
    private String defaultOpenids;

    @RequestMapping("/index")
    public String index(){
        return "admin/tempMsg/index";
    }

    @ResponseBody
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public JsonResult send(@ModelAttribute("templMsg") TbTemplateMessage templMsg){
        //获取用户列表
        List<String> openIds = new ArrayList<>();
        if (StringUtils.isBlank(defaultOpenids)) {
            UserList list = UserListApi.getUserList(AccessTokenApi.getAccessTokenStr(), "");
            openIds.addAll(Arrays.asList(list.getData().getOpenid()));
            while (list.getCount() == 10000) {
                list = UserListApi.getUserList(AccessTokenApi.getAccessTokenStr(), list.getNext_openid());
                openIds.addAll(Arrays.asList(list.getData().getOpenid()));
            }
        } else {
            openIds.addAll(Arrays.asList(defaultOpenids.split(",")));
        }

        //对每个用户发送模板消息
        for(String openid : openIds){
            TemplateMsgApi.send(TemplateData.New()
                    // 消息接收者
                    .setTouser(openid)
                            // 模板id
                    .setTemplate_id("sx-czeQJv_FxGykcTybkh1cBH7SjujTtrERzvYae7K4")
                    .setUrl(templMsg.getUrl())

                            // 模板参数
                    .add("first", templMsg.getFirst() + "\n", "#999")
                    .add("keyword1", templMsg.getKeyword1(), "#999")
                    .add("keyword2", templMsg.getKeyword2(), "#999")
                    .add("keyword3", templMsg.getKeyword3(), "#999")
                    .add("remark", templMsg.getRemark(), "#999")
                    .build());
        }

        //发送记录保存到数据库
        templateMessageService.createTemplateMessage(templMsg);

        return JsonResult.ok();
    }
}
