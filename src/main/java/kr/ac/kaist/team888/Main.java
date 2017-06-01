package kr.ac.kaist.team888;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        GlyphData glyphData = new GlyphData();
        glyphData.letter = '가';
        glyphData.width = 1000;

        ArrayList<Vector2D[]> contours = new ArrayList<>();
        Vector2D[] contour1 = new Vector2D[3];
        contour1[0] = new Vector2D(0,0);
        contour1[1] = new Vector2D(0,250);
        contour1[2] = new Vector2D(0,500);

        Vector2D[] contour2 = new Vector2D[3];
        contour1[0] = new Vector2D(0,500);
        contour1[1] = new Vector2D(250,500);
        contour1[2] = new Vector2D(500,500);

        Vector2D[] contour3 = new Vector2D[3];
        contour1[0] = new Vector2D(500,500);
        contour1[1] = new Vector2D(500,250);
        contour1[2] = new Vector2D(500,0);

        Vector2D[] contour4 = new Vector2D[3];
        contour1[0] = new Vector2D(500,0);
        contour1[1] = new Vector2D(250,0);
        contour1[2] = new Vector2D(0,0);

        contours.add(contour1);
        contours.add(contour2);
        contours.add(contour3);
        contours.add(contour4);

        glyphData.contours = contours;

        Generator generator = new Generator();
        try {
            generator.generate(glyphData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
