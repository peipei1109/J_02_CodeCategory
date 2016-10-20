package com.dataStructure.graph;

import java.awt.Event;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import com.dataStructure.graph.TopologicalSort.EdgeInfo;

/*
 * 求关键路径
 * AOE网：（Activity On Edge）即边表示活动的网
 */
public class CriticalPath {
	private static HashMap<Integer, HashMap<Integer, EdgeInfo>> edges = new HashMap<Integer, HashMap<Integer, EdgeInfo>>();
	private static HashMap<Integer, Integer> nodes = new HashMap<Integer, Integer>(); //special nodes
	private static int nodeNum = -1; //special nodes 的个数
	private static int[] indegree=new int[10];
	private static Stack<Integer> T=new Stack<>();
	private static int[] ve;
	private static int[] vl;
	
	
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
	
	
	public static boolean TopologicalOrder(HashMap<Integer, HashMap<Integer, EdgeInfo>> G){
		int count=0;int p;
		ve=new int[nodeNum+1];
		Stack<Integer> S=new Stack<>();
		for(int i=1;i<nodeNum+1;i++){
			if(indegree[i]==0) S.push(i);			
		}
		while(!S.isEmpty()){
			p=S.pop();T.push(p);++count;
			if(G.get(p)!=null){
			for(java.util.Map.Entry<Integer, EdgeInfo> entry: G.get(p).entrySet()){
				int k=entry.getKey();
				if((--indegree[k])==0)S.push(k);
				if(ve[p]+entry.getValue().cost>ve[k]) ve[k]=ve[p]+entry.getValue().cost;
			}
		}
		}
		
		if(count<nodeNum){
			System.out.println("有环！");
			return false;
		}
		else {
			System.out.println("无环！");
			return true;
		}
	
		
		
	}
	
	public static void CriticalActivities(HashMap<Integer, HashMap<Integer, EdgeInfo>> G){
		if(!TopologicalOrder(G)){
			System.out.println("网络有环~");
		}
		vl=new int[nodeNum+1];
		int j;
		for(int i=0;i<nodeNum+1;i++){
			vl[i]=ve[nodeNum];
		}
		while (!T.isEmpty()) {
			j=T.pop();
			if(G.get(j)!=null){
			for(java.util.Map.Entry<Integer, EdgeInfo> entry: G.get(j).entrySet()){
				int k=entry.getKey();
				if(vl[k]-entry.getValue().cost<vl[j]) vl[j]=vl[k]-entry.getValue().cost;
			}
			
		}
		}
		
		for(int p=0;p<nodeNum+1;p++){
			int ee,el; char tag;
			if(G.get(p)!=null){
				for(java.util.Map.Entry<Integer, EdgeInfo> entry: G.get(p).entrySet()){
					int k=entry.getKey();
					ee=ve[p];el=vl[k]-entry.getValue().cost;
					tag=(ee==el)?'*':' ';
					System.out.println(p+","+k+","+entry.getValue().cost+","+ee+","+el+","+tag);
					
				}
		}
		}
		
	}
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		String graphFilePath = "src/com/graph/AOE.csv";
		String graphContent = FileUtil.read(graphFilePath, null);
		readEdges(graphContent);		
		CriticalActivities(edges);

	
	}

}
