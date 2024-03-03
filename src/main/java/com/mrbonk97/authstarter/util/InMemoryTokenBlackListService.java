package com.mrbonk97.authstarter.util;

import java.util.HashSet;
import java.util.Set;

public class InMemoryTokenBlackListService {
    private static final Set<String> blackList = new HashSet<>();
    public static void addToBlockList(String token) {
        blackList.add(token);
    }
    public static boolean isBlacklisted(String token) {
        return blackList.contains(token);
    }
}
