//package tournamentpairer;

public class Quicksort 
{
    private float Split(Player[] List, int low, int high)
    {
        Player pivot = List[((high + low) / 2)];
        int i = low - 1;
        int j = high + 1;
        while (true)
        {
            do // Finding leftmost element less than or equal to pivot
            {
                i = i + 1;
            } while (List[i].compareTo(pivot) > 0);
            do // Finding rightmost element greater than or equal to pivot
            {
                j = j - 1;
            } while (List[j].compareTo(pivot) < 0);
            if (i >= j) // If 2 pointers have met
            {
                return j
            }
            Player x = List[i];
            List[i] = List[j];
            List[j] = x; // Swapping the 2 elements
            // Algorithm repeats until pointers meet
        }
    }

    public void Sort(Player[] List, int low, int high) 
    {
        if (low < high)// If there is more than 1 element in the list
        {
            int a = (int) Split(List, low, high); // a is the splitting index, This is to put the pivot in the right place in the list
            Sort(List, low, a); // Performs algorithm on items beofre pivot and sorts them
            Sort(List, a + 1, high); // Then after pivot
        }
    }
}