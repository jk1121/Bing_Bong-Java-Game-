package BingBong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.*;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class BingBong extends JPanel implements ActionListener{

	BufferedImage dbImage;
	Graphics dbg;
	Timer timer = null;
	private int score = 0;
	private int highScore ;
	static double BALLy;
	static double RECT1x;
	static double RECT2x;
	static double RECT3x;

	double velocity = 3.5;
	double velR1 = 1.1;
	double velR2 = 1.1;
	double velR3 = 1.1;
	boolean slow = true;

	public BingBong() {
/*
		try{
			FileInputStream file = new FileInputStream("score.txt");
			int c;
			while((c=file.read())!=-1){
				highScore = c;
				System.out.println(highScore);
			}
			file.close();
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
		}finally{
			
		}
		
*/	
	
		dbImage = new BufferedImage(900,650,BufferedImage.TYPE_INT_RGB);
	dbg = dbImage.getGraphics();	
		// vertical movement of ball
		timer = new Timer(0, this);
		timer.start();
	}

	public void playSound() {
		// TODO Auto-generated method stub
		AudioPlayer MGP = AudioPlayer.player;
		AudioStream BGM;
		AudioData MD;
		ContinuousAudioDataStream loop  = null;
		try{
			BGM = new AudioStream(new FileInputStream("sound.wav"));
			MD = BGM.getData();
			loop = new ContinuousAudioDataStream(MD);
			
		}catch(Exception e){
			
		}
		MGP.start(loop);
		
	}

	public Rectangle ballBounds(){
		return new Rectangle(getWidth() / 2 - 15, (int) BALLy, 50, 50);
	}
	public Rectangle Rect1Bounds(){
		return new Rectangle((int) RECT1x, 420, 50, 30);
	}
	public Rectangle Rect2Bounds(){
		return new Rectangle((int) RECT2x, 325, 50, 30);
	}
	public Rectangle Rect3Bounds(){
		return new Rectangle((int) RECT3x, 230 , 50, 30);
	}
	
	private boolean collision1(){
		return Rect1Bounds().intersects(ballBounds());
	}
	private boolean collision2(){
		return Rect2Bounds().intersects(ballBounds());
	}
	private boolean collision3(){
		return Rect3Bounds().intersects(ballBounds());
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		// Applying double buffering for smooth rendering

		dbg.setColor(Color.PINK);
		dbg.setFont(new Font("ARIAL",50,20));
		dbg.drawString("  SCORE : "+score, 700, 50);
		dbg.drawString("HIGH SCORE : "+highScore, 700, 25);
		dbg.setColor(Color.orange);
		dbg.fillRect((int) RECT1x, 420, 50, 30);
		dbg.fillRect((int) RECT2x, 325 , 50, 30);
		dbg.fillRect((int) RECT3x, 230, 50, 30);
		
		dbg.setColor(Color.yellow);
		dbg.fillOval(getWidth() / 2 - 15, (int) BALLy, 50, 50);
		dbg.setColor(Color.red);
		dbg.fillRect(getWidth() / 2 - 15, 0, 50, 70);
		dbg.fillRect(getWidth() / 2 - 15, 551, 50, 70);

		
		
		//		dbImage = createImage(getWidth(), getHeight());
//		dbg = dbImage.getGraphics();
//		paintComponent(dbg);   
		g.drawImage(dbImage, 0, 0 , null);
		dbg.clearRect(0,0,getWidth(),getHeight());
//		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
/*
		g.fillRect((int) RECT1x, 250, 50, 30);
		g.fillRect((int) RECT2x, 130, 50, 30);
		g.fillRect((int) RECT3x, 430, 50, 30);
		g.setColor(Color.yellow);
		g.fillOval(getWidth() / 2 - 15, (int) BALLy, 50, 50);
		g.setColor(Color.red);
		g.fillRect(getWidth() / 2 - 15, 0, 50, 70);
		g.fillRect(getWidth() / 2 - 15, 551, 50, 70);
*///		timer.start();
	}

	public static void main(String args[]) {

		// setting up the frame
		JFrame frame = new JFrame();
		frame.setTitle("Bing Bong");
//		playSound();
		frame.setVisible(true);
//		playSound();
		frame.setSize(900, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width - frame.getWidth()) / 2;
		int yPos = (dim.height - frame.getHeight()) / 2;
		frame.setLocation(xPos, yPos);

		// Constructing panel
		BingBong bb = new BingBong();
		bb.setBackground(Color.DARK_GRAY);
		frame.add(bb);
		frame.addKeyListener(bb.new AL());
		// default y position for ball
		BALLy = frame.getHeight() / 2 - 15;

		// default x positions of rectangles
		RECT1x = 1800;
		RECT2x = 1000;
		RECT3x = 700;

	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		playSound();	
		if (BALLy >= 498 || BALLy <= 73  ) {
			velocity = -velocity;
			slow = !slow;
			score++;
		}
		BALLy += velocity;

		if (RECT1x >= 1800 || RECT1x <= -700) {
			velR1 = -velR1;
		}
		RECT1x += velR1;

		if (RECT2x >= 1000 || RECT2x <= -500) {
			velR2 = -velR2;
		}
		RECT2x += velR2;
		if (RECT3x >= 2500 || RECT3x <= -1000) {
			velR3 = -velR3;
			
		}
		RECT3x += velR3;
		
		if(collision1()||collision2()||collision3()){
			JOptionPane.showMessageDialog(this, "Your Score is "+score, "GAME OVER", JOptionPane.DEFAULT_OPTION);
			System.exit(ABORT);
		/*	highScore = score;
			try{
			FileOutputStream file = new FileOutputStream("score.txt");
			String hs = Integer.toString(highScore);
			byte b[] = hs.getBytes();
			file.write(b);
			
			file.close();
			}catch(Exception ex){
				ex.getMessage();
				ex.printStackTrace();
			}finally{
				
			}*/
		}
		
		repaint();
	}
	class AL extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			super.keyPressed(e);
			playSound();
			int keyCode = e.getKeyCode();
			if(keyCode == e.VK_CONTROL){
				if(slow)
					velocity = 1;
				else
					velocity = -1;
			}
			//repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			super.keyReleased(e);
			int keyCode = e.getKeyCode();
			if(keyCode == e.VK_CONTROL){
				if(slow)
					velocity = 3.5;
				else
					velocity = -3.5;
			}
			//repaint();
		}
		
		
	}
}
