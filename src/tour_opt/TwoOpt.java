package tour_opt;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Arrays;

public class TwoOpt {
    public static ArrayList<Point2D> alternate(ArrayList<Point2D> cities) {
        ArrayList<Point2D> newTour;
        double bestDist = Length.routeLength(cities);
        double newDist;
        int swaps = 1;
        int improve = 0;
        int iterations = 0;
        long comparisons = 0;

        while (swaps != 0) { //loop until no improvements are made.
            swaps = 0;

            //initialise inner/outer loops avoiding adjacent calculations and making use of problem symmetry to half total comparisons.
            for (int i = 1; i < cities.size() - 2; i++) {
                for (int j = i + 1; j < cities.size() - 1; j++) {
                    comparisons++;
                    //check distance of line A,B + line C,D against A,C + B,D if there is improvement, call swap method.
                    if ((cities.get(i).distance(cities.get(i - 1)) + cities.get(j + 1).distance(cities.get(j))) >=
                            (cities.get(i).distance(cities.get(j + 1)) + cities.get(i - 1).distance(cities.get(j)))) {

                        newTour = swap(cities, i, j); //pass arraylist and 2 points to be swapped.

                        newDist = Length.routeLength(newTour);

                        if (newDist < bestDist) { //if the swap results in an improved distance, increment counters and update distance/tour
                            cities = newTour;
                            bestDist = newDist;
                            swaps++;
                            improve++;
                        }
                    }
                }
            }
            iterations++;
        }
        //System.out.println("Total comparisons made: " + comparisons);
        //System.out.println("Total improvements made: " + improve);
        //System.out.println("Total iterations made: " + iterations);
        return cities;
    }

    public static ArrayList<Point2D> SwapNeighbor(ArrayList<Point2D> cities) {
        ArrayList<Point2D> newTour = new ArrayList<>();
        boolean debug_print = false;
        Random gen= new Random();
        int size = cities.size();
        int i = 1+gen.nextInt(size-2);

        if(debug_print) {
            System.out.println("i = " + i);
        }

        for (int c = 0; c < size; c++) {
            newTour.add(cities.get(c));
        }

        Point2D tempCity = newTour.get(i);
        newTour.set(i,newTour.get(i+1));
        newTour.set(i+1,tempCity);

        return newTour;

    }

    public static int [] swapNeighbor_array(int [] tour)
    {
        int [] new_tour = tour;

        boolean debug_print = false;
        Random gen= new Random();
        int size = tour.length;
        int i = 1+gen.nextInt(size-3);
        if(debug_print)
            System.out.println("i = "+i);

        int temp = new_tour[i];
        new_tour[i]=new_tour[i+1];
        new_tour[i+1]=temp;

        return new_tour;
    }

    public static int [] randomSwap_array(int [] tour)
    {
        int [] new_tour = tour;

        boolean debug_print = false;
        Random gen= new Random();
        int size = tour.length;
        int i = 1+gen.nextInt(size-3);
        int j = 0;
        do{
            j = 1+gen.nextInt(size-3);
        }while(i==j);

        if(debug_print)
            System.out.println("i = "+i+" j = "+j);

        int temp = new_tour[i];
        new_tour[i]=new_tour[j];
        new_tour[j]=temp;

        return new_tour;
    }

    public static int [] randomInsert_array(int [] tour)
    {
        int [] new_tour = tour;

        boolean debug_print = false;
        Random gen= new Random();
        int size = tour.length;
        int range = size/10;
        int i = 1+gen.nextInt(size-3);
        int j = 0;
        do{
            j = 1+gen.nextInt(size-3);
        }while(i==j);

        int temp = 0;
        if(i>j)
        {
            temp = i;
            i = j;
            j = temp;
        }

        temp = new_tour[j];
        for(int k = j;k>i;k--)
        {
            new_tour[k] = new_tour[k-1];
        }
        new_tour[i]=temp;

        return new_tour;
    }

    public static int [] invertRange_array(int [] tour) {
        int [] new_tour = tour;

        boolean debug_print = false;
        Random gen= new Random();
        int size = tour.length;
        int range = size/10;
        int j = 0;
        int i = 1+gen.nextInt(size-range-2);
        do{
            j = i+gen.nextInt(range);
        }while(j >= size);

        int [] temp_array = new int [j-i];
        int counter = 0;
        for(int k = i;k<j;k++)
        {
            temp_array[counter]=new_tour[k];
            counter++;
        }

        for(int k = i;k<j;k++)
        {
            new_tour[k]=temp_array[counter-1];
            counter--;
        }


        return new_tour;
    }

    public static int [] insertRange_array(int [] tour) {
        int [] new_tour = tour;

        boolean debug_print = false;
        Random gen= new Random();
        int size = tour.length;
        int range =1+gen.nextInt(size/10);
        int j = 0;
        int i = 1+gen.nextInt(size-range-2);

        do{
            j = 1+gen.nextInt(size-range-2);
            if(i>j)
            {
                int temp = i;
                i=j;
                j=temp;
            }
        }while((i+range)>=j);

        if(debug_print)
            System.out.println("i = "+i+" j = "+j);


        int [] temp_array = new int [range];
        int counter = 0;
        for(int k = i;k<(i+range);k++)
        {
            temp_array[counter]=new_tour[k];
            counter++;
        }

        for(int k = i;k<j;k++)
        {
            new_tour[k]=new_tour[k+range];
        }


        counter = 0;
        for(int k = j;k<(j+range);k++)
        {
            new_tour[k]=temp_array[counter];
            counter++;
        }


        return new_tour;
    }

    public static int [] swapSequence_array(int [] tour) {
        int [] new_tour = tour;

        boolean debug_print = false;
        Random gen= new Random();
        int size = tour.length;
        int range =1+gen.nextInt(size/10);
        int j = 0;
        int i = 1+gen.nextInt(size-range-2);

        do{
            j = 1+gen.nextInt(size-range-2);
            if(i>j)
            {
                int temp = i;
                i=j;
                j=temp;
            }
        }while((i+range)>=j);


        int [] temp_array = new int [range];
        int [] temp_array2 = new int [range];

        int counter = 0;
        for(int k = i;k<(i+range);k++)
        {
            temp_array[counter]=new_tour[k];
            counter++;
        }

        counter = 0;
        for(int k =j;k<(j+range);k++)
        {
            temp_array2[counter]=new_tour[k];
            counter++;
        }

        counter = 0;
        for(int k = i;k<(i+range);k++)
        {
            new_tour[k]=temp_array2[counter];
            counter++;
        }

        counter = 0;
        for(int k = j;k<(j+range);k++)
        {
            new_tour[k]=temp_array[counter];
            counter++;
        }


        return new_tour;
    }

    public static ArrayList<Point2D> randomSwap(ArrayList<Point2D> cities) {
        boolean debug_print = false;
        ArrayList<Point2D> newTour = new ArrayList<>();
        Random gen= new Random();
        int size = cities.size();
        int i = 1+gen.nextInt(size-1);
        int j=0;
        do{
            j = 1+gen.nextInt(size-1);
        }while(i==j);

        if(debug_print) {
            System.out.println("i = " + i + " j = " + j);
        }

        for (int c = 0; c < size; c++) {
            newTour.add(cities.get(c));
        }

        Point2D tempCity = newTour.get(i);
        newTour.set(i,newTour.get(j));
        newTour.set(j,tempCity);

        return newTour;
    }

    public static ArrayList<Point2D> randomInsert(ArrayList<Point2D> cities) {
        boolean debug_print = false;
        ArrayList<Point2D> newTour = new ArrayList<>();
        Random gen= new Random();
        boolean swapped = false;
        int size = cities.size();
        int i = 1+gen.nextInt(size-1);
        int j=0;
        do{
            j = 1+gen.nextInt(size-1);
        }while(i==j);

        if(debug_print) {
            System.out.println("i = " + i + " j = " + j);
        }

        for (int c = 0; c <size; c++) {
            newTour.add(cities.get(c));
        }

        Point2D tempCity = newTour.get(i);

        if(i>j)
        {
            for(int c=i;c>j;c--)
            {
                newTour.set(c,newTour.get(c-1));
            }
        }
        else
        {
            for(int c=i;c<j;c++)
            {
                newTour.set(c,newTour.get(c+1));
            }
        }
        newTour.set(j,tempCity);

        return newTour;
    }

    public static ArrayList<Point2D> invertRange(ArrayList<Point2D> cities) {
        boolean debug_print=false;
        ArrayList<Point2D> newTour = new ArrayList<>();
        Random gen= new Random();
        int size = cities.size();
        int i = 0;
        int j =0;
        int range = size/100;
        do{
            i = 1+gen.nextInt(size-range-1);
            j = i + gen.nextInt(range);
        }while((j >= size) && (j==i));


        if(i>j)
        {
            int temp =j;
            j=i;
            i=temp;
        }
        if(debug_print) {
            System.out.println("i = " + i + " j = " + j);
        }

        for (int c = 0; c <size; c++) {
            newTour.add(cities.get(c));
        }

        Collections.reverse(newTour.subList(i,j));

        return newTour;
    }

    public static ArrayList<Point2D> insertRange(ArrayList<Point2D> cities) {
        boolean debug_print = false;
        ArrayList<Point2D> newTour = new ArrayList<>();
        Random gen= new Random();
        int size = cities.size();
        int range = size/100;
        int k = gen.nextInt((range));
        int i = 1+gen.nextInt(size-k-1);
        int j= 1+gen.nextInt(size-k-1);;
        do{
            if(i>j)
            {
                int temp = j;
                j = i;
                i = temp;
            }
            if(i+k>j)
            {
                i = gen.nextInt(size-k);
            }
        }while(i+k>j);

        if(debug_print) {
            System.out.println("i = " + i + " j = " + j);
        }

        for (int c = 0; c <i; c++) {
                newTour.add(cities.get(c));
        }

        for(int c = i+k;c<j;c++)
        {
            newTour.add(cities.get(c));
        }

        for(int c = i;c<i+k;c++)
        {
            newTour.add(cities.get(c));
        }

        for(int c = j;c<size;c++)
        {
            newTour.add(cities.get(c));
        }

        return newTour;
    }

    public static ArrayList<Point2D> swapSequence(ArrayList<Point2D> cities) {
        boolean debug_print = false;
        ArrayList<Point2D> newTour = new ArrayList<>();
        Random gen= new Random();
        int size = cities.size();
        int i = 0;
        int j=0;
        int range = size/100;
        int k = gen.nextInt(range);;
        i = 1+gen.nextInt(size-k-1);
        j = 1+gen.nextInt(size-k-1);
        do{
            if(i>j)
            {
                int temp = j;
                j = i;
                i = temp;
            }
            if(i+k>j)
            {
                i = 1+ gen.nextInt(size-k-1);
            }

        }while(i+k>j);

        if(debug_print) {
            System.out.println("i = " + i + " j = " + j);
        }

        for (int c = 0; c <i; c++) {
                newTour.add(cities.get(c));
        }

        for(int c = j; c<j+k;c++){
            newTour.add(cities.get(c));
        }

        for(int c = (i+k);c<j;c++)
        {
            newTour.add(cities.get(c));
        }

        for(int c = i;c<i+k;c++)
        {
            newTour.add(cities.get(c));
        }

        for(int c=j+k;c<size;c++)
        {
            newTour.add(cities.get(c));
        }

        return newTour;
    }

    public static ArrayList<Point2D> shuffleAll(ArrayList<Point2D> cities) {
        ArrayList<Point2D> newTour = new ArrayList<>();
        int size = cities.size();

        for (int c = 0; c <size; c++) {
            newTour.add(cities.get(c));
        }

        Collections.shuffle(newTour.subList(1,cities.size()));

        return newTour;
    }

    public static ArrayList<Point2D> shuffleRange(ArrayList<Point2D> cities) {
        boolean debug_print = false;
        ArrayList<Point2D> newTour = new ArrayList<>();
        int size = cities.size();
        Random gen= new Random();
        int i = gen.nextInt(size-1)+1;
        int j=0;
        do{
            j = gen.nextInt(size-1)+1;
        }while(i>=j);


        for (int c = 0; c <size; c++) {
            newTour.add(cities.get(c));
        }

        if(debug_print) {
            System.out.println("i = " + i + " j = " + j);
        }

        Collections.shuffle(newTour.subList(i,j));

        return newTour;
    }


    private static ArrayList<Point2D> swap(ArrayList<Point2D> cities, int i, int j) {
        //conducts a 2 opt swap by inverting the order of the points between i and j
        ArrayList<Point2D> newTour = new ArrayList<>();

        //take array up to first point i and add to newTour
        int size = cities.size();
        for (int c = 0; c <= i - 1; c++) {
            newTour.add(cities.get(c));
        }

        //invert order between 2 passed points i and j and add to newTour
        int dec = 0;
        for (int c = i; c <= j; c++) {
            newTour.add(cities.get(j - dec));
            dec++;
        }

        //append array from point j to end to newTour
        for (int c = j + 1; c < size; c++) {
            newTour.add(cities.get(c));
        }

        return newTour;
    }
}
