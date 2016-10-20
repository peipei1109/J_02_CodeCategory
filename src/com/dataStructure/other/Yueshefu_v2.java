package com.dataStructure.other;

import java.awt.List;
import java.util.ArrayList;

public class Yueshefu_v2 {
	
	public static ArrayList<Integer> list_circle = new ArrayList<Integer>();
	
	public static void initList(int number){
		for (int i=1;i<=number;i++){
			list_circle.add(i);			
		}
	}
	
	
	public static void Circle(int circle){
		int k=0;
		while(list_circle.size()>0){
			k=k+circle;
			k=k%list_circle.size()-1;
			if(k<0){//到达了链表的末尾
				
				System.out.print(list_circle.get(list_circle.size()-1)+"|");
				list_circle.remove(list_circle.size()-1);
				k=0;
			}
			else{
				System.out.print(list_circle.get(k)+"|");
				list_circle.remove(k);
				
			}
					
		}
	}
	
	public static void main(String[] args) {
		initList(7);
		Circle(3);
	}
}
