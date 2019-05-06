import java.util.Random;

public class SkeletonBrain extends AIBrain
{
    public void pickAbility(Player self, Player[] players)
    {
        super.pickAbility(self, players);

        Random r = new Random();

        int target = r.nextInt(1);
        Ability bash = self.abilities.get(0);
        Player[] allies = new Player[0];
        if(players[3] != null && target == 0){
            //Use bash on first rank
            Player[] enemies = {players[3]};
            bash.Use(self, allies, enemies);
            System.out.println("attacking first");
        }
        else if(players[2] != null)
        {
            //Use bash on second rank
            Player[] enemies = {players[2]};
            bash.Use(self, allies, enemies);
            System.out.println("attacking second");
        }
        else
        {
            //pass
            System.out.print("nothing to do");
        }
    }
}