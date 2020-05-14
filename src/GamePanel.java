import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

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
		player.update_collider();
	}
	   
	public void paintComponent(Graphics g) {			
		super.paintComponent(g);
		
		g.drawImage(background, 0, 0, this);
		g.drawImage(middleground, 0, 0, this);
		
		player.draw(g, this);
		
		g.setColor(Color.RED);
		g.drawRect(player.collider.rect.x, player.collider.rect.y, player.collider.rect.width, player.collider.rect.height);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Consolas", Font.BOLD, 20));
		// g.drawString("FPS: " + GameLoop.fps, 5, 25);
		      
		GameLoop.frameCount++;
	}
	
	public class KeyboardListener implements KeyListener {

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

		@Override
		public void keyTyped(KeyEvent e) {}
	}
}