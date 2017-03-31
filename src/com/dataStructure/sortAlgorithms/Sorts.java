package com.dataStructure.sortAlgorithms;

import java.text.ParseException;

/*
 * 内部排序
 * // ①，直接插入排序，折半插入排序，希尔排序===》插入排序
	// ②，冒泡排序，快速排序====》快速排序
	// ③，简单选择排序，树形选择排序，堆排序====》选择排序
	// ④，归并排序
	// ⑤， 基数排序
 */

public class Sorts {

	/*
	 * 1.直接插入排序,稳定算法
	 */

	public static void InsertSort(int[] a) {
		// temp不参与排序，作为哨子兵
		int temp;
		int i, j;
		for (i = 1; i < a.length; i++) {
			if (a[i] < a[i - 1]) {
				temp = a[i];
				for (j = i - 1; j >= 0 && temp < a[j]; j--) { // 里面的判断条件是短路型的，前后顺序是有影响的，不能把
					// System.out.println(j);
					a[j + 1] = a[j];
				}
				a[j + 1] = temp;
			}
		}

		for (int k = 0; k < a.length; k++) {
			System.out.print(a[k] + (k == a.length - 1 ? "\n" : "|"));
		}
	}

	/*
	 * 2.折半插入排序,不一定稳定~~
	 */

	public static void BInsertSort(int[] a) {
		int temp;
		int i, j, high, low, m;

		for (i = 1; i < a.length; i++) {
			if (a[i] < a[i - 1]) {
				
				//low 和high的初始化需要在这里面进行~~~
				low = 0;
				high = i - 1;
				temp = a[i];
				while (low <= high) {
					m = (low + high) / 2;
					if (temp <= a[m])
						high = m - 1;
					else
						low = m + 1;
				}
				for (j = i - 1; j >= high + 1; j--) {
					a[j + 1] = a[j];
				}
					a[high + 1] = temp;
			}

		}

		for (int k = 0; k < a.length; k++) {
			System.out.print(a[k] + (k == a.length - 1 ? "\n" : "|"));
		}

	}

	/*
	 * 3.希尔排序 3.1 希尔排序的一趟排序
	 */
	public static void ShellInsert(int[] a, int k) {

		int temp, i, j;
		for (i = k; i < a.length; i += k) {
			temp = a[i];
			if (temp < a[i - k]) {
				for (j = i - k; j >= 0 && temp < a[j]; j -= k) {
					a[j + k] = a[j];
				}
				a[j + k] = temp;
			}
		}

	}
	/*
	 * 3.希尔排序 3.2 希尔排序,dk取奇数，保证最后一趟为1
	 */

	public static void ShellSort(int[] a, int dk) {
		for (int t = dk; t >= 1; t -= 2) {
			ShellInsert(a, t);
		}

		for (int k = 0; k < a.length; k++) {
			System.out.print(a[k] + (k == a.length - 1 ? "\n" : "|"));
		}
	}

	/*
	 * 4.冒泡排序 改进的冒泡排序，用python写一下，美图秀秀的题目里有这样一道题。
	 */

	public static void BubbleSort(int[] a) {
		int t = a.length - 1;
		int temp;
		boolean flag = true;
		for (int i = t; i >= 0 && flag; i = t) {
			flag = false;
			for (int j = 0; j < i; j++) {
				if (a[j] > a[j + 1]) {
					temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
					flag = true;
					t = j + 1;
				}
			}
		}
		for (int k = 0; k < a.length; k++) {
			System.out.print(a[k] + (k == a.length - 1 ? "\n" : "|"));
		}
	}

	/*
	 * 双向起泡排序.......
	 */

	public static void DBubbleSort(int[] a) {
		int pre = 0;
		int post = a.length - 1;
		int temp;

		while (pre < post) {

			// 从前往后，将最大的value沉到最后面
			for (int i = pre; i < post; i++) {
				if (a[i] > a[i + 1]) {

					temp = a[i];
					a[i] = a[i + 1];
					a[i + 1] = temp;
				}
			}

			// 从后往前，将最小的value 浮到最前面
			for (int j = post; j > pre; j--) {
				if (a[j] < a[j - 1]) {

					temp = a[j];
					a[j] = a[j - 1];
					a[j - 1] = temp;
				}
			}

			pre++;
			post--;

		}

		for (int k = 0; k < a.length; k++) {
			System.out.print(a[k] + (k == a.length - 1 ? "\n" : "|"));
		}
	}

	/*
	 * 快速排序 若是出事记录顺序按关键字有序或者基本有序时，快速排序将蜕化为冒泡排序
	 */
	public static int partion(int[] a, int low, int high) {
		int temp = a[low];
		while (low < high) {
			while (low < high && temp < a[high]) //循环里面的low<high 不可以省略
				high--;
			a[low] = a[high];
			while (low < high && temp > a[low])
				low++;
			a[high] = a[low];
		}
		a[low] = temp;
		return low;
	}

	public static void QSort(int[] a, int low, int high) {
		int pivotloc;
		if (low < high) {
			pivotloc = partion(a, low, high);
			QSort(a, low, pivotloc);
			QSort(a, pivotloc + 1, high);
		}
		for (int k = 0; k < a.length; k++) {
			System.out.print(a[k] + (k == a.length - 1 ? "\n" : "|"));
		}
	}

	/*
	 * 选择排序
	 */
	// 寻找从i到a.length里面值最小的那个
	public static int SelectMinKey(int[] a, int i) {
		int min = i;
		int temp;
		for (int j = i + 1;  j< a.length; j++) {
			if (a[min] > a[j]) {
				min=j;
			}
		}

		return min;
	} 

	public static void SelectSort(int[] a) {
		int j, temp;
		for (int i = 0; i < a.length; i++) {
			j = SelectMinKey(a, i);
			if (i != j) {
				temp = a[i];
				a[i] = a[j];
				a[j] = temp;
			}
		}

		for (int k = 0; k < a.length; k++) {
			System.out.print(a[k] + (k == a.length - 1 ? "\n" : "|"));
		}

	}

	// 大顶堆
	//s 为开始的下标，m为结束的下标
	public static void HeapAjust(int[] H, int s, int m) {
		// 顶点从0编号，因为数组从0编号
		int rc = H[s];
		for (int j = 2 * s + 1; j <=m; j = j * 2 + 1) {
			if (j <= m -1 && H[j] < H[j + 1])
				j++;
			if (rc > H[j])
				break;
			H[s] = H[j];
			s = j;
		}

		H[s] = rc;
	}

	public static void HeapSort(int[] H) {
		int temp;
		for (int i = H.length / 2 - 1 ; i >= 0; i--) {
			HeapAjust(H, i, H.length-1);
		}

		for (int j = H.length - 1; j >0; j--) {
			temp = H[j];
			H[j] = H[0];
			H[0] = temp;

			HeapAjust(H, 0, j-1);
		}

		for (int k = 0; k < H.length; k++) {
			System.out.print(H[k] + (k == H.length - 1 ? "\n" : "|"));
		}
	}

	
	
	
	/*
	 * 归并排序,稳定排序
	 */
	
	
	public static void MergeSort(int[] a) {  
        MSort(a, 0, a.length - 1); 
        for (int k = 0; k < a.length; k++) {
			System.out.print(a[k] + (k == a.length - 1 ? "\n" : "|"));
		}
    }  
  
    public static void MSort(int[] data, int left, int right) {  
        if (left >= right)  
            return;  
        
        int center = (left + right) / 2;            // 找出中间索引          
        MSort(data, left, center);                 // 对左边数组进行递归         
        MSort(data, center + 1, right);           // 对右边数组进行递归  
        Merge(data, left, center, right);        // 合并  

        //测试代码
        // System.out.println(left+","+center+","+right);
        // Print(data);  
    }  
  
 
    public static void Merge(int[] data, int left, int center, int right) {  
        
        int[] tmpArr = new int[data.length];  // 临时数组  
        int mid = center + 1;           // 右数组第一个元素索引          
        int third = left;  // third 记录临时数组的索引         
        int tmp = left;  // 缓存左数组第一个元素的索引  
        while (left <= center && mid <= right) {  
        	// 从两个数组中取出最小的放入临时数组  
            if (data[left] <= data[mid]) {  
                tmpArr[third++] = data[left++];  
            } else {  
                tmpArr[third++] = data[mid++];  
            }  
        }  
        // 剩余部分依次放入临时数组（实际上两个while只会执行其中一个）  
        while (mid <= right) {  
            tmpArr[third++] = data[mid++];  
        }  
        while (left <= center) {  
            tmpArr[third++] = data[left++];  
        }  
        // 将临时数组中的内容拷贝回原数组中  
        // （原left-right范围的内容被复制回原数组）  
        while (tmp <= right) {  
            data[tmp] = tmpArr[tmp++];  
        }  
    }  
  
    
	/*
	 * 基数排序没有实现
	 */
	
	
	/**
	 * 打印函数
	 */
	public static void Print(int[] data) {  
		for (int k = 0; k < data.length; k++) {
			System.out.print(data[k] + (k == data.length - 1 ? "\n" : "|"));
		} 
    }  
	
	

	public static void main(String[] args) {
		int a[] = { 49, 38, 65, 97, 76, 13, 27, 49, 100, 2, 3, 24 };
		
		//需要依次测试，因为每运行一次排序，a的内容就变了（即a变成了有序的啦，排序函数没起作用啦）
		// InsertSort(a);
//		BInsertSort(a);
//		 ShellSort(a, 5);
		// BubbleSort(a);
		// DBubbleSort(a);
//		 QSort(a, 0, a.length - 1);
//		 SelectSort(a);
		 HeapSort(a);
//		 MergeSort(a);
	}
}
