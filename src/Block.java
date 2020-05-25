import java.awt.Image;
import java.awt.Graphics;

public class Block {
    public PhysicsCollider collider;
    Image img;
    public int x, y;
    
    public Block(Image img, int x, int y, PhysicsCollider collider) {
         this.img = img;
         this.x = x;
         this.y = y;
         this.collider = collider;
    }

    public void draw(Graphics g, GamePanel p) {
        g.drawImage(this.img, this.x, this.y, p);
    }
}