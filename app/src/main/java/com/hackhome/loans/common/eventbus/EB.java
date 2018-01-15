package com.hackhome.loans.common.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * desc: EventBus 事件发布类
 * author: aragon
 * date: 2018/1/2 0002.
 */
public class EB {

    private static EB instance = new EB();


    public static EB getInstance(){
        return instance;
    }

    /**
     * 发送普通事件
     * @param receiveObject 事件接收者
     * @param messageType 事件类型
     */
    public void send(int receiveObject,int messageType){
        EventBus.getDefault().post(new EventItem(receiveObject,messageType));
    }

    /**
     * 发送普通事件
     * @param receiveObject 事件接收者
     * @param messageType 事件类型
     * @param ob 传递的参数
     */
    public void send(int receiveObject,int messageType,Object ob){
        EventBus.getDefault().post(new EventItem(receiveObject,messageType,ob));
    }

    /**
     * 发送粘性事件
     * @param receiveObject 事件接收者
     * @param messageType 事件类型
     */
    public void sendSticky(int receiveObject,int messageType){
        EventBus.getDefault().postSticky(new EventItem(receiveObject,messageType));
    }

    /**
     * 发送粘性事件
     * @param receiveObject 事件接收者
     * @param messageType 事件类型
     * @param ob 传递的参数
     */
    public void sendSticky(int receiveObject,int messageType,Object ob){
        EventBus.getDefault().postSticky(new EventItem(receiveObject,messageType,ob));
    }

}
