package panes;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import main.ImageManager;
import menus.ManagerMenu;

public class ManagerPane extends JPanel {
	
	public ManagerPane() {
		setLayout(new BorderLayout());

		// 中间的拆分窗格
		ImageManager.sp = new JScrollPane();
		ImageManager.sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ImageManager.curShowAllPane = new ShowAllPane();
		new ManagerMenu().PopupMenuForPane(ImageManager.curShowAllPane);
		ImageManager.curShowAllPane.setPreferredSize(new Dimension(480,420));
		ImageManager.sp.getViewport().add(ImageManager.curShowAllPane);
		JSplitPane middle = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new DirectoryTree(), ImageManager.sp);
		middle.setDividerLocation(160);
		middle.setDividerSize(1);
		// 底下的状态栏
		ImageManager.statusbar = new JLabel();
		ImageManager.statusbar.setText("图像路径");

		add(middle, BorderLayout.CENTER);
		add(ImageManager.statusbar, BorderLayout.SOUTH);
		validate();
	}
}														

