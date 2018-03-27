package com.julyerr.interviews.designPattern.strategy;

public abstract class Role {
    protected String name;

    protected IDefendBehavior defendBehavior;
    protected IDisplayBehavior displayBehavior;
    protected IAttackBehavior attackBehavior;

    public void setName(String name) {
        this.name = name;
    }

    public void setDefendBehavior(IDefendBehavior defendBehavior) {
        this.defendBehavior = defendBehavior;
    }

    public void setDisplayBehavior(IDisplayBehavior displayBehavior) {
        this.displayBehavior = displayBehavior;
    }

    public void setAttackBehavior(IAttackBehavior attackBehavior) {
        this.attackBehavior = attackBehavior;
    }

    protected void display(){
        displayBehavior.display();
    }


    protected void attack() {
        attackBehavior.attack();
    }

    protected void defend(){
        defendBehavior.defend();
    }
}
