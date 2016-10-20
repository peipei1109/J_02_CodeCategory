package com.dataStructure.search;

/*************************
 * 添加父节点
 * 修复 
 * 修改父节点可以修改，但是在调节完balence后，是接在父节点的左子树还是右子树则需要判断~不能想当然~~~
 * 遗留问题：没有实现AVL的删除操作，删除后还是需要调整的~~·
 *
 */

/*
 * Balanced Binary Tree or Heighte-Balenced Tree，又称AVL树
 * 建立平衡二叉树的过程涉及到旋转等问题，删除操作和二叉排序树一样，没什么差别
 */

public class BalanceBinarySortTree_v2 {
	/*
	 * 以下四行是平衡二叉树的一些常量与变量命名
	 */

	public static final int LH = 1;
	public static final int EH = 0;
	public static final int RH = -1;
	public static boolean taller = false;
	public static boolean flag = false;

	public static int[] a = { 98,74,57,29,75,33,62,34,62,67,100,22 };// 将要插入树里面的节点
	public static TreeNode p = new TreeNode(); // 用来保存父节点的？？~
	public static TreeNode Tree = new TreeNode(a[0]); // 初始化了树根
	public static TreeNode parentNode = new TreeNode(-1, Tree, null, 0); // 删除结点的时候创建一个带头结点的树
	public static int cnt = 1;

	public static class TreeNode {
		int data;
		TreeNode lchild;
		TreeNode rchild;
		TreeNode parent;
		int bf; // 取值范围为{EH,LH,RH}，平衡二叉树所用

		public TreeNode() {
			this.data = -1;
		}

		public TreeNode(int data) {
			this.data = data;
			this.lchild = new TreeNode();
			this.rchild = new TreeNode();
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
		lc.rchild.parent = P;
		lc.rchild = P;
		lc.parent = P.parent;
		P.parent = lc;
		P = lc;
		System.out.println(P.data);
		return P;
	}

	public static TreeNode L_Rotate(TreeNode P) {
		TreeNode rc = P.rchild;
		P.rchild = rc.lchild;
		rc.lchild.parent = P;
		rc.lchild = P;
		rc.parent = P.parent;
		P.parent = rc;
		P = rc;
		return P;
	}

	public static TreeNode InsertAVL(TreeNode T, TreeNode parent, int key) {

		if (T.data == -1) {
			T.data = key;
			T.bf = EH;
			T.lchild = new TreeNode();
			T.rchild = new TreeNode();
			T.parent = parent;
			System.out.println("!!!" + T.data);
			taller = true;
			flag = true;
		} else {
			if (T.data == key) {
				taller = false;
				return T;
			}
			if (key < T.data) {
				InsertAVL(T.lchild, T, key);
				if (flag == false) {
					return T; // 未插入
				}
				if (taller) {
					System.out.println(key + ",插在左子树~" + T.data + "," + T.lchild.data + "," + T.lchild.bf + ","
							+ T.lchild.lchild.data + "," + T.rchild.data + "," + T.bf + "," + cnt++);
					switch (T.bf) {
					case LH: // 原本左子树比右子树高，需要做左平衡处理
						int direction=0;
						System.out.println(T.parent.data);
						if(T.parent.lchild==T){
							direction=1;
						}
						T = leftBalence(T);
						if(direction==0)
						{T.parent.rchild = T;}
						else {
						T.parent.lchild=T;
						System.out.println("TEST5~~~6");
						}
						System.out.println("左平衡调整后的根节点是：" + T.data);
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
				InsertAVL(T.rchild, T, key);
				if (flag == false) {
					return T;
				}
				if (taller) {
					System.out.println("插在右子树~" + T.data);
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
						int direction=0;
						System.out.println(T.parent.data);
						if(T.parent.lchild==T){
							direction=1;
						}
						T = RightBalance(T);
						if(direction==0)
						{T.parent.rchild = T;}
						else {
						T.parent.lchild=T;
						System.out.println("TEST3~~~4");
						}
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
		TreeNode tmp = null;
		TreeNode lc = t.lchild;
		switch (lc.bf) {
		case LH:
			t.bf = lc.bf = EH;
			t = R_Rotate(t);
			System.out.println("在PPP平衡调整后的根节点是：" + t.data);
			break;
		case RH:
			System.out.println("TEST__1");
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
			tmp = L_Rotate(t.lchild);
			t.lchild = tmp;
			t = R_Rotate(t);
		}
		return t;
	}

	public static TreeNode RightBalance(TreeNode t) {
		TreeNode tmp = null;
		TreeNode rc = t.rchild;
		switch (rc.bf) {
		case RH:
			t.bf = rc.bf = EH;
			System.out.println(t.data+","+rc.data);
			System.out.println("test___2");
			t = L_Rotate(t);
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
			tmp = R_Rotate(t.rchild);
			t.rchild = tmp;
			t = L_Rotate(t);
			break;
		}
		return t;

	}

	// 返回创建按成功后的root
	public static TreeNode createAVL(TreeNode T, TreeNode parent, int[] a) {
		for (int i = 1; i < a.length; i++) {
			System.out.println("iteration：" + i + "," + T.data);
			T = InsertAVL(T, parent, a[i]);
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

	// 递归前序遍历
	public static void preOrderTravl(TreeNode T) {
		if (T.data==-1) {
			return;
		}
		System.out.print(T.data + "|");
		preOrderTravl(T.lchild);
		preOrderTravl(T.rchild);

	}
	
	// 递归后序遍历
		public static void endOrderTravl(TreeNode T) {
			if (T.data==-1) {
				return;
			}
			
			endOrderTravl(T.lchild);
			endOrderTravl(T.rchild);
			System.out.print(T.data + "|");

		}

	public static void main(String[] args) {
		Tree.parent = parentNode;
		TreeNode tree = Tree;
		tree = createAVL(tree, parentNode, a);
		// System.out.println(Tree.lchild.data+","+Tree.rchild.data);
		System.out.println("中序遍历==========");
		inOrderTravl(tree);
		System.out.println("\n前序遍历==========");
		preOrderTravl(tree);
		System.out.println("\n后序遍历==========");
		endOrderTravl(tree);

	}

}
