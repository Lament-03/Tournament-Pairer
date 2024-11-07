//package tournamentpairer;

public class Stack 
{
    private int size;
    private Player[] stack;
    private int pointer = -1;

    public Stack(int newSize) 
    {
        size = newSize;
        stack = new Player[size];
    }

    public void Push(Player addingItem) 
    {
        if (!isFull()) 
        {
            stack[pointer + 1] = addingItem;
            pointer = pointer + 1;
        } 
        else
        {
            System.out.println("Sorry you can't do that it is full");
        }
    }

    public Player Pop() 
    {
        if (!isEmpty()) 
        {
            Player x = stack[pointer];
            stack[pointer] = null;
            pointer = pointer - 1;
            return x;
        } 
        else 
        {
            System.out.println("Sorry there is nothing in the stack");
            return null;
        }
    }

    public Player Peek() 
    {
        if (!isEmpty()) 
        {
            Player x = stack[pointer];
            return x;
        } 
        else 
        {
            System.out.println("Sorry seems the stack is empty");
            return null;
        }
    }

    public boolean isFull() 
    {
        return pointer == size - 1;
    }

    public boolean isEmpty() 
    {
        return pointer == -1;
    }
}