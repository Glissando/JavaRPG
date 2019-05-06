import java.awt.*;
import java.util.ArrayList;

public class Ability 
{
    //Ranks that can be selected
    byte targetableRanks;
    //Ranks that are affected, a value of 0 means the only selected target is affected
    byte affectedRanks;
    //Areas you have to be in order to use the ability
    byte positionRanks;
    String description;
    static Color background = new Color(150,150,150);

    public void SetRanks(byte targetableRanks, byte affectedRanks)
    {
        this.targetableRanks = targetableRanks;
        this.affectedRanks = affectedRanks;
    }

    public void Use(Player self, Player[] players, Player[] enemies)
    {

    }

    boolean isValidUse(int index, Player self, Player target)
    {
        if(isRankValid(index) && !target.isDead())
            return true;
        return false;
    }

    boolean isRankValid(int index)
    {
        return ((1 << (7 - index)) & targetableRanks) != 0;
    }

    boolean isAffectValid(int index)
    {
        return ((1 << (7 - index)) & affectedRanks) != 0;
    }

    ArrayList<Integer> targetedRanks(int selectedRank)
    {
        ArrayList<Integer> ranks = new ArrayList<Integer>();

        if(targetableRanks == 0)
        {
            ranks.add(selectedRank);
            return ranks;
        }

        for(int i=0;i<8;i++)
        {
            if(isRankValid(i))
            {
                ranks.add(i);
            }
        }

        return ranks;
    }

    ArrayList<Integer> affectedRanks(int selectedRank)
    {
        ArrayList<Integer> ranks = new ArrayList<Integer>();

        if(affectedRanks == 0)
        {
            ranks.add(selectedRank);
            return ranks;
        }

        for(int i=0;i<8;i++)
        {
            if(isAffectValid(i))
            {
                ranks.add(i);
            }
        }

        return ranks;
    }

    public void Draw(Graphics page, Rectangle dimensions)
    {
        page.setColor(background);
        page.drawRect(dimensions.x, dimensions.y, dimensions.width, dimensions.height);
    }

    public String toString()
    {
        String str = "";

        for(int i=0;i<8;i++)
        {
            if(i == 4)
                str += " | ";
            if(isRankValid(i))
                str += "x";
            else
                str += "o";
        }
        return str;
    }
}