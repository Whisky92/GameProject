package model;

import org.tinylog.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static java.lang.String.format;
public class Table {

    private final int tableSize = 8;
    private int stepCounter = 0;
    private int steps = 2;

    private LinkedList<Cell> possibleSteps = new LinkedList<>();

    private Point playerPoint = new Point(0, 0);
    Cell gameTable[][] = new Cell[tableSize][tableSize];

    private Point[] PositionsOfCircleTiles = new Point[]
            {
                    new Point(0, 4),
                    new Point(1, 2),
                    new Point(1, 6),
                    new Point(3, 2),
                    new Point(3, 4),
                    new Point(4, 0),
                    new Point(4, 3),
                    new Point(4, 7),
                    new Point(5, 3),
                    new Point(5, 6),
                    new Point(6, 2),
                    new Point(6, 7),
                    new Point(7, 0)
            };


    private Point[] PositionsOfRectangleTiles = new Point[]
            {
                    new Point(2, 2),
                    new Point(2, 7),
                    new Point(4, 1),
                    new Point(5, 5),
                    new Point(7, 3)
            };
    private Point startPosition = new Point(0, 0);
    private Point goalPosition = new Point(7, 7);

    /**
     * Creates a {@code Table} object that corresponds to the original
     * initial state to the puzzle.
     */
    public Table() {
        Logger.info("Using the constructor of Table class");
        playerPoint.x = 0;
        playerPoint.y = 0;

        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < tableSize; j++) {
                gameTable[i][j] = new Cell(i, j);
                addStatesToTableCells(i, j);
            }
        }
    }

    /**
     * Returns with the position of the player
     * @return the position of the player
     */
    public Point getPlayerPoint()
    {
        return playerPoint;
    }

    /**
     * Returns with the gametable
     * @return the gametable that is getting returned
     */
    public Cell[][] getGameTable()
    {
        Logger.info("Using the getGameTable method of Table class");
        return gameTable;
    }

    /**
     * Returns with the LinkedList containing the possible steps
     *
     * @return the LinkedList which contains the possible steps
     */
    public LinkedList<Cell> getPossibleSteps()
    {
        Logger.info("Using the getPossibleSteps method of Table class");
        return possibleSteps;
    }

    /**
     * Returns with the stepCounter, that counts the number of moves
     *
     * @return the variable containing the number of moves made
     */
    public int getStepCounter()
    {
        Logger.info("Using the getStepCounter method of Table class");
        return stepCounter;
    }

    /**
     *
     * {@return whether the current state of the game is GoalState or not}
     */
    public boolean isGoalState()
    {
        return playerPoint.x==7 && playerPoint.y==7;
    }

    /**
     * Moves the piece to the point given as parameter.
     *
     * @param p the point where the point has to go
     */
    public void movePiece(Point p) {
        Logger.trace("Entering the movePiece method of Table with Point {}",p);
        boolean contain = false;
        for (Cell cell : possibleSteps) {
            if (p.x == cell.getPosition().x && p.y == cell.getPosition().y)
                contain = true;
        }
        if (!contain) {
            Logger.warn("You can't step on this cell");
        } else {
            Cell c = cellOfGivenPos(p);
            if(c.getState()==States.GOAL) {
                playerPoint.x=p.x;
                playerPoint.y=p.y;
                Logger.info("Goal successfully reached!");
            }else if(c.getState()==States.CIRCLE) {
                Logger.info("Entered to a Cell with Circle State");
                if(steps==2)
                    steps=3;
                else
                    steps=2;
                playerPoint.x=p.x;
                playerPoint.y=p.y;
            }else{
                Logger.info("Entered to a cell with Empty or Start State");
                playerPoint.x=p.x;
                playerPoint.y=p.y;
            }
        }
        stepCounter++;
        Logger.info("Player's current position: x: {} y: {}", playerPoint.x, playerPoint.y);
        Logger.info("Possible steps:");
        showPossibleSteps();
    }

    /**
     * Shows the cells where the piece is able to step to.
     */
    public void showPossibleSteps()
    {
        Logger.info("Entering to the showPossibleSteps method of Table class");
        possibleSteps.clear();
        if(stepCounter==0) {
            showStepsIfStart();
            for(Cell c : possibleSteps){
                Logger.info("x: {}, y: {}",c.getPosition().x, c.getPosition().y);
            }
        } else {
            showSteps();
            for(Cell c : possibleSteps) {
                Logger.info("x: {}, y: {}", c.getPosition().x, c.getPosition().y);
            }
        }
    }

    private void showStepsIfStart()
    {
        Logger.info("Entering to the showPossibleStepsIfStart method of Table class");
        if(correctCell(getRightTwo(playerPoint)))
            possibleSteps.add(cellOfGivenPos(getRightTwo(playerPoint).get(1)));
        if(correctCell(getDownTwo(playerPoint)))
            possibleSteps.add(cellOfGivenPos(getDownTwo(playerPoint).get(1)));
    }


    private void showSteps()
    {
        Logger.info("Entering to the showSteps method of Table class");
        if(steps==2) {
            Logger.info("Steps: {}", steps);
            if (correctCell(getUpTwo(playerPoint)))
                possibleSteps.add(cellOfGivenPos(getUpTwo(playerPoint).get(1)));
            if (correctCell(getDownTwo(playerPoint)))
                possibleSteps.add(cellOfGivenPos(getDownTwo(playerPoint).get(1)));
            if (correctCell(getLeftTwo(playerPoint)))
                possibleSteps.add(cellOfGivenPos(getLeftTwo(playerPoint).get(1)));
            if (correctCell(getRightTwo(playerPoint)))
                possibleSteps.add(cellOfGivenPos(getRightTwo(playerPoint).get(1)));
        }else {
            Logger.info("Steps: {}", steps);
            if (correctCell(getUpThree(playerPoint)))
                possibleSteps.add(cellOfGivenPos(getUpThree(playerPoint).get(1)));
            if (correctCell(getDownThree(playerPoint)))
                possibleSteps.add(cellOfGivenPos(getDownThree(playerPoint).get(1)));
            if (correctCell(getLeftThree(playerPoint)))
                possibleSteps.add(cellOfGivenPos(getLeftThree(playerPoint).get(1)));
            if (correctCell(getRightThree(playerPoint)))
                possibleSteps.add(cellOfGivenPos(getRightThree(playerPoint).get(1)));
        }
    }



    private void addStatesToTableCells(int a, int b) {
        Point temporaryPosition = new Point(a, b);
        Logger.info("Entering the addStatesToTableCells with {} and {} coordinates",a,b);
        if (checkIfArrayContainsPoint(temporaryPosition, PositionsOfCircleTiles))
            gameTable[a][b].setState(States.CIRCLE);
        else if (checkIfArrayContainsPoint(temporaryPosition, PositionsOfRectangleTiles))
            gameTable[a][b].setState(States.RECTANGLE);
        else if (temporaryPosition.getX() == startPosition.getX() && temporaryPosition.getY() == startPosition.getY())
            gameTable[a][b].setState(States.START);
        else if (temporaryPosition.getX() == goalPosition.getX() && temporaryPosition.getY() == goalPosition.getY())
            gameTable[a][b].setState(States.GOAL);
        else
            gameTable[a][b].setState(States.EMPTY);

    }


    private boolean correctCell(ArrayList<Point> points)
    {
        Logger.info("Entering the correctCell method of Table class");
        if(!partOfTable(points.get(1)))
            return false;
        else
            if(cellOfGivenPos(points.get(1)).getState()==States.RECTANGLE)
                return false;
            else
                return true;
    }


    private boolean partOfTable(Point p)
    {
        Logger.info("Entering to the partOfTable method with Point {}",p);
        if(p.x>=0 && p.x<tableSize && p.y>=0 && p.y<tableSize)
            return true;
        return false;
    }

    private Cell cellOfGivenPos(Point p)
    {
        Logger.info("Entering to the cellOfGivenPos method with Point {}",p);
        int pos1=0;
        int pos2=0;
        for(int i=0; i<tableSize; i++)
        {
            for(int j=0; j<tableSize; j++)
            {
                if(i==p.x && j==p.y)
                {
                    Logger.info("i: {} j: {}",i,j);
                    pos1=i;
                    pos2=j;
                    break;
                }
            }
        }
        return gameTable[pos1][pos2];
    }

    private boolean checkIfArrayContainsPoint(Point p, Point[] arr) {
        Logger.info("Entering to the checkIfArrayContainsPoint method of Table class");
        boolean contain = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].x == p.x && arr[i].y == p.y) {
                contain = true;
                Logger.info("Contain: {}",contain);
                break;
            }
        }
        return contain;
    }

    private ArrayList<Point> getRightTwo(Point p)
    {
        Logger.info("Entering to the getRightTwo method with Point {}",p);
        return new ArrayList<>(Arrays.asList(p, new Point((playerPoint.x)+2, playerPoint.y)));
    }

    private ArrayList<Point> getRightThree(Point p)
    {
        Logger.info("Entering to the getRightThree method with Point {}",p);
        return new ArrayList<>(Arrays.asList(p, new Point((playerPoint.x)+3, playerPoint.y)));
    }


    private ArrayList<Point> getLeftTwo(Point p)
    {
        Logger.info("Entering to the getLeftTwo method with Point {}",p);
        return new ArrayList<>(Arrays.asList(p, new Point((playerPoint.x)-2, playerPoint.y)));
    }

    private ArrayList<Point> getLeftThree(Point p)
    {
        Logger.info("Entering to the getLeftThree method with Point {}",p);
        return new ArrayList<>(Arrays.asList(p, new Point((playerPoint.x)-3, playerPoint.y)));
    }

    private ArrayList<Point> getDownTwo(Point p)
    {
        Logger.info("Entering to the getDownTwo method with Point {}",p);
        return new ArrayList<>(Arrays.asList(p, new Point(playerPoint.x, (playerPoint.y)+2)));
    }

    private ArrayList<Point> getDownThree(Point p)
    {
        Logger.info("Entering to the getDownThree method with Point {}",p);
        return new ArrayList<>(Arrays.asList(p, new Point(playerPoint.x, (playerPoint.y)+3)));
    }


    private ArrayList<Point> getUpTwo(Point p)
    {
        Logger.info("Entering to the getUpTwo method with Point {}",p);
        return new ArrayList<>(Arrays.asList(p, new Point(playerPoint.x, (playerPoint.y)-2)));
    }

    private ArrayList<Point> getUpThree(Point p)
    {
        Logger.info("Entering to the getUpThree method with Point {}",p);
        return new ArrayList<>(Arrays.asList(p, new Point(playerPoint.x, (playerPoint.y)-3)));
    }


}
