import java.util.Random;
import java.util.Arrays;
/**
 * Class creates an array of coins, one being "fake" (weighted less), and tests to
 * see where the fake coin is located.
 *
 */
public class SimpleFakeCoin {

    private Coin[] initializeCoins(int numCoins){
        Coin[] coins = new Coin[numCoins];
        for(int j = 0; j < coins.length; j++)
            coins[j] = new Coin(0.02, j);
        Random ran = new Random();
        Float f = numCoins * ran.nextFloat();
        int fakePosition = f.intValue();
        coins[fakePosition].setWeight(0.01);  // this is the fake coin
        System.out.println("The fake coin is a position " + fakePosition);
        return coins;
    }

    /**********************************************************
     * Compares the weights of two sets of coins
     * @param left   The set of coins to be placed on the left balance pan
     * @param right  The set of coins to be placed on the right balance pan
     * @return
     *   -1 means the left pan is heavier
     *    0 means the pans have equal weight
     *    1 means the right pan is heavier
     **********************************************************/
    private int compareCoins(Coin[] left, Coin[] right){
        if(left.length != right.length)
            System.out.println("unequal piles");
        double leftWeight = 0.0;
        for (Coin aLeft : left)
            leftWeight += aLeft.getWeight();
        double rightWeight = 0.0;
        for (Coin aRight : right)
            rightWeight += aRight.getWeight();
        if(leftWeight < rightWeight)
            return 1;
        else if (leftWeight > rightWeight)
            return -1;
        else
            return 0;
    }

    private void searchMessage(Coin[] coins){
        if (coins.length >= 1){
            System.out.println("searching from " + coins[0].getPosition()+ " to "
                    + coins[coins.length - 1].getPosition());
        } else
            System.out.println("searching empty array");
    }

    /**
     * Task: Finds the location of the fake coin when separated into 3 piles
     * @param coins an array of coins
     * @return the location of the fake coin
     */
    private int findFakeCoin(Coin[] coins){
        if(coins.length == 0)
            return -1; // fake coin not found
        else if(coins.length == 1)
            return coins[0].getPosition();
        else {
            boolean oddNumCoins = coins.length % 2 == 1;
            int third = coins.length / 3;
            Coin[] leftCoins = Arrays.copyOfRange(coins, 0, third);
            Coin[] middleCoins = Arrays.copyOfRange(coins, third, 2 * third);
            Coin[] rightCoins = Arrays.copyOfRange(coins, 2 * third, coins.length);
            int result = compareCoins(leftCoins, middleCoins);
            if(result == 0) {
                searchMessage(rightCoins);
                return findFakeCoin(rightCoins);
            }
            else if (result == 1) {
                searchMessage(leftCoins);
                return findFakeCoin(leftCoins);
            }
            else if(result == -1){
                searchMessage(middleCoins);
                return findFakeCoin(middleCoins);
            }
            else if(oddNumCoins)
                return coins[coins.length-1].getPosition();
            else return -1; // no fake coin found
        }
    }

    public static void main(String[] args) {
        SimpleFakeCoin fc = new SimpleFakeCoin();
        Coin[] coins = fc.initializeCoins(100);
        int fakeCoinPosition = fc.findFakeCoin(coins);
        System.out.println("fake coin is found at position " + fakeCoinPosition);
    }

    private class Coin {  // inner class
        double weight;
        int position;

        Coin(double weight, int position){
            this.weight = weight;
            this.position = position;
        }

        double getWeight() {
            return weight;
        }

        void setWeight(double weight){
            this.weight = weight;
        }

        int getPosition(){
            return position;
        }
    }

}