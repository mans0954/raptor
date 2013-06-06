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

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * @author attributed to http://www.ioncannon.net/java/139/spiffing-up-jfreechart-charts/
 *
 */
public class ChartProcessorHelper {

    public static ConvolveOp getLinearBlurOp(int width, int height) {
	float[] data = new float[width * height];
	float value = 1.0f / (float) (width * height);
	for (int i = 0; i < data.length; i++) {
	    data[i] = value;
	}
	return new ConvolveOp(new Kernel(width, height, data));
    }

    public static BufferedImage buildChartDropShadow(BufferedImage chartImage, int padding) {
	return buildChartDropShadow(chartImage, padding, null);
    }

    public static BufferedImage buildChartDropShadow(BufferedImage chartImage, int padding, Color backgroundColor) {
	BufferedImage shadow = new BufferedImage(chartImage.getWidth() + (padding * 2), chartImage.getHeight() + (padding * 2), BufferedImage.TYPE_INT_ARGB);
	Graphics2D shadowCanvas = (Graphics2D) shadow.getGraphics();
	shadowCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	shadowCanvas.setColor(Color.white);
	shadowCanvas.fillRect(0, 0, chartImage.getWidth() + (padding * 2), chartImage.getHeight() + (padding * 2));
	shadowCanvas.setPaint(Color.black);
	shadowCanvas.fillRoundRect(padding, padding, chartImage.getWidth(), chartImage.getHeight(), 20, 20);
	shadowCanvas.dispose();

	BufferedImage finalImage = new BufferedImage(chartImage.getWidth() + (padding * 2), chartImage.getHeight() + (padding * 2), BufferedImage.TYPE_INT_ARGB);
	Graphics2D finalCanvas = (Graphics2D) finalImage.getGraphics();
	finalCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	finalCanvas.setColor(backgroundColor == null ? Color.white : backgroundColor);
	finalCanvas.fillRect(0, 0, chartImage.getWidth() + (padding * 2), chartImage.getHeight() + (padding * 2));
	finalCanvas.drawImage(shadow, getLinearBlurOp(10, 10), 2, 2);

	finalCanvas.setPaint(new GradientPaint(0.0f, 0.0f, new Color(0xdc, 0xe5, 0xf4), 0.0f, chartImage.getHeight() * 0.2f, new Color(0xff, 0xff, 0xff)));
	finalCanvas.fillRoundRect(padding, padding, chartImage.getWidth(), chartImage.getHeight(), 20, 20);
	finalCanvas.drawImage(chartImage, null, padding, padding);
	finalCanvas.dispose();

	return finalImage;
    }

}
