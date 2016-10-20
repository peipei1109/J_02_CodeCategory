package com.dataStructure.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Route
{
	//sourceid,targetid, 和sourceid相连的所有边
	private static HashMap<Integer, HashMap<Integer, EdgeInfo>> edges = new HashMap<Integer, HashMap<Integer, EdgeInfo>>();
	//节点，节点，两个值一样的
	private static HashMap<Integer, Integer> nodes = new HashMap<Integer, Integer>(); //special nodes
	private static HashMap<Integer, Integer> otherNodes = new HashMap<Integer, Integer>(); //otherNodes
	private static int sourceId = -1, targetId = -1;
	private static int nodeNum = -1; //special nodes 的个数
	private static int minDis = Integer.MAX_VALUE;  
	private static ArrayList<int []> result = new ArrayList<int []>(); //distance+route
	
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
			}
			
			edges.put(sourceId, map);
			
			//先把所有的点放在othersnodes里面
			otherNodes.put(sourceId, sourceId);
			otherNodes.put(targetId, targetId);
		}
		
		nodeNum = otherNodes.size();
	}
	
	public static void readNodes(String condition) throws IOException{
		String s[] = condition.trim().split(",");
		
		sourceId = Integer.parseInt(s[0]);
		targetId = Integer.parseInt(s[1]);
		String r[] = s[2].split("\\|");
		
		nodes.put(sourceId, sourceId);
		nodes.put(targetId, targetId);
		
		otherNodes.remove(sourceId);
		otherNodes.remove(targetId);
		
		for(String str : r){
			int nodeId = Integer.parseInt(str);
			nodes.put(nodeId, nodeId);
			otherNodes.remove(nodeId);
		}
	}
	
	// node[] 是所有节点
	public static void run(int sourceId, int sum, int node[], int index) throws IOException{
		if(sourceId == targetId || nodes.isEmpty()){
			
			if(sourceId == targetId && nodes.isEmpty()){
				minDis = Math.min(sum, minDis);
				
				node[0] = sum;  
				node[1] = Route.sourceId;
				node[index + 2] = -1;
				
				result.add(node.clone());  
			}
		}
		else {
			HashMap<Integer, EdgeInfo> map = edges.get(sourceId);

			if (map != null) {
				for (Map.Entry<Integer, EdgeInfo> entry : map.entrySet()) {
					int targetId = entry.getKey();
					int cost = entry.getValue().cost;
					
					if (sum + cost < minDis) {
						if(nodes.containsKey(targetId)){
							nodes.remove(targetId);
							node[index + 2] = targetId;
							run(targetId, sum + cost, node, index + 1);
							nodes.put(targetId, targetId);
						}else if(otherNodes.containsKey(targetId)){
							otherNodes.remove(targetId);
							node[index + 2] = targetId;
							run(targetId, sum + cost, node, index + 1);
							otherNodes.put(targetId, targetId);
						}
					}
				}
			}
		}
	}
	
	public static String getPath(){
		String s = "";
		
		if(minDis != Integer.MAX_VALUE){
			for(int [] a : result){
				if(a[0] == minDis){
					for(int i = 1; a[i+1] != -1; i ++){
						int linkId = edges.get(a[i]).get(a[i+1]).linkId;
						
						s += linkId + (a[i+2] == -1 ? "\n" : "|");
					}
					
					System.out.print(s);
					break;
				}
			}
		}else{
			System.out.println("NA");
		}
		
		return s;
	}
	
    public static String searchRoute(String graphContent, String condition) throws NumberFormatException, IOException
    {
    	readEdges(graphContent);
    	readNodes(condition);
    	
    	nodes.remove(sourceId);
    	
    	run(sourceId, 0, new int[nodeNum + 2], 0);
    	String path = getPath(); 
    	
        return path;
    }
}