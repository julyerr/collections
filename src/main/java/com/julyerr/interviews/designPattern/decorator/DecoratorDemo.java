package com.julyerr.interviews.designPattern.decorator;

public class DecoratorDemo {
    public static void main(String[] args){
        System.out.println(" 一个镶嵌1颗蓝宝石，1颗黄宝石的屠龙刀");
        IEquip equip = new BlueGemDecorator(new YellowGemDecorator(new BlueGemDecorator(new ArmEquip())));
        System.out.println("攻击力  : " + equip.caculateAttack());
        System.out.println("描述 :" + equip.description());
        System.out.println("-------");
        System.out.println(" 一个镶嵌2颗黄宝石，1颗蓝宝石的圣战戒指");
        equip = new YellowGemDecorator(new BlueGemDecorator(new YellowGemDecorator(new RingEquip())));
        System.out.println("攻击力  : " + equip.caculateAttack());
        System.out.println("描述 :" + equip.description());
        System.out.println("-------");
    }
}
