package com.dataStructure.search;

/*************************
*有 bug
*
*
*/

/*
 * Balanced Binary Tree or Heighte-Balenced Tree，又称AVL树
 * 建立平衡二叉树的过程涉及到旋转等问题，删除操作和二叉排序树一样，没什么差别
 */

public class BalenceBinarySortTree {
	/*
	 * 以下四行是平衡二叉树的一些常量与变量命名
	 */

	public static final int LH = 1;
	public static final int EH = 0;
	public static final int RH = -1;
	public static boolean taller = false;
	public static boolean flag=false; 

	public static int[] a = { 37,24,13 };// 将要插入树里面的节点
	public static TreeNode p = new TreeNode(); // 用来保存父节点的？？~
	public static TreeNode Tree = new TreeNode(a[0]); // 初始化了树根
	public static TreeNode parentNode = new TreeNode(-1, Tree, null,0); // 删除结点的时候创建一个带头结点的树
   public static int cnt=1;
	public static class TreeNode {
		int data;
		TreeNode lchild;
		TreeNode rchild;
		int bf; // 取值范围为{EH,LH,RH}，平衡二叉树所用

		public TreeNode() {
			this.data=-1;
		}

		public TreeNode(int data) {
			this.data = data;
			this.lchild=new TreeNode();
			this.rchild=new TreeNode();
		}

		public TreeNode(int data, TreeNode lchild, TreeNode rchild) {
			super();
			this.data = data;
			this.lchild = lchild;
			this.rchild = rchild;
		}

		public TreeNode(int data, TreeNode lchild, TreeNode rchild, int bf) {
			super();
			this.data = data;
			this.lchild = lchild;
			this.rchild = rchild;
			this.bf = bf;
		}
		
		
	}

	public static TreeNode R_Rotate(TreeNode P) {
		TreeNode lc = P.lchild;
		P.lchild = lc.rchild;
		lc.rchild = P;
		P = lc;
		System.out.println(P.data);
		return P;
	}

	public static TreeNode L_Rotate(TreeNode P) {
		TreeNode rc = P.rchild;
		P.rchild = rc.lchild;
		rc.lchild = P;
		P = rc;
		return P;
	}
 
	
	public static TreeNode InsertAVL(TreeNode T, int key) {

		if (T.data==-1) {
			T.data=key;
			T.bf=EH;
			T.lchild=new TreeNode();
			T.rchild=new TreeNode();
			System.out.println("!!!"+T.data);
			taller = true;
			flag=true;
		} else {
			if (T.data == key) {
				taller = false;
				return T;
			}
			if (key < T.data) {
				InsertAVL(T.lchild, key);
				if ( flag==false) {
					return T; // 未插入
				}
				if (taller) {
					System.out.println(key+",插在左子树~"+T.data+","+T.lchild.data+","+T.lchild.bf+","+T.lchild.lchild.data+","+T.rchild.data+","+T.bf+","+cnt++);
					switch (T.bf) {
					case LH: // 原本左子树比右子树高，需要做左平衡处理
						T=leftBalence(T);
						System.out.println("左平衡调整后的根节点是："+T.data);
						taller = false;
						break;
					case EH: // 原本左右子树等高，现因左子树增高而增高
						T.bf = LH;
						taller = true;
						break;
					case RH: // 原本右子树比左子树高，现在左右子树等高
						T.bf = EH;
						taller = false;
						break;

					default:
						break;
					}
				}
			}

			else {
				InsertAVL(T.rchild, key);
				if ( flag== false) {
					return T;
				}
				if (taller) {				
					System.out.println("插在右子树~"+T.data);
					switch (T.bf) {
					case LH:
						T.bf = EH;
						taller = false;
						break;
					case EH:
						T.bf = RH;
						taller = true;
						break;
					case RH:
						T=RightBalance(T);
						taller = false;
						break;
					default:
						break;
					}
				}
			}
		}
		return T;

	}

	public static TreeNode leftBalence(TreeNode t) {
		TreeNode lc = t.lchild;
		switch (lc.bf) {
		case LH:
			t.bf = lc.bf = EH;
			t=R_Rotate(t);
			System.out.println("在PPP平衡调整后的根节点是："+t.data);
			break;
		case RH:
			TreeNode rd = lc.rchild;
			switch (rd.bf) {
			case LH:
				t.bf = RH;
				lc.bf = EH;
				break;
			case EH:
				t.bf = lc.bf = EH;
				break;
			case RH:
				t.bf = EH;
				lc.bf = LH;
				break;
			}
			rd.bf = EH;
			L_Rotate(t.lchild);
			t=R_Rotate(t);
		}
		return t;
	}

	public static TreeNode RightBalance(TreeNode t) {
		TreeNode rc = t.rchild;
		switch (rc.bf) {
		case RH:
			t.bf = rc.bf = EH;
			t=L_Rotate(t);
			break;
		case LH:
			TreeNode ld = rc.lchild;
			switch (ld.bf) {
			case LH:
				t.bf = EH;
				rc.bf = RH;
				break;
			case EH:
				t.bf = rc.bf = EH;
				break;
			case RH:
				t.bf = LH;
				rc.bf = EH;
				break;
			}
			ld.bf = EH;
			R_Rotate(t.rchild);
			t=L_Rotate(t);
			break;
		}
		return t;
		
		

	}
	//返回创建按成功后的root
	public static TreeNode createAVL(TreeNode T,int[] a){
		for(int i=1;i<a.length;i++){
			System.out.println("iteration："+i+","+T.data);
			T=InsertAVL(T, a[i]);
		}
		
		return T;
		
	}
	
	
	// 递归中序遍历
		public static void inOrderTravl(TreeNode T) {
			if (T.data==-1) {
				return;
			}

			inOrderTravl(T.lchild);
			System.out.print(T.data + "|");
			inOrderTravl(T.rchild);

		}
	public static void main(String[] args) {
		//TreeNode t=new TreeNode(3);
		//System.out.println(t+","+t.data+","+t.lchild+t.lchild.data);
		TreeNode tree =Tree;
		tree=createAVL(tree,a);
		//System.out.println(Tree.lchild.data+","+Tree.rchild.data);
		inOrderTravl(tree);
		
		
	}

}
