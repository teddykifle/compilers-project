
package simplescan;

import java.io.File;

/**
 * A unit test based on the ScannerTestOne.txt input file.
 * @author steinmee
 */
public class ScannerTest {
    public static void main(String[] args) {
        // Create a Scanner and point it at a file
        // In ScannerTestOne, there should be > program  # account >=
        Scanner s = new Scanner( new File( "ScannerTestOne.txt"));

        // This should be the >
        boolean hasToken = s.nextToken();
        TokenType token = s.getToken();
        String lexeme = s.getLexeme();
        
        // Test to make sure these values are correct
        assert hasToken : "No token for >";
        assert token == TokenType.GREATER_THAN : "No token type for >";
        assert lexeme.equals( ">") : "No lexeme for >";
        
        // This should be the program token
        hasToken = s.nextToken();
        token = s.getToken();
        lexeme = s.getLexeme();
        
        // Test to make sure these values are correct
        assert hasToken : "No token for program";
        assert token == TokenType.PROGRAM : "No token type for program";
        assert lexeme.equals( "program") : "No lexeme for program";
        
  
    
        // This should be the # token
        hasToken = s.nextToken();
        token = s.getToken();
        lexeme = s.getLexeme();
        
        // Test to make sure these values are correct
        assert hasToken == false : "No token for #";
        assert token == null : "No token type for #";
        assert lexeme.equals( "#") : "No lexeme for #";
        
        
        // This should be the account token
        hasToken = s.nextToken();
        token = s.getToken();
        lexeme = s.getLexeme();
        
        // Test to make sure these values are correct
        assert hasToken : "No token for account";
        assert token == TokenType.ID : "No token type for account";
        assert lexeme.equals( "account") : "No lexeme for account";
        
        
        // This should be the >= token
        hasToken = s.nextToken();
        token = s.getToken();
        lexeme = s.getLexeme();
        
        // Test to make sure these values are correct
        assert hasToken : "No token for >=";
        assert token == TokenType.GREATER_THAN_EQUALS : "No token type for >=";
        assert lexeme.equals( ">=") : "No lexeme for >=";
        
        // What happens when we hit the end of the file?
        hasToken = s.nextToken();
        token = s.getToken();
        lexeme = s.getLexeme();
        
        // Test to make sure these values are correct
        assert hasToken == false : "No token for EOF";
        assert token == null : "No token type for EOF";
        assert lexeme == null : "No lexeme for EOF";
        
    }
}
