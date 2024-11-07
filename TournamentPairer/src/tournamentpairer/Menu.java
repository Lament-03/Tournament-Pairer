//package tournamentpairer;

import java.util.Scanner;
public class Menu 
{
    private Scanner sc;
    DatabaseManager Man = new DatabaseManager();
    private int TournamentNum;
    private String Organizer;
    private String TournamentName;
    public void MainMenu()
    {
        System.out.println("Please pick an option that corresponds to the number" + "\n");
        System.out.println("1. Add a player (Without Rank)");
        System.out.println("2. Add a rank to existing player");
        System.out.println("3. Add a match's information");
        System.out.println("4. Remove a player");
        System.out.println("5. Remove a player from a team");
        System.out.println("6. Insert a new tournament");
        System.out.println("7. Add a player to a team");
        System.out.println("8. Delete a match");
        System.out.println("9. Start a tournament (This creates one too)");
        System.out.println("0. Exit program");

        sc = new Scanner(System.in);
        while(!sc.hasNextInt())
        {
            sc.next();
        }
        int input = sc.nextInt();
        switch(input)
        {
            case 1:
            AddPlayer();
            break;

            case 2:
            AddRank();
            break;

            case 3:
            AddMatch();
            break;
            
            case 4:
            RemovePlayer();
            break;
            
            case 5:
            RemovePlayerTeam();
            break;

            case 6:
            AddTournament();
            break;
            
            case 7:
            AddPlayerTeam();
            break;

            case 8:
            RemoveMatch();
            break;

            case 9:
            SetOrganizer();
            break;

            case 0:
            Exit();
            break;
            
            default:
            MainMenu();
        }
    }

    public void Exit()
    {
        Man.close();
        sc.close();
        System.exit(0);
    }

    public void AddPlayer()
    {
        System.out.println("Enter the Tag of the player you want to add or press 0 to go back");
        sc = new Scanner(System.in);
        while(!sc.hasNext())
        {
            sc.next();
        }
        String Tag = sc.next();
        if (Tag.equals("0"))
        {
            MainMenu();
        }
        else if (Tag.length() <= 30)
        {
            System.out.println("Enter the Name of the player");
            sc = new Scanner(System.in);
            while(!sc.hasNext())
            {
                sc.next();
            }
            String Name = sc.next();
            if (Name.length() <= 30)
            {
                Man.insertPlayerInfo(Tag, Name);
                System.out.println("Player has been added successfully");
                MainMenu();
            }
            else
            {
                System.out.println("Please limit it to 30 characters");
                AddPlayer();
            }
        }
        else
        {
            System.out.println("Please limit it to 30 characters");
            AddPlayer();
        }
    }

    public void AddRank()
    {
        System.out.println("\n");
        Man.displayPlayers();
        System.out.println("Press a number corresponding to the Player who's ID you want to edit or press 0 to go back");
        sc = new Scanner(System.in);
        while(!sc.hasNextInt())
        {
            sc.next();
        }
        int ID = sc.nextInt();
        if (ID == 0)
        {
            MainMenu();
        }
        else if (Man.checkPlayer(ID))
        {
            Man.displayRanks();
            System.out.println("Enter the name of the rank you want to add to the Player");
            sc = new Scanner(System.in);
            while (!sc.hasNext())
            {
                sc.next();
            }
            String Rank = sc.next();
            if (Man.insertPlayerRank(ID, Rank))
            {
                System.out.println("Rank has been added successfully");
                MainMenu();
            }
            else//If the rank is not in the database
            {
                AddRank();
            }
        }
        else
        {
            System.out.println("You have entered an invalid ID");
            AddRank();
        }
    }
    
    public void AddMatch()
    {
        System.out.println("\n");
        Man.displayTournaments();
        System.out.println("Enter the ID corresponding to the tournament or press 0 to go back");
        sc = new Scanner(System.in);
        while (!sc.hasNextInt())
        {
            sc.next();
        }
        int TournamentNum = sc.nextInt();
        int TotalTournaments = Man.getTotalTournaments();
        if (TournamentNum == 0)
        {
            MainMenu();
        }
        
        else if (TournamentNum <= TotalTournaments)
        {
            Man.displayPlayers();
            System.out.println("Press the number corresponding to the ID of Player 1");
            while(!sc.hasNextInt())
            {
                sc.next();
            }
            int ID = sc.nextInt();
            if (Man.checkPlayer(ID))
            {
                System.out.println("Press the number corresponding to the ID of Player 2");
                while(!sc.hasNextInt())
                {
                    sc.next();
                }
                int ID2 = sc.nextInt();
                if (Man.checkPlayer(ID2))
                {
                    System.out.println("Enter a 1 if Player 1 had won and a 2 if Player 2 had won");
                    while(!sc.hasNext("[1-2]"))
                    {
                        sc.next();
                    }
                    int input = sc.nextInt();
                    switch (input)
                    {
                        case 1:
                        Man.insertPreviousMatch(ID, ID2, TournamentNum, 1);
                        System.out.println("Added match sucessfully");
                        MainMenu();
                        break;
                        
                        case 2:
                        Man.insertPreviousMatch(ID, ID2, TournamentNum, 0);
                        System.out.println("Added match successfully");
                        MainMenu();
                        break;
                    }
                }
                else
                {
                    System.out.println("You have entered an invalid ID");
                    AddMatch();
                }
            }
            else
            {
                System.out.println("You have entered an invalid ID");
                AddMatch();
            }
        }
        else
        {
            System.out.println("You have entered an ID out of bounds");
            AddMatch();
        }
    }
    
    public void RemovePlayer()
    {
        System.out.println("\n");
        Man.displayPlayers();
        System.out.println("Enter the ID corresponding to the player you want to remove or press 0 to go back");
        sc = new Scanner(System.in);
        while(!sc.hasNextInt())
        {
            sc.next();
        }
        int ID = sc.nextInt();
        if (ID == 0)
        {
            MainMenu();
        }
        else if (Man.checkPlayer(ID))
        {
            Man.deletePlayerAll(ID);
            System.out.println("Player has been deleted successfully");
            MainMenu();
        }
        else
        {
            System.out.println("You have entered an invalid ID");
            RemovePlayer();
        }
    }

    public void AddTournament()
    {
        System.out.println("\n");
        Man.displayTournaments();
        System.out.println("Would you like to create a new one? Press 1 to create and 0 to go back");
        sc = new Scanner(System.in);
        while(!sc.hasNext("[0-1]"))
        {
            sc.next();
        }
        int input = sc.nextInt();
        if (input == 0)
        {
            MainMenu();
        }
        else
        {
            System.out.println("What is the name of the tournament?");
            sc = new Scanner(System.in);
            while (!sc.hasNext())
            {
                sc.next();
            }
            String Name = sc.nextLine();
            if (Name.length() <= 30)
            {
                Man.displayTeams();
                System.out.println("Enter the name of the team that organised the tournament");
                sc = new Scanner(System.in);
                while(!sc.hasNext())
                {
                    sc.next();
                }
                String Team = sc.next();
                if (Man.checkTeam(Team))
                {
                    Man.insertTournament(Man.getTotalTournaments()+1, Name, Team);
                    System.out.println("Tournament Added successfully");
                    MainMenu();
                }
                else
                {
                    System.out.println("You have not entered a valid Team");
                    AddTournament();
                }
            }
            else
            {
                System.out.println("Please limit it to 30 characters");
                AddTournament();
            }
        }
    }

    public void AddPlayerTeam()
    {
        System.out.println("\n");
        Man.displayPlayers();
        System.out.println("Enter the number corresponding to the ID of the player you want to add or press 0 to go back");
        sc = new Scanner(System.in);
        while (!sc.hasNextInt())
        {
            sc.next();
        }
        int ID = sc.nextInt();
        if (ID == 0)
        {
            MainMenu();
        }
        else if (Man.checkPlayer(ID))
        {
            Man.displayTeams();
            System.out.println("Enter the name corresponding to the team you want to give the player");
            sc = new Scanner(System.in);
            while(!sc.hasNext())
            {
                sc.next();
            }
            String Team = sc.next();
            if (Man.checkTeam(Team))
            {
                Man.insertPlayerTeam(ID, Team);
                System.out.println("The player has successfully been added to the team");
                MainMenu();
            }
            else
            {
                System.out.println("You have not entered a valid team");
                AddPlayerTeam();
            }
        }
        else
        {
            System.out.println("You have entered an invalid ID");
            AddPlayerTeam();
        }
    }

    public void RemovePlayerTeam()
    {
        System.out.println("\n");
        Man.displayPlayers();
        System.out.println("Enter the ID corresponding to the player you want to edit or press 0 go back");
        sc = new Scanner(System.in);
        while(!sc.hasNextInt())
        {
            sc.next();
        }
        int ID = sc.nextInt();
        if (ID == 0)
        {
            MainMenu();
        }
        else if (Man.checkPlayer(ID))
        {
            Man.displayTeams();
            System.out.println("Enter the name of the team you want to remove");
            sc = new Scanner(System.in);
            while(!sc.hasNext())
            {
                sc.next();
            }
            String Team = sc.next();
            if (Man.checkTeam(Team))
            {
                Man.deletePlayerTeam(ID, Team);
                System.out.println("Deleted player from team successfully");
                MainMenu();
            }
            else
            {
                System.out.println("You have not entered a valid team");
                RemovePlayerTeam();
            }
        }
        else
        {
            System.out.println("You have not entered a valid ID");
            RemovePlayerTeam();
        }
    }

    public void RemoveMatch()
    {
        System.out.println("\n");
        Man.displayPlayers();
        System.out.println("\n");
        Man.displayPreviousMatches();
        System.out.println("Enter the ID of Player 1 (the first column) or press 0 to go back");
        System.out.println("Remember the number corresponds to their ID");
        sc = new Scanner(System.in);
        while(!sc.hasNextInt())
        {
            sc.next();
        }
        int ID = sc.nextInt();
        if (ID == 0)
        {
            MainMenu();
        }
        else if (Man.checkPlayer(ID))
        {
            System.out.println("Enter the ID of Player 2 (The second column)");
            while(!sc.hasNextInt())
            {
                sc.next();
            }
            int ID2 = sc.nextInt();
            if (Man.checkPlayer(ID2))
            {
                System.out.println("The number corresponding to the tournament (The third column)");
                while(!sc.hasNextInt())
                {
                    sc.next();
                }
                int TotalTournaments = Man.getTotalTournaments();
                int TournamentNum = sc.nextInt();
                if (TournamentNum == 0)
                {
                    System.out.println("Enter a non zero digit please");
                }
                else if (TournamentNum <= TotalTournaments)
                {
                    Man.deletePreviousMatch(ID, ID2, TournamentNum);
                    System.out.println("Deleted match successfully");
                    MainMenu();
                }
                else
                {
                    System.out.println("You have not entered a valid tournament");
                    RemoveMatch();
                }
            }
            else
            {
                System.out.println("You have not entered a valid index");
                RemoveMatch();
            }

        }
        else
        {
            System.out.println("You have not entered a valid ID");
            RemoveMatch();
        }
    }

    public void SetOrganizer()
    {
        Man.displayTeams();
        System.out.println("Who is the organizer of today's tournament? Write the name");
        sc = new Scanner(System.in);
        while (!sc.hasNext("[A-Za-z]+"))
        {
            sc.next();
        }
        Organizer = sc.next();
        if (Man.checkTeam(Organizer))
        {
            SetTournamentName();
        }
        else
        {
            System.out.println("You have not selected one of the organizers of these tournaments");
            SetOrganizer();
        }
    }

    public void SetTournamentName()
    {
        System.out.println("What is the name of today's tournament");
        sc = new Scanner(System.in);
        while (!sc.hasNext("[A-Za-z1-9]+"))
        {
            sc.next();
        }
        TournamentName = sc.nextLine();
        if (TournamentName.length() <= 30)
        {
            StartTournament();
        }
        else
        {
            StartTournament();
            System.out.println("That name is too long limit it to 30 characters please");
            SetTournamentName();
        }
    }

    public void StartTournament()
    {
        TournamentNum = Man.getTotalTournaments() + 1;//Creating a new tournament
        Man.insertTournament(TournamentNum, TournamentName, Organizer);
        int totalPlayers = Man.getTotalPlayers();
        Player[] PlayerList = Man.getPlayers();
        //Getting players
        Quicksort y = new Quicksort();
        //Sorting players according to their win ratio
        y.Sort(PlayerList, 0, totalPlayers-1);
        //Giving player their seed
        for (int i = 1; i < totalPlayers + 1; i++)
        {
            PlayerList[i-1].setSeed(i);
        }
        System.out.println("Here are the players that are going to be in the tournament");
        for (Player current: PlayerList)
        {
            System.out.println(current.getPlayerID() + "\t" + current.getTag() + "\t" + current.getWinRatio() + "\t" + current.getSeed());
        }
        Stack nah = new Stack(totalPlayers);
        CircularQueue fam = new CircularQueue(totalPlayers);
        Stack junk = new Stack(totalPlayers);
        CircularQueue winners = new CircularQueue(totalPlayers);
        Player[] compare = new Player[2];//Array to compare 2 players
        int keep = totalPlayers;
        if (keep % 2 == 0)//Checking if there are an even number of players
        {
            //Adding half the players to the Queue
            for(int i = 0; i < keep/2; i++)
            {
                fam.Add(PlayerList[i]);
            }
            //Adding half the players to the Stack,
            for(int i = keep/2; i < keep; i++)
            {
                nah.Push(PlayerList[i]);
            }
        }
        else
        {
            for(int i = 0; i < keep/2 + 1; i++)
            {
                fam.Add(PlayerList[i]);
            }
            //Adding half the players to the Stack, This is for if there are an odd number of player to make sure the Queue has 1 more than the stack
            for(int i = (keep/2) + 1; i < keep; i++)
            {
                nah.Push(PlayerList[i]);
            }
        }
        do //Do while loop repeats until the whole tournament is completed and there is a winner
        {
            while(!nah.isEmpty()) //This while loop keeps going until a round is completed
            {
                compare[0] = fam.Remove();
                compare[1] = nah.Pop();
                System.out.println(compare[0].getTag() + " and " + compare[1].getTag() + " are fighting now");
                System.out.println("Who won between " + compare[0].getTag() + " and " + compare[1].getTag() + "?");
                System.out.println("Enter 1 for " + compare[0].getTag() + ", 2 for " + compare[1].getTag());
                sc = new Scanner(System.in);
                while(!sc.hasNext("[1-2]"))//Checking if the input is 1 or 2
                {
                    sc.next();
                }
                int input = sc.nextInt();
                switch(input)
                {
                    case 1:
                    Man.insertPreviousMatch(compare[0].getPlayerID(), compare[1].getPlayerID(), TournamentNum, 1);//Inserting the match into the database
                    winners.Add(compare[0]);//Collecting the winners and losers of the round
                    junk.Push(compare[1]);
                    break;

                    case 2:
                    Man.insertPreviousMatch(compare[0].getPlayerID(), compare[1].getPlayerID(), TournamentNum, 0);
                    winners.Add(compare[1]);
                    junk.Push(compare[0]);
                    break;
                }

                if(nah.isEmpty() && keep % 2 != 0) //Checking if there are no extra matches and if there's a player who hasn't played a match
                {
                    compare[0] = fam.Remove();
                    winners.Add(compare[0]);
                }
            }
            //Adding winners back to queue and stack
            if (keep % 2 == 0) //Updates to show how many people are left over after each round
            {
                keep = keep/2;
            }
            else
            {
                keep = keep/2 + 1;
            }
            
            if (keep % 2 == 0)
            {
                for (int i = 0; i < keep/2; i++)
                {
                    //Adding half of the players to the Queue
                    fam.Add(winners.Remove());
                }

                for (int i = keep/2; i < keep; i++)
                {
                    //Adding hald of the players to the Stack
                    nah.Push(winners.Remove());
                }
            }
            else
            {    
            for (int i = 0; i < keep/2 + 1; i++)
                {
                    fam.Add(winners.Remove());
                }
                //Adding half the players to the Stack, making the sure the Queue has 1 less than the stack
                for (int i = (keep/2) + 1; i < keep + 1; i++)
                {
                    if (!winners.isEmpty()) //Checks if the entire tournament has been completed
                    {
                        nah.Push(winners.Remove());
                    }
                }
            }
            //Gathering the winners and losers of the round
        } while (!nah.isEmpty());//If stack is empty, the tournament is over
        System.out.println("Tournament has been completed!");
        Player winner = fam.Remove();
        System.out.println("Our winner is " + winner.getTag() + "! He was seeded " + winner.getSeed());
        Player[] losers = new Player[totalPlayers-1];
        for (int i = 0; i < totalPlayers - 1; i++)
        {
            losers[i] = junk.Pop();
            System.out.println("At " + (i + 2) + " is " + losers[i].getTag() + " He was seeded " + losers[i].getSeed());
        }
        UpdateRatios(winner, losers);
    }

    public void UpdateRatios(Player winner, Player[] losers)
    {
        String[] WinnerTeams = Man.getTeam(winner.getPlayerID());
        winner.setWinRatio(Man.getWinRatio(winner.getPlayerID()));
        for (int i = 0; i <=3; i++)
        {
            if (WinnerTeams[i].equals(Organizer))//If the player is the same team as the organizer, their rank gets a boost
            {
                float newRatio = winner.getWinRatio()+ (float) 0.05; //Boosting the ratio by 5%
                winner.setWinRatio(newRatio);
                i = i + 4;
            }
        }
        for (int i = 1; i <= 4; i++)
        {
            float LowerRank = Man.getLowerRate(Man.getRank(i));
            float UpperRank = Man.getUpperRate(Man.getRank(i));
            if ((LowerRank < winner.getWinRatio()) && (winner.getWinRatio() <= UpperRank))//Checking if the player's win ratio is within the range of the rank
            {
                Man.insertPlayerRankQuick(winner.getPlayerID(), Man.getRank(i));
                i = i + 4;
            }
            else if (winner.getWinRatio() > 1)//If win ratio is greater than 1, they are automatically put in the highest rank
            {
                Man.insertPlayerRankQuick(winner.getPlayerID(), Man.getRank(i));
                i = i + 4;
            }
        }
        for (Player loser : losers)//Updating the losers' win ratios and ranks
        {
            String[] LoserTeams = Man.getTeam(loser.getPlayerID());
            loser.setWinRatio(Man.getWinRatio(loser.getPlayerID()));
            for (int i = 0; i <=3; i++)
            {
                if (LoserTeams[i].equals(Organizer))
                {
                    float newRatio = loser.getWinRatio()+ (float) 0.05;
                    loser.setWinRatio(newRatio);
                    i = i + 4;
                }
            }
            for (int i = 1; i <= 4; i++)
            {
                float LowerRank = Man.getLowerRate(Man.getRank(i));
                float UpperRank = Man.getUpperRate(Man.getRank(i));
                if ((LowerRank < loser.getWinRatio()) && (loser.getWinRatio() <= UpperRank))
                {
                    Man.insertPlayerRankQuick(loser.getPlayerID(), Man.getRank(i));
                    i = i + 4;
                }
                else if (loser.getWinRatio() > 1)
                {
                    Man.insertPlayerRankQuick(loser.getPlayerID(), Man.getRank(i));
                    i = i + 4;
                }
            }
        }
        System.out.println("Everyone's rank has been updated successfully");//End of the tournament
        MainMenu();
    }
}