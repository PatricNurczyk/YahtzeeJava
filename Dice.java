public class Dice{
    private int value;
    private boolean isKept;

    public Dice(){
        this.value = 0;
        this.isKept = false;
    }

    public void setValue(int val){
        this.value = val;
    }

    public int getValue(){
        return this.value;
    }

    public void setKept(boolean k){
        this.isKept = k;
    }

    public boolean checkKept(){
        return this.isKept;
    }
}