package ru.otus.l161.front;

import ru.otus.l161.messages.Address;
import ru.otus.l161.messages.Addressee;

public interface FrontendService extends Addressee {

    boolean knowsHisAddress(Address address);

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
