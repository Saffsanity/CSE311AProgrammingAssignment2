import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;



public class Game {
    public static void main(String args[]) {
        int n = 100;
        int m = 5;
        double p = 5;
        int k = 20;




        

        p = p / 100;

        ArrayList<Player> evenSplit = evenSplit(new ArrayList<Player>(), n, m, p, k);
        ArrayList<Player> allACExcept2 = allACExcept2(new ArrayList<Player>(), n, m, p, k);
        ArrayList<Player> oneT4T = oneT4T(new ArrayList<Player>(), n, m, p, k);
        ArrayList<Player> noG = noG(new ArrayList<Player>(), n, m, p, k);


//        try (PrintWriter writer = new PrintWriter(new File("VariedPAveragePayoffs.csv"))) {
//
//            StringBuilder sb = new StringBuilder();
//            sb.append("p,T4T,G,AC,AD,Total\n");
//
//            for(int i = 0; i < k; i++) {
//                sb.append(evenSplit(new ArrayList<Player>(), n, m, (double) (2 * i) / 100, k, 0));
//            }
//
//            writer.write(sb.toString());
//
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//        }

//      ^Above is the code used to print out the systematically changing P data into a CSV to transform into a graph
//      Similar code was written for Question 2 and deleted to keep the file small and readable



    }

    public static String evenSplit(ArrayList<Player> prisoners, int n, int m, double p, int k, int a) {

        System.out.println("Running a 25% split 4 ways \n");


        for(int i = 0; i < n; i++) {
            if (i < n/4) {
                prisoners.add(new Player(Player.TYPE.AC));
            } else if (i > n/4 - 1 && i < n/2) {
                prisoners.add(new Player(Player.TYPE.AD));
            } else if (i > n/2 - 1 && i < n*3/4) {
                prisoners.add(new Player(Player.TYPE.T4T));
            } else {
                prisoners.add(new Player(Player.TYPE.G));
            }
        }

        for(int i = 0; i < k; i++) {

            for(Player player : prisoners) {
                player.payoff = 0;
            }

            for(int x = 0; x < n; x++) {
                for(int y = x + 1; y < n; y++) {
                    prisoners.get(x).playDilemma(m, prisoners.get(y));
                }
            }

            Collections.shuffle(prisoners);

            Collections.sort(prisoners, new PlayerSorter());

            int evict = (int)(p * n);

            for(int r = n - 1; r > n - evict - 1; r--) {
                prisoners.remove(r);
            }

            for(int t = 0; t < evict; t++) {
                prisoners.add(new Player(prisoners.get(t)));
            }

            double[] percentages = new double[4];
            int[] payoffs = new int[4];
            int[] numType = new int[4];

            for(Player player : prisoners) {
                if (player.getType() == Player.TYPE.T4T) {
                    percentages[0] += 1;
                    payoffs[0] += player.payoff;
                    numType[0] += 1;
                } else if (player.getType() == Player.TYPE.G) {
                    percentages[1] += 1;
                    payoffs[1] += player.payoff;
                    numType[1] += 1;
                } else if (player.getType() == Player.TYPE.AC) {
                    percentages[2] += 1;
                    payoffs[2] += player.payoff;
                    numType[2] += 1;
                } else {
                    percentages[3] += 1;
                    payoffs[3] += player.payoff;
                    numType[3] += 1;
                }
            }

            double[] NaNchecker = new double[4];

            if(numType[0] == 0) {
                NaNchecker[0] = 0;
            } else {
                NaNchecker[0] = (double)payoffs[0]/numType[0];
            }

            if(numType[1] == 0) {
                NaNchecker[1] = 0;
            } else {
                NaNchecker[1] = (double)payoffs[1]/numType[1];
            }

            if(numType[2] == 0) {
                NaNchecker[2] = 0;
            } else {
                NaNchecker[2] = (double)payoffs[2]/numType[2];
            }

            if(numType[3] == 0) {
                NaNchecker[3] = 0;
            } else {
                NaNchecker[3] = (double) payoffs[3] / numType[3];
            }

            return (p*100 + "," + NaNchecker[0] + "," + NaNchecker[1] + "," + NaNchecker[2] + "," + NaNchecker[3] + "\n");
        }
        return "";
    }

    public static ArrayList<Player> evenSplit(ArrayList<Player> prisoners, int n, int m, double p, int k) {

        System.out.println("Running a 25% split 4 ways \n");


        for(int i = 0; i < n; i++) {
            if (i < n/4) {
                prisoners.add(new Player(Player.TYPE.AC));
            } else if (i > n/4 - 1 && i < n/2) {
                prisoners.add(new Player(Player.TYPE.AD));
            } else if (i > n/2 - 1 && i < n*3/4) {
                prisoners.add(new Player(Player.TYPE.T4T));
            } else {
                prisoners.add(new Player(Player.TYPE.G));
            }
        }

        for(int i = 0; i < k; i++) {

            for(Player player : prisoners) {
                player.payoff = 0;
            }

            for(int x = 0; x < n; x++) {
                for(int y = x + 1; y < n; y++) {
                    prisoners.get(x).playDilemma(m, prisoners.get(y));
                }
            }

            Collections.shuffle(prisoners);

            Collections.sort(prisoners, new PlayerSorter());

            int evict = (int)(p * n);

            for(int r = n - 1; r > n - evict - 1; r--) {
                prisoners.remove(r);
            }

            for(int t = 0; t < evict; t++) {
                prisoners.add(new Player(prisoners.get(t)));
            }

            double[] percentages = new double[4];
            int[] payoffs = new int[4];
            int[] numType = new int[4];

            for(Player player : prisoners) {
                if (player.getType() == Player.TYPE.T4T) {
                    percentages[0] += 1;
                    payoffs[0] += player.payoff;
                    numType[0] += 1;
                } else if (player.getType() == Player.TYPE.G) {
                    percentages[1] += 1;
                    payoffs[1] += player.payoff;
                    numType[1] += 1;
                } else if (player.getType() == Player.TYPE.AC) {
                    percentages[2] += 1;
                    payoffs[2] += player.payoff;
                    numType[2] += 1;
                } else {
                    percentages[3] += 1;
                    payoffs[3] += player.payoff;
                    numType[3] += 1;
                }
            }

            printGeneration(i, n, numType, payoffs, percentages);
        }

        return prisoners;
    }

    public static ArrayList<Player> allACExcept2(ArrayList<Player> prisoners, int n, int m, double p, int k) {

        System.out.println("All starting prisoners are AC except 2 (one AD and one TFT) \n");

        for(int i = 0; i < n; i++) {
            if (i == 0) {
                prisoners.add(new Player(Player.TYPE.T4T));
            } else if (i == 1) {
                prisoners.add(new Player(Player.TYPE.AD));
            } else {
                prisoners.add(new Player(Player.TYPE.AC));
            }
        }

        for(int i = 0; i < k; i++) {

            for(Player player : prisoners) {
                player.payoff = 0;
            }

            for(int x = 0; x < n; x++) {
                for(int y = x + 1; y < n; y++) {
                    prisoners.get(x).playDilemma(m, prisoners.get(y));
                }
            }

            Collections.shuffle(prisoners);

            Collections.sort(prisoners, new PlayerSorter());

            int evict = (int)(p * n);

            for(int r = n - 1; r > n - evict - 1; r--) {
                prisoners.remove(r);
            }

            for(int t = 0; t < evict; t++) {
                prisoners.add(new Player(prisoners.get(t)));
            }

            double[] percentages = new double[4];
            int[] payoffs = new int[4];
            int[] numType = new int[4];

            for(Player player : prisoners) {
                if (player.getType() == Player.TYPE.T4T) {
                    percentages[0] += 1;
                    payoffs[0] += player.payoff;
                    numType[0] += 1;
                } else if (player.getType() == Player.TYPE.G) {
                    percentages[1] += 1;
                    payoffs[1] += player.payoff;
                    numType[1] += 1;
                } else if (player.getType() == Player.TYPE.AC) {
                    percentages[2] += 1;
                    payoffs[2] += player.payoff;
                    numType[2] += 1;
                } else {
                    percentages[3] += 1;
                    payoffs[3] += player.payoff;
                    numType[3] += 1;
                }
            }

            printGeneration(i, n, numType, payoffs, percentages);
        }

        return prisoners;
    }

    public static ArrayList<Player> oneT4T(ArrayList<Player> prisoners, int n, int m, double p, int k) {
        System.out.println("Running with 1 T4T \n");

        for(int i = 0; i < n; i++) {
            if (i == 0) {
                prisoners.add(new Player(Player.TYPE.T4T));
            } else if (i < n/3) {
                prisoners.add(new Player(Player.TYPE.AD));
            } else if (i < n*2/3) {
                prisoners.add(new Player(Player.TYPE.AC));
            } else {
                prisoners.add(new Player(Player.TYPE.G));
            }
        }

        for(int i = 0; i < k; i++) {

            for(Player player : prisoners) {
                player.payoff = 0;
            }

            for(int x = 0; x < n; x++) {
                for(int y = x + 1; y < n; y++) {
                    prisoners.get(x).playDilemma(m, prisoners.get(y));
                }
            }

            Collections.shuffle(prisoners);

            Collections.sort(prisoners, new PlayerSorter());

            int evict = (int)(p * n);

            for(int r = n - 1; r > n - evict - 1; r--) {
                prisoners.remove(r);
            }

            for(int t = 0; t < evict; t++) {
                prisoners.add(new Player(prisoners.get(t)));
            }

            double[] percentages = new double[4];
            int[] payoffs = new int[4];
            int[] numType = new int[4];

            for(Player player : prisoners) {
                if (player.getType() == Player.TYPE.T4T) {
                    percentages[0] += 1;
                    payoffs[0] += player.payoff;
                    numType[0] += 1;
                } else if (player.getType() == Player.TYPE.G) {
                    percentages[1] += 1;
                    payoffs[1] += player.payoff;
                    numType[1] += 1;
                } else if (player.getType() == Player.TYPE.AC) {
                    percentages[2] += 1;
                    payoffs[2] += player.payoff;
                    numType[2] += 1;
                } else {
                    percentages[3] += 1;
                    payoffs[3] += player.payoff;
                    numType[3] += 1;
                }
            }

            printGeneration(i, n, numType, payoffs, percentages);
        }

        return prisoners;
    }

    public static ArrayList<Player> noG(ArrayList<Player> prisoners, int n, int m, double p, int k) {
        System.out.println("Running with no G \n");

        for(int i = 0; i < n; i++) {
            if (i < n/3) {
                prisoners.add(new Player(Player.TYPE.AD));
            } else if (i < n*2/3) {
                prisoners.add(new Player(Player.TYPE.AC));
            } else {
                prisoners.add(new Player(Player.TYPE.T4T));
            }
        }

        for(int i = 0; i < k; i++) {

            for(Player player : prisoners) {
                player.payoff = 0;
            }

            for(int x = 0; x < n; x++) {
                for(int y = x + 1; y < n; y++) {
                    prisoners.get(x).playDilemma(m, prisoners.get(y));
                }
            }

            Collections.shuffle(prisoners);

            Collections.sort(prisoners, new PlayerSorter());

            int evict = (int)(p * n);

            for(int r = n - 1; r > n - evict - 1; r--) {
                prisoners.remove(r);
            }

            for(int t = 0; t < evict; t++) {
                prisoners.add(new Player(prisoners.get(t)));
            }

            double[] percentages = new double[4];
            int[] payoffs = new int[4];
            int[] numType = new int[4];

            for(Player player : prisoners) {
                if (player.getType() == Player.TYPE.T4T) {
                    percentages[0] += 1;
                    payoffs[0] += player.payoff;
                    numType[0] += 1;
                } else if (player.getType() == Player.TYPE.G) {
                    percentages[1] += 1;
                    payoffs[1] += player.payoff;
                    numType[1] += 1;
                } else if (player.getType() == Player.TYPE.AC) {
                    percentages[2] += 1;
                    payoffs[2] += player.payoff;
                    numType[2] += 1;
                } else {
                    percentages[3] += 1;
                    payoffs[3] += player.payoff;
                    numType[3] += 1;
                }
            }

            printGeneration(i, n, numType, payoffs, percentages);
        }

        return prisoners;
    }

    public static void printGeneration(int i, int n, int[] numType, int[] payoffs, double[] percentages) {
        double[] NaNchecker = new double[4];

        if(numType[0] == 0) {
            NaNchecker[0] = 0;
        } else {
            NaNchecker[0] = (double)payoffs[0]/numType[0];
        }

        if(numType[1] == 0) {
            NaNchecker[1] = 0;
        } else {
            NaNchecker[1] = (double)payoffs[1]/numType[1];
        }

        if(numType[2] == 0) {
            NaNchecker[2] = 0;
        } else {
            NaNchecker[2] = (double)payoffs[2]/numType[2];
        }

        if(numType[3] == 0) {
            NaNchecker[3] = 0;
        } else {
            NaNchecker[3] = (double)payoffs[3]/numType[3];
        }

        System.out.println("Gen " + (i+1) + ":   T4T: " + (percentages[0]/n)*100 + "%   " + "G: " + (percentages[1]/n)*100 + "%   " + "AC: " + (percentages[2]/n)*100 + "%   " + "AD: " + (percentages[3]/n)*100 + "%");
        System.out.println("Gen " + (i+1) + ":   T4T: " + payoffs[0] + "   " + "G: " + payoffs[1] + "   " + "AC: " + payoffs[2] + "   " + "AD: " + payoffs[3] + "    Total: " + Arrays.stream(payoffs).sum());
        System.out.println("Gen " + (i+1) + ":   T4T: " + NaNchecker[0] + "   " + "G: " + NaNchecker[1] + "   " + "AC: " + NaNchecker[2] + "   " + "AD: " + NaNchecker[3]);
        System.out.println("");
    }

}
