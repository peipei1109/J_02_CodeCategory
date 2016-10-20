package com.dataStructure.search;


public class BinarySortTree {
	public static int[] a = { 45, 24, 53, 45, 12, 24, 90 };
	public static TreeNode p = new TreeNode();
	public static TreeNode T = new TreeNode(a[0]);
	public static TreeNode parentNode=new TreeNode(-1,T,null); //删除结点的时候创建一个带头结点的树
	 
	

	public static class TreeNode {
		int data;
		TreeNode lchild;
		TreeNode rchild;

		public TreeNode() {
		}

		public TreeNode(int data) {
			this.data = data;
			this.lchild = null;
			this.rchild = null;
		}
		
		public TreeNode(int data, TreeNode lchild, TreeNode rchild) {
			super();
			this.data = data;
			this.lchild = lchild;
			this.rchild = rchild;
		}

	}

	// 在儿叉排序树里面插入元素V1
	public static void insertBST(TreeNode treeNode, int a) {

		if (treeNode == null) {
			treeNode = new TreeNode(a);
			System.out.println(treeNode.data);
		}

		else if (a < treeNode.data && treeNode.lchild == null) {
			treeNode.lchild = new TreeNode(a);

		} else if (a > treeNode.data && treeNode.rchild == null) {

			treeNode.rchild = new TreeNode(a);
		} else if (a < treeNode.data) {
			insertBST(treeNode.lchild, a);
		} else if (a > treeNode.data) {
			insertBST(treeNode.rchild, a);
		}

	}

	// 在儿叉排序树里面插入元素V2
	public static void insertBST_v2(TreeNode T, int a) {
		if (!SearchBST(T, null, a)) {
			TreeNode sNode = new TreeNode(a);
			if (p == null)
				T = sNode;
			else if (a < p.data)
				p.lchild = sNode;
			else if (a > p.data)
				p.rchild = sNode;
		}

	}

	// 创建二叉排序树（里面的元素都不重复，重复没意义~~）
	public static void createBST(TreeNode T, int[] a) {
		for (int i = 1; i < a.length; i++) {
			// insertBST(T, a[i]);
			insertBST_v2(T, a[i]);
			System.out.println("插人:" + a[i]);
		}

	}

	// 递归中序遍历
	public static void inOrderTravl(TreeNode T) {
		if (T == null) {
			return;
		}

		inOrderTravl(T.lchild);
		System.out.print(T.data + "|");
		inOrderTravl(T.rchild);

	}

	// 二叉树查找
	public static boolean SearchBST(TreeNode T, TreeNode f, int key) {
		if (T == null) {
			p = f;
			// System.out.println(p.data);
			return false;
		} else if (T.data == key) {
			p = T;
			// System.out.println(p.data);
			return true;
		} else if (key < T.data) {
			f = T;
			return SearchBST(T.lchild, f, key);
		} else if (key > T.data) {
			f = T;
			return SearchBST(T.rchild, f, key);
		}

		return false;
	}

	// 删除二叉树结点
	
	public static boolean DeleteBST( int key) {
	   
	    
		if (T == null)
			return false;
		else {
			if (T.data == key)
				return delete(T,parentNode);
			else if (key < T.data) {
				parentNode=T;
				T=T.lchild;
				return DeleteBST(key);
			} else {
				parentNode=T;
				T=T.rchild;
			    return DeleteBST(key);
			}
		}
		
	}

	private static boolean delete(TreeNode T,TreeNode p) {
		if (T.rchild == null) {		
			if(p.rchild==T){
				p.rchild=T.lchild;
			}else p.lchild=T.lchild;

		} else if (T.lchild == null) {
			if(p.rchild==T){
				p.rchild=T.rchild;
			}else p.lchild=T.rchild;

		} else {

			TreeNode q = T;
			TreeNode s = T.lchild;
			while (s.rchild != null) {
				q = s;
				s = s.rchild;
			}
			T.data = s.data;
			if (q != T) {
				q.rchild = s.lchild;
			} else
				q.lchild = s.lchild;
			s = null;

		}
		return true;

	}
	
	

	@SuppressWarnings("null")
	public static void main(String[] args) {

		TreeNode treeNode = T;
		createBST(treeNode, a);
		System.out.println(T.lchild.data);
		inOrderTravl(T);
		System.out.println();

		boolean flag = SearchBST(T, null, 45);
		System.out.println(flag + "," + p.data);
		TreeNode treeNode2 = T;
		DeleteBST(90); 
		inOrderTravl(treeNode2);
	}

}
