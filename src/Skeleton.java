public class Skeleton extends Enemy
{
    
    public Skeleton()
    {
        super();

        attack = 5;
        defence = 4;
        hp = 12;
        maxHp = 12;
        speed = 2;
        isEnemy = true;
        
        abilities.add(new Bash());

        brain = new SkeletonBrain();
    }

    public Skeleton(ImageLabel sprite)
    {
        this();
        setSprite(sprite);
    }
}