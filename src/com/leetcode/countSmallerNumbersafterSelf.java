package com.leetcode;

import java.util.Arrays;
import java.util.List;

public class countSmallerNumbersafterSelf {
	
	
	
	class TreeNode{
        int smallCount;
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode(int count, int val){
            this.smallCount = count; //后来插入的节点中，有多少各比当前节点小的。
            this.val = val;
        }
    }
    
    public List<Integer> countSmaller(int[] nums) {
        TreeNode root = null;
        Integer[] ret = new Integer[nums.length];
        if(nums == null || nums.length == 0) return Arrays.asList(ret);
        for(int i=nums.length-1; i>=0; i--){
            root = insert(root, nums[i], ret, i, 0);
        }
        return Arrays.asList(ret);
    }
    
    public TreeNode insert(TreeNode root, int val, Integer[] ans, int index, int preSum){ //preSum, 之前插入的节点有多少比当前小的，即我们所求的。
        if(root == null){
            root = new TreeNode(0, val);
            ans[index] = preSum;
        }
        else if(root.val>val){
            root.smallCount++;
            root.left = insert(root.left, val, ans, index, preSum);
        }
        else{
            root.right = insert(root.right, val, ans, index, root.smallCount + preSum + (root.val<val?1:0));//only adding 1 on preSum if root.val is only smaller than val
        }
        return root;
    }
    
    public static void main(String[] args) {
    	
    	countSmallerNumbersafterSelf csnas=new countSmallerNumbersafterSelf();
        int [] arr={3,2,2,6,1};
    	List<Integer> res=csnas.countSmaller(arr);
    	for(Integer tmp:res){
            System.out.println(tmp);
        }
    	
    	
	}

}
