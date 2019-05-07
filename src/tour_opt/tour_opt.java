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
        /*System.out.println("Initial tour length is: " + length);
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

        System.out.println("Resulting tour node count: " + result.size());*/
        //result = TwoOpt.swapSequence(cities);
        //result = TwoOpt.insertRange((cities));
        result = TwoOpt.insertRange(cities);
        //result = TwoOpt.invertRange(cities);
        length = Length.routeLength(result);

    }

    public static void opt_tour2() {

    int [] tour = new int[100];

    for(int i=0;i<100;i++)
    {
        tour[i]=i;
    }

    tour = TwoOpt.randomInsert_array(tour);

    for(int i=0;i<100;i++)
    {
        System.out.println(tour[i]);
    }


    }

    public static int []match(int [] lin_tour)
    {
        int [] tour = new int[lin_tour.length];

        ArrayList<Point2D> cities = new ArrayList<>(Load.loadTSPLib("rat195.tsp")); //alter file name here.
        ArrayList<Point2D> temp = new ArrayList<>();
        ArrayList<Point2D> nearestN = new ArrayList<>();
        ArrayList<Point2D> shuffleT = new ArrayList<>();
        ArrayList<Point2D> result = new ArrayList<>();

        double length = 0;
        for(int i = 0;i<lin_tour.length;i++)
        {
            //System.out.println(lin_tour[i]);

            temp.add(cities.get(lin_tour[i]));
        }
        //length = Length.routeLength(temp);
        //System.out.println("length before :"+length);
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
            //shuffleT=TwoOpt.shuffleAll(temp);
            //shuffleT = TwoOpt.shuffleRange(temp);
            //shuffleT = TwoOpt.SwapNeighbor(temp);
            //shuffleT = TwoOpt.shuffleRange(shuffleT);
            //shuffleT=TwoOpt.randomSwap(shuffleT);
            //shuffleT = TwoOpt.alternate(shuffleT);
            //shuffleT = TwoOpt.alternate(shuffleT);
            //shuffleT = TwoOpt.SwapNeighbor(shuffleT);
            result=TwoOpt.alternate(temp);
            //result=TwoOpt.alternate(temp);
            //length = Length.routeLength(result);


        }
        result.add(temp.get(0));
        length = Length.routeLength(result);
        //System.out.println("length after :"+length);

        for(int i = 0;i<lin_tour.length;i++) {
            tour[i] = cities.indexOf(result.get(i));
        }

        return tour;
    }

    public static int []apply_llh(int [] lin_tour,int llh_idx,String file)
    {

        int [] tour = new int[lin_tour.length];
        int index = file.indexOf("_");
        String filename=file.substring(0,index)+".tsp";

        ArrayList<Point2D> cities = new ArrayList<>(Load.loadTSPLib(filename)); //alter file name here.
        ArrayList<Point2D> temp = new ArrayList<>();
        ArrayList<Point2D> result = new ArrayList<>();

        for(int i = 0;i<lin_tour.length;i++)
        {
            //System.out.println(lin_tour[i]);
            temp.add(cities.get(lin_tour[i]));
        }

        temp.remove((lin_tour.length-1));

        switch(llh_idx) {
            case 1:
                result = TwoOpt.invertRange(temp);
                break;
            case 2:
                result = TwoOpt.insertRange(temp);
                break;
            case 3:
                result = TwoOpt.randomInsert(temp);
                break;
            case 4:
                result = TwoOpt.randomSwap(temp);
                break;
            case 5:
                result = TwoOpt.SwapNeighbor(temp);
                break;
            case 6:
                result = TwoOpt.swapSequence(temp);
                break;
            default:
                System.out.println("Error wrong idx used :" + llh_idx);
                break;
        }

        result.add(temp.get(0));

        for(int i = 0;i<lin_tour.length;i++) {
            tour[i] = cities.indexOf(result.get(i));
        }

        return tour;
    }

    public static int []apply_llh2(int [] lin_tour,int llh_idx)
    {

        int [] tour = new int[lin_tour.length];

        switch(llh_idx) {
            case 1:
                tour = TwoOpt.invertRange_array(lin_tour);
                break;
            case 2:
                tour = TwoOpt.insertRange_array(lin_tour);
                break;
            case 3:
                tour = TwoOpt.randomInsert_array(lin_tour);
                break;
            case 4:
                tour = TwoOpt.randomSwap_array(lin_tour);
                break;
            case 5:
                tour = TwoOpt.swapNeighbor_array(lin_tour);
                break;
            case 6:
                tour = TwoOpt.swapSequence_array(lin_tour);
                break;
            default:
                System.out.println("Error wrong idx used :" + llh_idx);
                break;
        }

        return tour;
    }

}
