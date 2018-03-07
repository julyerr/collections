package com.julyerr.interviews.designPattern.adapter;

public class AdapterDemo {
    public static void main(String[] args){
        V220Power v220Power = new V220Power();
        V5Power v5Power = new V5PowerAdapter(v220Power);
        Mobile mobile = new Mobile();
        mobile.inputPower(v5Power);
    }
}
