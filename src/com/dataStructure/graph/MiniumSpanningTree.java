package com.dataStructure.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.management.Descriptor;
import javax.swing.ListModel;

/*
 * 最小生成树
 * prim算法
 * Kruscal算法
 */
public class MiniumSpanningTree {
	private static HashMap<Integer, HashMap<Integer, EdgeInfo>> edges = new HashMap<Integer, HashMap<Integer, EdgeInfo>>();
	private static HashSet<Integer> nodes = new HashSet<>(); // special nodes
	private static int nodeNum = -1; // special nodes 的个数
	private static ArrayList<Integer> listNodes = new ArrayList<>();

	public static class EdgeInfo {

		public int linkId;
		public int cost;

		public EdgeInfo(int linkId, int cost) {
			this.linkId = linkId;
			this.cost = cost;
		}
	}

	public static void readEdges(String graphContent) throws NumberFormatException, IOException {
		String lines[] = graphContent.split("\\n");

		for (String line : lines) {
			String s[] = line.trim().split(",");
			int linkId = Integer.parseInt(s[0]);
			int sourceId = Integer.parseInt(s[1]);
			int targetId = Integer.parseInt(s[2]);
			int cost = Integer.parseInt(s[3]);

			EdgeInfo info = new EdgeInfo(linkId, cost);
			HashMap<Integer, EdgeInfo> map1 = new HashMap<Integer, EdgeInfo>();
			HashMap<Integer, EdgeInfo> map2 = new HashMap<Integer, EdgeInfo>();
			if (edges.containsKey(sourceId)) {
				map1 = edges.get(sourceId);
			}
			if (edges.containsKey(targetId)) {
				map2 = edges.get(targetId);
			}

			boolean exist1 = map1.containsKey(targetId);
			boolean exist2 = map1.containsKey(targetId);

			if (!exist1 || (exist1 && cost < map1.get(targetId).cost)) {
				map1.put(targetId, info);
			}
			if (!exist2 || (exist2 && cost < map2.get(sourceId).cost)) {
				map2.put(sourceId, info);
			}

			edges.put(sourceId, map1);
			edges.put(targetId, map2);

			nodes.add(sourceId);
			nodes.add(targetId);
		}

		nodeNum = nodes.size();
	}

	public static void MST_PRIM(HashMap<Integer, HashMap<Integer, EdgeInfo>> G) {

		int min = Integer.MAX_VALUE;
		int src = 0;
		int tar = 0;
		for (Integer v : G.keySet()) {
			for (Map.Entry entry : G.get(v).entrySet()) {
				int t = (int) entry.getKey();
				EdgeInfo info = (EdgeInfo) entry.getValue();
				if (info.cost < min) {
					min = info.cost;
					src = v;
					tar = t;
				}
			}
		}
		listNodes.add(src);
		listNodes.add(tar);
		min = Integer.MAX_VALUE;
		System.out.println(src + "," + tar);
		while (listNodes.size() != nodeNum) {
			for (Integer w : listNodes) {
				for (Map.Entry entry : G.get(w).entrySet()) {
					int t = (int) entry.getKey();
					EdgeInfo info = (EdgeInfo) entry.getValue();
					if (info.cost < min && listNodes.indexOf(t) == -1) {
						min = info.cost;
						src = w;
						tar = t;
					}
				}

			}
			listNodes.add(tar);
			System.out.println(src + "," + tar);
			min = Integer.MAX_VALUE;
		}
	}

	/*
	 * modify
	 */
	public static void MST_PRIM_v2(HashMap<Integer, HashMap<Integer, EdgeInfo>> G) {

		int min = Integer.MAX_VALUE;
		int src = 0;
		int tar = 0;
		for (Integer v : G.keySet()) {
			for (Map.Entry entry : G.get(v).entrySet()) {
				int t = (int) entry.getKey();
				EdgeInfo info = (EdgeInfo) entry.getValue();
				if (info.cost < min) {
					min = info.cost;
					src = v;
					tar = t;
				}
			}
		}
		listNodes.add(src);
		listNodes.add(tar);
		nodes.remove(src);
		nodes.remove(tar);
		min = Integer.MAX_VALUE;
		System.out.println(src + "," + tar);
		while (!nodes.isEmpty()) {
			for (Integer w : listNodes) {
				for (Integer v : nodes) {

					EdgeInfo info = (EdgeInfo) G.get(w).get(v);
					if (info!=null&&info.cost < min) {
						min = info.cost;
						src = w;
						tar = v;
					}
				}
			}

		listNodes.add(tar);
		nodes.remove(tar);
		System.out.println(src + "," + tar);
		min = Integer.MAX_VALUE;
	}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		String graphFilePath = "src/com/graph/mst.csv";
		String graphContent = FileUtil.read(graphFilePath, null);
		readEdges(graphContent);
		System.out.println(
				edges.size() + "," + edges.get(1).size() + "," + edges.get(2).size() + "," + edges.get(3).size() + ","
						+ edges.get(4).size() + "," + edges.get(5).size() + "," + edges.get(6).size());
		MST_PRIM(edges);
		System.out.println(listNodes);
		listNodes.clear();
		MST_PRIM_v2(edges);
		System.out.println(listNodes);
	}

}
