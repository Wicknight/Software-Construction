/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
    	//throw new RuntimeException("implement me!");
        double degree=90.0;
        for(int i=0;i<4;i++) {
        	turtle.forward(sideLength);
            turtle.turn(degree);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        if(sides<=2) {
        	throw new RuntimeException("implement me!");
        }
        else {
        	int anglesum=(sides-2)*180;
        	double angle=(double)anglesum/sides;
        	return angle;
        }
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        if(angle<=0||angle>=180) {
        	throw new RuntimeException("implement me!");
        }
        else {
        	int exteriorangle=(int)(180-angle);
        	int side=360/exteriorangle;
        	return side;
        }
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        if(sides<=2) {
        	throw new RuntimeException("implement me!");
        }
        else {
        	double angle=180-calculateRegularPolygonAngle(sides);
        	for(int i=0;i<sides;i++) {
        		turtle.forward(sideLength);
            	turtle.turn(angle);
        	}
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        //throw new RuntimeException("implement me!");
    	double targetpoint=0; //????????????????????????????????????9????????????????
    	if((currentX==targetX)&&(currentY==targetY)) {//????
    		targetpoint=0;
    	}
    	else if ((targetX<currentX)&&(targetY>currentY)) {//??????????????
			double difX=currentX-targetX;
			double difY=targetY-currentY;
			double tan=difX/difY;
			targetpoint=Math.atan(tan)*180/Math.PI;//????tan??????????
			targetpoint=360-targetpoint-currentBearing;//????????????????????????????????????????
		}
    	else if((targetX<currentX)&&(targetY==currentY)) {//????????????
    		targetpoint=270-currentBearing;
    	}
    	else if((targetX<currentX)&&(targetY<currentY)) {//????????????
    		double difX=currentX-targetX;
    		double difY=currentY-targetY;
    		double tan=difX/difY;
			targetpoint=Math.atan(tan)*180/Math.PI;//????tan??????????
			targetpoint=targetpoint+180-currentBearing;//????????????????????????????????????????
    	}
    	else if((currentX==targetX)&&(targetY<currentY)) {//????????????
    		targetpoint=180-currentBearing;
    	}
    	else if((targetX>currentX)&&(targetY<currentY)) {//????????????
    		double difX=targetX-currentX;
    		double difY=currentY-targetY;
    		double tan=difX/difY;
			targetpoint=Math.atan(tan)*180/Math.PI;//????tan??????????
			targetpoint=180-targetpoint-currentBearing;//????????????????????????????????????????
    	}
    	else if((targetX>currentX)&&(targetY==currentY)) {//????????????
    		targetpoint=90-currentBearing;
    	}
    	else if((targetX>currentX)&&(targetY>currentY)) {//????????????
    		double difX=targetX-currentX;
    		double difY=targetY-currentY;
    		double tan=difX/difY;
			targetpoint=Math.atan(tan)*180/Math.PI;//????tan??????????
			targetpoint=targetpoint-currentBearing;//????????????????????????????????????????
    	}
    	else {	//????????????
    		targetpoint=0-currentBearing;
    	}
    	if(targetpoint<0) {//????????360??????????????????????????????
    		targetpoint+=360;
    	}
    	return targetpoint;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
    	int xnum=xCoords.size();
    	int ynum=yCoords.size();
    	if(xnum!=ynum) {//????????x,y??????????????????????????????????
    		throw new RuntimeException("implement me!");
    	}
    	else {//????????
    		List<Double> BearingsList=new ArrayList<>();//??????????????????????point
    		double bearingpoint=0;//????bearingpoint??????0??????????????????????
    		int currentX,currentY,targetX,targetY,i;
    		for(i=0;i<xnum-1;i++) {//??n-1????????????cunrrentX,Y????xCoords??yCoords??????i??????
    			currentX=xCoords.get(i);
    			currentY=yCoords.get(i);
    			targetX=xCoords.get(i+1);//targetX,Y????????????????????
    			targetY=yCoords.get(i+1);
    			bearingpoint=calculateBearingToPoint(bearingpoint, currentX, currentY, targetX, targetY);
    			BearingsList.add(bearingpoint);//????????????????
    		}
    		return BearingsList;
    	}
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        //throw new RuntimeException("implement me!");
    	if(points.size()<=3) {
    		return points;
    	}
    	else {
    		Set<Point> convexset=new HashSet<Point>();
    		List<Point> convexlist=new ArrayList<Point>();//????List????????????????????????????????????List????
    		Point convexPoint=new Point(0,0);
    		double distance=0;
    		for(Point p:points) {//????????????????????????????????????????????????????????
    			if(p.x()*p.x()+p.y()*p.y()>distance) {
    				distance=p.x()*p.x()+p.y()*p.y();
    				convexPoint=p;
    			}
    		}
    		convexlist.add(convexPoint);
    		double bearingpoint=0;//??????????Y??????????????0
    		double pointbetween1=360;//????????????????????????????????????????
    		double pointbetween2=0;//??????????????????????????????????????????????
    		for(int i=0;i<points.size()-1;i++) {//????????????????????????calculateBearingToPoint????????????????????????????????????????????????????????????????????????????????
    			int currentX=(int)convexlist.get(convexlist.size()-1).x();//????????????????????
				int currentY=(int)convexlist.get(convexlist.size()-1).y();
				pointbetween1=360;
    			for(Point p:points) {//??????????????????????????????????
    				pointbetween2=calculateBearingToPoint(bearingpoint, currentX, currentY, (int)p.x(), (int)p.y());//????????????
    				if((pointbetween2<pointbetween1)&&(p!=convexlist.get(convexlist.size()-1))) {//????????
    					pointbetween1=pointbetween2;
    					convexPoint=p;
    				}
    			}
    			if(convexPoint==convexlist.get(0)) {//??????????????????????????????
    				break;
    			}
    			else {
    				if((pointbetween1==0)&&(bearingpoint==0)) {//??????????????????????????????????????????????????????????????????????????????????????????????
    					convexlist.remove(convexlist.size()-1);//????????????????????????????
    				}
    				bearingpoint=(bearingpoint+pointbetween1)%360;
    				convexlist.add(convexPoint);
    			}
    		}
    		for(int i=0;i<convexlist.size();i++) {//????????List??????????????????
    			convexset.add(convexlist.get(i));
    		}
    		return convexset;
    	}
    }

    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        //throw new RuntimeException("implement me!");
    	int i=0;
    	int k;
    	for(k=0;k<2;k++) {
    		i=0;
    		while(i<90) {
        		turtle.color(PenColor.RED);
        		turtle.forward(5);
        		turtle.turn(4);
        		i++;
        	}
    		turtle.turn(180);
    	}
    	
    	turtle.turn(90);
    	
    	for(k=0;k<2;k++) {
    		i=0;
    		while(i<90) {
        		turtle.color(PenColor.GREEN);
        		turtle.forward(5);
        		turtle.turn(4);
        		i++;
        	}
    		turtle.turn(180);
    	}
    	
    	turtle.turn(45);
    	for(k=0;k<2;k++) {
    		i=0;
    		while(i<90) {
        		turtle.color(PenColor.CYAN);
        		turtle.forward(5);
        		turtle.turn(4);
        		i++;
        	}
    		turtle.turn(90);
    	}
    	
    	for(k=0;k<2;k++) {
    		i=0;
    		while(i<90) {
        		turtle.color(PenColor.YELLOW);
        		turtle.forward(5);
        		turtle.turn(4);
        		i++;
        	}
    		turtle.turn(90);
    	}
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        //drawSquare(turtle, 40);
        //drawRegularPolygon(turtle, 8, 40);

         //draw the window 
        drawPersonalArt(turtle);
        turtle.draw();
    }

}
