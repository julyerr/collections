package com.julyerr.interviews.designPattern.strategy;

public class AttackJY implements IAttackBehavior {
    @Override
    public void attack() {
        System.out.println("九阳神功！");
    }
}
