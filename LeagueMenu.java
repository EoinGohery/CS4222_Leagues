import java.util.*;
import java.io.*;
import javax.swing.*;
public class LeagueMenu
{
    private static final String[] mainMenu = { "Create League", "View League", "Log Out" };
    private static final String[] subMenu = {"View Teams", "View Leaderboard", "View Fixtures", "Add Results", "Back to Main Menu" };
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
          }else if(section=="View League") { 
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
  
    public static void createLeague() throws IOException {
		//JOptionPane, asks for League name and number of teams, automatically asigns league number based on how many already exist
		// JOptionPane, asks to input team names corresponding to number of teams
		String leagueName;
		int leagueNumber = 0;
		int numberOfTeams;
		int adminNum = 0;
		String adminNumber = "admin"
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
	
	public static void viewTeams() {
		boolean found = false;
		String tempLeagueName = "";
		String tempLeagueNumber = "";
		int checkLeagueNumber;
		int i = 0;
		if (leagueInfo.length() == 0) {
            JOptionPane.showMessageDialog(null,"No leagues have been created yet");
		
		} else {
			try{
				checkLeagueNumber = JOptionPane.showInputDialog(null,"Please enter league number");
				x = new Scanner(leagueInfo);
				x.useDelimiter("[,\n]");
				while(x.hasNext() && !found) {
					tempLeagueNumber = x.next();
					if(tempLeagueNumber.trim().equals(checkLeagueNumber.trim())) {
					found = true
					
		}
		
	}	
    
    public static void viewFixtures() {
        
    }
    
    public static void addResults() {
        
    }
    
   public static void login() throws IOException{
        int newAdminNum = 1;
        String userName,password,account,checkA,checkB;
        FileWriter outputStream = new FileWriter(accountInfo,true);//true appends file instead
        PrintWriter pw = new PrintWriter(outputStream);            //of overwriting
        account  = JOptionPane.showInputDialog(null,"Do you have an existing account?  Y/N");
        account = account.toLowerCase();
        System.out.println(account);
        checkA = "y";
        checkB = "n";
        /*pw.println("line 1,a");
          pw.println("line 2");
          pw.println("line 3");   //I am not writing directly to the file,i am writing to the file
                                  //writer which writes it to the file in one chunk
          pw.close();             //indication to copy filewriter into text file */   
        while(!account.equals(checkA)&& !account.equals(checkB)) {
            JOptionPane.showMessageDialog(null,"Please enter either Y or N");
            account  = JOptionPane.showInputDialog(null,"Do you have an existing account?  Y/N");
            account = account.toLowerCase();                  
        }
        if (account.equals(checkA)){
            userName = JOptionPane.showInputDialog(null,"Enter username");
            password = JOptionPane.showInputDialog(null,"Enter password");
            verifyLogin(userName,password);
        }else{ 
            userName = JOptionPane.showInputDialog(null,"Please enter your desired username");
            password = JOptionPane.showInputDialog(null,"Please enter your desired password");
            findAdminNum = new Scanner(accountInfo);
            while(findAdminNum.hasNext()) {
                 findAdminNum.next();
                 newAdminNum++;
            }
            findAdminNum.close();
            String accountDetails = newAdminNum + "," + userName +"," + password;
            pw.println(accountDetails);     
        }
        pw.close();
    }
    
    public static void verifyLogin(String userName, String password) {
        boolean found = false;
        String tempUser = "";
        String tempPass = "";
        String tempNum = "";
        int i = 0;
        if (accountInfo.length() == 0) {
            JOptionPane.showMessageDialog(null,"No Accounts have been created yet");
            System.exit(0);
        } else {    
            try { 
                x = new Scanner(accountInfo);//reads file
                x.useDelimiter("[,\n]");//each field seperated by a coma or space
                while(x.hasNext() && !found) {
                    tempNum = x.next();
                    tempUser = x.next();
                    tempPass = x.next();
                    if (tempUser.trim().equals(userName.trim())&& tempPass.trim().equals(password.trim())) { 
                        found = true;//trim method removes spaces
                        JOptionPane.showMessageDialog(null,"Login successful");
                 
                    }else if (!x.hasNext())  { 
                        JOptionPane.showMessageDialog(null,"Login failed,2 attempts remaining");
                        userName = JOptionPane.showInputDialog(null,"Please enter  username");
                        password = JOptionPane.showInputDialog(null,"Please enter  password");
                    
                    }
                }
                y = new Scanner(accountInfo);//reads file
                y.useDelimiter("[,\n]");//each field seperated by a coma or space
                while(y.hasNext() && !found) { 
                    tempNum = y.next();
                    tempUser = y.next();
                    tempPass = y.next();
                    if (tempUser.trim().equals(userName.trim())&& tempPass.trim().equals(password.trim())) {
                        found = true;//trim method removes spaces
                        JOptionPane.showMessageDialog(null,"Login successful");
                    }else if (!y.hasNext()) { 
                        JOptionPane.showMessageDialog(null,"Login failed,1 attempts remaining");
                        userName = JOptionPane.showInputDialog(null,"Please enter your desired username");
                        password = JOptionPane.showInputDialog(null,"Please enter your desired password");
                    }
                }
                z = new Scanner(accountInfo);//reads file
                z.useDelimiter("[,\n]");//each field seperated by a coma or space
                while(z.hasNext() && !found) {
                   tempNum = z.next();
                   tempUser = z.next();
                   tempPass = z.next();
                   if (tempUser.trim().equals(userName.trim())&& tempPass.trim().equals(password.trim())) {
                        found = true;//trim method removes spaces
                        JOptionPane.showMessageDialog(null,"Login successful");
                    }else if (!z.hasNext()) {
                        JOptionPane.showMessageDialog(null,"Too many failed attempts,closing program");
                        System.exit(0);
               
                    }
                }
            }
            catch(Exception e)
            {
            }
        }
    }
}