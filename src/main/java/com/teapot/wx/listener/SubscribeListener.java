package com.teapot.wx.listener;

import com.weixin.sdk.command.Command;
import com.weixin.sdk.handler.Listener;
import com.weixin.sdk.textmsg.MsgStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注/取消关注事件
 */
public class SubscribeListener extends Listener {
    @Override
    public String handle(Command command) {
        if (Command.EVENT_KEYS.SUBSCRIBE_EVENT.equals(command.getCommandKey())) {
            List<String> params = new ArrayList<>();
            params.add("U0Th1Hu_fTkcpP8gqEkm5qtSNHpePMbkDDBZw6lW5CkEIVbZjt3ovJIw7ZWD9Xa_");
            return MsgStrategy.getReply(MsgStrategy.IMAGE, command.getToUserName(), command.getFromUserName(), params);
        }
        return null;
    }
}
