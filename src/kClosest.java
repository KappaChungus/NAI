import java.util.*;

import static java.lang.Math.sqrt;

public class kClosest {
    private final int k;
    TreeSet<Vector<Double>> closestSet;
    Vector<Double> initial;
    kClosest(int k, Vector<Double> initial) {
        this.initial = initial;
        closestSet = new TreeSet<>(Comparator.comparingDouble(v -> dist(initial, v)));

        this.k = k;
    }

    void add(Vector<Double> v){
        closestSet.add(v);
        if(closestSet.size()>k)
            closestSet.pollLast();
    }

    private static double dist(Vector<Double> v, Vector<Double> w) {
        if (v.size() != w.size()) throw new RuntimeException("xd");
        double res = 0;
        for (int i = 0; i < v.size(); i++) {
            res += Math.pow(v.get(i) - w.get(i), 2);
        }
        return sqrt(res);
    }

    public Set<Vector<Double>> getClosestSet() {
        return closestSet;
    }
}
