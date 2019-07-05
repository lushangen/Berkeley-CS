package byow.Core;

//@Source https://www.geeksforgeeks.org/quick-sort/
// Java program for implementation of QuickSort
public class QuickSort {

    public static int partition(Room[] arr, int low, int high) {
        Room pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            double dist1 = Math.pow(arr[j].point().getX(), 2) + Math.pow(arr[j].point().getY(), 2);
            double dist2 = Math.pow(pivot.point().getX(), 2) + Math.pow(pivot.point().getY(), 2);

            if (dist1 <= dist2) {
                i++;
                Room temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Room temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }


    public static void sort(Room[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }

}
