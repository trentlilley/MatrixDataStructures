package com.study.tlilley;

import java.util.Arrays;

/**
 * This program uses the Graph Class to convert a list of edges to each of the three graph data structures directly
 * Three static methods below the main method are used to convert between the graph representations
 * Compare the expected results printed by the Graph Class to the results printed by the static methods below main()
 * Explanations for time complexities are given within the comments above each static class
 * Test different combinations of edges by adjusting the values in the edges array at the top of main()
 * Refer to the Graph class description for details on supported graph/edge specifications
 *
 * NOTE: order of the edges printed in incidence matrix may not be consistent
 *       edges are printed above each column of the incidence matrix for clarity
 *
 * @Author: Trent Lilley
 */
public class Main {

    public static void main(String[] args) {
        // insert edges
        int[][] edges = {{0, 1}, {1, 2}, {2, 4}, {0, 4}, {4, 3}, {3, 0}};

        System.out.println("EXPECTED RESULTS FOR GIVEN EDGES");
        System.out.println(" ");

        // print expected results for given values in edges
        Graph graph = new Graph(edges);
        graph.printAdjMatrix();
        graph.printAdjList();
        graph.printIncMatrix();

        System.out.println("");
        System.out.println("END EXPECTED RESULTS");
        System.out.println("--------------------------------------");

        // start conversions
        adjMatrixToAdjList(graph.getAdjMatrix());
        adjListToIncMatrix(graph.getAdjList());
        incMatrixToAdjList(graph.getIncMatrix());
    }


    /** Converts an adjacency list to an incidence matrix
     * Algorithm time complexity depends on O(number of vertices) * O(number of vertices)
     * Time complexity: O(n^2)
     * */
    public static void adjMatrixToAdjList(int[][] adjMatrix) {
        SinglyLinkedList<Integer>[] adjList = new SinglyLinkedList[adjMatrix.length];

        // scan for 1's
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int k = 0; k < adjMatrix[i].length; k++) {
                if (adjMatrix[i][k] == 1) {
                    if (adjList[k] == null) adjList[k] = new SinglyLinkedList<>();
                    adjList[k].addLast(i);
                }
            }
        }
        // print resulting adjList
        System.out.println("Adjacency Matrix converted to Adjacency List...");
        for (int i = 0; i < adjList.length; i++) {
            System.out.println(i + ": " + adjList[i]);
        }
        System.out.println("");
    }

    /**Converts an adjacency list to an incidence matrix
     * collecting the number of edges in the adjacency list takes O(number of vertices) * O(number of items in each linked list)
     * reconstructing the edges also takes O(number of vertices) * O(number of items in each linked list)
     * creating the incidence matrix takes O(number of vertices) * O(number of edges)
     * Time complexity: O(n*m)
     * */
    public static void adjListToIncMatrix(SinglyLinkedList<Integer>[] adjList) {
        int[][] edges;
        int[][] incMatrix;
        int numberOfEdges = 0;
        int singleVertexEdges = 0;

        // get number of edges
        for (int i = 0; i < adjList.length; i++) {
            for (Integer item: adjList[i]) {
                if (item == i) singleVertexEdges++;
                else numberOfEdges++;
            }
        }

        // exclude duplicate edges from the count by halving
        numberOfEdges = numberOfEdges / 2;

        // add back the single vertex edges
        numberOfEdges = numberOfEdges + singleVertexEdges;

        // reconstruct edges
        edges = new int[numberOfEdges][2];
        int edgeInd = 0;
        for (int i = 0; i < adjList.length; i++) {
            for (int k = 0; k < adjList[i].size(); k++) {
                // excludes repeated edge values, ex: adds (0, 1) but not (1, 0) where (i, adjList[i].getValue(k))
                if (i <= adjList[i].getValue(k)) {
                    edges[edgeInd][0] = i;
                    edges[edgeInd][1] = adjList[i].getValue(k);
                    edgeInd++;
                }
            }
        }

        // create incidence matrix
        incMatrix = new int[adjList.length][numberOfEdges];
        for (int i = 0; i < incMatrix.length; i++) {
            for (int k = 0; k < incMatrix[i].length; k++) {
                if (edges[k][0] == i || edges[k][1] == i) incMatrix[i][k] = 1;
            }
        }

        // print resulting incidence matrix
        System.out.println("Adjacency List converted to Incidence Matrix...");
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
        System.out.println("");
    }

    /**Converts an Incidence Matrix to Adjacency List
     * reconstructing the edges takes O(number of vertices) * O(number of edges)
     * creating the adjacency list takes O(number of edges * 2)
     * Time Complexity: O(n*m)
     * */
    public static void incMatrixToAdjList(int[][] incMatrix) {
        SinglyLinkedList<Integer>[] adjList = new SinglyLinkedList[incMatrix.length];
        int[][] edges;

        // every column in the incidence matrix represents one edge
        edges = new int[incMatrix[0].length][2];

        // reconstruct edges
        int rowInd = 0;
        int colInd = 0;
        int connectionCount = 0;
        int edgeCount = 0;

        while (colInd < incMatrix[0].length) {
            while (rowInd < incMatrix.length) {
                // every col has one or two 1's, the rows these 1's are in represents the edge
                if (incMatrix[rowInd][colInd] == 1) {

                    // add to first position in edge coordinate
                    if (connectionCount == 0) {
                        edges[edgeCount][0] = rowInd;
                    }
                    // add to second position in coordinate
                    if (connectionCount == 1) {
                        edges[edgeCount][1] = rowInd;
                        edgeCount++;
                    }
                    connectionCount++;
                }
                rowInd++;
            }
            // if only a single 1 was observed, this means the edge connects to only one vertex
            // set second coord of edge equal to the first coord
            if (connectionCount == 1) {
                edges[edgeCount][1] = edges[edgeCount][0];
                edgeCount++;
            }
            // adjust values for next outer loop iteration
            connectionCount = 0;
            rowInd = 0;
            colInd++;
        } // end loops

        // use edges to create adjacency list
        for (int i = 0; i < edges.length; i++) {
            // initialize linked lists if they have not been already
            if (adjList[edges[i][0]] == null) adjList[edges[i][0]] = new SinglyLinkedList<Integer>();
            if (adjList[edges[i][1]] == null) adjList[edges[i][1]] = new SinglyLinkedList<Integer>();
            adjList[edges[i][0]].addLast(edges[i][1]);
            adjList[edges[i][1]].addLast(edges[i][0]);
        }

        // print the resulting adjacency list
        System.out.println("Incidence Matrix converted to Adjacency List...");
        for (int i = 0; i < adjList.length; i++) {
            System.out.println(i + ": " + adjList[i]);
        }
        System.out.println("");
    }

}

