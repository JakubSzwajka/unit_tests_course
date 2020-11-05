package Module3.BowlingKata;

public class App {
    public static void main(String[] args) {

        BowlingGame bowlingGame = new BowlingGame();

        for(String arg: args){
            System.out.println((int)arg.charAt(0) - '0');
        }
    }
}
