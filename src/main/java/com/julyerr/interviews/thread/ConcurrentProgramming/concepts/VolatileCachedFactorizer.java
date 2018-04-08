package com.julyerr.interviews.thread.ConcurrentProgramming.concepts;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class VolatileCachedFactorizer implements Servlet {

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

    class OneValueCache {
        private final BigInteger lastNumber;
        private final BigInteger[] lastFactors;

        public OneValueCache(BigInteger lastNumber, BigInteger[] lastFactors) {
            this.lastNumber = lastNumber;
//            重新生成一个对象，保证受到外部影响
            this.lastFactors = Arrays.copyOf(lastFactors, lastFactors.length);
        }

        public BigInteger[] getLastFactors(BigInteger i) {
            if (lastNumber == null || !lastNumber.equals(i)) {
                return null;
            } else {
//                返回的对象不会对内部产生影响
                return Arrays.copyOf(lastFactors, lastFactors.length);
            }
        }
    }

    private volatile OneValueCache cache = new OneValueCache(null, null);

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = UnsageCachingFactorizer.extractFromRequest(servletRequest);
        BigInteger[] factors = cache.getLastFactors(i);
        if (factors == null) {
            factors = UnsageCachingFactorizer.factor(i);
//            volatile保证多线程的可见性
            cache = new OneValueCache(i, factors);
        }
        UnsageCachingFactorizer.encodeIntoResponse(servletResponse, factors);
    }
}
