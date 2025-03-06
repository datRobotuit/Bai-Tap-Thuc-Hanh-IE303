class LAB01 {
    public static void main(String[] args) {
        pi = calculatePi();
        System.out.println("Pi = " + pi);
    }
    public static double calculatePi(){
        int points = 1000000;
        int inside = 0;
        Random rand = new Random();
        for (int i = 0; i<points; i++){
            double x = (2*rand.nextDouble()-1);
            double y = (2*rand.nextDouble()-1);
            if (x*x + y*y <= 1){
                inside++;
            }
        }
        pi = 4.0*inside/points;
        return pi;
    }
}