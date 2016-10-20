package com.dataStructure.graph;
/*
 * 有向图用拓扑排序检测是否有环~
 * AOV网（Activity On Vertex）：即顶点表示活动，弧表示活动间的优先关系的有向图
 */
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Stack;


public class TopologicalSort {
	private static HashMap<Integer, HashMap<Integer, EdgeInfo>> edges = new HashMap<Integer, HashMap<Integer, EdgeInfo>>();
	private static HashMap<Integer, Integer> nodes = new HashMap<Integer, Integer>(); //special nodes
	private static int nodeNum = -1; //special nodes 的个数
	private static  boolean[] visited;
	private static int[] indegree=new int[10];
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
			indegree[targetId]++;
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
	
	public static void Topological(HashMap<Integer, HashMap<Integer, EdgeInfo>> G) {
		int count=0;int p;
		Stack<Integer> S=new Stack<>();
		for(int i=1;i<nodeNum+1;i++){
			if(indegree[i]==0) S.push(i);			
		}
		while(!S.isEmpty()){
			p=S.pop();
			count++;
			System.out.print(p+"->");
			if(G.get(p)!=null){
			for(java.util.Map.Entry<Integer, EdgeInfo> entry: G.get(p).entrySet()){
				int k=entry.getKey();
				if((--indegree[k])==0)S.push(k);
			}
		}
		}
		
		if(count<nodeNum)System.out.println("有环！");
		else System.out.println("无环！");
		
	}

	
	public static void main(String[] args) throws NumberFormatException, IOException {
		String graphFilePath = "src/com/graph/topological.csv";
		String graphContent = FileUtil.read(graphFilePath, null);
		readEdges(graphContent);		
		Topological(edges);

	
	}


	
	

}
