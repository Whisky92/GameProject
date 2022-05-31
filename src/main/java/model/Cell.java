package model;

import org.tinylog.Logger;

import java.awt.*;

/**
 * A class containing the properties of a Cell
 */
public class Cell {
    Point p;
    States st;

    /**
     * A constructor for create a cell instance
     * @param a the x coordinate of the point
     * @param b the y coordinate of the point
     */
    public Cell(int a, int b)
    {
        Logger.info("Invoking the constructor of Cell class");
        p = new Point(a, b);
        st=States.EMPTY;
    }
    /**
     * Set the state of a cell to the given state
     * @param s a state to which the state member of the class instance will be set
     */
    public void setState(States s)
    {
        Logger.info("Using the setState method of Cell class");
        st=s;
    }

    /**
     * Returns with the state of the cell
     * @return the state of the cell
     */
    public States getState()
    {
        Logger.info("Using the getState method of Cell class");
        return st;
    }

    /**
     * Returns with the position of the cell
     * @return the position of the cell
     */
    public Point getPosition()
    {
        Logger.info("Using the getPosition method of Cell class");
        return p;
    }
}
