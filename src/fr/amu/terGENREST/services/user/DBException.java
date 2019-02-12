package fr.amu.terGENREST.services.user;

public class DBException  extends RuntimeException{

	private static final long serialVersionUID = 1L; 

	public DBException( String message ) {
        super( message );
    }

    public DBException( String message, Throwable cause ) {
        super( message, cause );
    }

    public DBException( Throwable cause ) {
        super( cause ); 
    }
}
