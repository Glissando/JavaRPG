public class Item 
{
    private int amount;
    private int maxAmount;
    private String name;
    private String description;

    public Item()
    {

    }
    
    public void setAmount(int amount)
    {
        this.amount = Math.min(Math.max(0,amount),maxAmount);
    }

    public int getAmount()
    {
        return amount;
    }

    public String getName()
    {
        return name;
    }

    public void Use(Party party, Player target)
    {
        this.setAmount(this.amount - 1);
    }
}