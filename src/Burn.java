import java.awt.*;

public class Burn extends Ability {

    public Burn()
    {
        this.SetRanks((byte)0b00001111, (byte)0);
    }

    public void Use(Player self, Player[] players, Player[] enemies)
    {
        super.Use(self, players, enemies);

        for(Player e : enemies)
        {
            if(e != null)
                e.SetHp(e.GetHp() - Math.max(1, (3 + self.attack - e.defence)));
        }
    }

    public void Draw(Graphics page, Rectangle dimensions)
    {
        super.Draw(page, dimensions);

    }
}