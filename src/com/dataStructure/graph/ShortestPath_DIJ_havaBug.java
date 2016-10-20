package com.dataStructure.graph;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ShortestPath_DIJ_havaBug {
	private static HashMap<Integer, HashMap<Integer, EdgeInfo>> edges = new HashMap<Integer, HashMap<Integer, EdgeInfo>>();
	private static HashMap<Integer, EdgeInfo> sedgs = new HashMap<>(); // 为了修复bug用的~把这个顶点到所有的其他顶点的距离都附上值，没有就付Inf，有就给真实值~
	private static HashSet<Integer> nodes = new HashSet<>(); // special nodes
	private static int nodeNum = -1; // special nodes 的个数
	private static int[] res;
	private static int[] p;

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
			HashMap<Integer, EdgeInfo> map = new HashMap<Integer, EdgeInfo>();

			if (edges.containsKey(sourceId)) {
				map = edges.get(sourceId);
			}

			boolean exist = map.containsKey(targetId);

			if (!exist || (exist && cost < map.get(targetId).cost)) {
				map.put(targetId, info);
				// System.out.println("target:"+targetId);
			}

			edges.put(sourceId, map);
			// System.out.println("sourse"+sourceId);

			// 先把所有的点放在othersnodes里面
			nodes.add(sourceId);
			nodes.add(targetId);
		}

		nodeNum = nodes.size();
	}

	public static void DIJ(HashMap<Integer, HashMap<Integer, EdgeInfo>> G, int v0) {

		sedgs = G.get(v0);// 需要把这个的size扩张成nodeNum；

		int min = 2000;
		int w = 0;
		res = new int[nodeNum + 1];
		p = new int[nodeNum + 1];
		for (int i = 0; i < nodeNum + 1; i++) {
			res[i] = 2000;
			p[i] = -1;
		}
		p[v0] = v0;
		if (G.get(v0) == null) {
			System.out.println("该顶点到其他点没有路径~");
			return;

		} else {
			for (Map.Entry<Integer, EdgeInfo> entry : G.get(v0).entrySet()) {
				int k = entry.getKey();
				int cost = entry.getValue().cost;
				p[k] = v0;
				if (cost < min) {
					min = cost;
					w = k;

				}
			}
			res[w] = min;
			nodes.remove(w);
			nodes.remove(v0);
		}
		while (!nodes.isEmpty()) {
			for (int k : nodes) {

				if (G.get(w).get(k) != null && (min + G.get(w).get(k).cost) < sedgs.get(k).cost) {
					sedgs.get(k).cost = min + G.get(w).get(k).cost;
					p[k] = w;

				}

			}
			for (Map.Entry<Integer, EdgeInfo> entry : sedgs.entrySet()) {
				int k = entry.getKey();
				int cost = entry.getValue().cost;
				if (cost < min) {
					min = cost;
					w = k;
				}
			}
			res[w] = min;
			nodes.remove(w);
		}

	}

}
