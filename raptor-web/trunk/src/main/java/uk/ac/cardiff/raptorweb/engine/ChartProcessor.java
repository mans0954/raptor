/**
 * Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package uk.ac.cardiff.raptorweb.engine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.imageio.ImageIO;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import uk.ac.cardiff.model.report.AggregatorGraphModel;
import uk.ac.cardiff.raptorweb.model.ChartOptions;
import uk.ac.cardiff.raptorweb.model.ChartOptions.ChartType;
import uk.ac.cardiff.raptorweb.model.ChartOptions.GraphPresentation;
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
import uk.ac.cardiff.raptorweb.model.RaptorJFreeChartModel;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.TableSeries;
import uk.ac.cardiff.raptorweb.model.records.Row;

/**
 * @author philsmart
 * 
 *         Takes a chart from the MUA, and wraps it inside the current graph view technologies (trinidad, JFreeChart) graph model
 */
public class ChartProcessor {

    /** Class logger */
    private final Logger log = LoggerFactory.getLogger(ChartProcessor.class);

    /** The location of the directory within the application that reports are saved to. */
    private Resource saveDirectory;
    private Resource baseDirectory;

    /** Allows chart name flip flop, to stop the browser from rendering an image from cache. */
    boolean flipFlopChartName;

    public String getRootDirectory(String user) {
        String root = null;
        try {
            // make sure base directory exists first
            File baseGraphDirectory = saveDirectory.getFile();
            if (!baseGraphDirectory.exists())
                baseGraphDirectory.mkdir();

            // then check for the existence of the users directory within the base
            root = saveDirectory.getFile().getCanonicalPath() + "/" + user;
            File dir = new File(root);
            log.debug("Save directory for charts exists: " + dir.exists());
            if (!dir.exists())
                dir.mkdir();

        } catch (IOException e) {
            log.error("Could not create save directory for JFree charts, {}", e.getMessage());
        }
        return root;
    }

    private String getRelativePath(File dir) {
        try {
            String relative = dir.getAbsolutePath().replace(baseDirectory.getFile().getAbsolutePath(), "");
            log.debug("Absolute Path {}, base Path {}, Relative Path is {}",new Object[]{dir.getAbsoluteFile(),baseDirectory.getFile().getAbsolutePath(),relative});
            relative = relative.replaceAll("\\\\","/");
            return relative;
        } catch (IOException e) {
            log.error("Could not get relative path for file {}, {}", dir, e.getMessage());
            return "";
        }
    }

    private BufferedImage extractImage(JFreeChart chart, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = img.createGraphics();
        chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));

        g2.dispose();
        return img;

    }

    /**
     * static method to extract and retrieve a BufferedImage from a JFreeChart respecting the presentation options in the chartOptions parameter
     * 
     * @param chart
     * @param chartOptions
     * @return
     */
    public static BufferedImage extractBufferedImage(JFreeChart chart, ChartOptions chartOptions) {
        if (chartOptions.getGraphPresentation() == GraphPresentation.FANCY) {
            int padding = 5;
            return ChartProcessorHelper.buildChartDropShadow(chart.createBufferedImage(chartOptions.getImageWidth() - (padding * 2), chartOptions.getImageHeight() - (padding * 2)), padding);
        } else {
            return chart.createBufferedImage(chartOptions.getImageWidth(), chartOptions.getImageHeight());
        }
    }

    public RaptorGraphModel constructRaptorGraphModel(AggregatorGraphModel gmodel) {
        RaptorGraphModel rgraph = new RaptorGraphModel();
        log.debug("Constructing Graph from : " + gmodel);
        rgraph.setGroupLabels(gmodel.getGroupLabels());
        rgraph.setSeriesLabels(gmodel.getSeriesLabels());
        rgraph.setChartValues(gmodel.getYValues());

        return rgraph;
    }

    public RaptorJFreeChartModel constructJFreeGraph(AggregatorGraphModel gmodel, String user, ChartOptions chartOptions) {
        return doConstructJFreeGraphCategory(gmodel, chartOptions, user, null);
    }

    /**
     * Will output into the root graphs directory
     * 
     * @param gmodel
     * @param chartOptions
     * @return
     */
    public RaptorJFreeChartModel constructJFreeGraph(AggregatorGraphModel gmodel, ChartOptions chartOptions) {
        return doConstructJFreeGraphCategory(gmodel, chartOptions, "", null);
    }

    public RaptorJFreeChartModel constructJFreeGraph(GraphPresentation graphPresentation, ChartType graphType, AggregatorGraphModel gmodel, int width, int height, String filename) {
        ChartOptions chartOptions = new ChartOptions();
        chartOptions.setImageHeight(height);
        chartOptions.setImageWidth(width);
        chartOptions.setGraphPresentation(graphPresentation);
        chartOptions.setGraphType(graphType);
        chartOptions.setOrientation(ChartOptions.OrientationType.VERTICAL);
        return doConstructJFreeGraphCategory(gmodel, chartOptions, "", filename);
    }

    private RaptorJFreeChartModel doConstructJFreeGraphPie(AggregatorGraphModel gmodel, ChartOptions chartOptions, String user, String filename) {
        log.info("Creating graph {} with presentation {} (legend {}), width={} height={}", new Object[] { chartOptions.getGraphType(), chartOptions.getGraphPresentation(),
                chartOptions.getGraphPresentation().getLegend(), chartOptions.getImageWidth(), chartOptions.getImageHeight() });

        final DefaultPieDataset dataset = new DefaultPieDataset();

        for (int j = 0; j < gmodel.getSeriesLabels().size(); j++) {
            for (int i = 0; i < gmodel.getGroupLabels().size(); i++) {
                dataset.setValue(gmodel.getGroupLabels().get(i), gmodel.getYValues().get(j).get(i));
            }
        }

        final JFreeChart chart = ChartFactory.createPieChart3D("Pie Chart 3D Demo 1", dataset, true, true, false);
        return doConstructChartPresentation(chart, chartOptions, user, filename);
    }

    /**
     * Requires websession, as charts stored on file system specific to the current users home directory
     * 
     * @param gmodel
     * @param session
     * @return
     */
    private RaptorJFreeChartModel doConstructJFreeGraphCategory(AggregatorGraphModel gmodel, ChartOptions chartOptions, String user, String filename) {
        log.info("Creating graph {} with presentation {} (legend {}), width={} height={}", new Object[] { chartOptions.getGraphType(), chartOptions.getGraphPresentation(),
                chartOptions.getGraphPresentation().getLegend(), chartOptions.getImageWidth(), chartOptions.getImageHeight() });

        // construct the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        log.info("Creating dataset with {} series and {} groups", gmodel.getSeriesLabels().size(), gmodel.getGroupLabels().size());
        for (int j = 0; j < gmodel.getSeriesLabels().size(); j++) {
            for (int i = 0; i < gmodel.getGroupLabels().size(); i++) {
                dataset.setValue(gmodel.getYValues().get(j).get(i), gmodel.getSeriesLabels().get(j), gmodel.getGroupLabels().get(i));
            }
        }
        JFreeChart chart = null;
        String chartTitle = "";
        String xAxisLabel = "";
        String yAxisLabel = "";
        if (gmodel.getPresentation() != null) {
            if (gmodel.getPresentation().getGraphTitle() != null)
                chartTitle = gmodel.getPresentation().getGraphTitle();
            if (gmodel.getPresentation().getxAxisLabel() != null)
                xAxisLabel = gmodel.getPresentation().getxAxisLabel();
            if (gmodel.getPresentation().getyAxisLabel() != null)
                yAxisLabel = gmodel.getPresentation().getyAxisLabel();
        }

        // initialise default, then change on condition
        PlotOrientation plotOrientation = PlotOrientation.HORIZONTAL;
        if (chartOptions.getOrientation() == ChartOptions.OrientationType.HORIZONTAL)
            plotOrientation = PlotOrientation.HORIZONTAL;
        else if (chartOptions.getOrientation() == ChartOptions.OrientationType.VERTICAL)
            plotOrientation = PlotOrientation.VERTICAL;

        log.debug("Graph Setup with Title {}, xAxisLabel {}, yAxisLabel {}", new Object[] { chartTitle, xAxisLabel, yAxisLabel });
        if (chartOptions.getGraphType() == ChartType.BAR3D)
            chart = ChartFactory.createBarChart3D(chartTitle, xAxisLabel, yAxisLabel, dataset, plotOrientation, chartOptions.getGraphPresentation().getLegend(), true, false);
        else if (chartOptions.getGraphType() == ChartType.AREA)
            chart = ChartFactory.createAreaChart(chartTitle, xAxisLabel, yAxisLabel, dataset, plotOrientation, chartOptions.getGraphPresentation().getLegend(), true, false);
        else if (chartOptions.getGraphType() == ChartType.LINE3D)
            chart = ChartFactory.createLineChart3D(chartTitle, xAxisLabel, yAxisLabel, dataset, plotOrientation, chartOptions.getGraphPresentation().getLegend(), true, false);
        else if (chartOptions.getGraphType() == ChartType.BAR)
            chart = ChartFactory.createBarChart(chartTitle, xAxisLabel, yAxisLabel, dataset, plotOrientation, chartOptions.getGraphPresentation().getLegend(), true, false);
        else if (chartOptions.getGraphType() == ChartType.LINE)
            chart = ChartFactory.createLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset, plotOrientation, chartOptions.getGraphPresentation().getLegend(), true, false);

        return doConstructChartPresentation(chart, chartOptions, user, filename);

    }

    private RaptorJFreeChartModel doConstructChartPresentation(JFreeChart chart, ChartOptions chartOptions, String filename, String user) {
        RaptorJFreeChartModel chartmodel = new RaptorJFreeChartModel();

        // setup the graph output
        if (chartOptions.getGraphPresentation() == GraphPresentation.FANCY)
            fancyGraphOutput(chart, chartOptions);
        else if (chartOptions.getGraphPresentation() == GraphPresentation.FRONT)
            frontGraphOutput(chart, chartOptions);

        // save the graph
        String endingFilename = "";
        if (filename != null)
            endingFilename = filename;

        // must create a random number, if the image url does not change, the browser uses the cached image
        int ran = getRandomChartFileExtension(100);

        File chartLocation = new File(getRootDirectory(user) + "/raptor-graphs-main" + endingFilename + ".svg");
        File chartLocationPNG = new File(getRootDirectory(user) + "/raptor-graphs-main" + endingFilename + ran + ".png");

        // png is used for screen output
        if (chartOptions.getGraphPresentation() == GraphPresentation.FANCY) {
            try {
                int padding = 5;
                log.debug("Writing PNG to {}", chartLocationPNG);
                ImageIO.write(
                        ChartProcessorHelper.buildChartDropShadow(chart.createBufferedImage(chartOptions.getImageWidth() - (padding * 2), chartOptions.getImageHeight() - (padding * 2)), padding),
                        "png", new FileOutputStream(chartLocationPNG));
            } catch (IOException e) {
                log.error("Could not save PNG for screen render File {}", e.getMessage());
            }
        } else {
            try {
                exportChartAsPNG(chart, new Rectangle(chartOptions.getImageWidth(), chartOptions.getImageHeight()), chartLocationPNG);
            } catch (IOException e) {
                log.error("Could not save PNG File {}", e.getMessage());
            }
        }

        chartmodel.setChartLocation(chartLocationPNG);
        chartmodel.setRelativeChartLocation(getRelativePath(chartLocationPNG));
        chartmodel.setChart(chart);
        return chartmodel;
    }

    private int getRandomChartFileExtension(int upperLimit) {
        int ran = ((int) (Math.random() * upperLimit));
        return ran;
    }

    private void fancyGraphOutput(JFreeChart chart, ChartOptions chartOptions) {
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(chartOptions.getxLabelPosition().getLabelPosition());
        chart.setBackgroundPaint(new Color(255, 255, 255, 0));
        chart.setPadding(new RectangleInsets(10, 5, 5, 5));
        chart.getLegend().setPosition(RectangleEdge.BOTTOM);
        chart.getLegend().setBorder(0.0, 0.0, 0.0, 0.0);
        chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
        plot.setBackgroundPaint(new Color(222, 222, 222, 125));
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.black);
        plot.setDomainGridlinePaint(Color.black);

        // axis
        CategoryAxis domainAxis = (CategoryAxis) plot.getDomainAxis();
        domainAxis.setUpperMargin(0.0);
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(0.0);
        domainAxis.setLowerMargin(0.0);
        domainAxis.setLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));

    }

    /**
     * Should only be used by graphs on the front page (dashboard) as it has certain uncostomisable options
     * 
     * @param chart
     * @param chartOptions
     */
    private void frontGraphOutput(JFreeChart chart, ChartOptions chartOptions) {
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
        // chart.setBackgroundPaint(new Color(255, 255, 255, 0));
        chart.setPadding(new RectangleInsets(10, 5, 5, 5));
        plot.setBackgroundPaint(new Color(222, 222, 222, 125));
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.black);
        plot.setDomainGridlinePaint(Color.black);
        // set the thickness of the first series
        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setForegroundAlpha(0.7f);
        plot.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 1f, 1f, new Color(210, 210, 210)));
        plot.getRenderer().setSeriesPaint(0, Color.blue);
        // axis
        CategoryAxis domainAxis = (CategoryAxis) plot.getDomainAxis();
        domainAxis.setUpperMargin(0.0);
        domainAxis.setLowerMargin(0.0);
        domainAxis.setCategoryLabelPositionOffset(26);
        domainAxis.setMaximumCategoryLabelWidthRatio(0.5f);
        domainAxis.setLabelFont(new Font("SansSerif", Font.PLAIN, 11));
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 11));

    }

    /**
     * Exports a JFreeChart to a SVG file.
     * 
     * @param chart
     *            JFreeChart to export
     * @param bounds
     *            the dimensions of the viewport
     * @param svgFile
     *            the output file.
     * @throws IOException
     *             if writing the svgFile fails.
     */
    private void exportChartAsSVG(JFreeChart chart, Rectangle bounds, File svgFile) throws IOException {
        // Get a DOMImplementation and create an XML document
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument(null, "svg", null);

        // Create an instance of the SVG Generator
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

        // draw the chart in the SVG generator
        chart.draw(svgGenerator, bounds);

        // Write svg file
        log.debug("Saving SVG Chart to {}", svgFile.getCanonicalPath());
        OutputStream outputStream = new FileOutputStream(svgFile);
        Writer out = new OutputStreamWriter(outputStream, "UTF-8");
        svgGenerator.stream(out, true /* use css */);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * Exports a JFreeChart to a jpg file.
     * 
     * @param chart
     *            JFreeChart to export
     * @param bounds
     *            the dimensions of the viewport
     * @param jpgFile
     *            the output file.
     * @throws IOException
     *             if writing the svgFile fails.
     */
    private void exportChartAsJPG(JFreeChart chart, Rectangle bounds, File jpgFile) throws IOException {
        log.debug("Saving JPG Chart to {}", jpgFile.getCanonicalPath());
        ChartUtilities.saveChartAsJPEG(jpgFile, chart, bounds.width, bounds.height);
    }

    private void exportChartAsPNG(JFreeChart chart, Rectangle bounds, File pngFile) throws IOException {
        log.debug("Saving PNG Chart to {}", pngFile.getCanonicalPath());
        ChartUtilities.saveChartAsPNG(pngFile, chart, bounds.width, bounds.height);
    }

    /**
     * @param gmodel
     * @return
     */
    public RaptorTableChartModel constructRaptorTableChartModel(AggregatorGraphModel gmodel) {
        log.info("Constructing Raptor Table for {}", gmodel.getPresentation().getGraphTitle());

        RaptorTableChartModel tableModel = new RaptorTableChartModel();

        for (int j = 0; j < gmodel.getSeriesLabels().size(); j++) {
            TableSeries tseries = new TableSeries();
            tseries.setSeriesLabel(gmodel.getSeriesLabels().get(j));

            for (int i = 0; i < gmodel.getGroupLabels().size(); i++) {
                Row<Double> row = new Row<Double>();
                row.setGroup(gmodel.getGroupLabels().get(i));
                row.setValue(gmodel.getYValues().get(j).get(i));
                tseries.addRow(row);
            }
            tableModel.addTableSeries(tseries);
        }
        tableModel.constructTableForView();

        log.debug("Raptor Table model constructed, with {} rows", tableModel.getRowList().size());

        return tableModel;
    }

    public void setSaveDirectory(Resource saveDirectory) {
        this.saveDirectory = saveDirectory;
    }

    public Resource getSaveDirectory() {
        return saveDirectory;
    }

    public void setBaseDirectory(Resource baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public Resource getBaseDirectory() {
        return baseDirectory;
    }

}
