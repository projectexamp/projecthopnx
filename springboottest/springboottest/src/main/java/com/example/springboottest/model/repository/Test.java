package com.example.springboottest.model.repository;

import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Set<String> n = new HashSet<>() ;
        n.add("n");
        n.add("n");
        n.add("n");
        n.add("n");
        n.add("n");
        n.add("m");
        for (String s  : n ){
            System.out.println(s);
        }

    }
}
