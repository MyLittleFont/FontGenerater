package kr.ac.kaist.team888;

import fontastic.FContour;
import fontastic.FGlyph;
import fontastic.FPoint;
import fontastic.Fontastic;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


// From: https://github.com/jindrapetrik/jpexs-decompiler
public class Generator {
    public Generator() {

    }

    public File generate(ArrayList<GlyphData> datas, String fontName) {
        File file = new File(String.format("./%s.ttf", fontName));
        file.delete();
        Fontastic f = null;
        try {
            f = new Fontastic(fontName, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        f.setAuthor("MyLitteFont");
        for (GlyphData data : datas) {
            FGlyph glyph = f.addGlyph(data.letter);
            for (FPoint[] contour : data.contours) {
                glyph.addContour(contour);
            }
            glyph.setAdvanceWidth(data.width);
        }
        f.buildFont();
        return file;
    }
}
