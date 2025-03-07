import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import static java.lang.Math.sqrt;

public class Main {
    public static void main(String[] args) {
        ArrayList<Vector<Double>> preprocessedVectors = new ArrayList<>();
        int k = Integer.parseInt(args[0]);
        HashMap<Vector<Double>, String> map = new HashMap<>();
        ArrayList<Vector<Double>> newVectors = new ArrayList<>();
        preprocess(args, map, preprocessedVectors, newVectors);
        knn(preprocessedVectors,newVectors,map,k);
    }

    public static void knn(ArrayList<Vector<Double>> preprocessedVectors,ArrayList<Vector<Double>> newVectors,HashMap<Vector<Double>, String> map, int k) {
        for(Vector v: newVectors){
            for(Vector w :preprocessedVectors){

            }
        }
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
        System.out.println(map);
        System.out.println(newVectors);
    }

}