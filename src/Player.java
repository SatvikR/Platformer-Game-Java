import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.Hashtable;

public class Player{
	public int x, y;
	public boolean a, d;
	public double[] jump_list, drop_list;
	public boolean jumping, sprinting;
	public PhysicsCollider collider;
	private int jump_index, img_index;
	private int orig_y;
	public String state, prev_state;
	public Hashtable<String, Image[]> state_imgs;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.orig_y = this.y;
		this.a = false;
		this.d = false;
		this.jump_list = create_jump();
		this.drop_list = create_grav();
		this.jump_index = 0;
		this.sprinting = false;
		this.img_index = 0;
		this.state = "idle";
		this.collider = new PhysicsCollider(new Rectangle(this.x + 79, this.y + 45, 87, 138));
		this.prev_state = "idle";
		
		Toolkit t = Toolkit.getDefaultToolkit();
		final String dir = System.getProperty("user.dir");
		
		Image idle_one = t.getImage(dir + "/../images/adventurer-idle-00.png");
		Image idle_two = t.getImage(dir + "/../images/adventurer-idle-01.png");
		Image idle_three = t.getImage(dir + "/../images/adventurer-idle-02.png");
		Image jump_one = t.getImage(dir + "/../images/adventurer-jump-00.png");
		Image jump_two = t.getImage(dir + "/../images/adventurer-jump-01.png");
		Image jump_three = t.getImage(dir + "/../images/adventurer-jump-02.png");
		
		Image[] idle_imgs = {idle_one, idle_two, idle_three};
		Image[] jump_imgs = {jump_two, jump_three, jump_one};
		
		this.state_imgs = new Hashtable<String, Image[]>();
		
		this.state_imgs.put("idle", idle_imgs);
		this.state_imgs.put("jump", jump_imgs);
	}

	public void update() {
		if (this.a) {
			if (!this.collider.check_left_boundary()) {
				if (this.sprinting) {
					this.x -= 9;
				}
				else {
					this.x -= 6;
				}
			}
		}	
		
		if (this.d) {
			if (!this.collider.check_right_boundary()) {
				if (this.sprinting) {
					this.x += 9;
				}
				else {
					this.x += 6;
				}
			}
		}
		
		if (this.state == "jump") {
			if (this.jump_index == this.jump_list.length) {
				this.state = "idle";
				this.y = this.orig_y;
				this.jump_index = 0;
				System.out.println(this.jump_list.length);
			}
			else {
				this.y = this.orig_y - (int) this.jump_list[this.jump_index];
				this.jump_index++;	
			}
		}
		else {
			this.orig_y = this.y;
		}
	}
	
	private double[] create_jump() {
        double[] jump_list = new double[0];
        float x = 0;
        double y = 0;

        while(y >= 0 && x < 10) {
            jump_list = Arrays.copyOf(jump_list, jump_list.length + 1);
            jump_list[jump_list.length - 1] = y;
            x += 1.0;
            y = -2 * Math.pow(x, 2) + (36 * x);
        }
        return jump_list;
	}
	
	public double[] create_grav() {
		double[] grav_list = new double[0];
		float x = 9;
		double y = 800;
		while (y >= 0) {
			grav_list = Arrays.copyOf(grav_list, grav_list.length + 1);
			grav_list[grav_list.length - 1] = 800 - y;
			x += 1.0;
			y = -2 * Math.pow(x, 2) + (36 * x) + 638;
		}
		return grav_list;
	}

	public void draw(Graphics g, GamePanel p) {
		if (GamePanel.frame == 7) {
			this.img_index += 1;
			GamePanel.frame = 0;
		}
		
		if (this.a) {
			int width = this.state_imgs.get(this.state)[this.img_index % 3].getWidth(p);
			int height = this.state_imgs.get(this.state)[this.img_index % 3].getHeight(p);
			
			if (this.prev_state == "jump" && this.state == "idle") {
				this.img_index = 0;
			}
			
			g.drawImage(this.state_imgs.get(this.state)[this.img_index % 3], this.x + width, this.y, -width, height, null);
			this.prev_state = this.state;
		}
		else {
			g.drawImage(this.state_imgs.get(this.state)[this.img_index % 3], this.x, this.y, p);
		}
	}
	
	public void update_collider() {
		this.collider.rect.x = this.x + 79;
		this.collider.rect.y = this.y + 43;
	}
}