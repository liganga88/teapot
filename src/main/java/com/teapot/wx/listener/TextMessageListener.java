package com.teapot.wx.listener;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.weixin.sdk.command.Command;
import com.weixin.sdk.handler.Listener;
import com.weixin.sdk.textmsg.MsgStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */
public class TextMessageListener extends Listener {
    private static String[] keys = {"最新法务安排"};

    @Override
    public String handle(Command command) {
        if (hasKeys(command.getContent())) {

            List<String> params = new ArrayList<>();
            params.add("Fo5jYyj6otn-xSa42xG5GX0Xm7hDF4Z-Jp7-EhVa0NPdpj69OQ_1gYG6XEfe5w0t");
            return MsgStrategy.getReply(MsgStrategy.IMAGE, command.getToUserName(), command.getFromUserName(), params);

        }  else {
            String text = "阿弥陀佛，感谢您关注扬州大明寺官方微信平台，如对佛学常识、理论等有疑问者，可添加微信号：renbai19841020。我们会及时的回复您；祝您阖家欢乐，六时吉祥！";
            return MsgStrategy.getTextMsg(command.getToUserName(), command.getFromUserName(), text);
        }
    }

    private boolean hasKeys(String msg) {
        for (String key : keys) {
            if (msg.startsWith(key))
                return true;
        }
        return false;
    }

}
