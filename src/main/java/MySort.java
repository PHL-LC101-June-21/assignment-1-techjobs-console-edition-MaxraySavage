import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class MySort {


    public static void sort(ArrayList<String> a, String methodChoice) {
        // Implemented to be case-insensitive
        ArrayList<String> sortMethodChoices = new ArrayList<>();
        sortMethodChoices.add("bubble");
        sortMethodChoices.add("insertion");
        sortMethodChoices.add("selection");
        sortMethodChoices.add("merge");
        sortMethodChoices.add("heap");
        sortMethodChoices.add("quick");

        if(methodChoice.equalsIgnoreCase("bubble")) {
            bubbleSort(a);
        } else if(methodChoice.equalsIgnoreCase("insertion")) {
            insertionSort(a);
        } else if(methodChoice.equalsIgnoreCase("selection")) {
            selectionSort(a);
        } else if(methodChoice.equalsIgnoreCase("merge")) {
            ArrayList<String> sortedList = mergeSort(a);
            a.clear();
            a.addAll(sortedList);
        } else if(methodChoice.equalsIgnoreCase("heap")) {
            ArrayList<String> sortedList = heapSort(a);
            a.clear();
            a.addAll(sortedList);
        } else if(methodChoice.equalsIgnoreCase("quick")) {
            quicksort(a, 0, a.size()-1);
        }
    }

    public static void bubbleSort(@NotNull ArrayList<String> a){
        // Keep track of how much of the left side of the array is still unsorted
        int unsortedLength = a.size();
        while(unsortedLength > 1) {
            int lastSwap = 0;
            for (int i = 0; i < unsortedLength - 1; i++){
                if(a.get(i).compareToIgnoreCase(a.get(i+1)) > 0){
                    Collections.swap(a, i, i+1);
                    lastSwap = i + 1;
                }
            }
            unsortedLength = lastSwap;

        }
    }

    public static void insertionSort(@NotNull ArrayList<String> values){
        int i = 1;
        while(i < values.size()){
            int j = i;
            while(j > 0 && values.get(j-1).compareToIgnoreCase(values.get(j)) > 0){
                Collections.swap(values, j-1, j);
                j -= 1;
            }
            i += 1;
        }
    }

    public static void selectionSort(@NotNull ArrayList<String> values){
        for(int i = 0; i < values.size() - 1; i++){
            int smallestStringIndex = i;
            for(int j = i + 1; j < values.size(); j++){
                if(values.get(smallestStringIndex).compareToIgnoreCase(values.get(j)) > 0){
                    smallestStringIndex = j;
                }
            }
            Collections.swap(values, i, smallestStringIndex);
            System.out.println(values);
        }
    }

    public static ArrayList<String> mergeSort(@NotNull ArrayList<String> values){
        // Unlike the previous sort this one is recursive
        // so it cant be implemented in place, instead returns the sorted arraylist
        // Essentially we split the input array in half and merge sort each half
        // all this function does is split the input array in half
        // and then merge the two halves
        // the merge is where the sorting happens

        if(values.size() <= 1) {
            return values;
        }
        ArrayList<String> left = new ArrayList<>();
        ArrayList<String> right = new ArrayList<>();
        for(int i =0; i < values.size(); i++){
            if(i < (values.size() / 2)){
                left.add(values.get(i));
            } else {
                right.add(values.get(i));
            }
        }
        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    public static ArrayList<String> merge(@NotNull ArrayList<String> left, @NotNull ArrayList<String> right){
        ArrayList<String> result = new ArrayList<>();

        // This is where the sort really happens
        // we compare the first element of each array
        // and append the smaller element to our result array
        // left and right are sorted
        // so this results in a sorted result list
        // left and right are trivially sorted in the base case where they are length 1 or 0

        while(!left.isEmpty() && !right.isEmpty()){
            if(left.get(0).compareToIgnoreCase(right.get(0)) < 0){
                result.add(left.remove(0));
            } else {
                result.add(right.remove(0));
            }
        }

        // only one of these next loops will run
        // the purpose is to append any remaining elements to the end of result
        while(!left.isEmpty()) {
            result.add(left.remove(0));
        }
        while(!right.isEmpty()) {
            result.add(right.remove(0));
        }
        return result;
    }

    public static ArrayList<String> heapSort(@NotNull ArrayList<String> a){
        // a heap is a representation of a tree
        // where the parent of the node at index i is at index i/2
        //
        // in a zero indexed array
        // the left child is at 2i + 1 and the right child is at 2i + 2
        //
        // for a max heap called a, a[i] >= a[2i+1] && a[i] >= a[2i+2]

        ArrayList<String> maxHeap = new ArrayList<>(a);
        ArrayList<String> sortedList = new ArrayList<>();
        buildMaxHeap(maxHeap);
        int heapEnd = maxHeap.size() - 1;
        while (heapEnd >= 0){
            Collections.swap(maxHeap, 0, heapEnd);
            sortedList.add(0, maxHeap.remove(heapEnd));
            maxHeapify(maxHeap, 0);
            heapEnd = maxHeap.size() - 1;
        }
        return sortedList;
    }

    public static int getLeftChildIndex(int i) {
        return i * 2 + 1;
    }

    public static int getRightChildIndex(int i) {
        return i * 2 + 2;
    }

    public static void maxHeapify(@NotNull ArrayList<String> maxHeap, int i){
        // corrects one violation of max heap invariants for input array
        // for node at index i
        // where left and right children of i are both max heaps
        int left = getLeftChildIndex(i);
        int right = getRightChildIndex(i);
        int largest = i;

        if(left < maxHeap.size() && maxHeap.get(left).compareToIgnoreCase(maxHeap.get(i)) > 0) {
            largest = left;
        }
        if(right < maxHeap.size() && maxHeap.get(right).compareToIgnoreCase(maxHeap.get(largest)) > 0){
            largest = right;
        }
        if(largest != i){
            Collections.swap(maxHeap, i, largest);
            maxHeapify(maxHeap, largest);
        }
    }

    public static void buildMaxHeap(@NotNull ArrayList<String> a){
        int lastParent = (a.size() - 2) / 2;
        for(int i = lastParent; i >= 0; i--) {
            maxHeapify(a, i);
        }
    }

    public static void quicksort(@NotNull ArrayList<String> a, int start, int end){
        if(start >= end){
            return;
        }
        int pivot = partition(a, start, end);

        quicksort(a, start, pivot - 1);
        quicksort(a, pivot + 1, end);
    }

    public static int partition(@NotNull ArrayList<String> a, int start, int end){
        // i was confused about this until i figured out that
        // the index of the partition is exactly equal to the number of elements in the array
        // that are less than the pivot value
        // so pivotIndex is just counting that
        String pivotValue = a.get(end);
        int pivotIndex = start;
        for(int i = start; i < end; i++){
            if(a.get(i).compareToIgnoreCase(pivotValue) < 0){
                Collections.swap(a, i, pivotIndex);
                pivotIndex += 1;
            }
        }
        Collections.swap(a, pivotIndex, end);
        return pivotIndex;
    }
}
