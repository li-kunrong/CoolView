package com.coolview.ui.model;

import javax.swing.Icon;

public class IconData {
    private Icon icon;
    private Icon expandedIcon;
    private Object data;

    public IconData(Icon icon, Object data) {
//        super();
        this.icon = icon;
        this.expandedIcon = null;
        this.data = data;
    }

    public IconData(Icon icon, Icon expandedIcon, Object data) {
//        super();
        this.icon = icon;
        this.expandedIcon = expandedIcon;
        this.data = data;
    }

    public Icon getIcon() {
        return icon;
    }

    public Icon getExpandedIcon() {
        return expandedIcon;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public Object getObject() {
        return data;
    }

}
