package com.cqupt.project.shop.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * ━━━━━━oooo━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃stay hungry stay foolish
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 *
 * @author weigs
 * @date 2018/4/15 0015
 */
public class GsonConvertUtil {

    private final static Gson GSON = new GsonBuilder().create();

    /**
     * 将json字符串转化为实体对象
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convertJSONToObject(String data, Class<T> clazz) {
        try {
            T t = GSON.fromJson(data, clazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将实体对象转化为JSON字符串
     *
     * @param object
     * @return
     */
    public static String convertObjectToJSON(Object object) {
        try {
            return GSON.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将JSON字符串转化为list集合
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> convertJSONToArray(String data,Type clazz) {
        try {
            return GSON.fromJson(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        Person p1 = new Person("weigs", 22);
        Person p2 = new Person("dxh", 23);
        List<Person> personList = new ArrayList<>();
        personList.add(p1);
        personList.add(p2);
        System.out.println(convertObjectToJSON(personList));


    }

}

class Person {
    private String username;
    private int age;

    public Person() {
    }

    public Person(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}