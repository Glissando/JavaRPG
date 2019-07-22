import java.util.Collections;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.*;

public class TurnManager extends JPanel {
    private static final long serialVersionUID = 1L;
    Player[] actors = new Player[8];
    ArrayList<Player> playerOrder;
    private int currentPlayer;
    private boolean mouseDown;
    private boolean playerInput;
    private boolean turnLock = false;
    private boolean inCombat = true;
    Rectangle[] rects;
    Point rectPosition;
    int rectPadding = 100;
    int rectWidth = 100;
    int turnCount;
    int turnDelay = 1000;
    Ability selectedAbility;
    Player selectedPlayer;
    GradientBackground background;
    Timer drawTimer;

    public TurnManager()
    {
        rects = new Rectangle[3];
        rectPosition = new Point(370, 500);
        for(int i=0;i<3;i++)
        {
            rects[i] = new Rectangle(rectPosition.x + i * (rectWidth + rectPadding), rectPosition.y, rectWidth, rectWidth);
        }
        turnCount = 1;
        currentPlayer = 0;
        background = new GradientBackground(Project.screenWidth, Project.screenHeight);
        playerInput = false;

        addMouseListener(new MouseAdapter() { 
            public void mousePressed(MouseEvent e) { 
              onClick(e);
            } 
        });

        ActionListener taskPerformer = new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
                repaint();
            }
        };
        drawTimer = new Timer(25, taskPerformer);
        drawTimer.start();
    }

    //Boolean method that returns true if the game has ended
    public boolean nextTurn()
    {  
        //Check if the game ended
        if(isOver())
        {
            gameOver();
            return true;
        }

        if(turnLock)
            return false;

        //Sort and remove dead actor(s) from the turn queue.
        updateOrder();

        updateBackground();

        repaint();
        
        Player actor = playerOrder.get(currentPlayer);
        
        //Update player state
        actor.update();

        if(actor.stunned)
        {
            actor.stunned = false;
            currentPlayer++;
            nextTurn();
            return false;
        }

        if(actor.isDead())
        {
            nextTurn();
            return false;
        }
        System.out.println("Current Player: " + currentPlayer);
        System.out.println("Current Turn: " + turnCount);
        
        if(actor.isEnemy)
        {
            turnLock = true;
            Enemy enemy = (Enemy)actor;
            enemy.brain.pickAbility(enemy, actors);
            
            repaint();
            ActionListener taskPerformer = new ActionListener() 
            {
                public void actionPerformed(ActionEvent evt) 
                {
                    if(!isOver())
                    {
                        turnLock = false;
                        currentPlayer++;
                    }
                    nextTurn();
                    
                }
            };
            Timer timer = new Timer(turnDelay, taskPerformer);
            timer.setRepeats(false);
            timer.start();
        }
        else
        {
            //Wait for player input
            turnLock = true;
            selectedAbility = actor.abilities.get(0);
            playerInput = true;
            return false;
        }

        return false;
    }

    public void gameOver()  
    {
        turnLock = true;
        inCombat = false;
        System.out.println("You win!");
    }

    public void initOrder()
    {
        int c = 0;
        for(Player a : actors)
        {
            if(a != null)
                c++;
        }

        playerOrder = new ArrayList<Player>(c);
        for(Player a : actors)
        {
            if(a != null)
                playerOrder.add(a);
        }

        if(playerOrder.size() > 0)
            selectedPlayer = playerOrder.get(0);
        sortOrder();
    }

    public void updateOrder()
    {
        //Remove dead player(s) from turn order
        for(int i=0;i<playerOrder.size();i++)
        {
            if(playerOrder.get(i).hp == 0)
            {
                playerOrder.remove(i);
                i--;
            }
        }

        //Check if we already finished going through the players, if so resort the arraylist for a new round
        if(currentPlayer == playerOrder.size())
        {
            currentPlayer = 0;
            sortOrder();
            turnCount++;
        }
    }

    public void sortOrder()
    {     
        Collections.sort(playerOrder, (a,b) -> a.speed > b.speed ? -1 : 1);     
    }

    public boolean isOver()
    {
        //Check if the game has ended
        for(int i=4;i<actors.length;i++)
        {
            if(actors[i] != null && actors[i].hp != 0)
                break;
            if(i==7)
                return true; //enemies defeated
        }
 
        for(int i=0;i<4;i++)
        {
            if(actors[i] != null && actors[i].hp != 0)
                break;
            if(i==3)
                return true; //players defeated
        }

        return false;
    }

    public void update()
    {
        //PointerInfo a = MouseInfo.getPointerInfo();
        //Point p = a.getLocation();
        //int x = (int)p.getX();
        //int y = (int)p.getY();

    }

    public void onClick(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            mouseDown = true;
            //Is it currently a player's turn?
            if(playerInput)
            {
                Point p = e.getPoint();

                Player player = playerOrder.get(currentPlayer);

                //Check if an ability has been clicked on
                for(int i=0;i<rects.length;i++)
                {
                    if(rects[i].contains(p))
                    {
                        selectedAbility = player.abilities.get(i);
                        return;
                    }
                }

                //Iterate over all actor(s)
                for(int i=0;i<actors.length;i++)
                {
                    Player actor = actors[i];
                    //Check if an actor has been clicked on
                    if(actor != null && actor.rect.contains(p))
                    {
                        //If the actor clicked was previously selected then try to apply the currently selected ability
                        if(actor == selectedPlayer)
                        {
                            //Verify that the ability can be applied to this position
                            if(selectedAbility.isValidUse(i, player, actor))
                            {
                                ArrayList<Integer> targets = selectedAbility.affectedRanks(i);
                                ArrayList<Player> players = new ArrayList<Player>();
                                ArrayList<Player> enemies = new ArrayList<Player>();

                                for(int j=0;j<targets.size();j++)
                                {
                                    if(actors[targets.get(j)] != null)
                                    {
                                        //The target is a player
                                        if(targets.get(j) < 4)
                                        {
                                            players.add(actors[targets.get(j)]);
                                        }
                                        else
                                        {
                                            enemies.add(actors[targets.get(j)]);
                                            actors[targets.get(j)].getHit();
                                        }
                                    }
                                }
                                //players.
                                Player[] tp = players.toArray(new Player[players.size()]);
                                Player[] te = enemies.toArray(new Player[enemies.size()]);
                                selectedAbility.Use(playerOrder.get(currentPlayer), tp, te);
                                playerInput = false;
                                repaint();
                                //Wait for animation to finish then start the next turn
                                ActionListener taskPerformer = new ActionListener() 
                                {
                                    public void actionPerformed(ActionEvent evt) 
                                    {
                                        if(!isOver())
                                        {
                                            turnLock = false;
                                            currentPlayer++;
                                        }
                                        nextTurn();
                                        
                                    }
                                };
                                Timer timer = new Timer(turnDelay, taskPerformer);
                                timer.setRepeats(false);
                                timer.start();
                            }
                        }
                        //If the player selected a different actor then update their selection
                        else
                        {
                            selectedPlayer = actors[i];
                            return;
                        }
                    }
                }
                
            }
        }
    }

    public void draw(Graphics page)
    {
        background.draw(page);

        for(Player p : actors)
        {
            if(p != null)
            {
                p.draw(page);
            }
        }

        if(currentPlayer > -1)
        {
            Player p = playerOrder.get(currentPlayer);
            if(!p.isEnemy)
            {
                for(int i=0;i<p.abilities.size();i++)
                {
                    Ability ability = p.abilities.get(i);
                    ability.Draw(page, rects[i]);
                }
            }
        }
        
        Player current = currentPlayer();
        if(current != null)
        {
            Rectangle rect = current.rect;
            page.setColor(new Color(122,122,20));
            page.fillRect(rect.x, rect.y + rect.height + 20, rect.width, 10);
        }
        Font font = new Font("Verdana", Font.BOLD, 28);
        page.setFont(font);
        page.drawString("Turn: " + turnCount, 50, 50);
    }

    public void updateBackground()
    {
        float pScore = 0f;
        for(int i=0;i<4;i++)
        {
            if(actors[i] != null)
                pScore += actors[i].healthRatio() * actors[i].statTotal();
        }

        float eScore = 0.0f;
        for(int i=0;i<8;i++)
        {
            if(actors[i] != null)
                eScore += actors[i].healthRatio() * actors[i].statTotal();
        }

        float t = Math.max(Math.min(pScore / eScore, 1.0f), 0.0f);

        System.out.println("blue amount: " + t);
        Color c = background.clerp(new Color(255,0,0), new Color(0,0,255), t);

        background.color = c;
    }

    public Player currentPlayer()
    {
        if(currentPlayer < playerOrder.size())
            return playerOrder.get(currentPlayer);
        return null;
    }

    public void paint(Graphics page) 
    {
        double scaleX = getSize().getWidth() / 1280;
        double scaleY = getSize().getHeight() / 720;
        Graphics2D g2d = (Graphics2D)page;
        g2d.scale(scaleX, scaleY);
        super.paint(page);
        draw(page);

        for(Component c : getComponents())
        {
            c.paint(page);
        }
        g2d.scale(-scaleX, -scaleY);
        
        
    }
}