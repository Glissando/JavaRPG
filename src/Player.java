import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Player
{
    int hp;
    int maxHp;
    int speed;
    int attack;
    int defence;
    int restore;
    Rectangle rect;
    float damageModifier;
    float defenceModifier;
    boolean isEnemy;
    Color color;
    private int animationCount = 0;
    private Timer timer;
    ArrayList<Condition> conditions;
    ArrayList<Ability> abilities;
    ImageLabel sprite;

    public Player()
    {
        conditions = new ArrayList<Condition>();
        abilities = new ArrayList<Ability>();
        color = new Color(125,125,125);
    }

    public Player(Player player)
    {
        hp = player.hp;
        maxHp = player.maxHp;
        speed = player.speed;
        attack = player.attack;
    }

    public void setSprite(ImageLabel sprite)
    {
        this.sprite = sprite;
        Project.tm.add(sprite);
    }

    public void SetHp(int hp)
    {
        this.hp = Math.min(Math.max(hp, 0), maxHp);
    }

    public int GetHp()
    {
        return hp;
    }

    public void addHp(int amount)
    {
        SetHp(hp + amount);
    }

    public void AddCondition(Condition condition)
    {
        conditions.add(condition);
        condition.Add(this);
    }

    public void removeCondition(Condition condition)
    {
        condition.Remove(this);
    }

    public void loadImage(String filepath, JPanel panel)
    {
        ImageLabel label = new ImageLabel(new ImageIcon("images/reactor.png"));
        label.setLocation(rect.x, rect.y);
        panel.add(label);
    }

    public void update()
    {
        for(Condition c : conditions)
        {
            c.update(this);
        }

        this.addHp(restore);

        for(int i=0;i<conditions.size();i++)
        {
            Condition c = conditions.get(i);
            if(c.isFinished())
            {
                removeCondition(c);
            }
        }
    }

    public boolean isDead()
    {
        return hp == 0;
    }

    public int statTotal()
    {
        return attack + defence + speed;
    }

    public double healthRatio()
    {
        return (double)hp / maxHp;
    }

    public String toString()
    {
        String str = "";
        str += "HP: " + hp + "/" + maxHp + "\n";
        str += "Speed: " + speed + "\n";
        str += "Attack: " + attack + "\n";
        str += "Defence: " + defence + "\n";
        return str;
    }

    public void getHit()
    {
        ActionListener taskPerformer = new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
                if(animationCount == 10)
                {
                    timer.stop();
                    animationCount = 0;
                    MoveLeft();
                }
                else
                {
                    rect.x += 5;
                    animationCount++;
                }
            }
        };
        timer = new Timer(20, taskPerformer);
        timer.start();
    }

    public void MoveLeft()
    {
        ActionListener taskPerformer = new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
                if(animationCount == 20)
                {
                    timer.stop();
                    animationCount = 0;
                    MoveRight();
                }
                else
                {
                    rect.x -= 5;
                    animationCount++;
                }
            }
        };
        timer = new Timer(20, taskPerformer);
        timer.start();
    }

    public void MoveRight()
    {
        ActionListener taskPerformer = new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
                if(animationCount == 10)
                {
                    timer.stop();
                    animationCount = 0;
                }
                else
                {
                    rect.x += 5;
                    animationCount++;
                }
            }
        };
        timer = new Timer(20, taskPerformer);
        timer.start();
    }

    public void draw(Graphics page)
    {
        
        if(sprite != null)
            sprite.setLocation(rect.x, rect.y);
        else
        {
            //Draw actor
            page.setColor(color);
            page.fillRect(rect.x, rect.y, rect.width, rect.height);
        }
        //Draw healthbar
        int w = (int)Math.round(rect.width * ((double)hp / maxHp));
        int h = 15;
        int x = rect.x;
        int y = rect.y - 25;
        page.setColor(Color.RED);
        page.fillRect(x, y, rect.width, h);
        page.setColor(Color.GREEN);
        page.fillRect(x, y, w, h);
    }
}