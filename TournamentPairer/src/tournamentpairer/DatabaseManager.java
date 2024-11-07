//package tournamentpairer;

import java.sql.*;

public class DatabaseManager 
{
    private Connection conn;

    public DatabaseManager() 
    {
        String url = "jdbc:sqlite:TournamentParticipants.db";
        try 
        {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) 
        {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public void close() 
    {
        try 
        {
            conn.close();
        } catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }

    public void displayPlayers()
    {
        String sql = "Select PlayerID, Tag, Name, RankingID From PlayerInfo";
        try (Statement temp = conn.createStatement())
        {
            ResultSet rs = temp.executeQuery(sql);
            while (rs.next())
            {
                System.out.println(rs.getInt("PlayerID") + "\t" +
                        rs.getString("Tag") + "\t" +
                        rs.getString("Name") + "\t" +
                        rs.getInt("RankingID"));
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void displayPreviousMatches()
    {
        String sql = "Select Player1, Player2, Tournament, Win From PreviousMatches";
        try (Statement temp = conn.createStatement())
        {
            ResultSet rs = temp.executeQuery(sql);
            while (rs.next())
            {
                System.out.println(rs.getInt("Player1") + "\t" +
                rs.getInt("Player2") + "\t" +
                rs.getInt("Tournament") + "\t" +
                rs.getInt("Win"));
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void displayRanks()
    {
        String sql = "Select RankingID, RankName, LowerRate, UpperRate From RankingInfo";
        try (Statement temp = conn.createStatement())
        {
            ResultSet rs = temp.executeQuery(sql);
            while (rs.next())
            {
                System.out.println(rs.getInt("RankingID") + "\t" +
                rs.getString("RankName") + "\t" +
                rs.getFloat("LowerRate") + "\t" +
                rs.getFloat("UpperRate"));
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void displayTournaments()
    {
        String sql = "Select TournamentID, TourneyName, Organizer From TournamentInfo";
        try (Statement temp = conn.createStatement())
        {
            ResultSet rs = temp.executeQuery(sql);
            while (rs.next())
            {
                System.out.println(rs.getInt("TournamentID") + "\t" +
                rs.getString("TourneyName") + "\t" +
                rs.getInt("Organizer"));
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void displayTeams()
    {
        String sql = "Select TeamID, TeamName From TeamInfo";
        try (Statement temp = conn.createStatement())
        {
            ResultSet rs = temp.executeQuery(sql);
            while (rs.next())
            {
                System.out.println(rs.getInt("TeamID") + "\t" +
                rs.getString("TeamName"));
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkTeam(String Team)//Checks if the team exists
    {
        String sql = "Select TeamName From TeamInfo where TeamName = \""+Team+"\"";
        try (PreparedStatement temp = conn.prepareStatement(sql))
        {
            ResultSet rs = temp.executeQuery();
            String Check = rs.getString("TeamName");
            return true;
        } catch (SQLException e)
        {
            return false;
        }
    }

    public boolean checkPlayer(int ID)//Checks if the player exists
    {
        String sql = "Select PlayerID From PlayerInfo Where PlayerID = " + ID;
        try (PreparedStatement temp = conn.prepareStatement(sql))
        {
            ResultSet rs = temp.executeQuery();
            int Check = rs.getInt("PlayerID");
            return true;
        } catch (SQLException e)
        {
            return false;
        }
    }
    
    public void insertPlayerInfo(String Tag, String Name)//Inserts a player into the database
    {
        String sql = "INSERT INTO PlayerInfo (Tag, Name) VALUES(\""+Tag+"\", \""+Name+"\")";
        try (PreparedStatement temp = conn.prepareStatement(sql)) 
        {
            temp.executeUpdate();
            temp.close();
        } catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }

    public void insertPlayerTeam(int ID, String Team)//Assigns a player to a team
    {
        String sql = "Insert Into TeamMembers " +
        "Values("+ID+", " +
        "(Select TeamID from TeamInfo Where TeamName = \""+Team+"\"))";
        try (PreparedStatement temp = conn.prepareStatement(sql))
        {
            temp.executeUpdate();
            temp.close();
        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void deletePlayerAll(int ID)
    {
        String sql = "Delete From PreviousMatches " +
        "Where (Player1 = "+ID+") " +
        "or (Player2 = "+ID+")";
        PreparedStatement temp;
        try
        {
            temp = conn.prepareStatement(sql);
            temp.executeUpdate();
        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        sql = "Delete From TeamMembers Where Player = "+ID+"";
        try
        {
            temp = conn.prepareStatement(sql);
            temp.executeUpdate();
        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        sql = "Delete From PlayerInfo Where PlayerID = "+ID+"";
        try
        {
            temp = conn.prepareStatement(sql);
            temp.executeUpdate();
            temp.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public String[] getTeam(int PlayerID)//Returns the team the player is on
    {
        String sql = "Select TeamInfo.TeamName " + 
        "From TeamInfo, TeamMembers, PlayerInfo " +
        "Where (PlayerInfo.PlayerID = "+PlayerID+") and (PlayerInfo.PlayerID = TeamMembers.Player) and (TeamMembers.TeamID = TeamInfo.TeamID)";
        String[] x = new String[4];
        try(PreparedStatement temp = conn.prepareStatement(sql))
        {
            ResultSet rs = temp.executeQuery();
            int i = 0;
            while (rs.next())
            {
                x[i] = rs.getString(1);
                i++;
            }
            while (i < 4)//Fills the rest of the array with 0s
            {
                x[i] = "0";
                i++;
            }
        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return x;
    }

    public int getTotalPlayers()//Returns the total number of players
    {
        String sql = "SELECT Count(*) FROM PlayerInfo";
        int totalPlayers = 0;
        try (PreparedStatement temp = conn.prepareStatement(sql)) 
        {
            ResultSet rs = temp.executeQuery();
            totalPlayers = rs.getInt(1);
            rs.close();
            temp.close();
        } catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        return totalPlayers;
    }

    public String getRank(int ID)//Returns the rank of the player
    {
        String sql = "Select RankName From RankingInfo Where RankingID = " + ID;
        String Rank = null;
        try (PreparedStatement temp = conn.prepareStatement(sql))
        {
            ResultSet rs = temp.executeQuery();
            Rank = rs.getString("RankName");
            rs.close();
            temp.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return Rank;
    }

    public Player[] getPlayers()//Returns an array of all the players
    {
        String sql = "SELECT PlayerID, Tag FROM PlayerInfo ";
        Player[] x = new Player[getTotalPlayers()];
        try (PreparedStatement temp = conn.prepareStatement(sql)) 
        {
            ResultSet rs = temp.executeQuery();
            int i = 0;
            while (rs.next())
            {
                x[i] = new Player(rs.getInt("PlayerID"), rs.getString("Tag"), getWinRatio(rs.getInt("PlayerID")));
                i = i + 1;
            }
            rs.close();
            temp.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return x;
    }

    public float getWinRatio(int PlayerID)//Returns the win ratio of the player
    {
        String sql = "Select count(PreviousMatches.Win) " + "from PreviousMatches, PlayerInfo "
                + "where ((PlayerInfo.PlayerID = "+PlayerID+") and (PreviousMatches.Player1 = PlayerInfo.PlayerID) and (PreviousMatches.Win = 1)) "
                + "or ((PlayerInfo.PlayerID = "+PlayerID+") and (PreviousMatches.Player2 = PlayerInfo.PlayerID) and (PreviousMatches.Win = 0))";
        float totalWins = 0;
        float totalGames = 0;
        PreparedStatement temp;
        ResultSet rs;
        try 
        {
            temp = conn.prepareStatement(sql);
            rs = temp.executeQuery();
            totalWins = rs.getInt(1);
        } catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }

        sql = "Select count(PreviousMatches.Win) " + "from PreviousMatches, PlayerInfo "
                + "where ((PlayerInfo.PlayerID = "+PlayerID+") and (PreviousMatches.Player1 = PlayerInfo.PlayerID)) "
                + "or ((PlayerInfo.PlayerID = "+PlayerID+") and (PreviousMatches.Player2 = PlayerInfo.PlayerID))";
        try 
        {
            temp = conn.prepareStatement(sql);
            rs = temp.executeQuery();
            totalGames = rs.getInt(1);
            rs.close();
            temp.close();
        } catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }

        if (totalGames != 0)
        {
            float winRatio = totalWins / totalGames;
            return winRatio;
        } 
        else
        {
            return 0;
        }
    }

    public float getLowerRate(String Rank)//Returns minimum win ratio for a rank
    {
        String sql = "Select LowerRate From RankingInfo Where RankName = \""+Rank+"\"";
        float Lower = 0;
        try(PreparedStatement temp = conn.prepareStatement(sql))
        {
            ResultSet rs = temp.executeQuery();
            Lower = rs.getFloat("LowerRate");
            rs.close();
            temp.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return Lower;
    }

    public float getUpperRate(String Rank)//Returns maximum win ratio for a rank
    {
        String sql = "Select UpperRate From RankingInfo Where RankName = \""+Rank+"\"";
        float Upper = 0;
        try (PreparedStatement temp = conn.prepareStatement(sql))
        {
            ResultSet rs = temp.executeQuery();
            Upper = rs.getFloat("UpperRate");
            rs.close();
            temp.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return Upper;
    }
    
    public boolean insertPlayerRank(int PlayerID, String Rank)//Assigns a rank to a player
    {
        float Lower = getLowerRate(Rank);
        float Upper = getUpperRate(Rank);
        PreparedStatement temp;
        float Ratio = getWinRatio(PlayerID);
        if ((Lower < Ratio) && (Ratio <= Upper)) 
        {
            String sql = "Update PlayerInfo Set RankingID = (select RankingID from RankingInfo where RankName = \""+Rank+"\") where PlayerID = " + PlayerID;
            try 
            {
                temp = conn.prepareStatement(sql);
                temp.executeUpdate();
                temp.close();
                return true;
            } catch (SQLException e) 
            {
                System.out.println(e.getMessage());
                return false;
            }
        } 
        else 
        {
            System.out.println("The player does not have the win ratio for this rank");
            return false;
        }
    }

    public void insertPlayerRankQuick(int PlayerID, String Rank)//Assigns a rank to a player without checking the win ratio
    {
        String sql = "Update PlayerInfo Set RankingID = (select RankingID from RankingInfo where RankName = \""+Rank+"\") where PlayerID = " + PlayerID;
        try (PreparedStatement temp = conn.prepareStatement(sql))
        {
            temp.executeUpdate();
            temp.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void deletePreviousMatch(int Player1, int Player2, int TournamentNum)
    {
        String sql = "DELETE FROM PreviousMatches " +
        "WHERE (Player1 = "+Player1+") " +
        "AND (Player2 = "+Player2+") " + 
        "AND (Tournament = "+TournamentNum+")";
        try(PreparedStatement temp = conn.prepareStatement(sql))
        {
            temp.executeUpdate();
            temp.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void deletePlayerTeam(int ID, String Team)
    {
        String sql = "Delete from TeamMembers " +
        "Where (Player = "+ID+") " +
        "And (TeamID = (Select TeamID From TeamInfo Where TeamName = \""+Team+"\"))";
        try (PreparedStatement temp = conn.prepareStatement(sql))
        {
            temp.executeUpdate();
            temp.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void insertPreviousMatch(int Player1, int Player2, int TournamentNum, int Win)
    {
        String sql = "Insert Into PreviousMatches "+
        "Values("+Player1+", " +
        ""+Player2+", " +
        ""+TournamentNum+", " +
        ""+Win+")";
        try(PreparedStatement temp = conn.prepareStatement(sql))
        {
            temp.executeUpdate();
            temp.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public int getTotalTournaments()
    {
        String sql = "Select Count(*) From TournamentInfo";
        int totalTournaments = 0;
        try(PreparedStatement temp = conn.prepareStatement(sql))
        {
            ResultSet rs = temp.executeQuery();
            totalTournaments = rs.getInt(1);
            rs.close();
            temp.close();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return totalTournaments;
    }

    public boolean insertTournament(int ID, String TournamentName, String Organizer)//Inserts a tournament into the database
    {
        String sql = "Insert Into TournamentInfo " +
        "Values ("+ID+", \""+TournamentName+"\", (Select TeamID From TeamInfo Where TeamName = \""+Organizer+"\"))";
        try (PreparedStatement temp = conn.prepareStatement(sql))
        {
            temp.executeUpdate();
            temp.close();
            return true;
        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
}