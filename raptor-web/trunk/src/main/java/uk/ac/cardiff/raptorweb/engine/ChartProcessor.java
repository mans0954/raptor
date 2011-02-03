/**
 *
 */
package uk.ac.cardiff.raptorweb.engine;

import java.awt.Color;
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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
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
import uk.ac.cardiff.raptorweb.model.WebSession;
import uk.ac.cardiff.raptorweb.model.records.Row;

/**
 * @author philsmart
 *
 *         Takes a chart from the MUA, and wraps it inside the current view technologies (trinidads) graph model
 */
public class ChartProcessor {
    static Logger log = LoggerFactory.getLogger(ChartProcessor.class);

    /* the location of the directory within the application that reports are saved to*/
    private Resource saveDirectory;
    private Resource baseDirectory;

    public String getRootDirectory(String user){
	String root=null;
	try {
	    //make sure base directory exists first
	    File baseGraphDirectory = saveDirectory.getFile();
	    if (!baseGraphDirectory.exists())
		baseGraphDirectory.mkdir();

	    //then check for the existence of the users directory within the bae
	    root = saveDirectory.getFile().getCanonicalPath()+"/"+user;
	    File dir = new File(root);
	    log.debug("Save directory for charts exists: "+dir.exists());
	    if (!dir.exists())
		dir.mkdir();

	} catch (IOException e) {
	    log.error("Could not create save directory for JFree charts, {}",e.getMessage());
	}
	return root;
    }

    private File getRelativePath(File dir){
	File relative =null;
	try {
	    relative = new File(dir.getAbsolutePath().replace(baseDirectory.getFile().getAbsolutePath(), ""));
	} catch (IOException e) {
	   log.error("Could not get relative path for file {}, {}",dir,e.getMessage());
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
     * @param gmodel
     * @param session
     * @return
     */
    public RaptorJFreeChartModel constructJFreeGraph(AggregatorGraphModel gmodel, WebSession session) {
	return doConstructJFreeGraph(gmodel, session.getUser().getName());
    }

    /**
     * Will output into the root graphs directory
     * @param gmodel
     * @return
     */
    public RaptorJFreeChartModel constructJFreeGraph(AggregatorGraphModel gmodel) {
	return doConstructJFreeGraph(gmodel, "");
    }

    /**
     * Requires websession, as charts stored on file system specific to the current users home directory
     * @param gmodel
     * @param session
     * @return
     */
    private RaptorJFreeChartModel doConstructJFreeGraph(AggregatorGraphModel gmodel, String user) {
	RaptorJFreeChartModel chartmodel = new RaptorJFreeChartModel();

	//construct the graph
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	for (int j = 0; j < gmodel.getSeriesLabels().size(); j++) {
	    for (int i = 0; i < gmodel.getGroupLabels().size(); i++) {
		dataset.setValue(gmodel.getYValues().get(i).get(j), gmodel.getSeriesLabels().get(j),gmodel.getGroupLabels().get(i));
	    }
	}
	JFreeChart chart = ChartFactory.createBarChart3D(gmodel.getPresentation().getGraphTitle(),gmodel.getPresentation().getxAxisLabel(), gmodel.getPresentation().getyAxisLabel(), dataset, PlotOrientation.VERTICAL, true,true, false);

	//setup the graph output
	CategoryPlot plot = (CategoryPlot)chart.getPlot();
	CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
	xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
	chart.setBackgroundPaint(new Color(255,255,255,0));
	chart.setPadding(new RectangleInsets(10, 5, 5, 5));
	chart.getLegend().setPosition(RectangleEdge.RIGHT);
	chart.getLegend().setBorder(0.0, 0.0, 0.0, 0.0);
	chart.getLegend().setBackgroundPaint(new Color(255,255,255,0));
	plot.setBackgroundPaint(new Color(222,222,222,125));
	plot.setDomainGridlinesVisible(true);
	plot.setRangeGridlinesVisible(true);
	plot.setRangeGridlinePaint(Color.black);
	plot.setDomainGridlinePaint(Color.black);


	//save the graph
	File chartLocation = new File(getRootDirectory(user)+"/raptor-graphs-main.svg");
	File chartLocationJPG = new File(getRootDirectory(user)+"/raptor-graphs-main.jpg");
	File chartLocationPNG = new File(getRootDirectory(user)+"/raptor-graphs-main.png");
	try {
	    exportChartAsSVG(chart, new Rectangle(800,600), chartLocation);
	} catch (IOException e) {
	    log.error("Could not save SVG File {}",e.getMessage());
	}
	try {
	    exportChartAsJPG(chart, new Rectangle(1024,800), chartLocationJPG);
	} catch (IOException e) {
	    log.error("Could not save JPG File {}",e.getMessage());
	}
	//png is used for screen output
	try
	    {
	      int padding = 10;
	      int width = 1440;
	      int height = 1024;
	      ImageIO.write(ChartProcessorHelper.buildChartDropShadow(chart.createBufferedImage(width-(padding*2), height-(padding*2)), padding), "png", new FileOutputStream(chartLocationPNG));
	  }
	  catch (IOException e)
	  {
	      log.error("Could not save PNG for screen render File {}",e.getMessage());
	  }

	chartmodel.setChartLocation(chartLocationPNG);
	chartmodel.setRelativeChartLocation(getRelativePath(chartLocationPNG));
	return chartmodel;
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
	log.debug("Saving SVG Chart to {}",svgFile.getCanonicalPath());
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
	log.debug("Saving JPG Chart to {}",jpgFile.getCanonicalPath());
	ChartUtilities.saveChartAsJPEG(jpgFile, chart, bounds.width, bounds.height);
    }

    private void exportChartAsPNG(JFreeChart chart, Rectangle bounds, File pngFile) throws IOException {
	log.debug("Saving PNG Chart to {}",pngFile.getCanonicalPath());
	ChartUtilities.saveChartAsPNG(pngFile, chart, bounds.width, bounds.height);
    }

    /**
     * @param gmodel
     * @return
     */
    public RaptorTableChartModel constructRaptorTableChartModel(AggregatorGraphModel gmodel) {

	RaptorTableChartModel tableModel = new RaptorTableChartModel();

	for (int i = 0; i < gmodel.getGroupLabels().size(); i++) {
	    Row<Double> row = new Row<Double>();
	    row.setSeries(gmodel.getGroupLabels().get(i));
	    /*
	     * important, as currently we only operate with one group, the second list of Doubles in the YValues will only have a List of size 1, which is
	     * assumed here, hence get(0).
	     */
	    row.setValue(gmodel.getYValues().get(i).get(0));
	    tableModel.addRow(row);
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
