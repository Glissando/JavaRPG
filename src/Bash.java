public class Bash extends Ability
{
    public Bash()
    {

    }
    
    public void Use(Player self, Player[] players, Player[] enemies)
    {
        super.Use(self, players, enemies);

        Player e = enemies[0];
        e.SetHp(e.GetHp() - Math.max(1, (2 + self.attack - e.defence)));
    }
}