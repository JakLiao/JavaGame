import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 游戏的菜单类
 * @author xw
 * @version 1.0
 */
public class Main extends JFrame{
	/**
     * 用来实现打开对话框
     */
	private JFileChooser chooser = new JFileChooser();
	/**
     * 显示给用户看的label文本
     */
	public String text = "WELCOME!";
	/**
     * 用来开始计时的按钮
     */
	private JButton begin = new JButton("开始挑战");
	/**
     * 两个date类用来获得时间
     */
	private java.util.Date time_0, time_1;
	/**
	 * 储存存档中的时间
	 */
	private long oldTime = 0;
	/**
	 * 用来储存用户玩了的时间
	 */
	private long usedTime;
	/**
	 * updateButtom()参数:开始挑战
	 */
	private final int BEGIN = 2;
	/**
     * updateButtom()参数:挑战模式
     */
	private final int GAME = 0;
	/**
	 * updateButtom()参数：解题模式
	 */
	private final int SOLVING = 1;
	/**
	 * updateButtom参数:DIY
	 */
	private final int DIY = 3;
	/**
     *  菜单条
     */
	private JMenuBar bar = new JMenuBar();
	/**
     * 菜单项
     */
	private JMenu file = new JMenu("文件"), game = new JMenu("游戏"), about = new JMenu("关于"), style = new JMenu("风格"), solve = new JMenu("智能解题");
	/**
	 * 子菜单
	 */
	private JMenuItem 
	              newGame = new JMenuItem("新游戏"),
	              restart = new JMenuItem("重新开始"),
	              diy = new JMenuItem("录入题目"),
	              solve_1 = new JMenuItem("清空当前"),
	              solve_2 = new JMenuItem("保留当前"),
	              exit = new JMenuItem("退出"),
	              //以上部分将 添加到  “文件"
	              oldGame = new JMenuItem("载入存档"),
	              saveGame = new JMenuItem("保存游戏"),
	              harder = new JMenuItem("增加难度"),
	              easier = new JMenuItem("降低难度"),
	              //以上部分将添加到”游戏"
	              help = new JMenuItem("帮助"),
	              info = new JMenuItem("关于");
	              //以上部分将添加到“关于”
	/**
	 * 单选菜单项
	 */
	private JCheckBoxMenuItem style1 = new JCheckBoxMenuItem("风格1", true),
	                          style2 = new JCheckBoxMenuItem("风格2"),
	                          style3 = new JCheckBoxMenuItem("风格3");
	/**
	 * 游戏界面底部的部分
	 */
	private JPanel buttomPanel = new JPanel();
	/**
	 * 底部的具体组件
	 */
	private JLabel time = new JLabel("WELCOME!");
	private JButton sovling = new JButton("解题");
	private JButton diyButton = new JButton("保存题目");
	/**
	 * 排行榜窗口
	 */
	private JFrame eastFrame = new JFrame();
	/**
	 * 右部组件
	 */
	private JLabel label_1 = new JLabel(), label_2 = new JLabel(), label_3 = new JLabel();
	private String[] name = new String[3];//冠亚季军姓名
	private long[] theirTime = new long[3];//冠亚季军用时
	private JLabel lvl = new JLabel();
	public Main(){
		try {
			updateRanking(bord.getLvl());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		eastFrame.setTitle("排行榜");
		eastFrame.setSize(400,160);
		eastFrame.setLocationRelativeTo(this);
		updateEast(bord.getLvl());
		createMenu();
		buttomPanel.add(time);
		this.setJMenuBar(bar);
		this.setLayout(new BorderLayout());
		this.add(bord, BorderLayout.CENTER);
		this.add(buttomPanel, BorderLayout.SOUTH);
	}
	/**
	 * 游戏的面板
	 */
	private Bord bord = new Bord();
	/**
	 * 构造一个菜单栏
	 */
	public void createMenu(){
		/**
		 * 添加菜单
		 */
		bar.add(file);
		bar.add(game);
		bar.add(about);
		/**
		 *  添加“文件”的选项
		 */
		file.add(newGame);
		file.add(restart);
		restart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK));
		file.addSeparator();
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		file.add(diy);
		diy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		file.add(solve);
		file.addSeparator();
		file.add(exit);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
		//添加solve 子菜单\
		solve.add(solve_1);
		solve.add(solve_2);
		/**
		 * 添加“游戏"的选项
		 */
		game.add(style);
		game.addSeparator();
		game.add(oldGame);
		oldGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		game.add(saveGame);
		saveGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		game.addSeparator();
		game.add(harder);
		harder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.CTRL_DOWN_MASK));
		game.add(easier);
		easier.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.CTRL_DOWN_MASK));
		// 添加style子菜单
		style.add(style1);
		style.add(style2);
		style.add(style3);
		/**
		 * 添加”关于“选项
		 */
		about.add(help);
		help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, KeyEvent.CTRL_DOWN_MASK));
		about.add(info);
		info.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10, KeyEvent.CTRL_DOWN_MASK));
		/**
		 * 添加监听器
		 */
		addMonitor();
	}
	/**
	 *  为菜单项添加监听器
	 */
	public void addMonitor(){
		//使用户点关闭的时候，问他是否退出
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				Object[] options = { "确定", "取消" };
				int returnVal = JOptionPane.showOptionDialog(null, "确定要退出游戏？", "Warning", 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
						null, options, options[1]);
			    if(returnVal == JOptionPane.OK_OPTION)
			    	System.exit(0);
			}
		});
		//新游戏
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bord.newGame(bord.getLvl());
				updateButtom(GAME);
				repaint();
				eastFrame.setVisible(true);
			}
		});
	    //重新玩
		restart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				usedTime = 0;
				time_0 = new java.util.Date();
				time_1 = new java.util.Date();
				bord.restart();
				bord.update(bord.getStyle());
			}
		});
		//自己录入
		diy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bord.clear();
				bord.update(bord.getStyle());
				bord.enableInput();
				updateButtom(DIY);
				//刷新窗口
				setSize(400,400);
				setSize(400,500);
				repaint();
			}
		});
		//载入存档
		oldGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				FileNameExtensionFilter filter = new FileNameExtensionFilter("数独存档文件","sdd");
    			chooser.setFileFilter(filter);
    			int returnVal = chooser.showOpenDialog(null);
    			if(returnVal == JFileChooser.APPROVE_OPTION){
    				File file = chooser.getSelectedFile();
    				try {
						DataInputStream reader = new DataInputStream(new FileInputStream(file));
						int val;
						boolean isG;
						for(int i = 0; i < 9; i++){
							for(int j = 0; j < 9; j++){
								val = reader.readInt();
								isG = reader.readBoolean();
								bord.changeForm_1(i, j, val, isG);
								}
						}
						bord.setLvl(reader.readInt());
						oldTime = reader.readLong();
						reader.close();
						time_0 = new java.util.Date();
						time_1 = new java.util.Date();
						text = "已用时：" +(oldTime + usedTime) + "秒";
						updateButtom(BEGIN);
						updateEast(bord.getLvl());
						bord.update(bord.getLvl());
						bord.enableInput();
						setTitle("数独游戏--lvl:" + bord.getLvl());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
    			}
			}
		});
		saveGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				FileNameExtensionFilter filter = new FileNameExtensionFilter("数独存档文件.sdd","sdd");
    			chooser.setFileFilter(filter);
    			int returnVal = chooser.showSaveDialog(null);
    			if(returnVal == JFileChooser.APPROVE_OPTION){
			         File file = chooser.getSelectedFile();
			         try {
						DataOutputStream output = new DataOutputStream(new FileOutputStream(file));
						for(int i = 0; i < 9; i++){
							for(int j = 0; j < 9; j++){
								output.writeInt(bord.getForm_1(i, j));
								output.writeBoolean(bord.isG(i, j));
							}
						}
						output.writeInt(bord.getLvl());
						output.writeLong(usedTime);
						output.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
		        }
			}
		});
		// 解题
		solve_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bord.clear();
				bord.update(bord.getStyle());
				bord.enableInput();
				updateButtom(SOLVING);
				sovling.setEnabled(true);
				sovling.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						bord.solving();
						sovling.setEnabled(false);
					}
				});
				//使buttomPanel的跟新
				setSize(400,400);
				setSize(400,500);
				repaint();
				eastFrame.setVisible(false);
			}
		});
		solve_2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bord.enableInput();
				updateButtom(SOLVING);
				sovling.setEnabled(true);
				sovling.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						bord.solving();
						sovling.setEnabled(false);
					}
				});
				//使buttomPanel的跟新
				setSize(400,400);
				setSize(400,500);
				repaint();
				eastFrame.setVisible(false);
			}
		});
		/**
		 * 退出游戏
		 */
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		//改变风格
		style1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bord.setStyle(bord.STYLE_1);
				bord.update(bord.getStyle());
				style1.setState(true);
				style2.setState(false);
				style3.setState(false);
			}
		});
		style2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bord.setStyle(bord.STYLE_2);
				bord.update(bord.getStyle());
				style1.setState(false);
				style2.setState(true);
				style3.setState(false);
			}
		});
		style3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bord.setStyle(bord.STYLE_3);
				bord.update(bord.getStyle());
				style1.setState(false);
				style2.setState(false);
				style3.setState(true);
			}
		});
		//增加难度
		harder.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bord.setLvl(bord.getLvl() + 1);
				bord.clear();
				try {
					updateRanking(bord.getLvl());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateEast(bord.getLvl());
				eastFrame.setVisible(true);
				setTitle("数独--Lvl：" + bord.getLvl());
			}
		});
		//降低难度 
		easier.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bord.setLvl(bord.getLvl() - 1);
				bord.clear();
				try {
					updateRanking(bord.getLvl());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateEast(bord.getLvl());
				eastFrame.setVisible(true);
				setTitle("数独--Lvl：" + bord.getLvl());
			}
		});
		/**
		 * 弹出帮助提示
		 */
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "单击要填入的空格，然后在弹出的窗口中选择要填入的数字.");
			}
		});
		/**
		 * 显示产品相关信息
		 */
		info.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "简单数独游戏\n作者：肖卫\n版本：v1.0");
			}
		});
		/**
		 * 开始计时
		 */
		begin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				updateButtom(BEGIN);
				repaint();
				time.setText("开始计时！");
				time_0 = new java.util.Date();
				time_1 = new java.util.Date();
				text = "已用时：" + usedTime + "秒";
				bord.enableInput();
				/*try{
					while(!bord.isOver()){
						Thread.sleep(1000);
						time_1 = new java.util.Date();
						usedTime = (time_1.getTime() - time_0.getTime()) / 100;
						time.setText("已用时：" + usedTime);
						time.repaint();
					}
				}
				catch(Exception ex){
					ex.printStackTrace();
				}*/
			}
		});
		diyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				FileNameExtensionFilter filter = new FileNameExtensionFilter("数独存档文件.sdd","sdd");
    			chooser.setFileFilter(filter);
    			int returnVal = chooser.showSaveDialog(null);
    			if(returnVal == JFileChooser.APPROVE_OPTION){
			         File file = chooser.getSelectedFile();
			         try {
						DataOutputStream output = new DataOutputStream(new FileOutputStream(file));
						for(int i = 0; i < 9; i++){
							for(int j = 0; j < 9; j++){
								output.writeInt(bord.getForm_1(i, j));
								output.writeBoolean(bord.isG(i, j));
							}
						}
						output.writeInt(bord.getLvl());
						//将时间清0
						usedTime = 0;
						output.writeLong(usedTime);
						output.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
		        }
			}
		});
	}
	/**
	 * 更新buttomPanel
	 * @param model 模式
	 */
	public void updateButtom(int model){
		switch(model){
		case 0: {
			buttomPanel.add(begin);
			buttomPanel.remove(time);
		    buttomPanel.remove(sovling);
		    buttomPanel.remove(diyButton);
		}
		break;
		case 1: {
			buttomPanel.add(sovling);
			buttomPanel.remove(time);
			buttomPanel.remove(begin);
			buttomPanel.remove(diyButton);
		}
		break;
		case 2:{
			buttomPanel.add(time);
			buttomPanel.remove(begin);
			buttomPanel.remove(sovling);
			buttomPanel.remove(diyButton);
		}
		break;
		case 3:{
			buttomPanel.add(diyButton);
			buttomPanel.remove(begin);
			buttomPanel.remove(sovling);
			buttomPanel.remove(time);
		}
		}
	}
	/**
	 * 更新eastPanel
	 */
	public void updateEast(int n){
		label_1.setIcon(new ImageIcon("image/jin.gif"));
		label_2.setIcon(new ImageIcon("image/yin.gif"));
		label_3.setIcon(new ImageIcon("image/tong.gif"));
		label_1.setText(name[0] + " " + theirTime[0]);
		label_2.setText(name[1] + " " + theirTime[1]);
		label_3.setText(name[2] + " " + theirTime[2]);
		label_1.setHorizontalTextPosition(SwingConstants.CENTER);
		label_2.setHorizontalTextPosition(SwingConstants.CENTER);
		label_3.setHorizontalTextPosition(SwingConstants.CENTER);
		label_1.setVerticalTextPosition(SwingConstants.BOTTOM);
		label_2.setVerticalTextPosition(SwingConstants.BOTTOM);
		label_3.setVerticalTextPosition(SwingConstants.BOTTOM);
		lvl.setIcon(new ImageIcon("image/lvl" + n + ".gif"));
		lvl.setText("当前级别:");
		lvl.setHorizontalTextPosition(SwingConstants.CENTER);
		lvl.setVerticalTextPosition(SwingConstants.TOP);
		eastFrame.setLayout(new GridLayout(1, 4));
		eastFrame.add(label_1);
		eastFrame.add(label_2);
		eastFrame.add(label_3);
		eastFrame.add(lvl);
	}
	/**
	 * 更新排名榜
	 * @param n 对应的级别
	 * @throws IOException
	 */
	public void updateRanking(int n) throws IOException{
		File file = new File("rank_" + n + ".dat");
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		for(int i = 0; i < 3; i++){
			name[i] = FixedLengthStringIO.readFixedLengthString(3, in);
			theirTime[i] = in.readInt();
		}
		in.close();
	}
	public void saveRanking(int lvl) throws IOException{
		//如果排不上名次就返回
		if(usedTime > theirTime[2])
			return;
		String currentUserName;//用户的名字
		if(usedTime < theirTime[2]){
			if(usedTime >= theirTime[1]){
				currentUserName = JOptionPane.showInputDialog("CONGRATULATION!/n你刷新了第三名的记录,请输入你的大名");
				if(currentUserName == null || currentUserName.equalsIgnoreCase(""))
					currentUserName = "未输入";
				//更新第三名
				name[2] = currentUserName;
				theirTime[2] = usedTime;
			}
			else if(usedTime >= theirTime[0]){
				currentUserName = JOptionPane.showInputDialog("CONGRATULATION!/n再努力一点你就是冠军了，请输入你的大名");
				if(currentUserName == null || currentUserName.equalsIgnoreCase(""))
					currentUserName = "未输入";
				//将第二名的数据移到第三名
				name[2] = name[1];
				theirTime[2] = theirTime[1];
				//更新第二名的数据
				name[1] = currentUserName;
				theirTime[1] = usedTime;
			}
			else{
				currentUserName = JOptionPane.showInputDialog("CONGRATULATION!/n你是冠军，请输入你的大名");
				if(currentUserName == null || currentUserName.equalsIgnoreCase(""))
					currentUserName = "未输入";
				//将第二名的数据移到第三名
				name[2] = name[1];
				theirTime[2] = theirTime[1];
				//将第一名的数据移到第二名
				name[1] = name[0];
				theirTime[1] = theirTime[0];
				//更新第一名数据
				name[0] = currentUserName;
				theirTime[0] = usedTime;
			}
		}
		
		File file = new File("rank_" + lvl + ".dat");
		DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
		for(int i = 0; i < 3; i++){
			FixedLengthStringIO.writeFixedLengthString(name[i], 3, out);
			out.writeInt((int)theirTime[i]);
		}
		out.close();
	}
	public static void main(String[] args){
		Main frame = new Main();
		frame.setTitle("数独" + "--Lvl:" + frame.bord.getLvl());
		frame.setSize(400, 500);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.time_0 = new java.util.Date();
		try{
			while(!frame.bord.isOver()){
				Thread.sleep(1000);
				frame.time_1 = new java.util.Date();
				if(!frame.text.equalsIgnoreCase("WELCOME!")){
					frame.usedTime = frame.oldTime + (frame.time_1.getTime() - frame.time_0.getTime()) / 1000;
					frame.text = "已用时：" + frame.usedTime + "秒";
					frame.time.setText(frame.text);
					frame.time.repaint();
				}
			}
			frame.saveRanking(frame.bord.getLvl());
			frame.updateRanking(frame.bord.getLvl());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
