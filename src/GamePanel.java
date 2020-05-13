import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	final String dir = System.getProperty("user.dir");
	
	Toolkit t = Toolkit.getDefaultToolkit();
	
	Image idle = t.getImage(dir + "/../images/adventurer-idle-00.png");
	Image background = t.getImage(dir + "/../images/background.png");
	Image middleground = t.getImage(dir + "/../images/middleground.png");
	
	public Player player = new Player(100, 500, idle);
	int playerWidth, playerHeight;
	   
	public GamePanel() {
		playerWidth = playerHeight = 50;
		KeyListener listener = new KeyboardListener();
		addKeyListener(listener);
		setFocusable(true);
	}
	   
	public void update() {
		player.update();
	}
	   
	public void paintComponent(Graphics g) {			
		super.paintComponent(g);
		
		g.drawImage(background, 0, 0, this);
		g.drawImage(middleground, 0, 0, this);
		
		g.drawImage(player.img, player.x, player.y, this);
		  
		 
		g.setColor(Color.BLACK);
		g.setFont(new Font("Consolas", Font.BOLD, 20));
		g.drawString("FPS: " + GameLoop.fps, 5, 25);
		      
		GameLoop.frameCount++;
	}
	
	public class KeyboardListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			if (key == KeyEvent.VK_SPACE && !player.jumping) {
				player.jumping = true;
			}
			
			if (key == KeyEvent.VK_A) {
				player.a = true;
			}
			
			if (key == KeyEvent.VK_D) {
				player.d = true;
			}
			
			if (key == KeyEvent.VK_SHIFT) {
				player.sprinting = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			
			
			if (key == KeyEvent.VK_A) {
				player.a = false;
			}
			
			
			if (key == KeyEvent.VK_D) {
				player.d = false;
			}
			
			if (key == KeyEvent.VK_SHIFT) {
				player.sprinting = false;
			}
		}
	}
}