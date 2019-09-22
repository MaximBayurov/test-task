package com.company;
import java.util.*;

public class Mondelbrot {

    static int BAILOUT = 16;
    static int MAX_ITERATIONS = 1000000;
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

    public static double[] countTime(){
        double avgTime[]=new double[3];

        for (int i = 0; i < 80; i++)
            for (int j = 0; j < 80; j++)
                arr[i][j] = new IsPoint();
        Date d1 = new Date();
        int x,y;
        Date d4;
        long diff2;
        for (y = -39; y < 39; y++) {
            for (x = -39; x < 39; x++) {
                Date d3 = new Date();
                boolean isStar;
                if (iterate(x/40.0f,y/40.0f) == 0) {
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
        //Тут начинаются тёрки с подсчётом времени каждого типа
        ArrayList<Integer[]> empty = new ArrayList<Integer[]>();
        ArrayList<Integer[]> border = new ArrayList<Integer[]>();
        ArrayList<Integer[]> filling = new ArrayList<Integer[]>();
        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 80; j++) {
                Integer[] coords = new Integer[2];
                try {
                    if (!arr[i][j].star) {
                        coords = new Integer[2];
                        coords[0] = i - 39;
                        coords[1] = j - 39;
                        empty.add(coords);
                    } else if (!arr[i + 1][j].star || !arr[i - 1][j].star || !arr[i][j + 1].star || !arr[i][j - 1].star) {
                        coords = new Integer[2];
                        coords[0] = i - 39;
                        coords[1] = j - 39;
                        border.add(coords);
                    } else {
                        coords = new Integer[2];
                        coords[0] = i - 39;
                        coords[1] = j - 39;
                        filling.add(coords);
                    }
                } catch (IndexOutOfBoundsException err) {
                    coords[0] = i - 39;
                    coords[1] = j - 39;
                    border.add(coords);
                }
            }
        }
//        empty.forEach((e) -> System.out.print(e[0] + "" + e[1]) ); // просто интересная штука
        int sizeEmpty = empty.size();
        Integer[][] emptyDots = new Integer[sizeEmpty][2]; // Создаём массив размера динамического листа
        empty.toArray(emptyDots);                             // Кладём туда инфу из динамчиеского листа
        // Всё это делали для того, чтобы не выводить из динамического листа, т.к. он выводит вроде бы менее охотно
        Date dateStart = new Date(); // !!! Можно сделать функцию и вызвать её три раза !!!
        for (Integer[] element : emptyDots) {
            iterate(element[0]/40.0f, element[1]/40.0f); // Игнорирование вывода, подсчёт времени после нахождения групп,
        }                                    //а также преобразование динамического листа - идёт ради чистоты времени
        Date dateFinish = new Date();
        long differenceEmpty = dateFinish.getTime() - dateStart.getTime();

        int sizeBorder = border.size();
        Integer[][] borderDots = new Integer[sizeBorder][2];
        border.toArray(borderDots);
        dateStart = new Date();
        for (Integer[] element : emptyDots) {
            iterate(element[0]/40.0f, element[1]/40.0f);
        }
        dateFinish = new Date();
        long differenceBorder = dateFinish.getTime() - dateStart.getTime();

        int sizeFilling = filling.size();
        Integer[][] fillingDots = new Integer[sizeFilling][2];
        border.toArray(fillingDots);
        dateStart = new Date();
        for (Integer[] element : emptyDots) {
            iterate(element[0]/40.0f, element[1]/40.0f);
        }
        dateFinish = new Date();
        long differenceFilling = dateFinish.getTime() - dateStart.getTime();

        avgTime[0]=differenceEmpty / (double)sizeEmpty;
        avgTime[1]=differenceBorder / (double)sizeBorder;
        avgTime[2]=differenceFilling / (double)sizeFilling;

        return avgTime;
    }

    public static void setMAX_ITERATIONS(int newMax){
        MAX_ITERATIONS= newMax;
    }

    public static void main(String args[]) {
        double[] avgEmpty= new double[5];
        double[] avgBorder= new double[5];
        double[] avgFilling= new double[5];
        double[] avgBuffer= new double[3];


        for (int i=2;i<=6;i++){
            setMAX_ITERATIONS(10^i);
            avgBuffer=countTime();
            avgEmpty[i-2]=avgBuffer[0];
            avgBorder[i-2]=avgBuffer[1];
            avgFilling[i-2]=avgBuffer[2];
        }

        gui.setSeries(avgFilling,avgBorder,avgEmpty);
        gui.draw();
    }
}




