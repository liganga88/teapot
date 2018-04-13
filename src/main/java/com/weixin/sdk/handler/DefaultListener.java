package com.weixin.sdk.handler;

import com.weixin.sdk.command.Command;

public class DefaultListener extends Listener{
    @Override
    public String handle(Command command) {
        return "success";
    }
}
