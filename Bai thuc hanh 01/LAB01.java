import java.util.Random;
import java.util.Scanner;

public class LAB01 {
    public static void main(String[] args) {
        //Nhap ban kinh hinh tron
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap ban kinh hinh tron: ");
        double r = scanner.nextDouble();

        double area = calculateCircleArea(r);
        System.out.println("Dien tich xap xi cua hinh tron ban kinh " + r + ": " + area);

        scanner.close();
    }
    
    public static double calculateCircleArea(double r) {

        int points = 1000000;
        Random random = new Random();
        int insideCircle = 0;
        
        for (int i = 0; i < points; i++) {
            double x = (2 * random.nextDouble() - 1) * r;
            double y = (2 * random.nextDouble() - 1) * r;
            
            if (x*x + y*y <= r*r) {
                insideCircle++;
            }
        }
        
        double pi = 4.0 * insideCircle / points;
        
        return pi * r * r;
    }
}