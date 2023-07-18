package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
    private static int deep = 0;

    public static void main(String[] args) {
        String path = "..";
        printFile(path);
    }

    static void printFile(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                System.out.println(" ".repeat(deep) + file1.getName());
                deep++;
                printFile(file1.getAbsolutePath());
            } else {
                System.out.println(" ".repeat(deep) + file1.getName());
            }
        }
        deep--;
    }
}
