package com.julyerr.interviews.designPattern.adapter;

public class V5PowerAdapter implements V5Power {
    /**
     * 组合的方式
     */
    private V220Power v220Power ;

    public V5PowerAdapter(V220Power v220Power){
        this.v220Power = v220Power ;
    }

    @Override
    public int provideV5Power()
    {
        int power = v220Power.provideV220Power() ;
        System.out.println("适配器：适配了电压。");
        return 5 ;
    }

}
