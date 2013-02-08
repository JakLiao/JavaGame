import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * ��Ϸ�Ĳ˵���
 * @author xw
 * @version 1.0
 */
public class Main extends JFrame{
	/**
     * ����ʵ�ִ򿪶Ի���
     */
	private JFileChooser chooser = new JFileChooser();
	/**
     * ��ʾ���û�����label�ı�
     */
	public String text = "WELCOME!";
	/**
     * ������ʼ��ʱ�İ�ť
     */
	private JButton begin = new JButton("��ʼ��ս");
	/**
     * ����date���������ʱ��
     */
	private java.util.Date time_0, time_1;
	/**
	 * ����浵�е�ʱ��
	 */
	private long oldTime = 0;
	/**
	 * ���������û����˵�ʱ��
	 */
	private long usedTime;
	/**
	 * updateButtom()����:��ʼ��ս
	 */
	private final int BEGIN = 2;
	/**
     * updateButtom()����:��սģʽ
     */
	private final int GAME = 0;
	/**
	 * updateButtom()����������ģʽ
	 */
	private final int SOLVING = 1;
	/**
	 * updateButtom����:DIY
	 */
	private final int DIY = 3;
	/**
     *  �˵���
     */
	private JMenuBar bar = new JMenuBar();
	/**
     * �˵���
     */
	private JMenu file = new JMenu("�ļ�"), game = new JMenu("��Ϸ"), about = new JMenu("����"), style = new JMenu("���"), solve = new JMenu("���ܽ���");
	/**
	 * �Ӳ˵�
	 */
	private JMenuItem 
	              newGame = new JMenuItem("����Ϸ"),
	              restart = new JMenuItem("���¿�ʼ"),
	              diy = new JMenuItem("¼����Ŀ"),
	              solve_1 = new JMenuItem("��յ�ǰ"),
	              solve_2 = new JMenuItem("������ǰ"),
	              exit = new JMenuItem("�˳�"),
	              //���ϲ��ֽ� ��ӵ�  ���ļ�"
	              oldGame = new JMenuItem("����浵"),
	              saveGame = new JMenuItem("������Ϸ"),
	              harder = new JMenuItem("�����Ѷ�"),
	              easier = new JMenuItem("�����Ѷ�"),
	              //���ϲ��ֽ���ӵ�����Ϸ"
	              help = new JMenuItem("����"),
	              info = new JMenuItem("����");
	              //���ϲ��ֽ���ӵ������ڡ�
	/**
	 * ��ѡ�˵���
	 */
	private JCheckBoxMenuItem style1 = new JCheckBoxMenuItem("���1", true),
	                          style2 = new JCheckBoxMenuItem("���2"),
	                          style3 = new JCheckBoxMenuItem("���3");
	/**
	 * ��Ϸ����ײ��Ĳ���
	 */
	private JPanel buttomPanel = new JPanel();
	/**
	 * �ײ��ľ������
	 */
	private JLabel time = new JLabel("WELCOME!");
	private JButton sovling = new JButton("����");
	private JButton diyButton = new JButton("������Ŀ");
	/**
	 * ���а񴰿�
	 */
	private JFrame eastFrame = new JFrame();
	/**
	 * �Ҳ����
	 */
	private JLabel label_1 = new JLabel(), label_2 = new JLabel(), label_3 = new JLabel();
	private String[] name = new String[3];//���Ǽ�������
	private long[] theirTime = new long[3];//���Ǽ�����ʱ
	private JLabel lvl = new JLabel();
	public Main(){
		try {
			updateRanking(bord.getLvl());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		eastFrame.setTitle("���а�");
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
	 * ��Ϸ�����
	 */
	private Bord bord = new Bord();
	/**
	 * ����һ���˵���
	 */
	public void createMenu(){
		/**
		 * ��Ӳ˵�
		 */
		bar.add(file);
		bar.add(game);
		bar.add(about);
		/**
		 *  ��ӡ��ļ�����ѡ��
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
		//���solve �Ӳ˵�\
		solve.add(solve_1);
		solve.add(solve_2);
		/**
		 * ��ӡ���Ϸ"��ѡ��
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
		// ���style�Ӳ˵�
		style.add(style1);
		style.add(style2);
		style.add(style3);
		/**
		 * ��ӡ����ڡ�ѡ��
		 */
		about.add(help);
		help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, KeyEvent.CTRL_DOWN_MASK));
		about.add(info);
		info.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10, KeyEvent.CTRL_DOWN_MASK));
		/**
		 * ��Ӽ�����
		 */
		addMonitor();
	}
	/**
	 *  Ϊ�˵�����Ӽ�����
	 */
	public void addMonitor(){
		//ʹ�û���رյ�ʱ�������Ƿ��˳�
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				Object[] options = { "ȷ��", "ȡ��" };
				int returnVal = JOptionPane.showOptionDialog(null, "ȷ��Ҫ�˳���Ϸ��", "Warning", 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
						null, options, options[1]);
			    if(returnVal == JOptionPane.OK_OPTION)
			    	System.exit(0);
			}
		});
		//����Ϸ
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bord.newGame(bord.getLvl());
				updateButtom(GAME);
				repaint();
				eastFrame.setVisible(true);
			}
		});
	    //������
		restart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				usedTime = 0;
				time_0 = new java.util.Date();
				time_1 = new java.util.Date();
				bord.restart();
				bord.update(bord.getStyle());
			}
		});
		//�Լ�¼��
		diy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bord.clear();
				bord.update(bord.getStyle());
				bord.enableInput();
				updateButtom(DIY);
				//ˢ�´���
				setSize(400,400);
				setSize(400,500);
				repaint();
			}
		});
		//����浵
		oldGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				FileNameExtensionFilter filter = new FileNameExtensionFilter("�����浵�ļ�","sdd");
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
						text = "����ʱ��" +(oldTime + usedTime) + "��";
						updateButtom(BEGIN);
						updateEast(bord.getLvl());
						bord.update(bord.getLvl());
						bord.enableInput();
						setTitle("������Ϸ--lvl:" + bord.getLvl());
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
				FileNameExtensionFilter filter = new FileNameExtensionFilter("�����浵�ļ�.sdd","sdd");
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
		// ����
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
				//ʹbuttomPanel�ĸ���
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
				//ʹbuttomPanel�ĸ���
				setSize(400,400);
				setSize(400,500);
				repaint();
				eastFrame.setVisible(false);
			}
		});
		/**
		 * �˳���Ϸ
		 */
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		//�ı���
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
		//�����Ѷ�
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
				setTitle("����--Lvl��" + bord.getLvl());
			}
		});
		//�����Ѷ� 
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
				setTitle("����--Lvl��" + bord.getLvl());
			}
		});
		/**
		 * ����������ʾ
		 */
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "����Ҫ����Ŀո�Ȼ���ڵ����Ĵ�����ѡ��Ҫ���������.");
			}
		});
		/**
		 * ��ʾ��Ʒ�����Ϣ
		 */
		info.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "��������Ϸ\n���ߣ�Ф��\n�汾��v1.0");
			}
		});
		/**
		 * ��ʼ��ʱ
		 */
		begin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				updateButtom(BEGIN);
				repaint();
				time.setText("��ʼ��ʱ��");
				time_0 = new java.util.Date();
				time_1 = new java.util.Date();
				text = "����ʱ��" + usedTime + "��";
				bord.enableInput();
				/*try{
					while(!bord.isOver()){
						Thread.sleep(1000);
						time_1 = new java.util.Date();
						usedTime = (time_1.getTime() - time_0.getTime()) / 100;
						time.setText("����ʱ��" + usedTime);
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
				FileNameExtensionFilter filter = new FileNameExtensionFilter("�����浵�ļ�.sdd","sdd");
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
						//��ʱ����0
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
	 * ����buttomPanel
	 * @param model ģʽ
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
	 * ����eastPanel
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
		lvl.setText("��ǰ����:");
		lvl.setHorizontalTextPosition(SwingConstants.CENTER);
		lvl.setVerticalTextPosition(SwingConstants.TOP);
		eastFrame.setLayout(new GridLayout(1, 4));
		eastFrame.add(label_1);
		eastFrame.add(label_2);
		eastFrame.add(label_3);
		eastFrame.add(lvl);
	}
	/**
	 * ����������
	 * @param n ��Ӧ�ļ���
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
		//����Ų������ξͷ���
		if(usedTime > theirTime[2])
			return;
		String currentUserName;//�û�������
		if(usedTime < theirTime[2]){
			if(usedTime >= theirTime[1]){
				currentUserName = JOptionPane.showInputDialog("CONGRATULATION!/n��ˢ���˵������ļ�¼,��������Ĵ���");
				if(currentUserName == null || currentUserName.equalsIgnoreCase(""))
					currentUserName = "δ����";
				//���µ�����
				name[2] = currentUserName;
				theirTime[2] = usedTime;
			}
			else if(usedTime >= theirTime[0]){
				currentUserName = JOptionPane.showInputDialog("CONGRATULATION!/n��Ŭ��һ������ǹھ��ˣ���������Ĵ���");
				if(currentUserName == null || currentUserName.equalsIgnoreCase(""))
					currentUserName = "δ����";
				//���ڶ����������Ƶ�������
				name[2] = name[1];
				theirTime[2] = theirTime[1];
				//���µڶ���������
				name[1] = currentUserName;
				theirTime[1] = usedTime;
			}
			else{
				currentUserName = JOptionPane.showInputDialog("CONGRATULATION!/n���ǹھ�����������Ĵ���");
				if(currentUserName == null || currentUserName.equalsIgnoreCase(""))
					currentUserName = "δ����";
				//���ڶ����������Ƶ�������
				name[2] = name[1];
				theirTime[2] = theirTime[1];
				//����һ���������Ƶ��ڶ���
				name[1] = name[0];
				theirTime[1] = theirTime[0];
				//���µ�һ������
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
		frame.setTitle("����" + "--Lvl:" + frame.bord.getLvl());
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
					frame.text = "����ʱ��" + frame.usedTime + "��";
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
