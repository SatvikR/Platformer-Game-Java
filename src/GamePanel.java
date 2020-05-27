import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.util.Arrays;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	public static ArrayList<Block> terrain = new ArrayList<>(); // All Blocks
	public static ArrayList<ArrayList<Integer>> starting_points = new ArrayList<>(); // Starting Points for each level
	public Player player;
	private boolean first_render;
	int playerWidth, playerHeight;
	Toolkit t = Toolkit.getDefaultToolkit();
	final String dir = System.getProperty("user.dir");

	Image background = t.getImage(dir + "/../images/background.png");
	Image middleground = t.getImage(dir + "/../images/middleground.png");
	Image base_block = t.getImage(dir + "/../images/base_platform.png");

	public Block base = new Block(base_block, 0, 683, new PhysicsCollider(new Rectangle(0, 687, 400, 55)));
	public Block test_plat = new Block(base_block, 500, 625, new PhysicsCollider(new Rectangle(500, 629, 400, 55)));
	public static int frame = 0;
	
	public GamePanel() {
		playerWidth = playerHeight = 50;
		KeyListener listener = new KeyboardListener();
		addKeyListener(listener);
		setFocusable(true);
		first_render = true;
		terrain.add(base);
		terrain.add(test_plat);
		starting_points.add(new ArrayList<>(Arrays.asList(100, 100)));
		player = new Player(starting_points.get(0).get(0), starting_points.get(0).get(1));
	}
	   
	public void update() {
		player.update();
		player.update_collider();
		frame += 1;
	}
	   
	public void paintComponent(Graphics g) {			
		super.paintComponent(g);
		if (first_render) {
			render_all(g, player, terrain);
			first_render = !first_render;
			return;
		}


		g.drawImage(background, 0, 0, this);
		g.drawImage(middleground, 0, 0, this);

		player.draw(g, this);

		for (Block b : terrain) {
			g.drawImage(b.img, b.x, b.y, this);
			g.setColor(Color.RED);
			//g.drawRect(b.collider.rect.x, b.collider.rect.y, b.collider.rect.width, b.collider.rect.height);
		}
		
		g.setColor(Color.RED);
		//g.drawRect(player.collider.rect.x, player.collider.rect.y, player.collider.rect.width, player.collider.rect.height);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Consolas", Font.BOLD, 20));
		GameLoop.frameCount++;
	}


	public void render_all(Graphics g, Player p, ArrayList<Block> blocks) {
		for (Image i : p.state_imgs.get("idle")) {
			g.drawImage(i, 0, 0, this);
		}
		for (Image i : p.state_imgs.get("jump")) {
			g.drawImage(i, 0, 0, this);
		}
		for (Block b : blocks) {
			b.draw(g, this);
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