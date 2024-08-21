package com.NewsTok.Admin.Dtos;

import java.util.List;

public class ReelsRequestDto {
    private List<Long> ids;

    // Getter and setter
    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
