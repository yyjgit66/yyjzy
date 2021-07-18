package com.yyjzy.dsa.dataStructures.tree;

/**
 * 树结构
 * @author yyj
 * @since 2021年7月18日17:21:38
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {}
    public TreeNode(int val) { this.val = val; }
    public TreeNode(int val,TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }


}
