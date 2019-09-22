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

    public static void main(String args[]) {
        for (int i = 0; i < 80; i++)
            for (int j = 0; j < 80; j++)
                arr[i][j] = new IsPoint();
        Date d1 = new Date();
        int x,y;
        Date d4;
        long diff2;
        for (y = -39; y < 39; y++) {
//            System.out.print("\n");
            for (x = -39; x < 39; x++) {
                Date d3 = new Date();
                boolean isStar;
                if (iterate(x/40.0f,y/40.0f) == 0) {
//                    System.out.print("*");
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
//        System.out.println(MAX_ITERATIONS);
//        // Всё что идёт на выход ниже, пойдёт в графики, я думаю нужно и общее время областей(оно кстати очень нестабильно) и среднее время точки
//        //по факту эти графики имеют одинаковые оси, просто масштаб по у немного иной, но в целом идентичны
//        System.out.println("Все точки вне фигуры: " + differenceEmpty + "мс.\nВсе точки на границе: " + differenceBorder + "мс.\nВсе точки внутри фигуры: " + differenceFilling + "мс.");
//        System.out.print("Средняя точка вне фигуры: " + differenceEmpty / (double)sizeEmpty + "мс.\nСредняя точка на границе: " + differenceBorder / (double)sizeBorder + "мс.\nСредняя точка внутри фигуры: " + differenceFilling / (double)sizeFilling + "мс.");

//        System.out.println("Outside = " + outside); // Вывод точек на границе, вдруг понадобится? Там правда теперь надо много менять
//        System.out.println("Porebrik = " + porebrik);
//        System.out.println("Inside = " + inside);
//        System.out.println("Srednee Outside = " + outside/counterOutside);
//        System.out.println("Srednee Porebrik = " + porebrik/counterPorebrik);
//        System.out.println("Srednee Inside = " + inside/counterInside);
//
//        System.out.println("counterPorebrik: " + counterPorebrik);
//
//        for (int i = 0; i < 80; i++) {
//            for (int j = 0; j < 80; j++) {
//                boolean r = false;
//                for (int k = 0; k < 293; k++) {
//                    if (porebrikAria[k][0] == i && porebrikAria[k][1] == j) r = true;
//                }
//                if (r) System.out.print("*");
//                else System.out.print(" ");
//            }
//            System.out.println();
//        }
    }
}




