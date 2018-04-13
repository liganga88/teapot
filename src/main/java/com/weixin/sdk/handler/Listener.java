package com.weixin.sdk.handler;

import com.weixin.sdk.command.Command;

public abstract class Listener {
    public abstract String handle(Command command);
}
