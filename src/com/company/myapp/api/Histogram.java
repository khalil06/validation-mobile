package com.company.myapp.api;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer.Orientation;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.BarChart;
import com.codename1.charts.views.BarChart.Type;
import java.util.ArrayList;
import java.util.List;

import com.codename1.charts.util.ColorUtil;
import com.company.myapp.gui.PersonalityTest;


public class Histogram extends AbstractDemoChart{

    /**
     * Returns the chart name.
     *
     * @return the chart name
     */
    public String getName() {
        return "Sales horizontal bar chart";
    }

    /**
     * Returns the chart description.
     *
     * @return the chart description
     */
    public String getDesc() {
        return "The monthly sales for the last 2 years (horizontal bar chart)";
    }

    /**
     * Executes the chart demo.
     *
     * @param context the context
     * @return the built intent
     */
    public ChartComponent execute() {
        String[] titles = new String[] { "Trait 1", "Trait 2" };
        List<double[]> values = new ArrayList<double[]>();
        values.add(new double[] {
                (double) PersonalityTest.countNumbers(PersonalityTest.extrovertVsIntrovertAnswersStorage, 1),
                (double)  PersonalityTest.countNumbers(PersonalityTest.sensingVsIntuitionsAnswersStorage, 1) ,
                (double)  PersonalityTest.countNumbers(PersonalityTest.thinkingVsFeelingAnswersStorage, 1),
                (double) PersonalityTest.countNumbers(PersonalityTest.judgingVsPerceivingAnswersStorage, 1),
        });
        values.add(new double[] {
                (double) PersonalityTest.countNumbers(PersonalityTest.extrovertVsIntrovertAnswersStorage, 0),
                (double)  PersonalityTest.countNumbers(PersonalityTest.sensingVsIntuitionsAnswersStorage, 0) ,
                (double)  PersonalityTest.countNumbers(PersonalityTest.thinkingVsFeelingAnswersStorage, 0),
                (double) PersonalityTest.countNumbers(PersonalityTest.judgingVsPerceivingAnswersStorage, 0),
        });
        int[] colors = new int[] { ColorUtil.CYAN, ColorUtil.BLUE };
        XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
        renderer.setOrientation(Orientation.HORIZONTAL);
        setChartSettings(renderer, "Personality Test Answers Proportions", "Personality", "Answer", 0.5,
                12.5, 0, 5, ColorUtil.GRAY, ColorUtil.LTGRAY);
        renderer.setXLabels(1);
        renderer.setYLabels(10);
        renderer.addXTextLabel(1, "E VS I");
        renderer.addXTextLabel(3, "S VS N");
        renderer.addXTextLabel(5, "F VS T");
        renderer.addXTextLabel(7, "J VS P");

        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
            seriesRenderer.setDisplayChartValues(true);
        }

        BarChart chart = new BarChart(buildBarDataset(titles, values), renderer,
                Type.DEFAULT);
        return new ChartComponent(chart);
//        return wrap("", new ChartComponent(chart));

    }
}
