package workingCmeans;

public class MembershipMatrix {

    double[][] matrix;
    int n;
    int c;
    
    /**
     * creates a MembershipMatrix and randomly initialize memberships
     * 
     * @param n number of patterns/observations
     * @param c number of clusters/groups
     */
    public MembershipMatrix(int n, int c) {
            this.n = n;
            this.c = c;
            
            matrix = new double[n][c];
            
            // we can make the random value to be 1 / k, 
            // giving equal memberships for all clusters
            /*for (int i = 0; i < n; i++)
                    for (int j = 0; j < c; j++)
                            matrix[i][j] = 1.0 / (double) c;*/
    }
    
    /**
     * creates a MembershipMatrix using the specified matrix
     */
    public MembershipMatrix(double[][] matrix) {
            this.matrix = matrix ;
    }
    
 //   public static void main(String[] args) { 
  //          MembershipMatrix mm = new MembershipMatrix(5, 3);
//          List<Integer> clusters = mm.getClusters();
//          System.out.println("");
//          
//          int[] patterns0 = mm.getPatternsForCluster(0);
//          int[] patterns1 = mm.getPatternsForCluster(1);
//          int[] patterns2 = mm.getPatternsForCluster(2);
            
     //       System.out.println();
    //}
}