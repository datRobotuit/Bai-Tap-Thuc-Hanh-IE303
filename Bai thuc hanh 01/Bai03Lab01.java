import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.*;

public class Bai03Lab01 {
    static class Station {
        double x,y;
        Station(double x, double y){
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
    static int orientation(Station p, Station q, Station r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }
    static List<Station> findWarningStations(List<Station> stations){
        if (stations.size() < 3) return stations;

        int leftmost = 0;
        for (int i=1; i<stations.size(); i++) {
            if (stations.get(i).x < stations.get(leftmost).x){
                leftmost = i;
            }
        }
        Set<Station> hull = new HashSet<>();

        int p = leftmost, q;
        do {
            hull.add(stations.get(p));
            q = (p+1) % stations.size();
            for (int i=0; i<stations.size(); i++) {
                if (orientation(stations.get(p), stations.get(i), stations.get(q)) == 2) {
                    q = i;
                }
            }
            p = q;
        } while (p != leftmost);
        return new ArrayList<>(hull);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Station> stations = new ArrayList<>();
        for (int i=0; i<n; i++) {
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            stations.add(new Station(x, y));
        }
        List<Station> warningStations = findWarningStations(stations);
        for (Station station : warningStations) {
            System.out.println(station);
        }
        scanner.close();
    }
    
}