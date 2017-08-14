package com.beta.app.utils;

import java.util.ArrayList;
import java.util.List;

import com.beta.app.Demo;

public class Generic {
    public static  <T> List<T>     newClass(Class<T>  classEntity){
        List<T>  result = new ArrayList<T>();
        T obj = null;
        try {
            obj = classEntity.newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("私有方法");
        }
        result.add(obj);
        return result;
    }
    
    public static void main(String[] args) {
        List<Demo> s = Generic.newClass(Demo.class);
        System.out.println(s.size());
        System.out.println(s.get(0) == null);
    }
}
