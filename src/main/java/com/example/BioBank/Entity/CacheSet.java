package com.example.BioBank.Entity;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class CacheSet {
//    public static Cache<String, String> verifyCodeCache = Caffeine.newBuilder()
//            .expireAfterWrite(10, TimeUnit.MINUTES)
//            .initialCapacity(5)
//            .maximumSize(25)
//            .build();

    public static Cache<String, String> mailVerifyCodeCache = Caffeine.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .initialCapacity(5)
            .maximumSize(25)
            .build();

    public static Cache<String, User> userCache = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .initialCapacity(10)
            .maximumSize(100)
            .build();

    public static Cache<String, Integer> userErrorFrequencyCache = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .initialCapacity(10)
            .maximumSize(100)
            .build();
}
