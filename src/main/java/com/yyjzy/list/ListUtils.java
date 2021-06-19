package com.yyjzy.list;

import java.util.List;

/**
 * 生成容易上手的字符串操作
 * @author yyj
 *
 */
public class ListUtils {

    /**
     * 查找字符串currentDate是否在workindList集合中
     * @param currentDate
     * @param workindList
     * @return
     */
    public static String contains(List<String> workindList,String currentDate){
        if(null == currentDate || "".equals(currentDate)
                || null==workindList || workindList.size()==0)return null;
        int start = 0;
        int end = workindList.size()-1;
        // 先将workindList 排序
         quickSort(workindList,start,end);
        // 通过二分查找  如果找到返回currentDate  没找到返回空
        return  binarySearch(currentDate, workindList, start, end);
    }


    /**
     * 快速排序
     * @param workindList
     * @param low
     * @param heigh
     */
    public static void quickSort(List<String> workindList,int low,int heigh){
        int i = low;
        int j = heigh;
        String middle = workindList.get(i);
        // 计算基准值
        while(i<j){
            while(i<j && workindList.get(j).compareTo(middle)>=0)j--;
            if(i<j){
                workindList.add(i++,workindList.get(j));
                workindList.remove(i);
            }
            while(i<j && workindList.get(i).compareTo(middle)<=0)i++;
            if(i<j){
                workindList.add(j,workindList.get(i));
                workindList.remove(j+1);
                j--;
            }
        }
        workindList.remove(i);
        workindList.add(i,middle);
        if(low<i-1){
            quickSort(workindList,low,i-1); // 递归处理左子区
        }

        if(heigh>j+1){
            quickSort(workindList,j+1,heigh); // 递归处理右子区
        }
    }

    /**
     * 二分查找
     * @param data
     * @param workindList
     * @param start
     * @param end
     * @return
     */
    public static String binarySearch(String data,List<String> workindList,int start,int end){
        if(start>end)return null;

        int middle = (start+end)/2;

        if(workindList.get(middle).equals(data)) return data;

        if(workindList.get(middle).compareTo(data)>0){
            return binarySearch(data,workindList,start,middle-1);
        }
        return  binarySearch(data,workindList,middle+1,end);
    }
}
