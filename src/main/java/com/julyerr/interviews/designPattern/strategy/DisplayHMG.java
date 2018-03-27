package com.julyerr.interviews.designPattern.strategy;

public class DisplayHMG implements IDisplayBehavior {
    @Override
    public void display() {
        System.out.println("蛤蟆功");
    }
}
