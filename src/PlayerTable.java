import java.util.ArrayList;

import javax.swing.JTable;

public class PlayerTable 
{
    static int ITERATIONS = 10;

    public JTable[] createTables(Player p1, Player p2) 
    {
        String[] columnNames = {"HP","ATT","DEF","SPD"};
        JTable[] tables = new JTable[p1.abilities.size()];

        for (int i=0;i<p1.abilities.size();i++) 
        {
            Ability a = p1.abilities.get(i);
            Object[][] data = new Object[10][4];
            Player[] allies = {};
            Player[] enemies = { p2 };
            int count = 0;
            while (!p2.isDead() && count < 10) 
            {
                p2.update();
                a.Use(p1, allies, enemies);
                data[count][0] = new Integer(p2.hp);
                data[count][1] = new Integer(p2.attack);
                data[count][2] = new Integer(p2.defence);
                data[count][3] = new Integer(p2.speed);
                count++;
            }
            tables[i] = new JTable(data, columnNames);
        }

        return tables;
    }

    public void findBreakpoint(Ability a, Player p1, Player p2)
    {
        int attackCount = 0;

        while(!p2.isDead())
        {
            
        }
    }
}