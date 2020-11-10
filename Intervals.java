import java.util.*;

public class Intervals {
    private static final Map<String, List<Integer>> intervals = new HashMap<>();
    private static final String[] notes = new String[]{"C", "D", "E", "F", "G", "A", "B"};
    private static final String[] notesWithInterval = new String[]{"C", "-", "-", "D", "-", "-", "E", "-", "F", "-", "-", "G", "-", "-", "A", "-", "-", "B", "-"};
    private static String startNote;
    private static String finishNote;
    private static int startNoteIndex;
    private static int startNoteIndex2;
    private static int countFromTo;
    private static int startCount;
    private static int finishCount;
    private static int degree;
    private static int semitone;
    private static int index;
    private static String interval;

    static {
        intervals.put("m2", new ArrayList<>());
        intervals.get("m2").add(1);
        intervals.get("m2").add(2);
        intervals.put("M2", new ArrayList<>());
        intervals.get("M2").add(2);
        intervals.get("M2").add(2);
        intervals.put("m3", new ArrayList<>());
        intervals.get("m3").add(3);
        intervals.get("m3").add(3);
        intervals.put("M3", new ArrayList<>());
        intervals.get("M3").add(4);
        intervals.get("M3").add(3);
        intervals.put("P4", new ArrayList<>());
        intervals.get("P4").add(5);
        intervals.get("P4").add(4);
        intervals.put("P5", new ArrayList<>());
        intervals.get("P5").add(7);
        intervals.get("P5").add(5);
        intervals.put("m6", new ArrayList<>());
        intervals.get("m6").add(8);
        intervals.get("m6").add(6);
        intervals.put("M6", new ArrayList<>());
        intervals.get("M6").add(9);
        intervals.get("M6").add(6);
        intervals.put("m7", new ArrayList<>());
        intervals.get("m7").add(10);
        intervals.get("m7").add(7);
        intervals.put("M7", new ArrayList<>());
        intervals.get("M7").add(11);
        intervals.get("M7").add(7);
        intervals.put("P8", new ArrayList<>());
        intervals.get("P8").add(12);
        intervals.get("P8").add(8);
    }


    public static String intervalConstruction(String[] args) throws Exception {

        if (args.length < 2 || args.length > 3) {
            throw new Exception("Illegal number of elements in input array");
        }

        int startStep = intervals.get(args[0]).get(1);
        int semitoneStep = intervals.get(args[0]).get(0);


        if (args[0].equals("P8")) {
            finishNote = args[1];
        } else {

            String str = convertToString(args[1].charAt(0));

            startNoteIndex = getStartNoteIndex(notes, str);

            startNoteIndex2 = getStartNoteIndex(notesWithInterval, str);

            String length = args[0];
            startCount = getStartCountForward(length)*-1;

            String length2 = args[1];
            startCount = getStartCountForward(length2);

            ArrayList<String> arr;
            ArrayList<String> arr2;

            if (args.length == 2 || args[2].equals("asc")) {

                arr = getSortUpArrayList(notes, startNoteIndex);
                arr2 = getSortUpArrayList(notesWithInterval, startNoteIndex2);

                startNote = arr.get(startStep - 1);

                countFromTo = getCountFromConstruction(arr2);

                finishCount = (semitoneStep - countFromTo) + startCount;

            } else {

                if (args[2].equals("dsc")) {

                    arr = getSortDownArrayList(notes, startNoteIndex);
                    arr2 = getSortDownArrayList(notesWithInterval, startNoteIndex2);

                    startNote = arr.get(startStep - 1);

                    countFromTo = getCountFromConstruction(arr2);

                    finishCount = (countFromTo - semitoneStep) + startCount;
                }
            }

            switch (finishCount) {
                case -2 -> finishNote = startNote + "bb";
                case -1 -> finishNote = startNote + "b";
                case 0 -> finishNote = startNote + "";
                case 1 -> finishNote = startNote + "#";
                case 2 -> finishNote = startNote + "##";
            }
        }

        return finishNote;

    }


    public static String intervalIdentification(String[] args) throws Exception {

        if (args.length < 2 || args.length > 3) {
            throw new Exception("Cannot identify the interval");
        }

        if (args[0].equals(args[1])) {
            interval = "P8";

        } else {

            String str = convertToString(args[0].charAt(0));
            String str2 = convertToString(args[1].charAt(0));

            startNoteIndex = getStartNoteIndex(notes, str);
            startNoteIndex2 = getStartNoteIndex(notesWithInterval, str);

            ArrayList<String> arr;
            ArrayList<String> arr2;

            if (args.length == 2 || args[2].equals("asc")) {

                String length = args[0];
                startCount = (getStartCountForward(length))*-1;

                String length2 = args[1];
                finishCount = getFinishCountForward(length2);

                arr = getSortUpArrayList(notes, startNoteIndex);
                arr2 = getSortUpArrayList(notesWithInterval, startNoteIndex2);

                degree = getDegree(arr, str2);

                countFromTo = getCountFromToInterval(arr2, str2);

            } else {
                if (args[2].equals("dsc")) {

                    String length = args[0];
                    startCount = getStartCountForward(length);

                    String length2 = args[1];
                    finishCount = getFinishCountForward(length2) * -1;

                    arr = getSortDownArrayList(notes, startNoteIndex);
                    arr2 = getSortDownArrayList(notesWithInterval, startNoteIndex2);

                    degree = getDegree(arr, str2);

                    countFromTo = getCountFromToInterval(arr2, str2);
                }
            }
            semitone = startCount + countFromTo + finishCount;
        }

        interval = getInterval(degree, semitone);

        return interval;
    }


    public static String convertToString(char charAt) {
        return String.valueOf(charAt);
    }


    public static int getCountFromConstruction(ArrayList<String> arr) {
        int count = 0;
        for (String s : arr) {

            if (s.contains("-")) {
                count++;
            }
            if (s.equals(startNote)) {
                break;
            }
            countFromTo = count;
        }
        return countFromTo;
    }


    public static int getCountFromToInterval(ArrayList<String> arr, String str) {
        int count = 0;
        for (String s : arr) {

            if (s.contains("-")) {
                count++;
            }
            if (s.equals(str)) {
                break;
            }
            countFromTo = count;
        }

        return countFromTo;
    }


    public static int getStartNoteIndex(String[] arr, String str) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(str)) {
                index = i;
            }
        }
        return index;
    }


    public static ArrayList<String> getSortUpArrayList(String[] arr1, int index) {
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < arr1.length; i++) {
            String element = arr1[(index + i) % arr1.length];
            arr.add(element);
        }
        return arr;

    }


    public static ArrayList<String> getSortDownArrayList(String[] arr1, int index) {
        ArrayList<String> arr = new ArrayList<>();
        for (int i = arr1.length - 1; i >= 0; i--) {
            String element = arr1[(index + 1 + i) % arr1.length];
            arr.add(element);
        }
        return arr;
    }


    public static int getDegree(ArrayList<String> arrayList, String str) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).contains(str)) {
                degree = i + 1;
            }
        }
        return degree;
    }


    public static String getInterval(int degree, int semitone) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(semitone);
        arrayList.add(degree);

        Set<Map.Entry<String, List<Integer>>> entrySet = intervals.entrySet();

        for (Map.Entry<String, List<Integer>> pair : entrySet) {
            if (arrayList.equals(pair.getValue())) {
                interval = pair.getKey();
            }
        }
        return interval;
    }

    public static int getStartCountForward(String str) {
        if (str.length() == 1) {
            startCount = 0;
        }
        if (str.length() == 2) {
            if (str.charAt(1) == 'b')
                startCount = -1;
            if (str.charAt(1) == '#')
                startCount = 1;
        }
        return startCount;
    }

    public static int getFinishCountForward(String str) {
        if (str.length() == 1) {
            finishCount = 0;
        }
        if (str.length() == 2) {
            if (str.charAt(1) == 'b')
                finishCount = -1;
            if (str.charAt(1) == '#')
                finishCount = 1;
        }
        if (str.length() == 3) {
            if (str.charAt(2) == 'b')
                finishCount = -2;
            if (str.charAt(2) == '#')
                finishCount = 2;
        }
        return finishCount;
    }
}