package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Collections;

public class GildedRoseTest {

    @Test
    public void foo() {
        Item[] items = new Item[] { new Item("fixme", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        var item = app.getItem(0);
        assertNotNull(item);
        assertEquals("fixme", item.name);
    }

    @Test
    public void normalCase() {
        Item[] items = new Item[] { new Item("fixme", 1, 1) };
        var app = new GildedRose(items);
        app.updateQuality();
        var item = app.getItem(0);
        assertNotNull(item);
        assertEquals("fixme", item.name);
        assertEquals(0, item.quality);
        assertEquals(0, item.sellIn);
    }

    /**
     * Once the sell by date has passed, Quality degrades twice as fast
     */
    @Test
    public void sellDatePassed() {
        Item[] items = new Item[] { new Item("fixme", 0, 2) };
        var app = new GildedRose(items);
        app.updateQuality();
        var item = app.getItem(0);
        assertNotNull(item);
        assertEquals("fixme", item.name);
        assertEquals(0, item.quality);
        assertEquals(-1, item.sellIn);
    }

    /**
     * The Quality of an item is never negative
     */
    @Test
    public void qualityCannotBeNegative() {
        Item[] items = new Item[] { new Item("fixme", 0, 0) };
        var app = new GildedRose(items);
        app.updateQuality();
        var item = app.getItem(0);
        assertNotNull(item);
        assertEquals("fixme", item.name);
        assertEquals(0, item.quality);
        assertEquals(-1, item.sellIn);
    }

    /**
     * "Aged Brie" actually increases in Quality the older it gets
     */
    @Test
    public void agedBrieQualityIncreaseWithTime() {
        Item[] items = new Item[] { new Item("Aged Brie", 10, 1) };
        var app = new GildedRose(items);
        app.updateQuality();
        var item = app.getItem(0);
        assertNotNull(item);
        assertEquals("Aged Brie", item.name);
        assertEquals(2, item.quality);
        assertEquals(9, item.sellIn);
    }

    /**
     * The Quality of an item is never more than 50
     */
    @Test
    public void itemQualityCannotMaximumis50(){
        Item[] items = new Item[] { new Item("Aged Brie", 10, 50) };
        var app = new GildedRose(items);
        app.updateQuality();
        var item = app.getItem(0);
        assertNotNull(item);
        assertEquals("Aged Brie", item.name);
        assertEquals(50, item.quality);
        assertEquals(9, item.sellIn);
    }

    /**
     * "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
     */
    @Test
    public void sulfurasNeverDecreaseHisQuality() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 10, 80) };
        var app = new GildedRose(items);
        app.updateQuality();
        var item = app.getItem(0);
        assertNotNull(item);
        assertEquals("Sulfuras, Hand of Ragnaros", item.name);
        assertEquals(80, item.quality);
        assertEquals(10, item.sellIn);
    }

    /**
     * "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
     * 	Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
     * 	Quality drops to 0 after the concert
     */
    @Test
    public void bacstagePassIncrease1WhenSellinHigherThan10() {

        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 20, 10) };
        var app = new GildedRose(items);
        app.updateQuality();
        var item = app.getItem(0);
        assertNotNull(item);
        assertEquals("Backstage passes to a TAFKAL80ETC concert", item.name);
        assertEquals(11, item.quality);
        assertEquals(19, item.sellIn);
    }

    /**
     * "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
     * 	Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
     * 	Quality drops to 0 after the concert
     */
    @Test
    public void bacstagePassIncrease2WhenSellinIsBetween6and10() {

        Item[] items = new Item[] {
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 10),
                new Item("Backstage passes to a TAFKAL80ETC concert", 6, 10),
        };
        var app = new GildedRose(items);
        app.updateQuality();
        var item1 = app.getItem(0);
        assertNotNull(item1);
        assertEquals("Backstage passes to a TAFKAL80ETC concert", item1.name);
        assertEquals(12, item1.quality);
        assertEquals(9, item1.sellIn);


        var item2 = app.getItem(1);
        assertNotNull(item2);
        assertEquals("Backstage passes to a TAFKAL80ETC concert", item2.name);
        assertEquals(12, item2.quality);
        assertEquals(5, item2.sellIn);
    }

    /**
     * "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
     * 	Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
     * 	Quality drops to 0 after the concert
     */
    @Test
    public void bacstagePassIncrease3WhenSellinIsBetween1and5() {

        Item[] items = new Item[] {
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 10),
                new Item("Backstage passes to a TAFKAL80ETC concert", 1, 10),
        };
        var app = new GildedRose(items);
        app.updateQuality();
        var item1 = app.getItem(0);
        assertNotNull(item1);
        assertEquals("Backstage passes to a TAFKAL80ETC concert", item1.name);
        assertEquals(13, item1.quality);
        assertEquals(4, item1.sellIn);


        var item2 = app.getItem(1);
        assertNotNull(item2);
        assertEquals("Backstage passes to a TAFKAL80ETC concert", item2.name);
        assertEquals(13, item2.quality);
        assertEquals(0, item2.sellIn);
    }

    /**
     * "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
     * 	Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
     * 	Quality drops to 0 after the concert
     */
    @Test
    public void bacstagePassQualityIs0WhenSellinIs0() {

        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 10) };
        var app = new GildedRose(items);
        app.updateQuality();
        var item = app.getItem(0);
        assertNotNull(item);
        assertEquals("Backstage passes to a TAFKAL80ETC concert", item.name);
        assertEquals(0, item.quality);
        assertEquals(-1, item.sellIn);
    }

}
