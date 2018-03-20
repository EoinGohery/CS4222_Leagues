import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;
public class ValidLoginDetails
{
  public static void main (String [] args) throws IOException
  {
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

