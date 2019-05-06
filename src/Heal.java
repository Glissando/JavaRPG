public class Heal extends Ability 
{
    public Heal()
    {
        this.SetRanks((byte)0b11110000,(byte)0);
    }

    public void Use(Player self, Player[] players, Player[] enemies)
    {
        super.Use(self, players, enemies);

        for(Player p : players)
        {
            p.SetHp(p.GetHp() + self.attack);
        }
    }
}