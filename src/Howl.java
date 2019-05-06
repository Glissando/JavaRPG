public class Howl extends Ability
{
    public Howl()
    {
        this.SetRanks((byte)0b00001111, (byte)0b00001111);
    }

    public void Use(Player self, Player[] players, Player[] enemies)
    {
        super.Use(self, players, enemies);

        for(Player e : enemies)
        {
            e.AddCondition(new Condition(3, 0f, -0.3f, -1, 0));
        }
    }
}