package v3;

import java.io.*;
import java.util.*;

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

    public String Replace(String str){
        String reStr=str;
        for(int i=0;i<list.size();i++){
            reStr=reStr.replace(String.valueOf(list.get(i).c),list.get(i).string);
        }
        System.out.println(reStr);
        return reStr;
    }

    public String readFile(String path){
        File file=new File(path);
        FileReader fr=null;
        String dataStr="";
        try{
            fr=new FileReader(file);
            BufferedReader bfr=new BufferedReader(fr);
            String s="";
            while((s=bfr.readLine())!=null){
                dataStr+=s;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return dataStr;
    }

    public int transByte(String str){
        int length = str.length();
        int padding = 8 - (length % 8);
        if(length%8!=0) {
            for (int i = 0; i < padding; i++) {
                str += "0";
            }
        }
        byte[] bytes = new byte[str.length() / 8];
        for (int i = 0; i < bytes.length; i++) {
            String byteString = str.substring(i * 8, (i + 1) * 8);
            byte b = (byte) Integer.parseInt(byteString, 2);
            bytes[i] = b;
        }
        System.out.println("请输入压缩数据名");
        Scanner scanner=new Scanner(System.in);
        String dataName=scanner.next();
        try (FileOutputStream fos = new FileOutputStream(dataName)) {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return padding;
    }

    public void cMap(String str){
        HashMap<Character,String> codeMap=new HashMap<>();
        if(!Objects.equals(str,"")){
            codeMap.put('@',str);
        }
        for(int i=0;i<list.size();i++){
            codeMap.put(list.get(i).c,list.get(i).string);
        }
        System.out.println("请输入压缩表名");
        Scanner scanner=new Scanner(System.in);
        String mapName=scanner.next();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(mapName))) {
            oos.writeObject(codeMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String unzipData(String path){
        StringBuilder originCode=new StringBuilder();
        try{
            FileInputStream file=new FileInputStream(path);
            BufferedInputStream bfi=new BufferedInputStream(file);

            int bytesRead;
            byte[] buffer = new byte[1024];

            while ((bytesRead = bfi.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    originCode.append(String.format("%8s", Integer.toBinaryString(buffer[i] & 0xFF)).replace(' ', '0'));
                }
            }
            bfi.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        int length = originCode.length();
        int i = length - 1;
        while (i >= 0 && originCode.charAt(i) == '2') {
            i--;
        }
        return originCode.substring(0, i + 1).toString();
    }

    public static HashMap<String, Character> unzipRule(String path){
        HashMap<Character,String> map=null;
        try {
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream objI=new ObjectInputStream(file);
            map=(HashMap<Character, String>) objI.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        HashMap<String, Character> newMap = new HashMap<>();
        for (Map.Entry<Character, String> entry : map.entrySet()) {
            char key = entry.getKey();
            String value = entry.getValue();
            newMap.put(value, key);
        }
        return newMap;
    }


    public String restore(String Str, HashMap<String, Character> map) {
        boolean ifAdd=map.containsValue('@');
        String addStr="";
        if(ifAdd) {
            for (Map.Entry<String, Character> entry : map.entrySet()) {
                if (entry.getValue().equals('@')) {
                    addStr=entry.getKey();
                }
            }
        }
        int addNum=addStr.length();
        Str=Str.substring(0,Str.length()-addNum);
        int start = 0;
        StringBuilder builder = new StringBuilder();
        while (start < Str.length()) {
            for (int end = start + 1; end <= Str.length(); end++) {
                String cut = Str.substring(start, end);
                if (map.containsKey(cut)) {
                    builder.append(map.get(cut));
                    start = end;
                    break;
                }
            }
        }
        return builder.toString();
    }

    public void writeStringToFile(String content, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(content);
            writer.close();
            System.out.println("字符串已成功写入文件 " + fileName);
        } catch (IOException e) {
            System.err.println("写入文件时发生错误: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        HfmTree hfmTree=new HfmTree();
        String file="C:\\Users\\罗浩洋\\Desktop\\JAVA\\HfmTree\\";
        System.out.println("请输入0或1进行压缩或解压操作");
        Scanner scanner=new Scanner(System.in);
        int choose=scanner.nextInt();
        boolean judge = false;
        if(choose==0){
            judge=false;
        }
        if(choose==1){
            judge=true;
        }
        if(!judge) {
            System.out.println("现在进行压缩操作，请输入压缩文件名");
            String txtName=scanner.next();
            String Str=hfmTree.readFile(file+txtName);
            hfmTree.countString(Str);
            treeNode root=hfmTree.buildTree(hfmTree.list);
            hfmTree.setCode(root);
            hfmTree.outLeave(root);
            String reStr=hfmTree.Replace(Str);
            int add=hfmTree.transByte(reStr);
            String addStr="";
            if(add!=0&&add!=8){
                for(int i=0;i<add;i++){
                    addStr+="z";
                }
            }
            hfmTree.cMap(addStr);
        }
        if(judge) {
            System.out.println("现在进行解压操作，请输入解压文件名");
            String unzipDataName=scanner.next();
            String unzipStr = unzipData(file+unzipDataName);
            System.out.println("解压得到的01数据串"+unzipStr);
            System.out.println("请输入解压表名");
            String unzipMapName=scanner.next();
            HashMap<String, Character> map = unzipRule(file+unzipMapName);
            String contentToWrite = hfmTree.restore(unzipStr, map);
            System.out.println("请输入你的输出文档名");
            String fileName = scanner.next();
            hfmTree.writeStringToFile(contentToWrite, fileName);
        }
    }
}
class treeNode{
    public treeNode left;
    public treeNode right;
    public int data;
    public char c;
    public String string="";
    public treeNode(int data){
        this.data=data;
    }
}