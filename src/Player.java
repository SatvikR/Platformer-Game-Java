import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Arrays;

public class Player{
	public int x;
	public int y;
	public Image img;
	public boolean a;
	public boolean d;
	public double[] jump_list;
	public boolean jumping;
	public boolean sprinting;
	public PhysicsCollider collider;
	private int jump_index;
	private int orig_y;

	public Player(int x, int y, Image i) {
		this.x = x;
		this.y = y;
		this.orig_y = this.y;
		this.img = i;
		this.a = false;
		this.d = false;
		this.jump_list = create_jump();
		this.jumping = false;
		this.jump_index = 0;
		this.sprinting = false;
		this.collider = new PhysicsCollider(new Rectangle(this.x + 79, this.y + 45, 87, 138));
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
		
		if (this.jumping) {
			if (this.jump_index == this.jump_list.length) {
				this.jumping = false;
				this.y = this.orig_y;
				this.jump_index = 0;
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

        while(y >= 0) {
            jump_list = Arrays.copyOf(jump_list, jump_list.length + 1);
            jump_list[jump_list.length - 1] = y;
            x += 1.0;
            y = -2 * Math.pow(x, 2) + (36 * x);
        }
        return jump_list;
	}
	
	public void draw(Graphics g, GamePanel p) {
		g.drawImage(this.img, this.x, this.y, p);
	}
	
	public void update_collider() {
		this.collider.rect.x = this.x + 79;
		this.collider.rect.y = this.y + 43;
	}
}
