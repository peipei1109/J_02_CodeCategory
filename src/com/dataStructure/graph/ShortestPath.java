package com.dataStructure.graph;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import com.dataStructure.graph.ArticulationPoints.EdgeInfo;
import com.dataStructure.search.BalanceBinarySortTree_v2.TreeNode;

public class ShortestPath {

	public final static int Inf = 2000;
	public static int n = 6;
	private static HashSet<Integer> nodes = new HashSet<>(); // special nodes
	private static int[][] dist = new int[n][n];

	private static int[][] map = new int[n][n];

	private static int[][] path = new int[n][n];

	private static boolean[][] p = new boolean[n][n];
	private static int[] pre = new int[n];
	private static int[] D = new int[n];
	private static boolean[] fin = new boolean[n];

	public static void readEdges(String graphContent) throws NumberFormatException, IOException {

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				dist[i][j] = Inf;
			}
			dist[i][i] = 0;
		}
		String lines[] = graphContent.split("\\n");

		for (String line : lines) {
			String s[] = line.trim().split(",");
			int linkId = Integer.parseInt(s[0]);
			int sourceId = Integer.parseInt(s[1]);
			int targetId = Integer.parseInt(s[2]);
			int cost = Integer.parseInt(s[3]);
			dist[sourceId][targetId] = cost;

		}
	}

	public static void floyd() {
		map = dist.clone();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (map[i][j] == Inf) {
					path[i][j] = -1;// 表示 i -> j 不通
				} else {
					path[i][j] = i;// 表示 i -> j 前驱为 i
				}
			}
		}
		for (int k = 0; k < n; k++) { // 中间节点一定要放在最外层循环~参考博客http://www.cnblogs.com/hxsyl/p/3270401.html
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (!(dist[i][k] == Inf || dist[k][j] == Inf) && dist[i][j] > dist[i][k] + dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
						// path[i][k] = i; // 调试出来这句话是多余的~~~但是也可以加上吧~不妨碍结果一样的
						path[i][j] = path[k][j];
					}
				}
			}
		}
	}

	public static void DIJ(int v0) {
		int min = Inf;
		int t = v0;
		for (int v = 0; v < n; v++) {
			fin[v] = false; D[v] = dist[v0][v];pre[v]=-1;
			System.out.println(D[v] == Inf ? "" : D[v]);
			for (int w = 0; w < n; w++) p[v][w] = false;
			if (D[v] < Inf) { p[v][v0] = true; p[v][v] = true;pre[v]=v0;}
		}
		D[v0] = 0; fin[v0] = true;
		for (int i = 0; i < n; i++) {
			min = Inf;
			for (int w = 0; w < n; w++) {
				if (!fin[w]) {
					if (D[w] < min) {t = w;min = D[w];}
				}
			}
			System.out.println("min="+min+",t="+t);

			fin[t] = true;
			for (int w = 0; w < n; w++) {
				if (!fin[w] && min + dist[t][w] < D[w]) {
					
					D[w] = min + dist[t][w];
					System.out.println("min="+min+",D[w]="+D[w]+",w="+w+",t="+t);

					pre[w] = t;
					p[w] = p[t].clone();
					p[w][w] = true;

				}
			}
		}
		
		printDIJ(v0);

	}

	public static void printPath(int from, int to) {
		/*
		 * 这是倒序输出，若想正序可放入栈中，然后输出。
		 * 
		 * 这样的输出为什么正确呢？个人认为用到了最优子结构性质， 即最短路径的子路径仍然是最短路径
		 */
		System.out.println("from: " + from + ", to: " + to);
		//System.out.print(path[from][to] + " ,距离为" + dist[from][to] + "");

		while (path[from][to] != from) {
			System.out.print(path[from][to] + "|");

			to = path[from][to];
		}
	}

	public static void printDIJ(int v0) {
		for (int i = 0; i < n; i++) {
			System.out.print((D[i]==Inf? "":D[i]) + "|");
		}
		System.out.println();
		for (int i = 0; i < n; i++) {
			int t=i;
			System.out.print(i+"|");
			while(t!=v0&&t!=-1){
				System.out.print(pre[t]+"|");
				t=pre[t];
			}
			System.out.println();
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		String graphFilePath = "src/com/graph/shortest.csv";
		String graphContent = FileUtil.read(graphFilePath, null);
		readEdges(graphContent);
		//DIJ(0);
		floyd();
		printPath(0, 5);

	}

}
