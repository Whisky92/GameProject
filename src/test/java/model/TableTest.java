package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @Test
    void isGoalState1()
    {
        Table testTable = new Table();
        testTable.showPossibleSteps();
        Point[] pointSequence = new Point[] {
                new Point(2,0), new Point(4,0), new Point(4,3),
                new Point(4,5), new Point(4,7), new Point(7,7)
        };
        for(var point : pointSequence)
        {
            testTable.movePiece(point);
        }
        assertTrue(testTable.isGoalState());
    }
    @Test
    void isGoalState2()
    {
        Table testTable = new Table();
        assertFalse(testTable.isGoalState());
    }

    @Test
    void showPossibleSteps1()
    {
        Table testTable = new Table();
        testTable.showPossibleSteps();
        assertTrue(testTable.getPossibleSteps().size()==2);
        assertTrue(testTable.getPossibleSteps().get(1).getPosition().x == 0 && testTable.getPossibleSteps().get(1).getPosition().y == 2);
        assertTrue(testTable.getPossibleSteps().get(0).getPosition().x == 2 && testTable.getPossibleSteps().get(0).getPosition().y == 0);
    }

    @Test
    void showPossibleSteps2()
    {
        Table testTable = new Table();
        testTable.showPossibleSteps();
        Point[] pointSequence = new Point[] {
                new Point(0,2), new Point(0,4), new Point(3,4) };
        for(var point : pointSequence)
            testTable.movePiece(point);
        assertFalse(testTable.getPossibleSteps().size()==2);
        assertTrue(testTable.getPossibleSteps().size()==4);
        assertTrue(testTable.getPossibleSteps().get(0).getPosition().x == 3 && testTable.getPossibleSteps().get(0).getPosition().y == 2);
        assertTrue(testTable.getPossibleSteps().get(1).getPosition().x == 3 && testTable.getPossibleSteps().get(1).getPosition().y == 6);
        assertTrue(testTable.getPossibleSteps().get(2).getPosition().x == 1 && testTable.getPossibleSteps().get(2).getPosition().y == 4);
        assertTrue(testTable.getPossibleSteps().get(3).getPosition().x == 5 && testTable.getPossibleSteps().get(3).getPosition().y == 4);
    }

    @Test
    void showPossibleSteps3()
    {
        Table testTable = new Table();
        testTable.showPossibleSteps();
        Point[] pointSequence = new Point[] {
                new Point(0,2), new Point(0,4), new Point(3,4),
                new Point(3,2), new Point(3,5), new Point(3,2),
                new Point(3,4)};
        for(var point : pointSequence)
            testTable.movePiece(point);
        assertFalse(testTable.getPossibleSteps().size()==2);
        assertTrue(testTable.getPossibleSteps().size()==4);
        assertTrue(testTable.getPossibleSteps().get(0).getPosition().x == 3 && testTable.getPossibleSteps().get(0).getPosition().y == 1);
        assertTrue(testTable.getPossibleSteps().get(1).getPosition().x == 3 && testTable.getPossibleSteps().get(1).getPosition().y == 7);
        assertTrue(testTable.getPossibleSteps().get(2).getPosition().x == 0 && testTable.getPossibleSteps().get(2).getPosition().y == 4);
        assertTrue(testTable.getPossibleSteps().get(3).getPosition().x == 6 && testTable.getPossibleSteps().get(3).getPosition().y == 4);
    }

    @Test
    void movePiece1()
    {
        Table testTable = new Table();
        testTable.showPossibleSteps();
        testTable.movePiece(new Point(0,2));
        assertTrue(testTable.getPlayerPoint().x==0 && testTable.getPlayerPoint().y==2);
    }

    @Test
    void movePiece2()
    {
        Table testTable = new Table();
        testTable.showPossibleSteps();
        Point[] pointSequence = new Point[] {
                new Point(0,2), new Point(0,4) };
        for(var point : pointSequence)
            testTable.movePiece(point);
        assertTrue(testTable.getPlayerPoint().x==0 && testTable.getPlayerPoint().y==4);
    }


    @Test
    void movePiece3()
    {
        Table testTable = new Table();
        testTable.showPossibleSteps();
        Point[] pointSequence = new Point[] {
                new Point(0,2), new Point(0,4), new Point(3,4) };
        for(var point : pointSequence)
            testTable.movePiece(point);
        assertTrue(testTable.getPlayerPoint().x==3 && testTable.getPlayerPoint().y==4);
    }

    @Test
    void movePiece4()
    {
        Table testTable = new Table();
        testTable.showPossibleSteps();
        testTable.movePiece(new Point(0,1));
        assertTrue(testTable.getPlayerPoint().x==0 && testTable.getPlayerPoint().y==0);
    }

    @Test
    void movePiece5()
    {
        Table testTable = new Table();
        testTable.showPossibleSteps();
        Point[] pointSequence = new Point[] {
                new Point(2,0), new Point(4,0), new Point(4,3),
                new Point(4,5), new Point(4,7), new Point(7,7)
        };
        for(var point : pointSequence)
        {
            testTable.movePiece(point);
        }
        assertTrue(testTable.getPlayerPoint().x==7 && testTable.getPlayerPoint().y==7);
    }
}