//package tournamentpairer;

public class CircularQueue 
{
    private int maxSize;
    private int size;
    private Player[] queue;
    private int frontPointer = -1;
    private int rearPointer = 0;

    public CircularQueue(int newMaxSize) 
    {
        maxSize = newMaxSize;
        queue = new Player[maxSize];
    }

    public void Add(Player addingItem)
    {
        if (!isFull()) 
        {
            if (frontPointer == -1) 
            {
                queue[frontPointer + 1] = addingItem;
                frontPointer = frontPointer + 1;
                size = size + 1;
            } 
            else
            {
                rearPointer = (rearPointer + 1) % maxSize;
                queue[rearPointer] = addingItem;
                size = size + 1;
            }
        } 
        else 
        {
            System.out.println("Seems the queue is full");
        }
    }

    public Player Remove()
    {
        if (!isEmpty()) 
        {
            Player x = queue[frontPointer];
            queue[frontPointer] = null;
            frontPointer = (frontPointer + 1) % maxSize;
            size = size - 1;
            return x;
        }
        else 
        {
            System.out.println("Seems that the queue is empty");
            return null;
        }
    }

    public Player Peek() 
    {
        if (!isEmpty()) 
        {
            Player x = queue[frontPointer];
            return x;
        } 
        else
        {
            System.out.println("Seems the queue is empty ");
            return null;
        }
    }

    public boolean isFull() 
    {
        return size == maxSize;
    }

    public boolean isEmpty() 
    {
        return size == 0;
    }
}