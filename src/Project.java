import java.awt.*;
import javax.swing.*;

public class Project
{  
    static TurnManager tm;
    static int screenWidth = 1280;
    static int screenHeight = 720;
    public static void main(String[] args){
        tm = new TurnManager();
        ImageLabel img = new ImageLabel("images/ariel.png");
        ImageLabel dadImg = new ImageLabel("images/skeleton.png");
        ImageLabel momImg = new ImageLabel("images/skeleton.png");

        Stella stella = new Stella(img);
        Skeleton boneDaddy = new Skeleton(dadImg);
        Skeleton boneMommy = new Skeleton(momImg);

        Player[] players = new Player[4];
        Player[] enemies = new Player[4];
        players[3] = stella;
        enemies[0] = boneDaddy;

        tm.actors[3] = stella;
        tm.actors[4] = boneDaddy;
        tm.actors[5] = boneMommy;
        
        for(int i=0;i<tm.actors.length;i++)
        {
            int divisor = i > 3 ? 100 : 0;
            if(tm.actors[i] != null)
            {
                Icon icon = tm.actors[i].sprite.getIcon();
                tm.actors[i].rect = new Rectangle(150 * i + divisor, 250, icon.getIconWidth(), icon.getIconHeight());
            }
        }
        //boneDaddy.speed = 10;
        tm.initOrder();

        initFrame();
        tm.nextTurn();

        
    }

    public static void initFrame()
    {
        // create a basic JFrame
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("JRPG");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // add panel to main frame
        frame.add(tm);

        

        frame.setVisible(true);
    }
}