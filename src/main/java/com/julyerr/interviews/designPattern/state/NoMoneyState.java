package com.julyerr.interviews.designPattern.state;

public class NoMoneyState implements State {
    private VendingMachine machine;

    public NoMoneyState(VendingMachine machine){
        this.machine = machine;

    }

    @Override
    public void insertMoney(){
        System.out.println("投币成功");
        machine.setState(machine.getHasMoneyState());
    }

    @Override
    public void backMoney(){
        System.out.println("您未投币，不能退钱...");
    }

    @Override
    public void turnCrank(){
        System.out.println("您未投币，不能拿东西...");
    }

    @Override
    public void dispense(){
        throw new IllegalStateException("非法状态！");
    }
}
