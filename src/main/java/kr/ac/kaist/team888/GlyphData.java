package kr.ac.kaist.team888;

import fontastic.FPoint;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;

public class GlyphData {
    public char letter;
    public ArrayList<FPoint[]> contours = new ArrayList<>();
    public int width;
}
