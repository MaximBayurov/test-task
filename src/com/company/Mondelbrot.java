package com.company;
import java.util.*;

public class Mondelbrot {

    static int BAILOUT = 16;
    static int MAX_ITERATIONS = 1000;
    static IsPoint[][] arr = new IsPoint[80][80];

    private static int iterate(float x, float y) {
        float cr = y-0.5f;
        float ci = x;
        float zi = 0.0f;
        float zr = 0.0f;
        int i = 0;
        while (true) {
            i++;
            float temp = zr * zi;
            float zr2 = zr * zr;
            float zi2 = zi * zi;
            zr = zr2 - zi2 + cr;
            zi = temp + temp + ci;
            if (zi2 + zr2 > BAILOUT)
                return i;
            if (i > MAX_ITERATIONS)
                return 0;
        }
    }

    public static void main(String args[]) {
        for (int i = 0; i < 80; i++)
            for (int j = 0; j < 80; j++)
                arr[i][j] = new IsPoint();
        Date d1 = new Date();
        int x,y;
        Date d4;
        long diff2;
        for (y = -39; y < 39; y++) {
            System.out.print("\n");
            for (x = -39; x < 39; x++) {
                Date d3 = new Date();
                boolean isStar;
                if (iterate(x/40.0f,y/40.0f) == 0) {
                    System.out.print("*");
                    d4 = new Date();
                    isStar = true;
                }
                else {
                    System.out.print(" ");
                    d4 = new Date();
                    isStar = false;
                }
                diff2 = d4.getTime() - d3.getTime();
                arr[y+39][x+39].secs = diff2/1000.0f;
                arr[y+39][x+39].star = isStar;
            }
        }
        Date d2 = new Date();
        long diff = d2.getTime() - d1.getTime();
        System.out.println("\nJava Elapsed " + diff/1000.0f);
        float outside = 0, inside = 0, porebrik = 0;
        int counterOutside = 0, counterInside = 0, counterPorebrik = 0;
        int[][] porebrikAria = new int[293][2];
        int fuck = 0;
        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 80; j++) {
                try {
                    if (!arr[i][j].star) {
                        outside += arr[i][j].secs;
                        counterOutside++;
                    } else if (!arr[i + 1][j].star || !arr[i - 1][j].star || !arr[i][j + 1].star || !arr[i][j - 1].star) {
                        porebrik += arr[i][j].secs;
                        counterPorebrik++;
                        porebrikAria[fuck][0] = i;
                        porebrikAria[fuck][1] = j;
                        fuck++;
                    } else {
                        inside += arr[i][j].secs;
                        counterInside++;
                    }
                } catch (IndexOutOfBoundsException err) {
                    porebrik += arr[i][j].secs;
                    counterPorebrik++;
                }
            }
        }

        System.out.println("Outside = " + outside);
        System.out.println("Porebrik = " + porebrik);
        System.out.println("Inside = " + inside);
        System.out.println("Srednee Outside = " + outside/counterOutside);
        System.out.println("Srednee Porebrik = " + porebrik/counterPorebrik);
        System.out.println("Srednee Inside = " + inside/counterInside);

        System.out.println("counterPorebrik: " + counterPorebrik);

        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 80; j++) {
                boolean r = false;
                for (int k = 0; k < 293; k++) {
                    if (porebrikAria[k][0] == i && porebrikAria[k][1] == j) r = true;
                }
                if (r) System.out.print("*");
                else System.out.print(" ");
            }
            System.out.println();
        }
    }
}




