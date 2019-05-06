import java.awt.Color;

public class Stella extends Player
{
    
    public Stella()
    {
        super();
        attack = 6;
        defence = 4;
        hp = 15;
        maxHp = 15;
        speed = 4;
        isEnemy = false;
        color = new Color(164,66,244);
        
        abilities.add(new Burn());
        abilities.add(new Heal());
        abilities.add(new Howl());
    }

    public Stella(ImageLabel sprite)
    {
        this();
        setSprite(sprite);
    }
}