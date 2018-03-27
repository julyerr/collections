package com.julyerr.interviews.designPattern.strategy;

public class RoleA extends Role {
    public RoleA(String name) {
        this.name = name;
    }

    public static void main(String[] args) {

        Role roleA = new RoleA("A");

        roleA.setAttackBehavior(new AttackJY());
        roleA.setDefendBehavior(new DefendTBS());
        roleA.setDisplayBehavior(new DisplayHMG());
        System.out.println(roleA.name);
        roleA.attack();
        roleA.defend();
        roleA.display();
    }
}
