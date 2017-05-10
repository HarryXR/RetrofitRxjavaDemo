/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.anno;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.harry.R;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/11/2.
 */
public class AnnotationActivity extends Activity {
    @ViewInject(R.id.tv) //BindView
    TextView tv;
    
    @ViewClick(R.id.tv) //OnClick
    public void doClick() {
        String s = "Injected view is clicked";
        tv.setText(s);
    }
    
    @AnnotationSingle(getName = "annotation")
    String name;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        ButterKnife.bind(this);
        ViewBind.bind(this);
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getType() == String.class && field.getAnnotation(AnnotationSingle.class) != null) {
                tv.setText(field.getAnnotation(AnnotationSingle.class).getName());
            }
        }
        
        HashMap<String, String> map = new HashMap<>();
        map.put("1", "11");
        map.put("2", "22");
        map.put("3", "333");
        map.put("4", "44");
        map.put(null, null);
        map.put(null, "null00");
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            HashMap.Entry entry = (Map.Entry) iterator.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
//            Log.e(getClass().getSimpleName(),"key="+key+"//value="+value);
        }
        for (HashMap.Entry entry : map.entrySet()) {

//            Log.e(getClass().getSimpleName(),"key="+entry.getKey()+"//value="+entry.getValue());
        }
        for (String key : map.keySet()) {
            Log.e(getClass().getSimpleName(), "key=" + key + "//value=" + map.get(key));
        }
        
        int[][] nums = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Log.e(getClass().getSimpleName(), "result=" + find(nums, 8));
        printOneToNthDigits(2);
        int[] args={45,67,23,57,19};
        sort(args,0,4);
        Log.e(getClass().getSimpleName(), "args=" + args[0]+"/"+args[1]+ args[2]+"/"+args[3]+ args[4]);
    }
    
    public int[] twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if (map.get(numbers[i]) != null) {
                int[] result = {map.get(numbers[i]) + 1, i + 1};
                return result;
            }
            else {
                map.put(target - numbers[i], i);
            }
        }
        int[] result = {};
        return result;
    }
    
    public boolean find(int[][] array, int target) {
        int rows = array.length;
        int columns = array[0].length;
        
        if (rows > 0 && columns > 0) {
            int row = 0;
            int column = columns - 1;
            while (row >= 0) {
                int value = array[row][column];
                if (value == target) {
                    return true;
                }
                else if (value < target) {
                    row++;
                }
                else if (value > target) {
                    column--;
                }
            }
        }
        return false;
    }
    
    /**
     * 输入数字n，按顺序打印出从1最大的n位十进制数。比如输入3，则打印出1、2、3 一直到最大的3位数即999。
     *
     * @param n 数字的最大位数
     */
    public static void printOneToNthDigits(int n) {
        // 输入的数字不能为小于1
        if (n < 1) {
            throw new RuntimeException("The input number must larger than 0");
        }
        // 创建一个数组用于打存放值
        int[] arr = new int[n];
        printOneToNthDigits(0, arr);
    }
    
    /**
     * 输入数字n，按顺序打印出从1最大的n位十进制数。
     *
     * @param n   当前处理的是第个元素，从0开始计数
     * @param arr 存放结果的数组
     */
    public static void printOneToNthDigits(int n, int[] arr) {
        // 说明所有的数据排列选择已经处理完了
        if (n >= arr.length) {
            // 可以输入数组的值
            printArray(arr);
        }
        else {
            // 对
            for (int i = 0; i <= 9; i++) {
                arr[n] = i;
                printOneToNthDigits(n + 1, arr);
            }
        }
    }
    
    /**
     * 输入数组的元素，从左到右，从第一个非0值到开始输出到最后的元素。
     *
     * @param arr 要输出的数组
     */
    public static void printArray(int[] arr) {
        // 找第一个非0的元素
        int index = 0;
        while (index < arr.length && arr[index] == 0) {
            index++;
        }
        // 从第一个非0值到开始输出到最后的元素。
        for (int i = index; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        // 条件成立说明数组中有非零元素，所以需要换行
        if (index < arr.length) {
            System.out.println();
        }
    }
    
    public int dividerAndChange(int[] args, int start, int end) {
        int key = args[start];
        while (start < end) {
            while (start < end && args[end] >= key) {
                end--;
            }
            if (start < end) {
                swap(args, start, end);
            }
            
            while (start < end && args[start] < key) {
                start++;
            }
            if (start < end) {
                swap(args, start, end);
            }
        }
        args[start] = key;
        return start;
    }
    
    private void swap(int[] args, int start, int end) {
        int temp = args[start];
        args[start] = args[end];
        args[end] = temp;
    }
    
    public void sort(int[] args, int start, int end) {
        if (end - start > 1) {
            int mid = dividerAndChange(args, start, end);
            sort(args, start, mid);
            sort(args, mid + 1, end);
        }
    }
}
