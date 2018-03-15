package frames;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.alee.laf.WebLookAndFeel;

import menus.ManagerMenu;
import panes.ManagerPane;

public class  ManagerFrame extends JFrame{
	public ManagerFrame(String name){
		super(name);
	}
	
	public void setFrame(){
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image image=tk.createImage("image\\logo_eye.png");
		this.setIconImage(image); 
		
//		// 设置初始界面外观
//		try {
//			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
////			 UIManager.setLookAndFeel(new WebLookAndFeel());
////			 UIManager.setLookAndFeel("com.bulenkov.darcula.DarculaLaf");
//			 try {
//                BeautyEyeLNFHelper.launchBeautyEyeLNF();
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//             UIManager.put("RootPane.setupButtonVisible", false);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		//菜单项
		setJMenuBar((new ManagerMenu(this)).createMenu());
		// 设置窗体的内容区
		setContentPane(new ManagerPane());
		
		//窗体位置及大小
		Toolkit kit=getToolkit();
//		Dimension winSize=kit.getScreenSize();
//		setBounds((winSize.width-750)/2,(winSize.height-530)/2,850,530);
//		setMinimumSize(new Dimension(500,300));
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸
        setBounds((int) (screenSize.width * 0.1), (int) (screenSize.height * 0.08), (int) (screenSize.width * 0.8),
                (int) (screenSize.height * 0.8));

        Dimension preferSize = new Dimension((int) (screenSize.width * 0.8),
                (int) (screenSize.height * 0.8));
        setPreferredSize(preferSize);
		setVisible(true);	
		
	}
	
//	public void windowClosing(WindowEvent e){
//		dispose();
//	}
	
}


class Middle extends JPanel implements TreeSelectionListener{

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

class DirectoryTree extends JPanel  implements TreeSelectionListener{

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

class ShowAllPane extends JPanel {
	
}

class toolbar extends JPanel {
	
}

class statusbar extends JPanel {
	
}
