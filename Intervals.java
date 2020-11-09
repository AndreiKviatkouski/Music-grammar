
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
        /*
          If there are more or fewer elements in the input array,
          an exception should be thrown: "Illegal number of elements in input array"
         */
        if (args.length < 2 || args.length > 3) {
            throw new Exception("Illegal number of elements in input array");
        }


        int startStep = intervals.get(args[0]).get(1);
        int semitoneStep = intervals.get(args[0]).get(0);

        /*
          this condition is met if the requested value is equal to an octave
         */
        if (args[0].equals("P8")) {
            finishNote = args[1];
        } else {


            /*
             defining the initial note without " b " and "#"
              in the arrays of notes and notesWithInterval
             */
            char ch = args[1].charAt(0);
            String str = String.valueOf(ch);

            for (int i = 0; i < notes.length; i++) {
                if (notes[i].equals(str)) {
                    startNoteIndex = i;
                }
            }

            for (int i = 0; i < notesWithInterval.length; i++) {
                if (notesWithInterval[i].equals(str)) {
                    startNoteIndex2 = i;
                }
            }

            /*
              defined by # or b music note
             */
            if (args[1].length() == 1) {
                startCount = 0;
            }
            if (args[1].length() == 2) {
                if (args[1].charAt(1) == 'b')
                    startCount = -1;
                if (args[1].charAt(1) == '#')
                    startCount = 1;
            }


            ArrayList<String> arr = new ArrayList<>();
            ArrayList<String> arr2 = new ArrayList<>();
            /*
              finding an increasing interval
             */
            if (args.length == 2 || args[2].equals("asc")) {

                for (int i = 0; i < notes.length; i++) {
                    String element = notes[(startNoteIndex + i) % notes.length];
                    arr.add(element);
                }

                for (int i = 0; i < notesWithInterval.length; i++) {
                    String element = notesWithInterval[(startNoteIndex2 + i) % notesWithInterval.length];
                    arr2.add(element);
                }

                startNote = arr.get(startStep - 1);

                int count = 0;
                for (String s : arr2) {

                    if (s.contains("-")) {
                        count++;
                    }
                    if (s.equals(startNote)) {
                        break;
                    }
                    countFromTo = count;
                }


                finishCount = (semitoneStep - countFromTo) + startCount;


            } else {
                /*
                  finding the decreasing interval
                 */
                if (args[2].equals("dsc")) {
                    for (int i = notes.length - 1; i >= 0; i--) {
                        String element = notes[(startNoteIndex + 1 + i) % notes.length];//сдвигаем элементы влево
                        arr.add(element);
                    }
                    for (int i = notesWithInterval.length - 1; i >= 0; i--) {
                        String element = notesWithInterval[(startNoteIndex2 + 1 + i) % notesWithInterval.length];//сдвигаем элементы влево
                        arr2.add(element);
                    }

                    startNote = arr.get(startStep - 1);


                    int count = 0;
                    for (String s : arr2) {

                        if (s.contains("-")) {
                            count++;
                        }
                        if (s.equals(startNote)) {
                            break;
                        }
                        countFromTo = count;
                    }

                    finishCount = (countFromTo - semitoneStep) + startCount;


                }
            }

            /*
              get the necessary note
             */
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
        /*
          If the interval does not fit a description, an exception thrown: "Cannot identify the interval".
         */
        if (args.length < 2 || args.length > 3) {
            throw new Exception("Cannot identify the interval");
        }

        if (args[0].equals(args[1])) {
            interval = "P8";

        } else {

            char ch = args[0].charAt(0);
            char ch2 = args[1].charAt(0);
            String str = String.valueOf(ch);
            String str2 = String.valueOf(ch2);

            for (int i = 0; i < notes.length; i++) {
                if (notes[i].equals(str)) {
                    startNoteIndex = i;
                }
            }
            for (int i = 0; i < notesWithInterval.length; i++) {
                if (notesWithInterval[i].equals(str)) {
                    startNoteIndex2 = i;
                }
            }

            ArrayList<String> arr = new ArrayList<>();
            ArrayList<String> arr2 = new ArrayList<>();

/*
get the  semitone, startCount, countFromTo, finishCount if asc
 */
            if (args.length == 2 || args[2].equals("asc")) {


                if (args[0].length() == 1) {
                    startCount = 0;
                }
                if (args[0].length() == 2) {
                    if (args[0].charAt(1) == 'b')
                        startCount = 1;
                    if (args[0].charAt(1) == '#')
                        startCount = -1;
                }

                if (args[1].length() == 1) {
                    finishCount = 0;
                }
                if (args[1].length() == 2) {
                    if (args[1].charAt(1) == 'b')
                        finishCount = -1;
                    if (args[1].charAt(1) == '#')
                        finishCount = 1;
                }
                if (args[1].length() == 3) {
                    if (args[1].charAt(2) == 'b')
                        finishCount = -2;
                    if (args[1].charAt(2) == '#')
                        finishCount = 2;
                }


                for (int i = 0; i < notes.length; i++) {
                    String element = notes[(startNoteIndex + i) % notes.length];
                    arr.add(element);
                }

                for (int i = 0; i < arr.size(); i++) {
                    if (arr.get(i).contains(str2)) {
                        degree = i + 1;
                    }
                }

                for (int i = 0; i < notesWithInterval.length; i++) {
                    String element = notesWithInterval[(startNoteIndex2 + i) % notesWithInterval.length];
                    arr2.add(element);
                }


                int count = 0;
                for (String s : arr2) {

                    if (s.contains("-")) {
                        count++;
                    }
                    if (s.equals(str2)) {
                        break;
                    }
                    countFromTo = count;
                }

                semitone = startCount + countFromTo + finishCount;


            } else {
/*
get the  semitone, startCount, countFromTo, finishCount if dsc
 */
                if (args[2].equals("dsc")) {

                    if (args[0].length() == 1) {
                        startCount = 0;
                    }
                    if (args[0].length() == 2) {
                        if (args[0].charAt(1) == 'b')
                            startCount = -1;
                        if (args[0].charAt(1) == '#')
                            startCount = 1;
                    }

                    if (args[1].length() == 1) {
                        finishCount = 0;
                    }
                    if (args[1].length() == 2) {
                        if (args[1].charAt(1) == 'b')
                            finishCount = 1;
                        if (args[1].charAt(1) == '#')
                            finishCount = -1;
                    }
                    if (args[1].length() == 3) {
                        if (args[1].charAt(2) == 'b')
                            finishCount = 2;
                        if (args[1].charAt(2) == '#')
                            finishCount = -2;
                    }


                    for (int i = notes.length - 1; i >= 0; i--) {
                        String element = notes[(startNoteIndex + 1 + i) % notes.length];//сдвигаем элементы влево
                        arr.add(element);
                    }
                    for (int i = 0; i < arr.size(); i++) {
                        if (arr.get(i).contains(str2)) {
                            degree = i + 1;


                        }
                    }
                    for (int i = notesWithInterval.length - 1; i >= 0; i--) {
                        String element = notesWithInterval[(startNoteIndex2 + 1 + i) % notesWithInterval.length];
                        arr2.add(element);
                    }


                    int count = 0;
                    for (String s : arr2) {

                        if (s.contains("-")) {
                            count++;
                        }
                        if (s.equals(str2)) {
                            break;
                        }
                        countFromTo = count;
                    }


                }
                semitone = startCount + countFromTo + finishCount;


            }
        }
/*
get the necessary note
 */
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
}
