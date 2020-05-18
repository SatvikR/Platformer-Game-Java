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
	
	
	
	public Player player = new Player(100, 500);
	private boolean first_render;
	int playerWidth, playerHeight;
	Toolkit t = Toolkit.getDefaultToolkit();
	final String dir = System.getProperty("user.dir");
	Image background = t.getImage(dir + "/../images/background.png");
	Image middleground = t.getImage(dir + "/../images/middleground.png");
	
	public static int frame = 0;
	
	public GamePanel() {
		playerWidth = playerHeight = 50;
		KeyListener listener = new KeyboardListener();
		addKeyListener(listener);
		setFocusable(true);
		first_render = true;
	}
	   
	public void update() {
		player.update();
		player.update_collider();
		frame += 1;
	}
	   
	public void paintComponent(Graphics g) {			
		super.paintComponent(g);
		if (first_render) {
			render_all(g, player);
			first_render = !first_render;
			return;
		}
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
	
	public void render_all(Graphics g, Player p) {
		for (Image i : p.state_imgs.get("idle")) {
			g.drawImage(i, 0, 0, this);
		}
		for (Image i : p.state_imgs.get("jump")) {
			g.drawImage(i, 0, 0, this);
		}
			
	}
	
	public class KeyboardListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			if (key == KeyEvent.VK_SPACE && !player.jumping) {
				player.state = "jump";
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