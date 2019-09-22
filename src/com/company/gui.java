package com.company;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public  class gui {
    static double[] insideSeriesData=new double[5];
    static double[] borderSeriesData=new double[5];
    static double[] outsideSeriesData=new double[5];

    /**
     * Приватный конструктор не позволяет создавать экземпляров класса
     */
    protected gui(){

    }

    /**
     *Метод добавляет данные для графиков в формате double[] для корректной работы метода необходимо передавать массивы
     * из 5 элементов (double[5])
     * @param inside - массив средних значений вычисления точки внутри множества
     * @param border - массив средних значенийточки на границе множества
     * @param outside - массив средних значений точки вне множества
     */
    public static void setSeries(double[] inside, double[] border, double[] outside){

        insideSeriesData=inside;
        borderSeriesData=border;
        outsideSeriesData=outside;
    }

    /**
     *выводит на экран окно с графиком, данные необходимо устанавливать заранее с помощью метода setSeries()
     */
    public static void draw(){
        XYSeries insideSeries = new XYSeries("Внутренняя");
        XYSeries borderSeries = new XYSeries("Пограничная");
        XYSeries outsideSeries = new XYSeries("Внешняя");

        for(int i = 100, j=0; i <= Math.pow(10,6); i*=10, j+=1){
            insideSeries.add(i, insideSeriesData[j]);
            borderSeries.add(i,borderSeriesData[j]);
            outsideSeries.add(i,outsideSeriesData[j]);
        }

        XYSeriesCollection xyDataset = new XYSeriesCollection();
        xyDataset.addSeries(insideSeries);
        xyDataset.addSeries(borderSeries);
        xyDataset.addSeries(outsideSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Зависимость времени выполнения от MAX_ITERATION",
                "MAX_ITERATION","Среднее время, мс",
                xyDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                true
        );
        XYPlot plot=(XYPlot) chart.getPlot();
        LogAxis axis=new LogAxis("MAX_ITERATION");
        plot.setDomainAxis(axis);

        plot.getRenderer().setSeriesPaint(2, new Color(46,59,45));

        JFrame frame = new JFrame("Тестовое задание");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(400,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
