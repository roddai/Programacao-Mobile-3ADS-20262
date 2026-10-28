package com.example.aula11_paises_api;

import java.util.List;

public class Country {
    public Name name;
    public List<String> capital;
    public String region;
    public long population;
    public Flags flags;

    public static class Name {
        public String common;
    }

    public static class Flags {
        public String png;
    }
}