package com.coolview.ui.model;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;

import com.coolview.logic.BasicFunction;
import com.coolview.ui.panes.DirectoryTree;

public class FileNode {
    private File file;

    public FileNode(File file) {
        super();
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return file.getName().length()  > 0 ?file.getName():file.getPath();
    }
    
    public boolean expand(DefaultMutableTreeNode parent) {
        
        DefaultMutableTreeNode flag = (DefaultMutableTreeNode) parent.getFirstChild();
        if (flag == null){
            return false;
        }
        
        Object obj = flag.getUserObject();
        if (!(obj instanceof Boolean)) // obj 中没有AllowChild,表示没有子结点，即已全部展开
            return false;
        parent.removeAllChildren();
        
        File []files = BasicFunction.getDir(file);
        if (files == null)
            return true;
        
        
        LinkedList<FileNode> fileNodes = new LinkedList<FileNode>();
        
//        fileNodes.sort(new Comparator());
        for (File f : files) {
              FileNode newNode = new FileNode(f);
              fileNodes.add(newNode);
        }
        
        Iterator<FileNode> iterator = fileNodes.iterator();
        
        while(iterator.hasNext()){
            FileNode node = (FileNode) iterator.next();
            IconData iData = new IconData(DirectoryTree.ICON_FOLDER,DirectoryTree.ICON_EXPANDEDFOLDER, node);   
            DefaultMutableTreeNode top = new DefaultMutableTreeNode(iData);
            parent.add(top);
            if (BasicFunction.hasSubDir(node.file)){
                top.add(new DefaultMutableTreeNode(new Boolean(true)));
            }
        }
        
        return true;
        
    }
    
    

}
