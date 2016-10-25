package com.dataStructure.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import com.dataStructure.graph.MiniumSpanningTree.EdgeInfo;

public class ArticulationPoints {
	private static HashMap<Integer, HashMap<Integer, EdgeInfo>> edges = new HashMap<Integer, HashMap<Integer, EdgeInfo>>();
	private static HashSet<Integer> nodes = new HashSet<>(); // special nodes
	private static int nodeNum = -1; // special nodes 的个数
	private static int[] visited;
	private static int[] low;
	private static int min=0;
	private static int count;
	private static HashSet<Integer> setNodes=new HashSet<Integer>();

	private static ArrayList<Integer> listNodes = new ArrayList<>();

	public static class EdgeInfo {

		public int linkId;
		public int cost;

		public EdgeInfo(int linkId, int cost) {
			this.linkId = linkId;
			this.cost = cost;
		}
	}

	public static void readEdges(String graphContent)
			throws NumberFormatException, IOException {
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

	public static void FindArticul(
			HashMap<Integer, HashMap<Integer, EdgeInfo>> G) {
		visited = new int[nodeNum + 1];
		low = new int[nodeNum + 1];
		count = 1;
		visited[1] = 1;
		//其实这个循环没必要，因为在new的时候默认为0了~
		 for(int i=2;i<nodeNum+1;i++){
		 visited[i]=0;
		 }

		if (G.get(1) != null) {
			Iterator iter = G.get(1).entrySet().iterator();
			if (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				int v = (int) entry.getKey();
				EdgeInfo info = (EdgeInfo) entry.getValue();
				DFSArticul(G, v);
				if (count < nodeNum) {
					setNodes.add(1);
					while (iter.hasNext()) {
						Map.Entry entry1 = (Map.Entry) iter.next();
						int v1 = (int) entry.getKey();
						if (v1!=v&&visited[v1] == 0)
							DFSArticul(G, v1);
					}
				}
			}
		}

	}

	private static void DFSArticul(
			HashMap<Integer, HashMap<Integer, EdgeInfo>> G, int v) {
		visited[v] = min = ++count;
		if (G.get(v) != null) {
			Iterator iter = G.get(v).entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				int w = (int) entry.getKey();
				if(visited[w]==0){
					DFSArticul(G, w);
					if(low[w]<min)min=low[w];
					if(low[w]>=visited[v]) setNodes.add(v);
				}else if(visited[w]<min) min=visited[w];
				
			}
		}
     low[v]=min;
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		String graphFilePath = "src/com/graph/articulationppints.csv";
		String graphContent = FileUtil.read(graphFilePath, null);
		readEdges(graphContent);
		System.out.println(
				edges.size() + "," + edges.get(1).size() + "," + edges.get(2).size() + "," + edges.get(3).size() + ","
						+ edges.get(4).size() + "," + edges.get(5).size() + "," + edges.get(6).size());
		FindArticul(edges);
		System.out.println(setNodes);
	
	}

}