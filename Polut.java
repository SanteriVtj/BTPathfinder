import java.util.*;

public class Polut {
    private int pathCount;
    
    public int laske(int n) {
        pathCount = 0;

        if (n == 1) return 1;
        int[][] visited = new int[n][n];    //Merkkaa reittiä tähän taulukkoon
        visited[0][0] = 1;
        int[][] howMany = new int[2][n];    //ensimmäiseen listaa merkka kuinka monta kertaa kullakin rivillä on käyty ja toiseen kuinka monta kertaa sarakkeissa
        howMany[0][0] = 1;
        howMany[1][0] = 1;
        findPaths(1,0,visited,2,howMany);
//        int[][] x = {
//            {1,8,9,0},
//            {2,7,10,0},
//            {3,6,11,0},
//            {4,5,12,0}
//        };

//        int[][] a = {{3,3,3,3},{4,4,4,0}};
//        System.out.println(oneWay(a, 4));
//        int[][] a = changeZeros(0,3,x);
//        for (int[] i : a) System.out.println(Arrays.toString(i));
        return pathCount * 2;
    }
    
    public void findPaths(int x, int y, int[][] visited, int count, int[][] howMany) {
//        for (int[] i : howMany) System.out.println(Arrays.toString(i));               //####testi tulostuksia
//        System.out.println("===***===***===");
//        for (int[] i : visited) System.out.println(Arrays.toString(i));
//        System.out.println("------------");
        if (count == (visited.length * visited.length)) {
//            System.out.println("ADDED");
            pathCount++;
        } else {
            if (visited[x][y] == 0) {
                visited[x][y] = count;
                howMany[0][x] += 1;
                howMany[1][y] += 1;
//                for (int[] i : visited) System.out.println(Arrays.toString(i));
//                if (divide(howMany,visited.length - 1)) {

//                  ((test(x - 1, y, visited) && test(x + 1, y, visited) && test(x, y + 1, visited) && test(x, y - 1, visited)
//                        && test(x - 1, y - 1, visited) && test(x + 1, y + 1, visited) && test(x + 1, y - 1, visited) && test(x - 1, y + 1, visited)) || count == (visited.length * visited.length) - 1)
                if (div(x,y,visited)) {
//                    System.out.println("***");
//                    for (int[] i : visited) System.out.println(Arrays.toString(i));
//                    System.out.println("-------------------");
                    if (doesItExist(x + 1, y, visited.length)
                            && visited[x + 1][y] == 0) findPaths(x + 1, y, visited, count + 1, howMany);
                    if (doesItExist(x, y + 1, visited.length)
                            && visited[x][y + 1] == 0) findPaths(x, y + 1, visited, count + 1, howMany);
                    if (doesItExist(x - 1, y, visited.length)
                            && visited[x - 1][y] == 0) findPaths(x - 1, y, visited, count + 1, howMany);
                    if (doesItExist(x, y - 1, visited.length)
                            && visited[x][y - 1] == 0) findPaths(x, y - 1, visited, count + 1, howMany);
                }
            }
            visited[x][y] = 0;
            howMany[0][x] -= 1;
            howMany[1][y] -= 1;
        }
    }
    
    public boolean oneWay(int[][] howMany, int n) {
        int row = 0, column = 0, aRow = 0, aColumn = 0;
        for (int i = 0; i < howMany[0].length; i++) {
            if (howMany[0][i] == n) row++;
            if (howMany[0][i] == n - 1) aRow++;
            if (howMany[1][i] == n) column++;
            if (howMany[1][i] == n - 1) aColumn++;
        }
        if ((row == n - 1 && aColumn == n) || (aRow == n && column == n - 1)) return false;
        return true;
    }
    
    public boolean div(int x, int y, int[][] visited) {
        int[][] a = new int[visited.length][visited.length];
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited.length; j++) {
                a[i][j] = visited[i][j];
            }
        }
        if (doesItExist(x + 1,y,visited.length) && visited[x + 1][y] == 0) a = changeZeros(x + 1,y,a);
        else if (doesItExist(x - 1,y,visited.length) && visited[x - 1][y] == 0) a = changeZeros(x - 1,y,a);
        else if (doesItExist(x,y + 1,visited.length) && visited[x][y + 1] == 0) a = changeZeros(x,y + 1,a);
        else if (doesItExist(x,y - 1,visited.length) && visited[x][y - 1] == 0) a = changeZeros(x,y - 1,a);
        
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (a[i][j] == 0) return false;
            }
        }
        return true;
    } 
    
    public int[][] changeZeros(int x, int y, int[][] a) {
        a[x][y] = -1;
        if (doesItExist(x + 1, y, a.length) && a[x + 1][y] == 0) changeZeros(x + 1, y, a);
        if (doesItExist(x - 1, y, a.length) && a[x - 1][y] == 0) changeZeros(x - 1, y, a);
        if (doesItExist(x, y + 1, a.length) && a[x][y + 1] == 0) changeZeros(x, y + 1, a);
        if (doesItExist(x, y - 1, a.length) && a[x][y - 1] == 0) changeZeros(x, y - 1, a);
        return a;
    }
    
    public boolean divide(int[][] howMany, int n) {                                             //Tutkii jakaako reitti taulukon kahtia esim näin:
        for (int i = 1; i < howMany[0].length - 1; i++) {                                       //{{1,2,0}
            if (howMany[0][i] == n + 1) {                                                       //{0,3,0}
                boolean smallerS = true, biggerS = true;                                        //{0,4,0}}
                for (int s = i - 1; s >= 0; s--){
                    if (howMany[0][s] < n + 1) smallerS = false;
                }
                for (int b = i + 1; b < howMany[0].length; b++) {
                    if (howMany[0][b] < n + 1) biggerS = false;
                }
                if (!smallerS && !biggerS) return false;
            } else if (howMany[1][i] == n + 1) {
                if (i > 0 && i < n) {
                    boolean smallerS = true, biggerS = true;
                for (int s = i - 1; s >= 0; s--){
                    if (howMany[1][s] < n + 1) smallerS = false;
                }
                for (int b = i + 1; b < howMany[1].length; b++) {
                    if (howMany[1][b] < n + 1) biggerS = false;
                }
                if (!smallerS && !biggerS) return false;
                }
            }
        }
        return true;
    }
     
    public boolean test(int x, int y, int[][] visited) {                //Testaa onko annetussa koordinaatissa "jumissa olevaa ruutua". Jos ruutua ei ole palauttaa true
        if (!doesItExist(x, y, visited.length)) return true;            //jumissa oleva = kaikki sen ympärillä on täytetty eikä siihen voi päästä enää käsiksi
        else if (doesItExist(x, y, visited.length) && canItMove(x, y, visited)) return true;
        return false;
    }
    
    public boolean doesItExist(int x, int y, int n) {               //Kuuluuko koordinaattien mukainen ruutu taulukkoon
        if (x < 0 || y < 0 || x >= n || y >= n) return false;
        return true;
    }
    
    public boolean canItMove(int x, int y, int[][] visited) {                                   //Testaa onko ruudun ympärillä vapaita ruutuja
        if (visited[x][y] != 0) return true;
        if ((doesItExist(x - 1, y, visited.length) && visited[x - 1][y] == 0) ||
                (doesItExist(x + 1, y, visited.length) && visited[x + 1][y] == 0) ||
                (doesItExist(x, y + 1, visited.length) && visited[x][y + 1] == 0) ||
                (doesItExist(x, y - 1, visited.length) && visited[x][y - 1] == 0)) return true;
        return false;
    }
}
