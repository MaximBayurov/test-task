package com.company;
import java.nio.Buffer;
import java.io.*;
import java.util.*;

public class Mondelbrot {

    static int BAILOUT = 16;
    static int MAX_ITERATIONS = 1000000;

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

    public static long getTimeDifference(ArrayList<Integer[]> listOfElements) {
        int sizeOfList = listOfElements.size();
        Integer[][] arrayOfCoordinates = new Integer[sizeOfList][2];
        listOfElements.toArray(arrayOfCoordinates);
        Date dateStart = new Date();
        for (Integer[] element : arrayOfCoordinates) {
            iterate(element[0]/40.0f, element[1]/40.0f);
        }
        Date dateFinish = new Date();
        return dateFinish.getTime() - dateStart.getTime();
    }

    public static double[] countTime(){

        boolean arr[][] = new boolean[80][80];

        int x,y;
        for (y = -39; y < 39; y++) {
            for (x = -39; x < 39; x++) {
                boolean isStar;
                if (iterate(x/40.0f,y/40.0f) == 0) {
                    isStar = true;
                }
                else {
                    isStar = false;
                }
                arr[y+39][x+39] = isStar;
            }
        }

        //Тут начинаются тёрки с подсчётом времени каждого типа
        ArrayList<Integer[]> empty = new ArrayList<Integer[]>();
        ArrayList<Integer[]> border = new ArrayList<Integer[]>();
        ArrayList<Integer[]> filling = new ArrayList<Integer[]>();
        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 80; j++) {
                Integer[] coords = new Integer[2];
                try {
                    if (!arr[i][j]) {
                        coords[0] = i - 39;
                        coords[1] = j - 39;
                        empty.add(coords);
//                        System.out.print(" ");
                    } else if (!arr[i + 1][j] || !arr[i - 1][j] || !arr[i][j + 1] || !arr[i][j - 1]) {
                        //на случай если нужна область вокруг
//                    } else if (!arr[i + 1][j] || !arr[i - 1][j] || !arr[i][j + 1] || !arr[i][j - 1] ||
//                            !arr[i+1][j+1] || !arr[i+1][j-1] || !arr[i-1][j+1] || !arr[i-1][j-1] ) {
                        coords[0] = i - 39;
                        coords[1] = j - 39;
                        border.add(coords);
//                        System.out.print("*");
                    } else {
                        coords[0] = i - 39;
                        coords[1] = j - 39;
                        filling.add(coords);
//                        System.out.print(" ");
                    }
                } catch (IndexOutOfBoundsException err) {
                    coords[0] = i - 39;
                    coords[1] = j - 39;
                    border.add(coords);
//                    System.out.print("*");
                }
            }
//            System.out.println();
        }

        double[] avgTime = new double[3];

        long differenceEmpty = getTimeDifference(empty);
        long differenceBorder = getTimeDifference(border);
        long differenceFilling = getTimeDifference(filling);

        avgTime[0] = differenceEmpty / (double) empty.size();
        avgTime[1] = differenceBorder / (double) border.size();
        avgTime[2] = differenceFilling / (double) filling.size();

        try(FileWriter writer = new FileWriter("Console_output.txt", true))
        {
            String text = "Количество итераций: " + MAX_ITERATIONS+'\n'+
                    "              |  Снаружи  |  На границе  |  Внутри"+'\n'+
                    "Общее время:  | " + differenceEmpty + "мс. | " + differenceBorder +
                    "мс. | " + differenceFilling + "мс."+'\n'+
                    "Среднее время:| " + differenceEmpty / (double) empty.size() +
                    "мс. | " + differenceBorder / (double) border.size() +
                    "мс. | " + differenceFilling / (double) filling.size() + "мс.\n";
            writer.write(text);

            writer.append("\n\n");

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

        return avgTime;
    }

    public static void setMAX_ITERATIONS(double newMax){
        MAX_ITERATIONS= (int) newMax;
    }

    public static void main(String args[]) {
        double[] avgEmpty= new double[5];
        double[] avgBorder= new double[5];
        double[] avgFilling= new double[5];
        double[] avgBuffer= new double[3];


        for (int i=0;i<=4;i++){
            setMAX_ITERATIONS(Math.pow(10,(i+2)));
            avgBuffer=countTime();
            avgEmpty[i]=avgBuffer[0];
            avgBorder[i]=avgBuffer[1];
            avgFilling[i]=avgBuffer[2];
        }

        gui.setSeries(avgFilling,avgBorder,avgEmpty);
        gui.draw();
    }
}




