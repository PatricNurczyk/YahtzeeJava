public class Player {
    //Upper Section
    private int aces = 0;
    private int twos = 0;
    private int threes = 0;
    private int fours = 0;
    private int fives = 0;
    private int sixes = 0;
    //Lower Section
    private int threeKind = 0;
    private int fourKind = 0;
    private int fullHouse = 0;
    private int smStraight = 0;
    private int lgStraight = 0;
    private int yahtzee = 0;

    // Getters
    public int getAces() {
        return aces;
    }
    public int getTwos() {
        return twos;
    }
    public int getThrees() {
        return threes;
    }
    public int getFours() {
        return fours;
    }
    public int getFives() {
        return fives;
    }
    public int getSixes() {
        return sixes;
    }
    public int getThreeKind() {
        return threeKind;
    }
    public int getFourKind() {
        return fourKind;
    }
    public int getFullHouse() {
        return fullHouse;
    }
    public int getSmStraight() {
        return smStraight;
    }
    public int getLgStraight() {
        return lgStraight;
    }
    public int getYahtzee() {
        return yahtzee;
    }
    public int getTotal() {
        return aces + twos + threes + fours + fives + sixes +
               threeKind + fourKind + fullHouse +
               smStraight + lgStraight + yahtzee;
    }
    // Setters
    public void setAces(int aces) {
        this.aces = aces;
    }
    public void setTwos(int twos) {
        this.twos = twos;
    }
    public void setThrees(int threes) {
        this.threes = threes;
    }
    public void setFours(int fours) {
        this.fours = fours;
    }
    public void setFives(int fives) {
        this.fives = fives;
    }
    public void setSixes(int sixes) {
        this.sixes = sixes;
    }
    public void setThreeKind(int threeKind) {
        this.threeKind = threeKind;
    }
    public void setFourKind(int fourKind) {
        this.fourKind = fourKind;
    }
    public void setFullHouse(int fullHouse) {
        this.fullHouse = fullHouse;
    }
    public void setSmStraight(int smStraight) {
        this.smStraight = smStraight;
    }
    public void setLgStraight(int lgStraight) {
        this.lgStraight = lgStraight;
    }
    public void setYahtzee(int yahtzee) {
        this.yahtzee = yahtzee;
    }
}
