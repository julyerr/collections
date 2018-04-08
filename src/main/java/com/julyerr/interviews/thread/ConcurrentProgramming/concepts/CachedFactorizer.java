package com.julyerr.interviews.thread.ConcurrentProgramming.concepts;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

public class CachedFactorizer implements Servlet {
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

    private BigInteger lastNumber;
    private BigInteger[] lastFactors;
    private long hits;
    private long cacheHits;

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        BigInteger[] factors = null;
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }
        if (factors == null) {
            factors = factor(i);
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
        encodeIntoResponse(servletResponse,factors);
    }

    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCachedHitRatio() {
        return (double) cacheHits / hits;
    }

    public BigInteger extractFromRequest(ServletRequest request) {
        return new BigInteger(String.valueOf(0));
    }

    public void encodeIntoResponse(ServletResponse request, BigInteger[] bigIntegers) {

    }

    public BigInteger[] factor(BigInteger bigInteger) {
        return new BigInteger[]{bigInteger};
    }
}
