package Module3.BowlingKata;

public class BowlingGameException extends Throwable {
    private String message;

    BowlingGameException( String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
