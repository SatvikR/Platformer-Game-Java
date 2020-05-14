import java.awt.Rectangle;

public class PhysicsCollider {
	public Rectangle rect;
	
	public PhysicsCollider(Rectangle rect) {
		this.rect = rect;
	}
	
	public boolean check_lower(PhysicsCollider p) {
		int player_target = this.rect.y + this.rect.height;
		int block_target = p.rect.y;
		
		return player_target >= block_target || player_target >= GameLoop.HEIGHT;
	}
	
	public boolean check_upper(PhysicsCollider p) {
		int player_target = this.rect.y;
		int block_target = p.rect.y + p.rect.height;
		
		return player_target <= block_target || player_target <= 0;
	}
	
	public boolean check_left(PhysicsCollider p) {
		int player_target = this.rect.x;
		int block_target = p.rect.x + p.rect.width;
		
		return player_target <= block_target || player_target <= 0;
	}
	
	public boolean check_right(PhysicsCollider p) {
		int player_target = this.rect.x + this.rect.width;
		int block_target = p.rect.x;
		
		return player_target >= block_target;
	}
	
	public boolean check_lower_boundary() {
		int player_target = this.rect.y + this.rect.height;
		
		return player_target >= GameLoop.HEIGHT;
	}
	
	public boolean check_upper_boundary() {
		int player_target = this.rect.y;
		
		return player_target <= 0;
	}
	
	public boolean check_left_boundary() {
		int player_target = this.rect.x;
		
		return player_target <= 0;
	}
	
	public boolean check_right_boundary() {
		int player_target = this.rect.x + this.rect.width;
		
		return player_target >= GameLoop.WIDTH;
	}
}
