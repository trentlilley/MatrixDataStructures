package com.study.tlilley;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Graph that supports multiple methods of representation
 * Assumes graph is undirected and unweighted
 * Assumes vertices are labeled from 0 to N where N is any integer > 0 and that the number of vertices > 0
 * Edges automatically formatted with the lesser value node as the first coordinate
 * Supports edges connecting to a single vertex
 *
 * int[][] adjMatrix: the adjacency matrix representation of the graph
 * SinglyLinkedList[] adjList: the adjacency list representation of the graph
 * int[][] incMatrix: the incidence matrix representation of the graph
 * int[][] edges: reformatted edges representing connections between vertices passed through the constructor
 * int vertices: number of vertices in the graph
 *
 * @Author: Trent Lilley
 */
public class Graph {
    private int[][] adjMatrix;
    private SinglyLinkedList<Integer>[] adjList;
    private int[][] incMatrix;
    int[][] edges;
    int vertices;

    // constructor: edges are formatted [[v1, v2], [v2,v3], [v5, v6], etc..] int[e][2]
    // makes all three graph representations using these edges
    public Graph(int[][] edges) {

        // verify edges
        if (edges.length == 0) throw new RuntimeException("No edges detected");
        this.edges = edges;
        reformatEdges();
        System.out.println("edges accepted: " + Arrays.deepToString(this.edges));

        // sets number of vertices
        computeVertices();
        System.out.println(" ");

        // creates an adjacency matrix from the edges
        adjMatrix = new int[vertices][vertices];
        createAdjMatrix();

        // creates an adjacency list from the edges
        adjList = new SinglyLinkedList[vertices];
        createAdjList();

        // creates an incidence matrix from the edges
        incMatrix = new int[vertices][edges.length];
        createIncMatrix();
    }

    // reformat edges so that smaller value node always comes first
    private void reformatEdges() {

        for (int i = 0; i < edges.length; i++) {
            if (edges[i][0] > edges[i][1]) {
                int tmp = edges[i][0];
                edges[i][0] = edges[i][1];
                edges[i][1] = tmp;
            }
        }
    }

    // compute the number of vertices from the given edges
    private void computeVertices() {
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < edges.length; i++) {
            for (int k = 0; k < edges[i].length; k++) {
                set.add(edges[i][k]);
            }
        }
        System.out.println("number of vertices: " + set.size());
        vertices = set.size();
    }

    // creates an adjacency matrix from the array of edges
    private void createAdjMatrix () {

        // change values to 1 when connections are found
        for (int i = 0; i < edges.length; i++) {
            adjMatrix[edges[i][0]][edges[i][1]] = 1;
            adjMatrix[edges[i][1]][edges[i][0]] = 1;
        }
    }

    // creates an adjacency list from the array of edges
    private void createAdjList () {

        // populate list tables
        for (int i = 0; i < edges.length; i++) {

            // initialize linked lists if they have not been already
            if (adjList[edges[i][0]] == null) adjList[edges[i][0]] = new SinglyLinkedList<Integer>();
            if (adjList[edges[i][1]] == null) adjList[edges[i][1]] = new SinglyLinkedList<Integer>();

            adjList[edges[i][0]].addLast(edges[i][1]);
            adjList[edges[i][1]].addLast(edges[i][0]);
        }
    }

    // creates an incidence matrix from the array of edges
    private void createIncMatrix() {
        for (int i = 0; i < incMatrix.length; i++) {
            for (int k = 0; k < incMatrix[i].length; k++) {
                if (edges[k][0] == i || edges[k][1] == i) incMatrix[i][k] = 1;
            }
        }
    }

    // returns the number of vertices in the graph
    public int size() {
        return vertices;
    }

    // returns an array containing the vertices
    public int[] getVertices() {
        int[] listOfVertices = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            listOfVertices[i] = i;
        }
        return listOfVertices;
    }

    // get the adjacency matrix
    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    // get the adjacency list
    public SinglyLinkedList<Integer>[] getAdjList() {
        return adjList;
    }

    // get the incidence matrix
    public int[][] getIncMatrix() {
        return incMatrix;
    }

    // returns the edges used to build this graph
    public int[][] getEdges() {
        return edges;
    }

    // print the adjacency Matrix
    public void printAdjMatrix() {
        System.out.println("Printing adjacency matrix...");
        for (int[] row : adjMatrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("");
    }

    // print the adjacency list
    public void printAdjList() {
        System.out.println("Printing adjacency list...");
        for (int i = 0; i < adjList.length; i++) {
            System.out.println(i + ": " + adjList[i]);
        }
        System.out.println("");
    }

    // print the incidence matrix
    public void printIncMatrix() {
        System.out.println("Printing incidence matrix...");
        for (int i = 0; i < edges.length; i++) {
            for (int k = 0; k < edges[i].length; k++) {
                System.out.print(edges[i][k]);
            }
            System.out.print(" ");
        }
        System.out.println(" edges");

        for (int[] row : incMatrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
