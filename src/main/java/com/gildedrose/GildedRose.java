package com.gildedrose;

import static com.gildedrose.Item.*;

class GildedRose {
    private Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        if (items == null) {
            return;
        }
        for (var item : items){
            item.sellIn = getSelling(item);
            item.quality = getQuality(item);
        }
    }

    private int getSelling(Item item){
        if (SULFURAS.equals(item.name)) {
            return item.sellIn;
        }
        return item.sellIn - 1;
    }

    private int getQuality(Item item){
        var name = item.name;
        var quality = item.quality;
        if (quality == 0){
            return 0;
        }
        if (SULFURAS.equals(name)){
            return 80;
        }
        switch (name){
            case AGED_BRIE:
                quality = getAgedBrieQuality(quality);
                break;
            case BACKSTAGE:
                quality = getBackstageQuality(quality, item.sellIn);
                break;
            case CONJURED:
                quality = getConjuredQuality(quality);
                break;
            default:
                quality = getDefaultQuality(quality, item.sellIn);
                break;
        }
        if (quality > 50) {
            return 50;
        }
        return Math.max(quality, 0);
    }

    private int getConjuredQuality(int quality) {
        return quality - 2;
    }

    private int getDefaultQuality(int quality, int sellIn) {
        if (sellIn < 0){
            return 0;
        }
        return quality - 1;
    }

    private int getBackstageQuality(int quality, int sellIn) {
        if (sellIn >= 10){
            return quality + 1 ;
        } else if (sellIn >= 5) {
            return quality + 2;
        } else if (sellIn >= 0) {
            return quality + 3;
        }
        return 0;
    }

    private int getAgedBrieQuality(int quality) {
        return quality + 1;
    }

    public Item getItem(int index) {
        if (items == null || index > items.length){
            return null;
        }
        return items[index];
    }
}
