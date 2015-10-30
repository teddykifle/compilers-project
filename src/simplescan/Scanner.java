
package simplescan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

/**
 * Scanner encompasses a scanner for the mini-Pascal language.
 * 
 * @author steinmee
 */
public class Scanner {

	//// Class constants
	private final int START = 0;
	private final int IN_ID_OR_KEYWORD = 1;
	private final int ERROR = 100;
	private final int ID_COMPLETE = 101;
	private final int SYMBOL_COMPLETE = 102;
	private final int SHORT_SYMBOL_COMPLETE = 103;

	//// Instance Variables

	private TokenType type;

	private String lexeme;

	private PushbackReader input;

	private LookupTable lookup = new LookupTable();

	/////  Constructor

	/**
	 * Creates a Scanner based on an input file.
	 * @param inputFile The file to read.
	 */
	public Scanner( File inputFile) {

		FileReader fr = null;
		try {
			fr = new FileReader(inputFile);
		}
		catch( FileNotFoundException fnfe) {
			System.out.println("Can't find file " + inputFile + ".");
			System.exit(1);
		}
		this.input = new PushbackReader( fr);
	}


	//// Methods

	/**
	 * Finds the next token in the input stream.
	 * Finds the next token in the attached input stream by
	 * implementing a state machine.
	 * When the end of file is encountered, this will return
	 * false setting both the lexeme and token type to null.
	 * When an illegal character or other error is encountered,
	 * this will return false setting the token type to null but
	 * setting the lexeme to the most recently constructed lexeme.
	 * @return true if a token is found, false otherwise.
	 */
	public boolean nextToken() {
		int stateNumber = 0;
		String currentLexeme = "";
		int currentCharacter = 0;

		while( stateNumber < ERROR) {
			try {
				currentCharacter = input.read();
			}
			catch( IOException ioe) {
				// FIXME
			}
			switch( stateNumber) {
			case START:
				if( currentCharacter == -1) {
					// at the end of file, set both lexeme and token type to null
					this.lexeme = null;
					this.type = null;
					return( false);
				}
				else{ 
					if( Character.isLetter( currentCharacter)) {
						stateNumber = IN_ID_OR_KEYWORD;
						currentLexeme += (char)currentCharacter;
					}
					else{ 
						if ( Character.isWhitespace( currentCharacter)) {

						}
						else{ 
							if( currentCharacter == '+' ||
									currentCharacter == '-') {
								stateNumber = SYMBOL_COMPLETE;
								currentLexeme += (char)currentCharacter;
							}
							else{ 
								if( currentCharacter == '>') {
									stateNumber = 2;
									currentLexeme += (char)currentCharacter;
								}
								else{ 
									if( currentCharacter == '{') {
										stateNumber = 3;
									}
									else {
										currentLexeme += (char)currentCharacter;
										stateNumber = ERROR;
									}
								}
							}
						}
					}
				}
				break;
			case IN_ID_OR_KEYWORD:
				if( currentCharacter == -1) {
					stateNumber = ID_COMPLETE;                        
				}
				else if( Character.isLetterOrDigit( currentCharacter)) {
					currentLexeme += (char)currentCharacter;                        
				}
				else {
					try {
						input.unread( currentCharacter);
					}
					catch( IOException ioe){
						// FIXME
					}
					stateNumber = ID_COMPLETE;
				}
				break;
			case 2:
				if( currentCharacter == '=') {
					stateNumber = SYMBOL_COMPLETE;
					currentLexeme += (char)currentCharacter;                        
				}
				else {
					try {
						input.unread( currentCharacter);
					}
					catch( IOException ioe){
						// FIXME
					}
					stateNumber = SHORT_SYMBOL_COMPLETE;
				}
				break;
			case 3:
				if( currentCharacter == '{') {
					currentLexeme += (char)currentCharacter;
					stateNumber = ERROR;
				}
				else if(currentCharacter == '}') {
					stateNumber = 0;
				}
				else {
					// Stay in the comment state 3
				}
				break;

			} // end switch 
		} // end while
		this.lexeme = currentLexeme;
		if( stateNumber == ERROR) {
			this.type = null;
			return( false);
		}
		else if( stateNumber == ID_COMPLETE) {
			this.type = lookup.get( this.lexeme);
			if( this.type == null) {
				this.type = TokenType.ID;
			}
			return( true);
		}
		else if( stateNumber == SYMBOL_COMPLETE) {
			this.type = lookup.get( this.lexeme);
			return( true);
		}
		else if( stateNumber == SHORT_SYMBOL_COMPLETE) {
			this.type = lookup.get( this.lexeme);
			return( true);
		}

		return( false);
	}

	/** 
	 * Returns the token type.
	 * @return The token type of the most recent token.
	 */
	public TokenType getToken() { return this.type;}

	/**
	 * Returns the lexeme.
	 * @return The lexeme of the most recent token.
	 */
	public String getLexeme() { return this.lexeme;}



}
