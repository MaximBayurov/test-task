package com.company;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;

public  class gui {
    static ArrayList<Double> insideSeriesData=new ArrayList<Double>();
    static ArrayList<Double> borderSeriesData=new ArrayList<Double>();
    static ArrayList<Double> outsideSeriesData=new ArrayList<Double>();

    /**
     * Приватный конструктор не позволяет создавать экземпляров класса
     */
    protected gui(){

    }

    /**
     *Метод добавляет данные для графиков
     * @param inside среднее значение вычисления точки внутри множества
     * @param border среднее значение вычисления точки на границе множества
     * @param outside среднее значение вычисления точки вне множества
     */
    public static void setSeries(double inside, double border, double outside){
        Double insideAreaAvg=inside;
        Double borderAreaAvg=border;
        Double outsideAreaAvg=outside;

        if (insideSeriesData.size()<5&&borderSeriesData.size()<5&&outsideSeriesData.size()<5) {
            insideSeriesData.add(insideAreaAvg);
            borderSeriesData.add(borderAreaAvg);
            outsideSeriesData.add(outsideAreaAvg);
        }
    }

    /**
     *выводит на экран окно с графиком, данные необходимо устанавливать заранее с помощью метода setSeries()
     */
    public static void draw(){
        XYSeries insideSeries = new XYSeries("Внутренняя");
        XYSeries borderSeries = new XYSeries("Пограничная");
        XYSeries outsideSeries = new XYSeries("Внешняя");

        for(int i = 100, j=0; i <= Math.pow(10,6); i*=10, j+=1){
            insideSeries.add(i, insideSeriesData.get(j));
            borderSeries.add(i,borderSeriesData.get(j));
            outsideSeries.add(i,outsideSeriesData.get(j));
        }

        XYSeriesCollection xyDataset = new XYSeriesCollection();
        xyDataset.addSeries(insideSeries);
        xyDataset.addSeries(borderSeries);
        xyDataset.addSeries(outsideSeries);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Зависимость времени выполнения от MAX_ITERATION",
                "MAX_ITERATION","Среднее время",
                xyDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        JFrame frame = new JFrame("Тестовое задание");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(400,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
