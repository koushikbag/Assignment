package com.example.assignment.utils;

import android.util.Log;

import com.example.assignment.model.Conversion;

import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Utils {
    private int V, E;
    private Edge edge[];

    public static String getCurrencySymbol(String currencyISO) {
        Locale.setDefault(Locale.ROOT);
        Currency c = Currency.getInstance(currencyISO);
        return c.getSymbol();
    }

    public static Double getMappedPrice(List<Conversion> conversions,
                                          String head, String convertTo) {

        Set<String> verticesName = new HashSet<>();
        for (int i = 0; i < conversions.size(); i++) {
            verticesName.add(conversions.get(i).getFrom());
            verticesName.add(conversions.get(i).getTo());
        }
        int counter = 1;
        Map<String, Integer> map = new HashMap<>();

        for (String s : verticesName) {
            if (s.equals(head)) {
                map.put(head, 0);
            } else {
                map.put(s, counter);
                counter++;
            }
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
        }

        int V = verticesName.size(); // Number of vertices in graph
        int E = conversions.size(); // Number of edges in graph
        System.out.println("V: " + V + " E: " + E);
        Utils graph = new Utils(V, E);

        //for (int i = 0; i < V; i++) {
        int i = 0;
        for (int j = 0; j < E; j++) {
            if (conversions.get(j).getFrom().equalsIgnoreCase(head)) {
                graph.edge[0].src = map.get(conversions.get(j).getFrom());
                graph.edge[0].dest = map.get(conversions.get(j).getTo());
                graph.edge[0].weight = Double.parseDouble(conversions.get(j).getRate());

                Log.d("", "src: " + graph.edge[0].src + " dest: " + graph.edge[0].dest + " weight: "
                        + graph.edge[0].weight);
            } else {
                i++;
                graph.edge[i].src = map.get(conversions.get(j).getFrom());
                graph.edge[i].dest = map.get(conversions.get(j).getTo());
                graph.edge[i].weight = Double.parseDouble(conversions.get(j).getRate());

                Log.d("", "src: " + graph.edge[i].src + " dest: " + graph.edge[i].dest + " weight: "
                        + graph.edge[i].weight);
            }
        }
        //}
        Double[] dist = graph.BellmanFord(graph);
        if (dist[map.get(convertTo)] != null){
            return dist[map.get(convertTo)];
        } else {
            return 0.0;
        }
    }

    // A class to represent a weighted edge in graph
    class Edge {
        int src;
        int dest;
        double weight;

        Edge() {
            src = dest = 0;
        }
    }

    //class Graph {
    // Creates a graph with V vertices and E edges
    private Utils(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[e];
        for (int i = 0; i < e; ++i)
            edge[i] = new Edge();
    }

    private Double[] BellmanFord(Utils graph) {
        int V = graph.V, E = graph.E;
        Double dist[] = new Double[V];

        // Step 1: Initialize distances from src to all other
        // vertices as INFINITE
        for (int i = 0; i < V; ++i)
            dist[i] = (double) Integer.MAX_VALUE;
        dist[0] = (double) 0;

        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        for (int i = 1; i < V; ++i) {
            for (int j = 0; j < E; ++j) {
                int u = graph.edge[j].src;
                int v = graph.edge[j].dest;
                double weight = graph.edge[j].weight;
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v])
                    dist[v] = (Double) (dist[u] + weight);
            }
        }

        // Step 3: check for negative-weight cycles. The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        // path, then there is a cycle.
        for (int j = 0; j < E; ++j) {
            int u = graph.edge[j].src;
            int v = graph.edge[j].dest;
            double weight = graph.edge[j].weight;
            if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v])
                System.out.println("Graph contains negative weight cycle");
        }
        //printArr(dist, V);
        return dist;
    }
    //}

}
