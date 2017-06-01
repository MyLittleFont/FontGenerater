package kr.ac.kaist.team888;

import fontastic.FContour;
import fontastic.FGlyph;
import fontastic.FPoint;
import fontastic.Fontastic;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.io.File;

public class Generator {
    public Generator() {

    }

    public void generate(GlyphData data) throws Exception {
        File file = new File("./examplefont.ttf");
        file.delete();
        Fontastic f = new Fontastic("ExampleFont", file);
        f.setAuthor("Nobody");
        FPoint[] points = new FPoint[]{ // Define a FPoint array containing the points of the shape
                new FPoint(0, 0),
                new FPoint(512, 0),
                //new FPoint(256, 1024),
                new FPoint(new FPoint(256, 1024), new FPoint(512, 512)),
                new FPoint(0, 0)
        };
        f.addGlyph('가').addContour(points);             // Assign contour to character A
        f.buildFont();
    }
}
