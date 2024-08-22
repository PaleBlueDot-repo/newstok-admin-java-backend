package com.NewsTok.Admin.Dtos;

import com.NewsTok.Admin.Models.Reels;

public class weightDto {
    private Reels reels;
    private int Weight;

    public Reels getReels() {
        return reels;
    }

    public void setReels(Reels reels) {
        this.reels = reels;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }
}
