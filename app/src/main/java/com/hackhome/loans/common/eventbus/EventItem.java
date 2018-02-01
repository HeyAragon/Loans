package com.hackhome.loans.common.eventbus;

/**
 * desc:
 * author: aragon
 * date: 2018/1/2 0002.
 */
public class EventItem {

    public static final int MAIN_OBJECT = 0x1;
    public static final int LOAN_DETAIL_OBJECT = 0x2;
    public static final int WEB_OBJECT = 0x3;
    public static final int DOWNLOAD_OBJECT = 0x4;

    public static final int HOME_FRAGMENT_OBJECT = 0x5;
    public static final int LOAN_FRAGMENT_OBJECT = 0x6;
    public static final int MINE_FRAGMENT_OBJECT = 0x7;

    public static final int LOGIN_SUCCESS = 0X1000;
    public static final int EXIT_SUCCESS = 0X1001;

    public static final int INSTALL_SUCCESS = 0X1002;
    public static final int UNINSTALL_SUCCESS = 0X1003;

    public static final int REFRESH_PROGRESS = 0x1004;


    private Object ob;
    private int messageType;
    private int receiveObject;

    public EventItem(int receiveObject,int messageType) {
        this.messageType = messageType;
        this.receiveObject = receiveObject;
    }

    public EventItem(int receiveObject, int messageType,Object ob ) {

        this.ob = ob;
        this.messageType = messageType;
        this.receiveObject = receiveObject;
    }

    public Object getOb() {
        return ob;
    }

    public void setOb(Object ob) {
        this.ob = ob;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getReceiveObject() {
        return receiveObject;
    }

    public void setReceiveObject(int receiveObject) {
        this.receiveObject = receiveObject;
    }
}
