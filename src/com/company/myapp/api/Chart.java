package com.company.myapp.api;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.company.myapp.gui.PersonalityTest;

public class Chart {
    /**
     * Creates a renderer for the specified colors.
     */
    private DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[]{20, 30, 15, 0});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    /**
     * Builds a category series using the provided values.
     *
     * @param titles the series titles
     * @param values the values
     * @return the category series
     */
    protected CategorySeries buildCategoryDataset(String title, double[] values) {
        String[] titles={"Extroversion","Introversion","Sensing","Intuition","Thinking","Feeling","Judging","Perceiving"};
        CategorySeries series = new CategorySeries(title);
        int k = 0;
        for (double value : values) {
            series.add(titles[k], value);
            ++k;
        }

        return series;
    }

    public ChartComponent createPieChartForm() {
        // Generate the values
        double[] values = new double[]{
                (double) PersonalityTest.countNumbers(PersonalityTest.extrovertVsIntrovertAnswersStorage, 1),
                (double)  PersonalityTest.countNumbers(PersonalityTest.extrovertVsIntrovertAnswersStorage, 0) ,
                (double)  PersonalityTest.countNumbers(PersonalityTest.sensingVsIntuitionsAnswersStorage, 1),
                (double)  PersonalityTest.countNumbers(PersonalityTest.sensingVsIntuitionsAnswersStorage, 0),
                (double)  PersonalityTest.countNumbers(PersonalityTest.thinkingVsFeelingAnswersStorage, 1),
                (double)  PersonalityTest.countNumbers(PersonalityTest.thinkingVsFeelingAnswersStorage, 0),
                (double) PersonalityTest.countNumbers(PersonalityTest.judgingVsPerceivingAnswersStorage, 1),
                (double)  PersonalityTest.countNumbers(PersonalityTest.judgingVsPerceivingAnswersStorage, 0)
        };

        // Set up the renderer
        int[] colors = new int[]{
                ColorUtil.BLUE,
                ColorUtil.GREEN,
                ColorUtil.MAGENTA,
                ColorUtil.YELLOW,
                ColorUtil.CYAN,
                ColorUtil.rgb(161,181,166),
                ColorUtil.rgb(98,70,163),
                ColorUtil.rgb(186,224,245),
        };
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(20);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        r.setGradientEnabled(true);
        r.setGradientStart(0, ColorUtil.BLUE);
        r.setGradientStop(0, ColorUtil.GREEN);
        r.setHighlighted(true);

        // Create the chart ... pass the values and renderer to the chart object.
        PieChart chart = new PieChart(buildCategoryDataset("Personality Result Chart",  values), renderer);

        // Wrap the chart in a Component so we can add it to a form
        ChartComponent c = new ChartComponent(chart);

        // Create a form and show it.
        return c;
        /*
        Form f = new Form("Budget", new BorderLayout());
        f.add(BorderLayout.CENTER, c);
        return f;*/
    }
}
