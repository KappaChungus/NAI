import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.sqrt;

public class Main {
    public static void main(String[] args) {
        ArrayList<Vector<Double>> preprocessedVectors = new ArrayList<>();
        int k = Integer.parseInt(args[0]);
        HashMap<Vector<Double>, String> map = new HashMap<>();
        ArrayList<Vector<Double>> newVectors = new ArrayList<>();
        preprocess(args, map, preprocessedVectors, newVectors);
        knn(preprocessedVectors,newVectors,map,k);
        saveToFile(map);
    }

    public static void saveToFile(Map<Vector<Double>, String> map) {
        Map<String, List<Vector<Double>>> groupedByValue = map.entrySet().stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        TreeMap::new,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList()) // Zapisanie kluczy jako lista
                ));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            groupedByValue.entrySet().stream()
                    .flatMap(entry -> entry.getValue().stream()
                            .map(vector -> vector.stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(",")) + "," + entry.getKey()))
                    .forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void knn(ArrayList<Vector<Double>> preprocessedVectors,ArrayList<Vector<Double>> newVectors,HashMap<Vector<Double>, String> map, int k) {
        for(Vector<Double> v: newVectors){
            kClosest closestVectors = new kClosest(k,v);
            for(Vector<Double> w :preprocessedVectors){
                closestVectors.add(w);
            }
            Set<Vector<Double>> closestSet = closestVectors.getClosestSet();
            map.put(v,findDominant(closestSet,map));
        }
    }

    public static String findDominant(Set<Vector<Double>> closestSet,HashMap<Vector<Double>, String> map) {
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

    public static double dist(Vector<Double> v, Vector<Double> w) {
        if (v.size() != w.size()) throw new RuntimeException("xd");
        double res = 0;
        for (int i = 0; i < v.size(); i++) {
            res += Math.pow(v.get(i) - w.get(i), 2);
        }
        return sqrt(res);
    }

    public static double scalar(Vector<Integer> v, Vector<Integer> w) {
        if (v.size() != w.size()) throw new RuntimeException("xd");
        double res = 0;
        for (int i = 0; i < v.size(); i++) {
            res += v.get(i) * w.get(i);
        }
        return res;
    }

    public static void preprocess(String args[], HashMap<Vector<Double>, String> map, ArrayList<Vector<Double>> preprocessedVectors, ArrayList<Vector<Double>> newVectors) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[1]));
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
                preprocessedVectors.add(vector);
            }
            br.close();
            br = new BufferedReader((new FileReader(args[2])));
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                Vector<Double> vector = new Vector<>();
                for (String token : tokens) {
                    vector.add(Double.parseDouble(token));
                }
                newVectors.add(vector);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading the file", e);
        }
    }

}