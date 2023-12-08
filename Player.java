import java.io.Serializable;
public class Player implements Serializable{
    //Upper Section
    private int aces;
    private int twos;
    private int threes;
    private int fours;
    private int fives;
    private int sixes;
    private int upperBonus;
    //Lower Section
    private int threeKind;
    private int fourKind;
    private int fullHouse;
    private int smStraight;
    private int lgStraight;
    private int yahtzee;
    private int chance;
    private int yahtzeeBonus;

    //Upper Section selected
    private boolean [] selected;

    public Player(){
        aces = 0;
        twos = 0;
        threes = 0;
        fours = 0;
        fives = 0;
        sixes = 0;
        upperBonus = 0;
        threeKind = 0;
        fourKind = 0;
        fullHouse = 0;
        smStraight = 0;
        lgStraight = 0;
        yahtzee = 0;
        chance = 0;
        yahtzeeBonus = 0;
        selected = new boolean[13];
        for (int i = 0; i < 13; ++i){
            selected[i] = true;
        }
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
    public int getUpperBonus(){
        return upperBonus;
    }
    public int getYahtzeeBonus(){
        return yahtzeeBonus;
    }
    public int getUpper() {
        return aces + twos + threes + fours + fives + sixes + upperBonus;
    }
    public int getLower() {
        return threeKind + fourKind + fullHouse +
               smStraight + lgStraight + yahtzee + chance + yahtzeeBonus;
    }

    public int getTotal() {
        return getUpper() + getLower();
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

    public void setUpperBonus(){
        this.upperBonus = 35;
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

    public void yahtzeeBonus(){
        this.yahtzeeBonus += 100;
    }
    public void setChance(int chance) {
        this.chance = chance;
    }

    public void addSelected(int i){
        this.selected[i] = false;
    }

    public boolean checkSelected(int i){
        return this.selected[i];
    }
}
