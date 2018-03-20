import java.util.*;
import java.io.*;
import javax.swing.*;
public class LeagueMenu {
    private static final String[] mainMenu = { "Create League", "View and Edit", "Log Out" };
    private static final String[] subMenu = {"View Leagues", "View Teams", "View Leaderboard", "View Fixtures", "Add Results", "Back to Main Menu" };
    private static Scanner x;
    private static Scanner y;
    private static Scanner z;
    private static Scanner findAdminNum;
    private static int currentAdminNum;
	private static File accountInfo = new File ("userInfo.txt"); 
	private static File leagueInfo = new File ("leagueInfo.txt");
	private static File teamInfo = new File ("teamInfo.txt");
    
	public static void main(String[] args) throws IOException {
      login();
      boolean main = true;
      while (main) {
          boolean sub = true;
          String section = (String) JOptionPane.showInputDialog(null, "Menu","",JOptionPane.QUESTION_MESSAGE, null, mainMenu, mainMenu[0]);
          if(section=="Create League") {
              createLeague();
          }else if(section=="View and Edit") { 
            while (sub) {
                String subSection = (String) JOptionPane.showInputDialog(null, "Menu","",JOptionPane.QUESTION_MESSAGE, null, subMenu, subMenu[0]);
                if(subSection=="View Leaderboard"){
                    viewLeaderboard();
                }else if(subSection=="View Fixtures"){
                    viewFixtures();
                }else if(subSection=="add Results") {
                    addResults();
                }else if(subSection =="View Teams") {
					viewTeams();
				}else if(subSection =="View Leagues") {
					viewLeagues();
				}else  {
                    sub = false;
                }
            }
          }else 
            main = false;
      }
      JOptionPane.showMessageDialog(null, "You have been logged out");
      System.exit(0);
    }
  
    
	public static void Leagues(int leagueNumber,String leagueName) {
		String[] leagueList = new String [leagueNumber];
		for (int i = 0;i < leagueNumber;i++) {
			leagueList[i] = leagueName;
		}	
	}
	

	public static void createLeague() throws IOException {
		//JOptionPane, asks for League name and number of teams, automatically asigns league number based on how many already exist
		// JOptionPane, asks to input team names corresponding to number of teams
		String leagueName;
		int leagueNumber = 0;
		int numberOfTeams;
		int adminNum = 0;
		String adminNumber = "admin";
		String fileTeamNames = "";
		String teamNames = ""; // The string for listing teams, in line 57 the teams are added 1 by 1.
		FileWriter outputStream = new FileWriter(leagueInfo,true);
		FileWriter outputStream2 = new FileWriter(teamInfo,true);
		PrintWriter pw = new PrintWriter(outputStream);
		PrintWriter pw2 = new PrintWriter(outputStream2);
		
		leagueName = (String) JOptionPane.showInputDialog(null,"Please enter League Name","",JOptionPane.QUESTION_MESSAGE);
		numberOfTeams = Integer.parseInt(JOptionPane.showInputDialog(null,leagueName + "\n How many teams are to be in the league? \n NOTE: If team number is odd the system will automatically add +1",JOptionPane.INFORMATION_MESSAGE));
		leagueNumber++;
		adminNum++;
		
		if((numberOfTeams % 2) != 0) {  // Checks if number of teams input is even
			numberOfTeams = numberOfTeams + 1; // If its odd it will add 1 to make it even.
		}
		String[] leagueTeamNames = new String[numberOfTeams];
		for (int i = 0; i < numberOfTeams;i++) {
		
		leagueTeamNames[i] = (String) (JOptionPane.showInputDialog(null,"("+ (i + 1) + ")" + " Enter team name",JOptionPane.INFORMATION_MESSAGE));
		if (leagueTeamNames[i].matches(".*[^a-zA-Z].*")) { //Only Alphabetical letters
			JOptionPane.showMessageDialog(null,"Team names may only contain letters!");
			i--; //If input is wrong then then the loop doesn't go forward.
			continue;
		}
		teamNames += (i + 1) + ". " + leagueTeamNames[i] + "\n"; // Format for JOptionPane message
		fileTeamNames = leagueNumber + "," + (i + 1) + ". " + leagueTeamNames[i]; // Actual format to be written to file
		pw2.println(fileTeamNames);
		}
		JOptionPane.showMessageDialog(null,teamNames); // List teams
		adminNumber = (adminNumber + adminNum);
		String LeagueDetails = leagueNumber + "," + leagueName + "," + adminNumber;
		pw.println(LeagueDetails);
		pw.close();
		pw2.close();
	
    }
	
	
	
  
    public static void viewLeaderboard() {
      
    }
	
	public static void viewLeagues(String[] leagueList) {
		String list = (String)(JOptionPane.showInputDialog(null, "Leagues","",JOptionPane.QUESTION_MESSAGE, null, leagueList, leagueList[0]));
	}
	
	
	
	
	public static void viewTeams() {
		boolean found = false;
		String tempLeagueName = "";
		String tempLeagueNumber = "";
		String checkLeagueNumber = "";
		int i = 0;
		if (leagueInfo.length() == 0) {
            JOptionPane.showMessageDialog(null,"No leagues have been created yet");
		
		} else {
			try{
				checkLeagueNumber = (String)JOptionPane.showInputDialog(null,"Please enter league number");
				x = new Scanner(leagueInfo);
				x.useDelimiter("[,\n]");
				while(x.hasNext() && !found) {
					tempLeagueNumber = x.next();
					if(tempLeagueNumber.trim().equals(checkLeagueNumber.trim())) {
					found = true;
					}	
				}				
			}
			catch(Exception e){}
		}	
    }
    public static void viewFixtures() {
        
    }
    
    public static void addResults() {
        
    }
    
   public static void createLogin() throws IOException{
        String UserName,password;
		UserName = JOptionPane.showInputDialog("Please enter desired username");
		password = JOptionPane.showInputDialog("Please enter desired password");
		
		
    
    
    public static void verifyLogin(String userName, String password) throws IOException  {
    String userName   = "",      userPassword   = "";
	File usersFile = new File("userInfo.txt");
	ArrayList<String> userNamesAndPasswords = new ArrayList<String>();
	ArrayList<String> allUserDetails = new ArrayList<String>();
	Scanner in;
	String lineFromFile;
	String fileElements[];
	String message1 = "Please enter your username";
	String message2 = "Please enter your password";
    String message3 = "Invalid input, please re-try";
	String message4 = "Invalid input, no more attempts";
    int chance = 1;
    int adminNumber, position;
    boolean validInput  = false, validAccessDetails = false;
	String aUser = "", tempUserDetails;
	if (usersFile.exists())
	{
	  in = new Scanner(usersFile);	
	  while(in.hasNext())
	  {		  
	    lineFromFile = in.nextLine();
        fileElements = lineFromFile.split(",");
        userNamesAndPasswords.add(fileElements[0] + "," + fileElements[1]);
        allUserDetails.add(lineFromFile);	  
	  }
	  in.close();
	}
	else
	  System.out.println("User file not found");	


    
    while((!(validInput)) && (chance <= 3))
    {
	  userName = JOptionPane.showInputDialog(null, message1);
	  if (userName != null)
	  {
	    userPassword = JOptionPane.showInputDialog(null, message2);  
		if (userPassword != null)
		{
		  aUser = userName + "," + userPassword;	
		  if (userNamesAndPasswords.contains(aUser))
		  {
		     validInput         = true;
			 validAccessDetails = true;
		  }
		  else
		  {
  		     chance += 1; 
			 if (chance <= 3)
			   JOptionPane.showMessageDialog(null, message3);
			 else
			 {
               JOptionPane.showMessageDialog(null, message4);
			   validInput = true;
			 }
		  }
		}
		else
		  validInput = true;
	  }
	  else
	    validInput = true;
    }
	if (validAccessDetails)
	{
	  position        = userNamesAndPasswords.indexOf(aUser);
      tempUserDetails = allUserDetails.get(position);	  
	  fileElements    = tempUserDetails.split(",");
      adminNumber     = Integer.parseInt(fileElements[2]);	  
	  System.out.println("Hello " + adminNumber);
	}	
	else
	  System.out.print("Goodbye");
	}
}
