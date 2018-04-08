package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.tools;

import com.julyerr.interviews.designPattern.command.Computer;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        return new BigInteger("0");
    }
}
