package com.coolview.ui.panes;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.coolview.logic.BasicFunction;
import com.coolview.logic.ShowImageTask;
import com.coolview.ui.MainWindow;
import com.coolview.ui.menus.ManagerMenu;
import com.coolview.ui.model.FileNode;
import com.coolview.ui.model.IconCellRenderer;
import com.coolview.ui.model.IconData;



public class DirectoryTree extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTree tree;
    private DefaultTreeModel treeModel;
    private JTextField showNumOfPictures;
    private String tempNode = null; // 用于记录上一次访问的结点
    private ExpandTask expandTask;
    private SelectedTask selectedTask; // 选择线程
    private ArrayList<SelectedTask> TempSelectedTasks; // 选择线程
    private ShowImageTask siTask = null; // 开启新的线程
    private ShowImageTask tempSiTask = null; // 用于记录上一次开启的线程，并进行wait和add操作
    private ArrayList<ShowImageTask> siTaskList; // 记录所有打开的siTask
    private HashMap<String,ShowImageTask> JudgeTaskNode;  //用于判断是否有开启过线程
    
    //设置图标
    public static final ImageIcon ICON_COMPUTER = new ImageIcon("icon\\computer.png");
    public static final ImageIcon ICON_COMPUTERCLOSE = new ImageIcon("icon\\computerClose.png");
    public static final ImageIcon ICON_DRIVE = new ImageIcon("icon\\drive.png");
    public static final ImageIcon ICON_FOLDER = new ImageIcon("icon\\folder.png");
    public static final ImageIcon ICON_EXPANDEDFOLDER = new ImageIcon("icon\\expandedfolder.png");

    public static FileNode getFileNode(DefaultMutableTreeNode node) {
        if (node == null)
            return null;
        Object obj = node.getUserObject();
        if (obj instanceof IconData)
            obj = ((IconData) obj).getObject();
        if (obj instanceof FileNode)
            return (FileNode) obj;
        else
            return null;
    }

    public static DefaultMutableTreeNode getTreeNode(TreePath path) {
        return (DefaultMutableTreeNode) (path.getLastPathComponent());
    }

    public DirectoryTree() {
        super(new BorderLayout());
        setFocusable(false);
        // 根目录

        DefaultMutableTreeNode top = new DefaultMutableTreeNode(new IconData(ICON_COMPUTERCLOSE, ICON_COMPUTER, "此电脑"));

        DefaultMutableTreeNode node;

        // 列出系统根
        File[] roots = File.listRoots();
        for (int i = 0; i < roots.length; i++) {
            // 添加磁盘结点，同时设置结点的图片，并设为有子结点
            node = new DefaultMutableTreeNode(new IconData(ICON_DRIVE, ICON_DRIVE, new FileNode(roots[i])));
            top.add(node);
            node.add(new DefaultMutableTreeNode(new Boolean(true)));
        }

        treeModel = new DefaultTreeModel(top);

        tree = new JTree(treeModel);
        tree.putClientProperty("JTree.lineStyle", "Angled");
        // 设置结点样式
        TreeCellRenderer renderer = new IconCellRenderer();
        tree.setCellRenderer(renderer);

        tree.addTreeExpansionListener(new DirExpansionListener());
        tree.addTreeSelectionListener(new DirSelectionListener());
//        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); // 单一选择
//        tree.setShowsRootHandles(true);
        tree.setEditable(false);
        tree.setFocusable(false);

        JScrollPane jsp = new JScrollPane();
        jsp.getViewport().add(tree);
        add(jsp, BorderLayout.CENTER);

        showNumOfPictures = new JTextField("");
        showNumOfPictures.setEditable(false);
        this.add(showNumOfPictures, BorderLayout.SOUTH);
        setVisible(true);
        
//        MainWindow.showSelcet_Pictures = new JTextField("");
//        MainWindow.showSelcet_Pictures.setEditable(false);
//        this.add(MainWindow.showSelcet_Pictures, BorderLayout.NORTH);
//        setVisible(true);
    }

    class ExpandTask extends SwingWorker<Void, Void> {

        DefaultMutableTreeNode node;

        public ExpandTask(DefaultMutableTreeNode node) {
            this.node = node;
        }

        public void setTreeNode(DefaultMutableTreeNode node) {
            this.node = node;
        }

        @Override
        public Void doInBackground() {
            final FileNode fnode = getFileNode(node);
            if (fnode != null && fnode.expand(node)) {
                treeModel.reload(node); // 更新该结点作为根的目录树
            }
            return null;
        }

        @Override
        public void done() {
        }
    }

    // 展开监听器，创建展开结点的树
    class DirExpansionListener implements TreeExpansionListener {
        @Override
        public void treeExpanded(TreeExpansionEvent event) {
            final DefaultMutableTreeNode node = getTreeNode(event.getPath());
            expandTask = new ExpandTask(node);
            expandTask.execute();
        }

        public void treeCollapsed(TreeExpansionEvent ignore) {
        }
    }// end of DirExpansionListener
     // 选择监听器后台线程

    class SelectedTask extends SwingWorker<Void, Void> {

        DefaultMutableTreeNode node;

        public SelectedTask(DefaultMutableTreeNode node) {
            this.node = node;
        }

        public void setTreeNode(DefaultMutableTreeNode node) {
            this.node = node;
        }

        @Override
        public Void doInBackground() {

            FileNode fnode = getFileNode(node);
            if (fnode != null) {
                // showPathArea.setText(fnode.getFile().getName());
                // 未完成
                // 获取选择目录下的所有图片到imageList中
                // 并将得到的数据传到showAll区域中

                File file = fnode.getFile();
                MainWindow.curNodePath = file;
                File[] images = BasicFunction.getImages(file);

                for (File i : images)
                    MainWindow.imagesList.add(i);
            } else {
                // showPathArea.setText("");
                // top结点才不会使得fnode == null
                // 此时没有任何图片
            }
            return null;
        }

        @Override
        public void done() {
            // System.out.println(javax.swing.SwingUtilities.isEventDispatchThread());
            showNumOfPictures.setText(MainWindow.imagesList.size() + " 张图片");
            ShowAllPane showAllPane = new ShowAllPane();
            MainWindow.curShowAllPane = showAllPane;
            new ManagerMenu().PopupMenuForPane(showAllPane);
            siTask = new ShowImageTask(showAllPane, node);
            tempSiTask = siTask;
            siTask.execute();
        }
    }

    // 选择监听器，得到该目录下的图片文件信息，在showNumberOfPicture区域展示所选目录中的文件数，以及在showView区域展示图片
    class DirSelectionListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent event) {
            // System.out.println(javax.swing.SwingUtilities.isEventDispatchThread());
            DefaultMutableTreeNode node = getTreeNode(event.getPath());
            MainWindow.currentNode = node;
            MainWindow.imagesList = new ArrayList<File>();
            new SelectedTask(node).execute();
        }// end of DirSelectionListener

    }// DirectoryTree

}

