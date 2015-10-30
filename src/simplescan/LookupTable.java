package simplescan;

import java.util.Hashtable;

/**
 * LookupTable provides a lookup for all symbols and keywords in
 * the mini-Pascal language.
 * @author steinmee
 */
public class LookupTable extends Hashtable<String,TokenType> {
    
    public LookupTable()  {
        
        // Keywords
        this.put("program", TokenType.PROGRAM);
        this.put("while", TokenType.WHILE);
        
        // Symbols
        this.put( "+", TokenType.PLUS);
        this.put( "-", TokenType.MINUS);
        this.put( ">", TokenType.GREATER_THAN);
        this.put( ">=", TokenType.GREATER_THAN_EQUALS);
        
    }
}
