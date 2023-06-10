package graph;

import java.util.ArrayList;

/**
 * This class implements general operations on a graph as specified by
 * UndirectedGraphADT.
 * It implements a graph where data is contained in Vertex class instances.
 * Edges between verticies are unweighted and undirected.
 * A graph coloring algorithm determines the chromatic number.
 * Colors are represented by integers.
 * The maximum number of vertices and colors must be specified when the graph is
 * instantiated.
 * You may implement the graph in the manner you choose. See instructions and
 * course material for background.
 */

public class UndirectedUnweightedGraph<T> implements UndirectedGraphADT<T> {
  // private class variables here.
  private int MAX_VERTICES;
  private int MAX_COLORS;
  ArrayList<Vertex<T>> vertices;
  ArrayList<T> list;
  int numVertices;
  private boolean adjMat[][];

  /**
   * Initialize all class variables and data structures.
   */
  public UndirectedUnweightedGraph(int maxVertices, int maxColors) {
    MAX_VERTICES = maxVertices;
    MAX_COLORS = maxColors;
    vertices = new ArrayList<Vertex<T>>();
    list = new ArrayList<T>();
    numVertices = 0;
    adjMat = new boolean[MAX_VERTICES][MAX_VERTICES];

  }

  /**
   * Add a vertex containing this data to the graph.
   * Throws Exception if trying to add more than the max number of vertices.
   */
  public void addVertex(T data) throws Exception {
    if (numVertices + 1 > MAX_VERTICES) {
      throw new Exception();
    }
    list.add(data);
    Vertex<T> v = new Vertex<T>(data);
    vertices.add(v);
    ++numVertices;

  }

  /**
   * Return true if the graph contains a vertex with this data, false otherwise.
   */
  public boolean hasVertex(T data) {
    if (list.contains(data)) {
      return true;
    }
    return false;
  }

  /**
   * Add an edge between the vertices that contain these data.
   * Throws Exception if one or both vertices do not exist.
   */
  public void addEdge(T data1, T data2) throws Exception {
    if (!hasVertex(data1) && !hasVertex(data2)) {
      throw new Exception();
    }
    int index = list.indexOf(data1);
    int num = list.indexOf(data2);
    adjMat[num][index] = true;
    adjMat[index][num] = true;
  }

  /**
   * Get an ArrayList of the data contained in all vertices adjacent to the vertex
   * that
   * contains the data passed in. Returns an ArrayList of zero length if no
   * adjacencies exist in the graph.
   * Throws Exception if a vertex containing the data passed in does not exist.
   */
  public ArrayList<T> getAdjacentData(T data) throws Exception {
    ArrayList<T> arr = new ArrayList<T>();
    if (!list.contains(data)) {
      throw new Exception();
    }
    int index = list.indexOf(data);
    for (int i = 0; i < numVertices; i++) {
      if (adjMat[index][i] == true) {
        arr.add(list.get(i));
      }
    }
    return arr;
  }

  private ArrayList<Vertex<T>> getAdjacentVertices(Vertex<T> curVertex) {
    ArrayList<Vertex<T>> arr = new ArrayList<Vertex<T>>();
    T data = curVertex.getData();
    int index = list.indexOf(data);
    for (int i = 0; i < numVertices; i++) {
      if (adjMat[index][i] == true) {
        arr.add(vertices.get(i));
      }
    }
    return arr;
  }

  /**
   * Returns the total number of vertices in the graph.
   */
  public int getNumVertices() {
    return numVertices;
  }

  /**
   * Returns the total number of edges in the graph.
   */
  public int getNumEdges() {
    int count = 0;
    for (int i = 0; i < numVertices; i++) {
      for (int j = 0; j < numVertices; j++) {
        if (adjMat[i][j] == true && !vertices.get(j).isVisited()) {
          ++count;

        }
      }
      vertices.get(i).setVisited(true);

    }
    return count;
  }

  private int getColorToUse(Vertex<T> curVert) throws Exception {
    int colorToUse = -1;
    boolean[] adjColorsUsed = new boolean[MAX_COLORS];
    ArrayList<Vertex<T>> adjVertsList = getAdjacentVertices(curVert);
    for (Vertex<T> curAdjc : adjVertsList) {
      if (curAdjc.getColor() != -1) {
        adjColorsUsed[curAdjc.getColor()] = true;
      }
    }
    int j = 0;
    while (adjColorsUsed[j] == true && j <= MAX_COLORS) {
      j++;
    }
    if (j > MAX_COLORS) {
      throw new Exception("all colors have been used");
    }
    colorToUse = j;
    return colorToUse;
  }

  /**
   * Returns the minimum number of colors required for this graph as
   * determined by a graph coloring algorithm.
   * Throws an Exception if more than the maximum number of colors are required
   * to color this graph.
   */
  public int getChromaticNumber() throws Exception {
    int highestColorUsed = -1;
    int colorToUse = -1;
    for (Vertex<T> curVert : vertices) {
      if (curVert.getColor() == -1) {
        colorToUse = getColorToUse(curVert);
        curVert.setColor(colorToUse);
        if (colorToUse > highestColorUsed) {
          highestColorUsed = colorToUse;
        }
      }
    }
    return highestColorUsed + 1;
  }

}