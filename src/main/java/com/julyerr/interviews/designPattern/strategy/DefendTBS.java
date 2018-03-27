package com.julyerr.interviews.designPattern.strategy;

public class DefendTBS implements IDefendBehavior {
    @Override
    public void defend() {
        System.out.println("铁布衫");
    }
}
