package com.dataStructure.graph;
/*
 * 连通有向图的遍历
 */

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import com.dataStructure.graph.FileUtil;

public class TraverseGragh {
	
	private static HashMap<Integer, HashMap<Integer, EdgeInfo>> edges = new HashMap<Integer, HashMap<Integer, EdgeInfo>>();
	private static HashMap<Integer, Integer> nodes = new HashMap<Integer, Integer>(); //special nodes
	private static int sourceId = -1, targetId = -1;
	private static int nodeNum = -1; //special nodes 的个数
	private static  boolean[] visited;
	
	public static class EdgeInfo {

		public int linkId;
		public int cost;
		
		public EdgeInfo(int linkId, int cost){
			this.linkId = linkId;
			this.cost = cost;
		}
	}
	
	
	public static void readEdges(String graphContent) throws NumberFormatException, IOException{
		String lines[] = graphContent.split("\\n");
		
		for(String line : lines){
			String s[] = line.trim().split(",");
			int linkId = Integer.parseInt(s[0]);
			int sourceId = Integer.parseInt(s[1]);
			int targetId = Integer.parseInt(s[2]);
			int cost = Integer.parseInt(s[3]);
			
			EdgeInfo info = new EdgeInfo(linkId, cost);
			HashMap<Integer, EdgeInfo> map = new HashMap<Integer, EdgeInfo>();
			
			if(edges.containsKey(sourceId)){
				map = edges.get(sourceId);
			}
			
			boolean exist = map.containsKey(targetId);
			
			if(!exist || (exist && cost < map.get(targetId).cost)){
				map.put(targetId, info);
				//System.out.println("target:"+targetId);
			}
			
			edges.put(sourceId, map);
			//System.out.println("sourse"+sourceId);
			
			//先把所有的点放在othersnodes里面
			nodes.put(sourceId, sourceId);
			nodes.put(targetId, targetId);
		}
		
		nodeNum = nodes.size();
	}
	
	public static void DFSTraverse(HashMap<Integer, HashMap<Integer, EdgeInfo>> G){
		visited=new boolean[nodeNum+1];
		for(int i=0;i<nodeNum+1;i++){
			visited[i]=false;  //访问标志数组初始化
		}
		for(int v=0;v<nodeNum;v++){
			if(!visited[v])
				DFS(G,v+1);
		}
	}

	public static  void DFS(HashMap<Integer, HashMap<Integer, EdgeInfo>> g, int v) {
		visited[v]=true;
		System.out.print(v+"|");
		HashMap<Integer, EdgeInfo> map=g.get(v);
		if(map!=null){
		for(Integer w:map.keySet()){
			if(!visited[w]) DFS(g,w);			
		}
		}
	}
	
	
	public static void BFSTravese(HashMap<Integer, HashMap<Integer, EdgeInfo>> G){
		ArrayDeque<Integer> queue =new ArrayDeque<>();
		visited=new boolean[nodeNum+1];
		for(int i=0;i<nodeNum+1;i++){
			visited[i]=false;  //访问标志数组初始化
		}
		for(int v=1;v<=nodeNum;v++){
			if(!visited[v]){
				visited[v]=true;
				System.out.print(v+"|");
				queue.add(v);
				while(!queue.isEmpty()){
					int u=queue.remove();
					if(G.get(u)!=null){
					for(Integer w: G.get(u).keySet()){
						if(!visited[w]){
							visited[w]=true;
							System.out.print(w+"|");
							queue.add(w);
						}
						
					}
				}
				}
			}
			
		}
		
	}
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		String graphFilePath = "src/com/graph/roads.csv"; //file [/src/com/graph/roads.csv] is not exist or cannot read!!! src前面不能有“/”
		String graphContent = FileUtil.read(graphFilePath, null);
		readEdges(graphContent);
		DFSTraverse(edges);
		System.out.println();
		BFSTravese(edges);
		
	}

}
