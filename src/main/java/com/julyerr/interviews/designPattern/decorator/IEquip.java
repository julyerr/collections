package com.julyerr.interviews.designPattern.decorator;

public interface IEquip {

    /**
     * 计算攻击力
     *
     * @return
     */
    int caculateAttack();

    /**
     * 装备的描述
     *
     * @return
     */
    String description();
}
