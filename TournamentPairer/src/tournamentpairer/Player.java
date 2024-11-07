//package tournamentpairer;

public class Player implements Comparable<Player>
{
    private String Tag;
    private int Seed;
    private float winRatio;
    private int PlayerID;
    
    public Player(int ID, String newTag, float newWinRatio)
    {
        Tag = newTag;
        PlayerID = ID;
        winRatio = newWinRatio;
    }

    public void setSeed(int newSeed)
    {
        Seed = newSeed;
    }

    public void setWinRatio(float newWinRatio) 
    {
        winRatio = newWinRatio;
    }

    public void setPlayerID(int newPlayerID) 
    {
        PlayerID = newPlayerID;
    }

    public float getWinRatio()
    {
        return winRatio;
    }

    public String getTag()
    {
        return Tag;
    }

    public int getSeed() 
    {
        return Seed;
    }

    public int getPlayerID() 
    {
        return PlayerID;
    }
    
    @Override
    public int compareTo(Player Player2)
    { 
        return Float.compare(this.winRatio, Player2.winRatio);
    }
}
