package v2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class HfmTree {
    public List<treeNode> list=new ArrayList<>();
    public void quickSort(List<treeNode> list,int low,int high){
        if (low >= high) {
            return;
        }
        treeNode pivot = list.get(low);
        int l = low;
        int r = high;
        treeNode temp;
        while (l < r) {
            while (l < r && list.get(r).data >= pivot.data) {
                r--;
            }
            while (l < r && list.get(l).data <= pivot.data) {
                l++;
            }
            if (l < r) {
                temp = list.get(l);
                list.set(l,list.get(r));
                list.set(r,temp);
            }
        }
        list.set(low,list.get(l));
        list.set(l,pivot);
        if (low < l) {
            quickSort(list, low, l - 1);
        }
        if (r < high) {
            quickSort(list, r + 1, high);
        }
    }

    public treeNode buildTree(List<treeNode> list){
        while(list.size()>1){
            quickSort(list,0, list.size()-1);
            treeNode leftNode=list.remove(0);
            treeNode rightNode=list.remove(0);
            treeNode node=new treeNode(leftNode.data+rightNode.data);
            node.left=leftNode;
            node.right=rightNode;
            list.add(node);
        }
        return list.remove(0);
    }


    public void setCode(treeNode root){
        if(root!=null){
            if(root.left!=null) {
                root.left.string = root.string+"0";
                setCode(root.left);
            }
            if(root.right!=null) {
                root.right.string = root.string+"1";
                setCode(root.right);
            }
        }
    }

    public void outLeave(treeNode root){
        if(root.left==null&&root.right==null){
            list.add(root);
            System.out.println(root.c+" : "+root.string);
        }else{
            if(root.left!=null) {
                outLeave(root.left);
            }
            if(root.right!=null) {
                outLeave(root.right);
            }
        }
    }

    public void countString(String str){
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            boolean build=true;
            for(int j=0;j<list.size();j++){
                if(currentChar==list.get(j).c){
                    list.get(j).data++;
                    build=false;
                }
            }
            if(build){
                treeNode node = new treeNode(1);
                node.c=currentChar;
                list.add(node);
            }
        }
    }

    public void Replace(String str){
        String reStr=str;
        for(int i=0;i<list.size();i++){
            reStr=reStr.replace(String.valueOf(list.get(i).c),list.get(i).string);
        }
        System.out.println(reStr);
    }





    public static void main(String[] args) {
        HfmTree hfmTree=new HfmTree();
        String str="do not go gentle into that good night";
        hfmTree.countString(str);
        treeNode root=hfmTree.buildTree(hfmTree.list);
        hfmTree.setCode(root);
        hfmTree.outLeave(root);
        hfmTree.Replace(str);
    }
}
class treeNode{
    public treeNode left;
    public treeNode right;
    public int data;//次数
    public char c;//字符
    public String string="";//编码
    public treeNode(int data){
        this.data=data;
    }
}