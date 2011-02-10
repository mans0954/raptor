/**
 *
 */
package uk.ac.cardiff.raptorweb.engine;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.imageio.ImageIO;
import javax.transaction.SystemException;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import org.apache.batik.svggen.SVGGraphics2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import uk.ac.cardiff.model.Graph.AggregatorGraphModel;
import uk.ac.cardiff.raptorweb.model.RaptorGraphModel;
import uk.ac.cardiff.raptorweb.model.RaptorJFreeChartModel;
import uk.ac.cardiff.raptorweb.model.RaptorTableChartModel;
import uk.ac.cardiff.raptorweb.model.TableSeries;
import uk.ac.cardiff.raptorweb.model.WebSession;
import uk.ac.cardiff.raptorweb.model.records.Row;

/**
 * @author philsmart
 *
 *         Takes a chart from the MUA, and wraps it inside the current graph view technologies (trinidad, JFreeChart) graph model
 */
public class ChartProcessor {
    static Logger log = LoggerFactory.getLogger(ChartProcessor.class);

    /* the location of the directory within the application that reports are saved to */
    private Resource saveDirectory;
    private Resource baseDirectory;

    /* allows for selection of graph type */
    public enum GraphType {
	BAR, AREA, BAR3D, LINE3D, LINE
    };

    /* options for how the graph is displayed */
    public enum GraphPresentation {
	FANCY(true), SIMPLE(false);
	private boolean legend;

	GraphPresentation(boolean legend) {
	    this.legend = legend;
	}
    }

    public String getRootDirectory(String user) {
	String root = null;
	try {
	    // make sure base directory exists first
	    File baseGraphDirectory = saveDirectory.getFile();
	    if (!baseGraphDirectory.exists())
		baseGraphDirectory.mkdir();

	    // then check for the existence of the users directory within the bae
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

    private File getRelativePath(File dir) {
	File relative = null;
	try {
	    relative = new File(dir.getAbsolutePath().replace(baseDirectory.getFile().getAbsolutePath(), ""));
	} catch (IOException e) {
	    log.error("Could not get relative path for file {}, {}", dir, e.getMessage());
	}
	return relative;
    }

    public RaptorGraphModel constructRaptorGraphModel(AggregatorGraphModel gmodel) {
	RaptorGraphModel rgraph = new RaptorGraphModel();
	log.debug("Constructing Graph from : " + gmodel);
	rgraph.setGroupLabels(gmodel.getGroupLabels());
	rgraph.setSeriesLabels(gmodel.getSeriesLabels());
	rgraph.setChartValues(gmodel.getYValues());

	return rgraph;
    }

    /**
     * Outputs into the users home directory in the parent graph directory
     *
     * @param gmodel
     * @param session
     * @return
     */
    public RaptorJFreeChartModel constructJFreeGraph(GraphPresentation graphPresentation, GraphType graphType, AggregatorGraphModel gmodel, WebSession session, int width, int height) {
	return doConstructJFreeGraphBar(graphPresentation, graphType, gmodel, session.getUser().getName(), width, height);

    }

    /**
     * Will output into the root graphs directory
     *
     * @param gmodel
     * @return
     */
    public RaptorJFreeChartModel constructJFreeGraph(GraphPresentation graphPresentation, GraphType graphType, AggregatorGraphModel gmodel, int width, int height) {
	return doConstructJFreeGraphBar(graphPresentation, graphType, gmodel, "", width, height);
    }

    /**
     * Requires websession, as charts stored on file system specific to the current users home directory
     *
     * @param gmodel
     * @param session
     * @return
     */
    private RaptorJFreeChartModel doConstructJFreeGraphBar(GraphPresentation graphPresentation, GraphType graphType, AggregatorGraphModel gmodel, String user, int width, int height) {
	log.info("Creating graph {} with presentation {} (legend {}), width={} height={}", new Object[] { graphType, graphPresentation, graphPresentation.legend, width, height });
	RaptorJFreeChartModel chartmodel = new RaptorJFreeChartModel();

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

	log.debug("Graph Setup with Title {}, xAxisLabel {}, yAxisLabel {}", new Object[] { chartTitle, xAxisLabel, yAxisLabel });
	if (graphType == GraphType.BAR3D)
	    chart = ChartFactory.createBarChart3D(chartTitle, xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, graphPresentation.legend, true, false);
	else if (graphType == GraphType.AREA)
	    chart = ChartFactory.createAreaChart(chartTitle, xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, graphPresentation.legend, true, false);
	else if (graphType == GraphType.LINE3D)
	    chart = ChartFactory.createLineChart3D(chartTitle, xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, graphPresentation.legend, true, false);
	else if (graphType == GraphType.BAR)
	    chart = ChartFactory.createBarChart(chartTitle, xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, graphPresentation.legend, true, false);
	else if (graphType == GraphType.LINE)
	    chart = ChartFactory.createLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, graphPresentation.legend, true, false);

	// setup the graph output
	if (graphPresentation == GraphPresentation.FANCY)
	    fancyGraphOutput(chart);
	else if (graphPresentation == GraphPresentation.SIMPLE)
	    simpleGraphOutput(chart);

	// save the graph
	File chartLocation = new File(getRootDirectory(user) + "/raptor-graphs-main.svg");
	File chartLocationPNG = new File(getRootDirectory(user) + "/raptor-graphs-main.png");

	// png is used for screen output
	if (graphPresentation == GraphPresentation.FANCY) {
	    try {
		int padding = 5;
		ImageIO.write(ChartProcessorHelper.buildChartDropShadow(chart.createBufferedImage(width - (padding * 2), height - (padding * 2)), padding), "png", new FileOutputStream(chartLocationPNG));
	    } catch (IOException e) {
		log.error("Could not save PNG for screen render File {}", e.getMessage());
	    }
	} else {
	    try {
		exportChartAsPNG(chart, new Rectangle(width, height), chartLocationPNG);
	    } catch (IOException e) {
		log.error("Could not save PNG File {}", e.getMessage());
	    }
	}

	try {
	    exportChartAsSVG(chart, new Rectangle(800, 600), chartLocation);
	} catch (IOException e) {
	    log.error("Could not save SVG File {}", e.getMessage());
	}

	chartmodel.setChartLocation(chartLocationPNG);
	chartmodel.setRelativeChartLocation(getRelativePath(chartLocationPNG));
	return chartmodel;
    }

    private void fancyGraphOutput(JFreeChart chart) {
	CategoryPlot plot = (CategoryPlot) chart.getPlot();
	CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
	xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
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
	CategoryAxis rangeAxis = (CategoryAxis) plot.getDomainAxis();
	rangeAxis.setUpperMargin(0.0);
	rangeAxis.setLowerMargin(0.0);

    }

    private void simpleGraphOutput(JFreeChart chart) {
	CategoryPlot plot = (CategoryPlot) chart.getPlot();
	CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
	xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	// chart.setBackgroundPaint(new Color(255, 255, 255, 0));
	chart.setPadding(new RectangleInsets(10, 5, 5, 5));
	plot.setBackgroundPaint(new Color(222, 222, 222, 125));
	plot.setDomainGridlinesVisible(true);
	plot.setRangeGridlinesVisible(true);
	plot.setRangeGridlinePaint(Color.black);
	plot.setDomainGridlinePaint(Color.black);

	// axis
	CategoryAxis rangeAxis = (CategoryAxis) plot.getDomainAxis();
	rangeAxis.setUpperMargin(0.0);
	rangeAxis.setLowerMargin(0.0);

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
