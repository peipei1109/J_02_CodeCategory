package com.dataStructure.search;
/*
 * 折半查找,只适合有序表
 */
public class BinarySearch {
	
	
	public static void BinSearch(int [] a ,int key){
		int  low =0;
		int high =a.length-1;
		int mid;
		while(low<=high){
			mid=low+(high-low)/2 ;//之所以这么写是因为low+hight可能溢出
			if(a[mid]==key){
				System.out.println("find succes:"+(mid+1)+"," +a[mid]);
				break;
			} else if(a[mid]<key){
				low=mid+1;
			} else high = mid=1;
	
		}
		
		if(low>high){
			System.out.println("Failed!");
		}
	
	}
	
	public static void main(String[] args) {
		
		int a[] ={1,2,3,4,5,6,7};
		BinSearch(a, 8);
		
	}

}
