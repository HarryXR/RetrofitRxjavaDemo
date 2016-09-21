/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.test.rxjava.retrofit;

import java.io.Serializable;
import java.util.List;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/9/19.
 */
public class MovieResponse implements Serializable {
     String id;
    String alt;
     String year;
     String title;
     String original_title;
     List<String> genres;
     List<Cast> casts;
     List<Cast> directors;
     Avatars images;

    private class Cast implements Serializable{
         String id;
         String name;
         String alt;
         Avatars avatars;

    }

    private class Avatars implements Serializable{
         String small;
         String medium;
         String large;

    }
}
