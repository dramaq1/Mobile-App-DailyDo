package com.example.dailydo.contract;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class CustomAction {
    @DrawableRes
    private int iconRes;
    @StringRes
    private int textRes;
    private Runnable onCustomAction;

    public CustomAction(int iconRes, int textRes, Runnable onCustomAction) {
        this.iconRes = iconRes;
        this.textRes = textRes;
        this.onCustomAction = onCustomAction;
    }

    @DrawableRes
    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(@DrawableRes int iconRes) {
        this.iconRes = iconRes;
    }

    @StringRes
    public int getTextRes() {
        return textRes;
    }

    public void setTextRes(@StringRes int textRes) {
        this.textRes = textRes;
    }

    public Runnable getOnCustomAction() {
        return onCustomAction;
    }

    public void setOnCustomAction(Runnable onCustomAction) {
        this.onCustomAction = onCustomAction;
    }
}
