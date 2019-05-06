public class Condition
{
    float damageModifier = 0.0f;
    float defenceModifier = 0.0f;
    int restore;
    int speed;
    private int turns;
    int turnCount;

    public Condition(int turns, float damageModifier, float defenceModifier, int restore, int speed)
    {
        this.damageModifier = damageModifier;
        this.defenceModifier = defenceModifier;
        this.restore = restore;
        this.speed = speed;
        this.turns = turns;
        turnCount = 0;
    }

    public void Add(Player player)
    {
        player.conditions.add(this);
        player.speed += speed;
        player.restore += restore;
        player.damageModifier += damageModifier;
        player.defenceModifier += defenceModifier;
    }

    public void Remove(Player player)
    {
        player.conditions.remove(this);
        player.speed -= speed;
        player.restore -= restore;
        player.damageModifier -= damageModifier;
        player.defenceModifier -= defenceModifier;
    }

    public void update(Player player)
    {
        turnCount++;
    }

    public int getTurnCount()
    {
        return turnCount;
    }

    public boolean isFinished()
    {
        return turnCount == turns;
    }
}