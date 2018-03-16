import java.util.*;
import java.io.*;
import java.util.Arrays;
import javax.swing.*;
public class LeagueMenu
{
    public static final String[] mainMenu = { "Create League", "View League", "Log Out" };
    public static final String[] subMenu = { "View Leaderboard", "View Fixtures", "Add Results", "Back to Main Menu" };
      
    public static void main(String[] args) {
      login();
      boolean main = true;
      while (main) {
          boolean sub = true;
          String section = (String) JOptionPane.showInputDialog(null, "Menu","",JOptionPane.QUESTION_MESSAGE, null, mainMenu, mainMenu[0]);
          if(section=="Create League") {
              createLeague();
          }else if(section=="View League") { 
            while (sub) {
                String subSection = (String) JOptionPane.showInputDialog(null, "Menu","",JOptionPane.QUESTION_MESSAGE, null, subMenu, subMenu[0]);
                if(subSection=="View Leaderboard"){
                    viewLeaderboard();
                }else if(subSection=="View Fixtures"){
                    viewFixtures();
                }else if(subSection=="add Results") {
                    addResults();
                }else {
                    sub = false;
                }
            }
          }else 
            main = false;
      }
      JOptionPane.showMessageDialog(null, "You have been logged out");
      System.exit(0);
    }
  
    public static void createLeague() {
		//JOptionPane, asks for League name and number of teams, automatically asigns league number based on how many already exist
		// JOptionPane, asks to input team names corresponding to number of teams
		String leagueName;
		int leagueNumber;
		int numberOfTeams;
		String teamNames = ""; // The string for listing teams, in line 57 the teams are added 1 by 1.
		
		
		leagueName = (String) JOptionPane.showInputDialog(null,"Please enter League Name","",JOptionPane.QUESTION_MESSAGE);
		numberOfTeams = Integer.parseInt(JOptionPane.showInputDialog(null,leagueName + "\n How many teams are to be in the league? \n NOTE: If team number is odd the system will automatically add +1",JOptionPane.INFORMATION_MESSAGE));
		
		if((numberOfTeams % 2) != 0) {  // Checks if number of teams input is even
			numberOfTeams = numberOfTeams + 1; // If its odd it will add 1 to make it even.
		}
		String[] leagueTeamNames = new String[numberOfTeams];
		for (int i = 0; i < numberOfTeams;i++) {
		
		leagueTeamNames[i] = (String) (JOptionPane.showInputDialog(null,"("+ (i + 1) + ")" + " Enter team name",JOptionPane.INFORMATION_MESSAGE));
		teamNames += (i + 1) + ". " + leagueTeamNames[i] + "\n"; // Add teams
		}
		JOptionPane.showMessageDialog(null,teamNames); // List teams
		
		
	
    }
  
    public static void viewLeaderboard() {
      
    }
    
    public static void viewFixtures() {
        
    }
    
    public static void addResults() {
        
    }
    
    public static void login() {
        
    }
}