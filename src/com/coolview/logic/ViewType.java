package com.coolview.logic;

import java.io.File;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.coolview.ui.MainWindow;
import com.coolview.ui.menus.RepaintPane;

public class ViewType {
    

    public void changeToBig() {
        if (MainWindow.curImgWidth == MainWindow.BIGLABEL_WIDTH)
            return;
        MainWindow.curImgWidth = MainWindow.BIGLABEL_WIDTH;
        MainWindow.curImgHeight = MainWindow.BIGLABEL_HEIGHT;
        new RepaintPane().execute();

    }

    public void changeToMiddle() {
        if (MainWindow.curImgWidth == MainWindow.MIDDLELABEL_WIDTH)
            return;
        MainWindow.curImgWidth = MainWindow.MIDDLELABEL_WIDTH;
        MainWindow.curImgHeight = MainWindow.MIDDLELABEL_HEIGHT;
        new RepaintPane().execute();

    }

    public void changToSmall() {
        if (MainWindow.curImgWidth == MainWindow.SMALLLABEL_WIDTH)
            return;
        MainWindow.curImgWidth = MainWindow.SMALLLABEL_WIDTH;
        MainWindow.curImgHeight = MainWindow.SMALLLABEL_HEIGHT;
        new RepaintPane().execute();

    }

    public void sortByName() {
        if (MainWindow.curSortType.equals("name"))
            return;
        MainWindow.curSortType = "name";
        if (MainWindow.increase)
            MainWindow.imagesList.sort(new FileComparator_Name());
        else
            MainWindow.imagesList.sort(new FileComparator_ReName());

        new RepaintPane().execute();

    }

    public void sortByDate() {
        if (MainWindow.curSortType.equals("date"))
            return;
        MainWindow.curSortType = "date";
        if (MainWindow.increase)
            MainWindow.imagesList.sort(new FileComparator_Date());
        else
            MainWindow.imagesList.sort(new FileComparator_ReDate());

        new RepaintPane().execute();

    }

    public void sortByType() {
        if (MainWindow.curSortType.equals("type"))
            return;
        MainWindow.curSortType = "type";
        if (MainWindow.increase)
            MainWindow.imagesList.sort(new FileComparator_Extension());
        else
            MainWindow.imagesList.sort(new FileComparator_ReExtension());

        new RepaintPane().execute();

    }

    public void sortBySize() {
        if (MainWindow.curSortType.equals("size"))
            return;
        MainWindow.curSortType = "size";
        if (MainWindow.increase)
            MainWindow.imagesList.sort(new FileComparator_Size());
        else
            MainWindow.imagesList.sort(new FileComparator_ReSize());

        new RepaintPane().execute();

    }

    public void sortInIncrease(JFrame frame) {
        if (MainWindow.increase)
            return;
        MainWindow.increase = true;
        switch (MainWindow.curSortType) {
        case "name": {
            MainWindow.imagesList.sort(new FileComparator_Name());
            break;
        }
        case "date": {
            MainWindow.imagesList.sort(new FileComparator_Date());
            break;
        }
        case "type": {
            MainWindow.imagesList.sort(new FileComparator_Size());
            break;
        }
        default: {
            JOptionPane.showMessageDialog(frame, "排序出错", "错误", JOptionPane.WARNING_MESSAGE);
            break;
        }
        }
        new RepaintPane().execute();

    }

    public void sortInDecrease(JFrame frame) {
        if (!MainWindow.increase)
            return;
        MainWindow.increase = false;
        switch (MainWindow.curSortType) {
        case "name": {
            MainWindow.imagesList.sort(new FileComparator_ReName());
            break;
        }
        case "date": {
            MainWindow.imagesList.sort(new FileComparator_ReDate());
            break;
        }
        case "type": {
            MainWindow.imagesList.sort(new FileComparator_ReSize());
            break;
        }
        default: {
            JOptionPane.showMessageDialog(frame, "排序出错", "错误", JOptionPane.WARNING_MESSAGE);
            break;
        }
        }
        new RepaintPane().execute();

    }

}

class FileComparator_Name implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        if (o1.getName().equals(o2.getName()))
            return 1;

        return o1.compareTo(o2);
    }

}

class FileComparator_ReName implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        if (o2.getName().equals(o2.getName()))
            return 1;
        return o2.compareTo(o1);
    }

}

class FileComparator_Date implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        if (o1.lastModified() - o2.lastModified() >= 0)
            return 1;
        else
            return -1;
    }

}

class FileComparator_ReDate implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        if (o2.lastModified() - (o1.lastModified()) >= 0)
            return 1;
        else
            return -1;
    }

}

class FileComparator_Size implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        if (o1.length() - o2.length() >= 0)
            return -1;
        else
            return 1;
    }

}

class FileComparator_ReSize implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        if (o2.length() - o1.length() >= 0)
            return -1;
        else
            return 1;
    }

}

class FileComparator_Extension implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        String extensionOff1 = BasicFunction.getExtension(o1.getName());
        String extensionOff2 = BasicFunction.getExtension(o2.getName());
        return extensionOff1.compareTo(extensionOff2);
    }

}

class FileComparator_ReExtension implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        String extensionOff1 = BasicFunction.getExtension(o1.getName());
        String extensionOff2 = BasicFunction.getExtension(o2.getName());
        return extensionOff2.compareTo(extensionOff1);
    }

}


