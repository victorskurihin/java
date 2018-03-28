package ru.otus.l151.app;

import ru.otus.l151.front.FrontendService;
import ru.otus.l151.messageSystem.Address;
import ru.otus.l151.messageSystem.ControlBlock;

public class MsgChat extends MsgToFrontend {
    private final ControlBlock controlBlock;
    private final String text;

    public MsgChat(Address from, Address to, ControlBlock control, String text) {
        super(from, to);
        this.controlBlock = control;
        this.text = text;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.deliverString(controlBlock, text);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
