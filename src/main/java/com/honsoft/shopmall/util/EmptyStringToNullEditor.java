package com.honsoft.shopmall.util;

import java.beans.PropertyEditorSupport;

public class EmptyStringToNullEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) {
        if ("".equals(text)) {
            setValue(null);
        } else {
            setValue(text);
        }
    }
}
