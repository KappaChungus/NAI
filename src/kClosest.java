import java.util.*;

public class kClosest {
    private final int k;
    TreeSet<Vector<Double>> closestSet;
    Vector<Double> initial;
    kClosest(int k, Vector<Double> initial) {
        this.initial = initial;
        closestSet = new TreeSet<>(Comparator.comparingDouble(v -> Main.dist(initial, v)));

        this.k = k;
    }

    void add(Vector<Double> v){
        closestSet.add(v);
        if(closestSet.size()>k)
            closestSet.pollLast();
    }

    public Set<Vector<Double>> getClosestSet() {
        return closestSet;
    }
}
