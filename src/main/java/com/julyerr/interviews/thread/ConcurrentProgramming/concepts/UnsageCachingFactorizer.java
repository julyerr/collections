package com.julyerr.interviews.thread.ConcurrentProgramming.concepts;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

public class UnsageCachingFactorizer implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }

    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        if (i.equals(lastNumber.get())) {
            encodeIntoResponse(servletResponse, lastFactors.get());
        } else {
            BigInteger[] factors = factor(i);
//            依然不能保证线程的安全性
            lastNumber.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(servletResponse, factors);
        }
    }

    public static BigInteger extractFromRequest(ServletRequest request) {
        return new BigInteger(String.valueOf(0));
    }

    public static void encodeIntoResponse(ServletResponse request, BigInteger[] bigIntegers) {

    }

    public static BigInteger[] factor(BigInteger bigInteger) {
        return new BigInteger[]{bigInteger};
    }
}
