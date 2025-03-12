import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        HashMap<Vector<Double>, String> trainVectors = preprocess(args[1]);
        HashMap<Vector<Double>, String> testVectors = preprocess(args[2]);
        System.out.println(Math.round(100 * knn(trainVectors, testVectors, k)) + "%");
    }

    private static double knn(HashMap<Vector<Double>, String> trainVectors, HashMap<Vector<Double>, String> testVectors, int k) {
        int res = 0;
        for (Vector<Double> v : testVectors.keySet()) {
            kClosest closestVectors = new kClosest(k, v);
            for (Vector<Double> w : trainVectors.keySet()) {
                closestVectors.add(w);
            }
            Set<Vector<Double>> closestSet = closestVectors.getClosestSet();
            if (findDominant(closestSet, trainVectors).equals(testVectors.get(v)))
                res++;
        }
        return (double) res / testVectors.size();
    }

    private static String findDominant(Set<Vector<Double>> closestSet, HashMap<Vector<Double>, String> map) {
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        for (Vector<Double> v : closestSet) {
            String label = map.get(v);
            if (label != null)
                frequencyMap.put(label, frequencyMap.getOrDefault(label, 0) + 1);
        }
        return frequencyMap.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private static HashMap<Vector<Double>, String> preprocess(String filepath) {
        HashMap<Vector<Double>, String> map = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                Vector<Double> vector = new Vector<>();
                for (String token : tokens) {
                    try {
                        vector.add(Double.parseDouble(token));
                    } catch (NumberFormatException e) {
                        map.put(vector, token);
                    }
                }
            }
            return map;
        } catch (IOException e) {
            throw new RuntimeException("Error reading the file", e);
        }
    }

}