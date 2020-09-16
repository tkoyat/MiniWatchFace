/*
 * Copyright 2019 Stuart Kent
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.tkoyat.miniwatchface.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.util.Scanner;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
//import java.awt.Color;
//import java.awt.geom.Path2D;
//import java.awt.Graphics2D;
//import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.Polygon;
//import javax.swing.*;
//import javax.swing.JFrame;
//import java.math.*;
//import java.util.*;

/**
 * A utility class for constructing and drawing rounded regular polygons.
 */
public class DrawPolygonUtil {

    private final Path backingPath = new Path();
    private final RectF tempCornerArcBounds = new RectF();



    private Face[] faces;
    private Face face;
    private double screenHeight = 1000;
    private double screenWidth = 1000;
    private double scale;
    private int red;
    private int green;
    private int blue;
    private Color shapeColor;

//    public DrawPolygon(Face[] polyhedronFaces, int red, int green, int blue, double scale)
//    {
//        super();
//        faces = polyhedronFaces;
//        this.scale = scale;
//        this.red = red;
//        this.green = green;
//        this.blue = blue;
////        shapeColor = new Color(this.red, this.green, this.blue, 0.0);
////        shapeColor = new float[] { 0.0f, 0.0f, 0.0f, 1.0f };
//    }

    public void DrawPolygon(
            @NonNull final Canvas canvas,
            Face[] polyhedronFaces,
            final float centerX,
            final float centerY,
            double scale,
            Paint polyhedronPaint)
    {
//        super();
        faces = polyhedronFaces;
        this.scale = scale;
//        this.red = red;
//        this.green = green;
//        this.blue = blue;
//        shapeColor = new Color(this.red, this.green, this.blue, 0.0);
//        shapeColor = new float[] { 0.0f, 0.0f, 0.0f, 1.0f };

//        super.paintComponent(g);
//        g.translate(getWidth()/2, getHeight()/2);
//
        double intensity;
        double x;
        double y;

//        final Matrix rotationMatrix = new Matrix();
//        rotationMatrix.setRotate(0, centerX, centerY);

//        Log.d("DrawPolygonUtil", "DrawPolygonUtil.DrawPolygon");
        for (int i = 0; i < faces.length; i++) {
//            Path2D p = new Path2D.Double();
            Path p = new Path();

            // Translate coordinate from a 3D coordinate to a
            //     2D coordinate
            x = faces[i].getVertex(0).getXCoordinate()
                    * faces[i].getDistance()
                    / (faces[i].getDistance()
                    - faces[i].getVertex(0).getZCoordinate());
            y = faces[i].getVertex(0).getYCoordinate()
                    * faces[i].getDistance()
                    / (faces[i].getDistance()
                    - faces[i].getVertex(0).getZCoordinate());
            // Begin the polygon line
            p.moveTo((float)(scale(x)+centerX), (float)(scale(y)+centerY));
//            Log.d("DrawPolygonUtil", "DrawPolygonUtil.DrawPolygon" + (float)scale(x)+ "," + (float)scale(y));

//            p.moveTo(scale(x), scale(y));
            final Matrix rotationMatrix = new Matrix();
            rotationMatrix.setRotate(0, centerX, centerY);

            for ( int j = 1; j < faces[i].getNumVertices(); j++) {
                // Translate coordinate from a 3D coordinate to a
                //     2D coordinate
                x = faces[i].getVertex(j).getXCoordinate()
                        * faces[i].getDistance()
                        / (faces[i].getDistance()
                        - faces[i].getVertex(j).getZCoordinate());
                y = faces[i].getVertex(j).getYCoordinate()
                        * faces[i].getDistance()
                        / (faces[i].getDistance()
                        - faces[i].getVertex(j).getZCoordinate());

                // Move line to next polyhedron vertex
                p.lineTo((float)(scale(x)+centerX), (float)(scale(y)+centerY));
//
                canvas.drawPath(p, polyhedronPaint);

                Log.d("DrawPolygonUtil", "DrawPolygonUtil.DrawPolygon" + (float)scale(x)+ "," + (float)scale(y));
//                Log.d("DrawPolygonUtil", "DrawPolygonUtil.DrawPolygon" + p);
            }

            // Calculate face intensity
            intensity = faces[i].getIntensity();
            // Close the polyhedron path
            p.close();

            if (intensity >= 0 ) {
//                polyhedronPaint.setColor(Color.BLUE);
                polyhedronPaint.setColor(Color.WHITE);
            }
            else
            {
                polyhedronPaint.setColor(Color.GRAY);
            }

//
//            if (intensity >= 0 ) {
//                // Adjust the face based on intesnity
//                faceColor(intensity);
//                // Set the color of the face
//                ((Graphics2D) g).setPaint(shapeColor);
//                // Fill the face
//                ((Graphics2D) g).fill(p);
//            }
//            else
//            {
//                ((Graphics2D) g).setPaint(Color.BLACK);
//                ((Graphics2D) g).fill(p);
//            }
        }
    }

    /**
     * Constructs a regular polygonal {@link Path}.
     *
     * @param path         the {@link Path} to be filled with polygon components. Will be reset.
     * @param sideCount    the number of sides of the polygon
     * @param centerX      the x-coordinate of the polygon center in pixels
     * @param centerY      the y-coordinate of the polygon center in pixels
     * @param outerRadius  the distance from the polygon center to any vertex (ignoring corner
     *                      rounding) in pixels
     * @param cornerRadius the radius of the rounding applied to each corner of the polygon in
     *                      pixels
     * @param rotation     the rotation of the polygon in degrees
     */
    public void constructPolygonPath(
            @NonNull final Path path,
            @IntRange(from = 3) final int sideCount,
            final float centerX,
            final float centerY,
            @FloatRange(from = 0, fromInclusive = false) final float outerRadius,
            @FloatRange(from = 0) final float cornerRadius,
            final float rotation) {

        path.reset();

        final float inRadius = (float) (outerRadius * Math.cos(toRadians(180.0 / sideCount)));

        if (inRadius < cornerRadius) {
            /*
             * If the supplied corner radius is too small, we default to the "incircle".
             *   - https://web.archive.org/web/20170415150442/https://en.wikipedia.org/wiki/Regular_polygon
             *   - https://web.archive.org/web/20170415150415/http://www.mathopenref.com/polygonincircle.html
             */
            path.addCircle(centerX, centerY, inRadius, Path.Direction.CW);
        } else {
            if (abs(cornerRadius) < 0.01) {
                constructNonRoundedPolygonPath(
                        path,
                        sideCount,
                        centerX,
                        centerY,
                        outerRadius);
            } else {
                constructRoundedPolygonPath(
                        path,
                        sideCount,
                        centerX,
                        centerY,
                        outerRadius,
                        cornerRadius);
            }

            final Matrix rotationMatrix = new Matrix();
            rotationMatrix.setRotate(rotation, centerX, centerY);
            path.transform(rotationMatrix);
        }
    }

    private void constructRoundedPolygonPath(
            @NonNull final Path path,
            @IntRange(from = 3) final int sideCount,
            final float centerX,
            final float centerY,
            @FloatRange(from = 0, fromInclusive = false) final float outerRadius,
            @FloatRange(from = 0) final float cornerRadius) {

        final double halfInteriorCornerAngle = 90 - (180.0 / sideCount);
        final float halfCornerArcSweepAngle = (float) (90 - halfInteriorCornerAngle);
        final double distanceToCornerArcCenter = outerRadius - cornerRadius / sin(toRadians(halfInteriorCornerAngle));

        for (int cornerNumber = 0; cornerNumber < sideCount; cornerNumber++) {
            final double angleToCorner = cornerNumber * (360.0 / sideCount);
            final float cornerCenterX = (float) (centerX + distanceToCornerArcCenter * cos(toRadians(angleToCorner)));
            final float cornerCenterY = (float) (centerY + distanceToCornerArcCenter * sin(toRadians(angleToCorner)));

            tempCornerArcBounds.set(
                    cornerCenterX - cornerRadius,
                    cornerCenterY - cornerRadius,
                    cornerCenterX + cornerRadius,
                    cornerCenterY + cornerRadius);

            /*
             * Quoted from the arcTo documentation:
             *
             *   "Append the specified arc to the path as a new contour. If the start of the path is different from the
             *    path's current last point, then an automatic lineTo() is added to connect the current contour to the
             *    start of the arc. However, if the path is empty, then we call moveTo() with the first point of the
             *    arc."
             *
             * We construct our polygon by sequentially drawing rounded corners using arcTo, and leverage the
             * automatically-added moveTo/lineTo instructions to connect these corners with straight edges.
             */
            path.arcTo(
                    tempCornerArcBounds,
                    (float) (angleToCorner - halfCornerArcSweepAngle),
                    2 * halfCornerArcSweepAngle);
        }

        // Draw the final straight edge.
        path.close();
    }

    private void constructNonRoundedPolygonPath(
            @NonNull final Path path,
            @IntRange(from = 3) final int sideCount,
            final float centerX,
            final float centerY,
            @FloatRange(from = 0, fromInclusive = false) final float outerRadius) {

        for (int cornerNumber = 0; cornerNumber < sideCount; cornerNumber++) {
            final double angleToCorner = cornerNumber * (360.0 / sideCount);
            final float cornerX = (float) (centerX + outerRadius * cos(toRadians(angleToCorner)));
            final float cornerY = (float) (centerY + outerRadius * sin(toRadians(angleToCorner)));

            if (cornerNumber == 0) {
                path.moveTo(cornerX, cornerY);
            } else {
                path.lineTo(cornerX, cornerY);
            }
        }

        // Draw the final straight edge.
        path.close();
    }

    private static double toRadians(final double degrees) {
        return 2 * PI * degrees / 360;
    }

    // Chooses the colour for the polyhedron
//    public void faceColor(double intensity) {
//        Color newColor;
//        double intensityR = 0;
//        double intensityG = 0;
//        double intensityB = 0;
//
//        intensityR = red * (intensity);
//        intensityG = green * (intensity);
//        intensityB = blue* (intensity);
//
////        newColor = new Color((int)intensityR, (int)intensityG, (int)intensityB);
//        this.shapeColor = newColor;
//    }

    // Set the red value
    public void setRed(Scanner scanner) {
        red = -1;

        while(!(red >= 0 && red <= 255)) {
            System.out.print("Please enter a red value(0-255): ");
            red = scanner.nextInt();
        }
    }

    // Set the green value
    public void setGreen(Scanner scanner) {
        green = -1;

        while(!(green >= 0 && green <= 255)) {
            System.out.print("Please enter a green value(0-255): ");
            green = scanner.nextInt();
        }
    }

    // Set the blue value
    public void setBlue(Scanner scanner) {
        blue = -1;

        while(!(blue >= 0 && blue <= 255)) {
            System.out.print("Please enter a blue value(0-255): ");
            blue = scanner.nextInt();
        }
    }

    // Set the scale
    public void setScale(Scanner scanner) {
        do {
            System.out.print("Please enter a scale < 0: ");
            scale = scanner.nextInt();
        }while(!(scale > 0));
    }

    // Scales / zooms into the points of the polyhedron
    public double scale(double number) {
        return number * scale * 3;
//        Log.d("DrawPolygonUtil", "DrawPolygonUtil.DrawPolygon" + (float)scale(x)+ "," + (float)scale(y));
    }
}
