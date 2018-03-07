package com.julyerr.interviews.designPattern.observe.internel;

import java.util.Observable;

public class SubjectFor3d extends Observable{
    private String msg ;

    public String getMsg(){
        return msg;
    }


    /**
     * 主题更新消息
     *
     * @param msg
     */
    public void setMsg(String msg){
        this.msg = msg  ;
        setChanged();
        notifyObservers();
    }
}
