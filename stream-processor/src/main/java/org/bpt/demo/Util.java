package org.bpt.demo;

import java.util.Random;

public class Util {
    public static int getRandomNumber() {
        Random ran = new Random();
        return ran.nextInt(100);
    }
}
