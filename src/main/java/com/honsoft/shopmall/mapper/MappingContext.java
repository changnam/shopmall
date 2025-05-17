package com.honsoft.shopmall.mapper;

public class MappingContext {
    private final boolean isUpdate;

    public MappingContext(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean isUpdate() {
        return isUpdate;
    }
}
