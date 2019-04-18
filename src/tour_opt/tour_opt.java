package tour_opt;

        import java.awt.geom.Point2D;
        import java.util.ArrayList;

public class tour_opt{

    public static void opt_tour() {

        double startTime = System.currentTimeMillis();
        ArrayList<Point2D> cities = new ArrayList<>(Load.loadTSPLib("rat195.tsp")); //alter file name here.
        ArrayList<Point2D> nearestN;
        ArrayList<Point2D> result;

        double length = Length.routeLength(cities);
        System.out.println("Initial tour length is: " + length);
        double time = System.currentTimeMillis() - startTime;
        System.out.println("Time taken to initialize is: " + time);
        System.out.println("Generating Nearest Neighbour Solution...");
        nearestN = Neighbour.nearest(cities);
        length = Length.routeLength(nearestN);
        System.out.println("Nearest neighbour solution complete, distance: " + length);
        System.out.println("Validating solution...");
        Validator.validate(nearestN);
        time = System.currentTimeMillis() - startTime;
        System.out.println("Time taken for init and Nearest Neighbour: " + time);

        startTime = System.currentTimeMillis();
        System.out.println("Attempting 2-opt optimisation...");
        result = TwoOpt.alternate(nearestN);
        length = Length.routeLength(result);
        System.out.println("2-opt solution complete, distance: " + length);
        System.out.println("Validating solution...");
        Validator.validate(result);
        time = System.currentTimeMillis() - startTime;
        System.out.println("Time taken for 2 opt optimisation: " + time);

        System.out.println("Resulting tour node count: " + result.size());

    }

    public static int []match(int [] lin_tour)
    {
        int [] tour = new int[lin_tour.length];

        ArrayList<Point2D> cities = new ArrayList<>(Load.loadTSPLib("rat195.tsp")); //alter file name here.
        ArrayList<Point2D> temp = new ArrayList<>();
        ArrayList<Point2D> nearestN = new ArrayList<>();
        ArrayList<Point2D> result = new ArrayList<>();

        double length = 0;
        for(int i = 0;i<lin_tour.length;i++)
        {
            //System.out.println(lin_tour[i]);

            temp.add(cities.get(lin_tour[i]));
        }
        length = Length.routeLength(temp);
        temp.remove((lin_tour.length-1));
        if(false) {
            length = Length.routeLength(temp);
            System.out.println("original " + length);
            nearestN = Neighbour.nearest(temp);
            length = Length.routeLength(nearestN);
            System.out.println("nearest neighbour " + length);
            //result = TwoOpt.alternate(nearestN);
            result = TwoOpt.shuffleAll(nearestN);
            System.out.println("shuffle " + length);
            length = Length.routeLength(result);
        }
        else
        {
            //nearestN = TwoOpt.shuffleAll(temp);
            //length = Length.routeLength(nearestN);
            //temp = Neighbour.nearest(nearestN);
            //length = Length.routeLength(temp);
            result = TwoOpt.alternate(temp);
            length = Length.routeLength(result);
        }
        result.add(temp.get(0));
        length = Length.routeLength(result);

        for(int i = 0;i<lin_tour.length;i++) {
            tour[i] = cities.indexOf(result.get(i));
        }

        return tour;
    }

}
