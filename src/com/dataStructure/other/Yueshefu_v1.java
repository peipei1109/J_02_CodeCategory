package com.dataStructure.other;

/*
 * 用对象链表构成的
 */

public class Yueshefu_v1 {
	
	
	public static class PNode{
		
		int num;
		PNode next;	
		public PNode(int num) {
			this.num = num;
			
		}		
	}
		
	public static PNode head=new PNode(1); //初始化头结点
	public static PNode p=head;
	public static PNode q; //中间辅助结点
	public static void initJonsh(int number){
		for(int i=2;i<=number;i++){
			q=new PNode(i);
			p.next=q;
			p=p.next;
		}
		
		p.next=head;
		p=head;
	}
	
	public static void Circle(int circle){
       int cnt=1;
		while(p.next!=p){
			q=p;
			p=p.next;
			cnt++;
			if(cnt==circle){
				System.out.print(p.num+"|");
				q.next=p.next;
				p=p.next;
				cnt=1;
			}
		}
		System.out.println(p.num);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		initJonsh(7);
		Circle(3);

	}

}
