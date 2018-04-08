package com.julyerr.interviews.thread.ConcurrentProgramming.concepts;

import java.util.HashMap;
import java.util.Map;

/*
 * MutablePoint对象可以改变，返回的时候都需要拷贝数据，数据量较大性能成问题
 * */
public class MonitorVehicleTracker {
    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint point = locations.get(id);
        return point == null ? null : new MutablePoint(point);
    }

    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
        Map<String, MutablePoint> result = new HashMap<>();
        for (String id :
                m.keySet()) {
            result.put(id, new MutablePoint(m.get(id)));
        }
        return result;
    }

    static class MutablePoint {
        public int x, y;

        public MutablePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public MutablePoint(MutablePoint p) {
            this.x = p.x;
            this.y = p.y;
        }
    }
}
