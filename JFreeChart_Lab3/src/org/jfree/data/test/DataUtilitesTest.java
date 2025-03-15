package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jfree.data.KeyedValues;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

public class DataUtilitiesTest {

    @Test
    public void sumColumn_A() {
        Mockery ctx = new Mockery();
        final Values2D matrix = ctx.mock(Values2D.class);
        ctx.checking(new Expectations() {{
            one(matrix).getRowCount(); will(returnValue(4));
            one(matrix).getValue(0, 0); will(returnValue(2.2));
            one(matrix).getValue(1, 0); will(returnValue(3.1));
            one(matrix).getValue(2, 0); will(returnValue(4.7));
            one(matrix).getValue(3, 0); will(returnValue(1.0));
        }});
        double total = DataUtilities.calculateColumnTotal(matrix, 0);
        assertEquals(11.0, total, 0.000000001d);
    }

    @Test
    public void sumColumn_B() {
        Mockery ctx = new Mockery();
        final Values2D matrix = ctx.mock(Values2D.class);
        ctx.checking(new Expectations() {{
            one(matrix).getRowCount(); will(returnValue(3));
            one(matrix).getValue(0, 1); will(returnValue(5.5));
            one(matrix).getValue(1, 1); will(returnValue(7.2));
            one(matrix).getValue(2, 1); will(returnValue(3.3));
        }});
        double total = DataUtilities.calculateColumnTotal(matrix, 1);
        assertEquals(16.0, total, 0.000000001d);
    }

    @Test
    public void sumColumn_invalid() {
        Mockery ctx = new Mockery();
        final Values2D matrix = ctx.mock(Values2D.class);
        ctx.checking(new Expectations() {{
            one(matrix).getRowCount(); will(returnValue(2));
            one(matrix).getValue(0, 5); will(throwException(new IndexOutOfBoundsException()));
        }});
        try {
            DataUtilities.calculateColumnTotal(matrix, 5);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void sumRow_A() {
        Mockery ctx = new Mockery();
        final Values2D matrix = ctx.mock(Values2D.class);
        ctx.checking(new Expectations() {{
            one(matrix).getColumnCount(); will(returnValue(2));
            one(matrix).getValue(0, 0); will(returnValue(3.3));
            one(matrix).getValue(0, 1); will(returnValue(2.2));
        }});
        double result = DataUtilities.calculateRowTotal(matrix, 0);
        assertEquals(5.5, result, 0.000000001d);
    }

    @Test
    public void sumRow_B() {
        Mockery ctx = new Mockery();
        final Values2D matrix = ctx.mock(Values2D.class);
        ctx.checking(new Expectations() {{
            one(matrix).getColumnCount(); will(returnValue(3));
            one(matrix).getValue(1, 0); will(returnValue(6.6));
            one(matrix).getValue(1, 1); will(returnValue(1.4));
            one(matrix).getValue(1, 2); will(returnValue(2.0));
        }});
        double result = DataUtilities.calculateRowTotal(matrix, 1);
        assertEquals(10.0, result, 0.000000001d);
    }

    @Test
    public void rowInvalid() {
        Mockery ctx = new Mockery();
        final Values2D matrix = ctx.mock(Values2D.class);
        ctx.checking(new Expectations() {{
            one(matrix).getColumnCount(); will(returnValue(3));
            one(matrix).getValue(-1, 0); will(throwException(new IndexOutOfBoundsException()));
        }});
        try {
            DataUtilities.calculateRowTotal(matrix, -1);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void percentValues_A() {
        Mockery ctx = new Mockery();
        final KeyedValues data = ctx.mock(KeyedValues.class);
        ctx.checking(new Expectations() {{
            allowing(data).getItemCount(); will(returnValue(3));
            allowing(data).getKey(0); will(returnValue("P"));
            allowing(data).getKey(1); will(returnValue("Q"));
            allowing(data).getKey(2); will(returnValue("R"));
            allowing(data).getValue(0); will(returnValue(4));
            allowing(data).getValue(1); will(returnValue(6));
            allowing(data).getValue(2); will(returnValue(10));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(data);
        assertEquals(0.2, (Double) result.getValue(0), 0.000000001d);
        assertEquals(0.5, (Double) result.getValue(1), 0.000000001d);
        assertEquals(1.0, (Double) result.getValue(2), 0.000000001d);
    }

    @Test
    public void percentValues_invalid() {
        try {
            DataUtilities.getCumulativePercentages(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void numberArray() {
        double[] values = {2.0, 4.0, 6.0};
        Number[] result = DataUtilities.createNumberArray(values);
        assertEquals(3, result.length);
        assertEquals(2.0, result[0]);
        assertEquals(4.0, result[1]);
        assertEquals(6.0, result[2]);
    }

    @Test
    public void numberArray_null() {
        try {
            DataUtilities.createNumberArray(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void numberArray2D() {
        double[][] values = {{1.1, 2.2}, {3.3, 4.4}};
        Number[][] result = DataUtilities.createNumberArray2D(values);
        assertEquals(2, result.length);
        assertEquals(2, result[0].length);
        assertEquals(1.1, result[0][0]);
        assertEquals(2.2, result[0][1]);
        assertEquals(3.3, result[1][0]);
        assertEquals(4.4, result[1][1]);
    }

    @Test
    public void numberArray2D_null() {
        try {
            DataUtilities.createNumberArray2D(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
}