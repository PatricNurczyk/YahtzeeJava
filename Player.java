public class Player {
    //Upper Section
    private int aces;
    private int twos;
    private int threes;
    private int fours;
    private int fives;
    private int sixes;
    //Lower Section
    private int threeKind;
    private int fourKind;
    private int fullHouse;
    private int smStraight;
    private int lgStraight;
    private int yahtzee;
    private int chance;

    public Player(){
        aces = 0;
        twos = 0;
        threes = 0;
        fours = 0;
        fives = 0;
        sixes = 0;
        threeKind = 0;
        fourKind = 0;
        fullHouse = 0;
        smStraight = 0;
        lgStraight = 0;
        yahtzee = 0;
        chance = 0;
    }

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
    public int getChance() {
        return chance;
    }
    public int getTotal() {
        return aces + twos + threes + fours + fives + sixes +
               threeKind + fourKind + fullHouse +
               smStraight + lgStraight + yahtzee + chance;
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
    public void setChance(int chance) {
        this.chance = chance;
    }
}
