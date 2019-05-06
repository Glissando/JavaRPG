import java.awt.*;
import java.awt.geom.AffineTransform;

public class GradientBackground 
{
  
   private int height;    // height of gradient
   private int width;    // width of gradient
   Color color;
   private int time;
   /**
     * The only constructor for the class, 
     * initializes all the private data,
     * @param int w, int h
     */
   public GradientBackground (int w, int h)
   {
      height = h;
      width = w;
      color = new Color(0,0,200);
   }


   /** Draws a gradient relative to the height and width
      @param Graphics page
     * @return n/a 
      */
   public void draw (Graphics page)
   {
      //increases the blue value gradually
      for (int y = 0; y < height; y+=5)
      {
         float t = (float)(height-y) / height;
         Color c = clerp(Color.black, color, t);
         page.setColor(c);
         //sets each color to have a height of 5 to have more weight and
         //not limit the gradient height to just 256
         page.fillRect(0, y, width, 5);
      }
      Graphics2D g2d = (Graphics2D)page;
      //g2d.rotate(Math.toRadians(time));
      
      //for(int i=0;i<10;i++)
      //{
         int y = 100+time;
         float t = (float)(height-y) / height;
         //Color c = clerp(Color.black, color, t);
         g2d.setColor(Color.RED);
         Rectangle rect1 = new Rectangle(100+time, y, 100, 100);
         AffineTransform transform = new AffineTransform();
         AffineTransform old = g2d.getTransform();
         transform.rotate(Math.toRadians(time), rect1.getX() + rect1.width/2, rect1.getY() + rect1.height/2);
         g2d.setTransform(transform);
         g2d.draw(rect1);
         g2d.fill(rect1);
         
         g2d.setTransform(old);
      //}
      //g2d.rotate(-Math.toRadians(time));
      time += 1;
      if(time == 500)
         time = 0;
   }
   
   public Color clerp(Color a, Color b, float t)
   {
      int r = Math.round(a.getRed() + (b.getRed() - a.getRed()) * t);
      int g = Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t);
      int bc = Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t);
      Color ret = new Color(r,g,bc);
      
      return ret;
   }

   public String toString()
   {
      return "RGB: " + "(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")";
   }
}
