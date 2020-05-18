import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameLoop extends JFrame {
   private GamePanel gamePanel = new GamePanel();
   static int fps = 60;
   static int frameCount = 0;
   public static final int WIDTH = 1200;
   public static final int HEIGHT = 800;
   
   public GameLoop()
   {
      super("Platformer Game");
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      JPanel p = new JPanel();
      p.setLayout(new GridLayout(1,2));
      cp.add(gamePanel, BorderLayout.CENTER);
      cp.add(p, BorderLayout.SOUTH);
      setSize(WIDTH, HEIGHT);
      
   }
   
   
   
   //Starts a new thread and runs the game loop in it.
   public void runGameLoop()
   {
      Thread loop = new Thread()
      {
         public void run()
         {
            gameLoop();
         }
      };
      loop.start();
   }
   
   //Only run this in another Thread!
   private void gameLoop()
   {

      final double GAME_HERTZ = 30.0;
      final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;

      final int MAX_UPDATES_BEFORE_RENDER = 5;

      double lastUpdateTime = System.nanoTime();

      double lastRenderTime = System.nanoTime();
      
      //If we are able to get as high as this FPS, don't render again.
      final double TARGET_FPS = 60;
      final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
      
      //Simple way of finding FPS.
      int lastSecondTime = (int) (lastUpdateTime / 1000000000);
      
      while (true)
      {
         double now = System.nanoTime();
         int updateCount = 0;
         
            while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
            {
               updateGame();
               lastUpdateTime += TIME_BETWEEN_UPDATES;
               updateCount++;
            }
   

        if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
        {
           lastUpdateTime = now - TIME_BETWEEN_UPDATES;
        }
     
        drawGame();
        //Update the frames we got.
        int thisSecond = (int) (lastUpdateTime / 1000000000);
        if (thisSecond > lastSecondTime)
        {
           //System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
           fps = frameCount;
           frameCount = 0;
           lastSecondTime = thisSecond;
        }
     

        while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
        {
           Thread.yield();
        

           try {Thread.sleep(1);} catch(Exception e) {} 
        
           now = System.nanoTime();
        }
      }
   }
   
   private void updateGame()
   {
      gamePanel.update();
   }
   
   private void drawGame()
   {  
      gamePanel.repaint();
   }
}