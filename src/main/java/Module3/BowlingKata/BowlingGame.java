package Module3.BowlingKata;

import java.security.KeyPair;

public class BowlingGame {

    private int score;
    private char status; // could be:
    // "/" for spare
    // "X" for strike

    private int[][] frames = new int[11][2];

    private int[] currentFrame = new int[2];
    private int currentFrameNum = 1;

    public BowlingGame(){
        this.status = ' ';
    }

    public int getScore() {
        int totalScore = 0;
        for( int[] a : this.frames){
            totalScore += a[0];
            totalScore += a[1];
        }
        return totalScore;
    }

    public void roll(int pins) throws BowlingGameException {

        if (pins > 10){throw new BowlingGameException("There is only 10 Pins");}

        if (this.frames[this.currentFrameNum][0] == 0){
            this.frames[this.currentFrameNum][0] = pins;
        }else{
            this.frames[this.currentFrameNum][1] = pins;

            this.currentFrameNum += 1;
        }

        this.addBonusPoints(pins);

    }

    public void runFrame( String frame ) throws BowlingGameException {
        for(int i=0; i<2;i++){
            int x = (int)frame.charAt(i) - '0';
            if ( x > 0 && x < 10){
                this.roll(x);
            }else if( frame.charAt(i) == 'X' &&
                     (frame.charAt(i) == '/' &&
                     (frame.charAt(i) == '-'))){
                this.charRoll( frame.charAt(i));
            }
        }
    }

    public void charRoll(char charAt) throws BowlingGameException {
        if (charAt == 'X'){
            this.roll(10);
            this.addBonusPoints(10);
            this.status = charAt;
        }else if(charAt == '/'){
            int points = 10 - this.frames[this.currentFrameNum][0];
            this.roll(points);
            this.addBonusPoints(points);
            this.status = charAt;
        }
    }

    private void addBonusPoints( int points ){

        switch (this.status){
            case '/':
                this.frames[this.currentFrameNum - 1][1] += points;
                this.status = ' ';
            case 'X':
//                this.frames[this.currentFrameNum - 1][1] += points;
//                this.status = '/';
        }
    }

    public char getStatus() {
        return this.status;
    }

    public int getFrameNumber() {
        return this.currentFrameNum;
    }
}
