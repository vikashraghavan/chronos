package sample;

import java.util.*;

public class Scheduler {

    public static ClassLab[] cl;
    public static ClassSub[] cs;
    public static ArrayList<ClassLab[]> classLabList=new ArrayList<>();
    public static ArrayList<ClassSub[]> classSubList=new ArrayList<>();
    ArrayList<String> labTeacher=new ArrayList();
    ArrayList<String> labClass=new ArrayList();
    ArrayList<String> labCount=new ArrayList();
    List<String> teacher=new ArrayList();
    List<ArrayList<String>> teacherSubject=new ArrayList();
    List<ArrayList<String>> teacherClass=new ArrayList<>();
    List<ArrayList<String>> teacherCount=new ArrayList<>();
    ArrayList<String> cc2= new ArrayList<>(Arrays.asList("os lab"));
    ArrayList<String> cc4= new ArrayList<>(Arrays.asList("ite lab","compiler lab","extra lab"));
    ArrayList<String> cc6= new ArrayList<>(Arrays.asList("mad lab","dbms lab","c lab"));
    ArrayList<String> classesUnique=new ArrayList<>();
    List<Integer> labSlotIndex = new ArrayList<>();

    Scheduler() {

        createClasses();
        extractDetails();
        createSlots();
        createTeachers();
        extractLabs();
        assignLabs();
        ectractSubject();
        assignSubjects();

        System.out.println("Done");

    }


    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    void assignSubjects() {

        for (int i=0;i<classesUnique.size();i++){

            for (int j=0;j<classSubList.size();j++){
                //<Integer> freeSlt=new ArrayList<>();
                //DerbyConn.obtainFreeSlots(classesUnique.get(i),freeSlt);
                int subCnt=classSubList.get(i)[j].getCount();
                int lev=3;
                while (subCnt!=0 && lev!=0){
                    switch (lev){
                        case 3:
                            for (int t=1;t<=5;t++){

                            }
                    }
                }
            }

        }

    }


    void ectractSubject() {

        for (int i=0;i<classesUnique.size();i++){
            cs=new ClassSub[10];
            int toCnt=DerbyConn.extractSub(classesUnique.get(i),cs);
            if (toCnt==0){
                continue;
            }else {
                classSubList.add(cs);
            }
        }
        printSubDetails();

    }


//    void assignLabs1() {
//
//        ArrayList<Integer> canClubedDiff=new ArrayList<>();
//
//        for (int i=0;i<classLabList.size();i++){
//            List<Integer> labSlotIndex = new ArrayList<>();
//            labSlotIndex.add(1);
//            labSlotIndex.add(6);
//            labSlotIndex.add(9);
//            labSlotIndex.add(14);
//            labSlotIndex.add(17);
//            labSlotIndex.add(22);
//            labSlotIndex.add(25);
//            labSlotIndex.add(30);
//            labSlotIndex.add(33);
//            labSlotIndex.add(38);
//            int swt=0;
//            ArrayList<Integer> insIndex=new ArrayList<>();
//            ArrayList<Integer> canClubedSame=new ArrayList<>();
//
//            for (int j=0;j<classLabList.get(i).length;j++){
//                if (classLabList.get(i)[j]!=null) {
//                    String lab = "";
//                    String teach = classLabList.get(i)[j].getTeacher();
//                    if (cc2.contains(classLabList.get(i)[j].getSubject().toLowerCase())) {
//                        lab = "CC2_LAB";
//                    } else {
//                        if (cc4.contains(classLabList.get(i)[j].getSubject().toLowerCase())) {
//                            lab = "CC4_LAB";
//                        } else {
//                            if (cc6.contains(classLabList.get(i)[j].getSubject().toLowerCase())) {
//                                lab = "CC6_LAB";
//                            }
//                        }
//                    }
//                    //System.out.println(classLabList.get(i)[j].getSubject());
//                    if (classLabList.get(i)[j].getCount() == 6) {
//                        ArrayList<Integer> shouldAssign=new ArrayList<>(Arrays.asList(1,1));
//                        insIndex.add(labSlotIndex.get(0));
//                        insIndex.add(labSlotIndex.get(labSlotIndex.size() - 1));
//
//                        if (DerbyConn.checkLabFree(lab, insIndex.get(0)) == 1 && DerbyConn.checkLabFree(lab, insIndex.get(1)) == 1)
//
//                        if (DerbyConn.checkLabFree(lab, insIndex.get(0)) == 1 && DerbyConn.checkTeacherFree(teach, insIndex.get(0)) == 1 ) {
//                            if (shouldAssign.get(0)==1) {
//                                DerbyConn.assignSlot(insIndex.get(0), lab, classLabList.get(i)[j].getCls(), classLabList.get(i)[j].getTeacher(), classLabList.get(i)[j].getSubject());
//                                shouldAssign.set(0,0);
//                            }
//                            if (DerbyConn.checkLabFree(lab, insIndex.get(1)) == 1 && DerbyConn.checkTeacherFree(teach, insIndex.get(1)) == 1) {
//                                DerbyConn.assignSlot(insIndex.get(1), lab, classLabList.get(i)[j].getCls(), classLabList.get(i)[j].getTeacher(), classLabList.get(i)[j].getSubject());
//                            }
//                        }
//                    }
//                }
//            }
//
//            labSlotIndex.clear();
//        }
//
//    }

    void fillLabSlot(){

        labSlotIndex.clear();
        labSlotIndex.add(1);
        labSlotIndex.add(6);
        labSlotIndex.add(33);
        labSlotIndex.add(38);
        labSlotIndex.add(9);
        labSlotIndex.add(14);
        labSlotIndex.add(25);
        labSlotIndex.add(30);
        labSlotIndex.add(17);
        labSlotIndex.add(22);

    }

    void assignLabs() {

        for (int i=0;i<classLabList.size();i++){

            fillLabSlot();
            ArrayList<ArrayList<String>> clubed=new ArrayList<>();
            ArrayList<ArrayList<String>> tclubed=new ArrayList<>();
            ArrayList<String> cb2=new ArrayList<>();
            ArrayList<String> cb1=new ArrayList<>();
            ArrayList<String> tb2=new ArrayList<>();
            ArrayList<String> tb1=new ArrayList<>();
            int b2=0;
            int b1=0;

            for (int j=0;j<classLabList.get(i).length;j++){
                if (classLabList.get(i)[j]!=null){
                    if (classLabList.get(i)[j].getCount()==6){
                        cb2.add(classLabList.get(i)[j].getSubject());
                        tb2.add(classLabList.get(i)[j].getTeacher());
                        b2++;
                    }else if (classLabList.get(i)[j].getCount()==3){
                        System.out.println(classLabList.get(i)[j].getSubject());
                        cb1.add(classLabList.get(i)[j].getSubject());
                        tb1.add(classLabList.get(i)[j].getTeacher());
                        b1++;
                    }
                }
            }

            System.out.println(b1+" "+b2);

            if(b2==2){
                ArrayList<String> tmp=new ArrayList<>();
                ArrayList<String> ttmp=new ArrayList<>();
                tmp.add(cb2.get(0));
                tmp.add(cb2.get(1));
                ttmp.add(tb2.get(0));
                ttmp.add(tb2.get(1));
                clubed.add(tmp);
                clubed.add(tmp);
                tclubed.add(ttmp);
                tclubed.add(ttmp);
            }else if (b2==3){
                ArrayList<String> tmp1=new ArrayList<>();
                ArrayList<String> tmp2=new ArrayList<>();
                ArrayList<String> tmp3=new ArrayList<>();

                ArrayList<String> ttmp1=new ArrayList<>();
                ArrayList<String> ttmp2=new ArrayList<>();
                ArrayList<String> ttmp3=new ArrayList<>();

                tmp1.add(cb2.get(0));
                tmp1.add(cb2.get(1));
                clubed.add(tmp1);

                ttmp1.add(tb2.get(0));
                ttmp1.add(tb2.get(1));
                tclubed.add(ttmp1);

                tmp2.add(cb2.get(0));
                tmp2.add(cb2.get(2));
                clubed.add(tmp2);

                ttmp2.add(tb2.get(0));
                ttmp2.add(tb2.get(2));
                tclubed.add(ttmp2);

                tmp3.add(cb2.get(1));
                tmp3.add(cb2.get(2));
                clubed.add(tmp3);

                ttmp3.add(tb2.get(1));
                ttmp3.add(tb2.get(2));
                tclubed.add(ttmp3);
            }
            if (b1!=0){
                for (int k=0;k<b1;k++){
                    ArrayList<String> tmp=new ArrayList<>();
                    ArrayList<String> ttmp=new ArrayList<>();
                    //System.out.println(cb1.get(k)+" at ");
                    tmp.add(cb1.get(k));
                    ttmp.add(tb1.get(k));
                    //System.out.println(tmp);
                    clubed.add(tmp);
                    tclubed.add(ttmp);
                    //System.out.println(clubed);
                }
            }
            for (int c=0;c<clubed.size();c++){
                int assigned=0;
                //System.out.println(c);
                while (assigned!=1) {
                    int as = labSlotIndex.get(0);
                    if (clubed.get(c).size()==2) {
                        if (DerbyConn.checkLabFree(giveLab(clubed.get(c).get(0)), as) == 1 && DerbyConn.checkLabFree(giveLab(clubed.get(c).get(1)), as) == 1) {
                            if (DerbyConn.checkTeacherFree(tclubed.get(c).get(0), as) == 1 && DerbyConn.checkTeacherFree(tclubed.get(c).get(1), as) == 1) {
                                String t1,t2;
                                t1=clubed.get(c).get(0)+","+clubed.get(c).get(1);
                                t2=tclubed.get(c).get(0)+","+tclubed.get(c).get(1);
                                DerbyConn.insertLabTable(as,giveLab(clubed.get(c).get(0)),classLabList.get(i)[0].getCls(),tclubed.get(c).get(0));
                                DerbyConn.insertLabTable(as,giveLab(clubed.get(c).get(1)),classLabList.get(i)[0].getCls(),tclubed.get(c).get(1));
                                DerbyConn.insertTeacherTable(as,classLabList.get(i)[0].getCls(),tclubed.get(c).get(0),clubed.get(c).get(0));
                                DerbyConn.insertTeacherTable(as,classLabList.get(i)[0].getCls(),tclubed.get(c).get(1),clubed.get(c).get(1));
                                DerbyConn.insertClassTable(as,classLabList.get(i)[0].getCls(),t2,t1);
                                assigned=1;
                                if (as%2!=0){
                                    labSlotIndex.remove(1);
                                }
                                labSlotIndex.remove(0);
                            }else{
                                labSlotIndex.remove(0);
                                continue;
                            }
                        }else{
                            labSlotIndex.remove(0);
                            continue;
                        }
                    }
                }
            }
//            System.out.println(classLabList.get(i)[0].getCls());
//            System.out.println(clubed);
//            System.out.println(tclubed);
            labSlotIndex.clear();
        }

    }

    String giveLab(String sub){

        String lab;
        if (cc2.contains(sub.toLowerCase())) {
            lab = "CC2_LAB";
            return lab;
        } else {
            if (cc4.contains(sub.toLowerCase())) {
                lab = "CC4_LAB";
                return lab;
            } else {
                if (cc6.contains(sub.toLowerCase())) {
                    lab = "CC6_LAB";
                    return lab;
                }
            }
        }
        return "";
    }

    public void extractLabs() {

        for (int i=0;i<classesUnique.size();i++){
            cl=new ClassLab[10];
            int toCnt=DerbyConn.extractLab(classesUnique.get(i),cl);
            if (toCnt==0){
                continue;
            }else {
                classLabList.add(cl);
            }
        }
        //printLabDetails();

    }

    void printLabDetails() {

        for (int i=0;i<classLabList.size();i++){
            if (classLabList.get(i)[0]!=null) {
                System.out.println(classLabList.get(i)[0].getCls());
            }
            for (int j=0;j<classLabList.get(i).length;j++) {
                if (classLabList.get(i)[j] != null) {
                    System.out.println(classLabList.get(i)[j].id + " "+classLabList.get(i)[j].getCount()+" "+ classLabList.get(i)[j].getSubject());
                }
            }
            System.out.println("-----------------");
        }

    }

    void printSubDetails() {

        for (int i=0;i<classSubList.size();i++){
            if (classSubList.get(i)[0]!=null) {
                System.out.println(classSubList.get(i)[0].getCls());
            }
            for (int j=0;j<classSubList.get(i).length;j++) {
                if (classSubList.get(i)[j] != null) {
                    System.out.println(classSubList.get(i)[j].id + " "+classSubList.get(i)[j].getCount()+" "+ classSubList.get(i)[j].getSubject());
                }
            }
            System.out.println("-----------------");
        }

    }

    void createSlots() {

        for (int i=0;i<classesUnique.size();i++) {
            DerbyConn.createSlotsTable(classesUnique.get(i));
            //System.out.println("created "+classesUnique.get(i));
            for (int j=0;j<teacher.size();j++){
                for (int k=0;k<teacherClass.get(j).size();k++) {
                    if (classesUnique.get(i).equals(teacherClass.get(j).get(k))){
                        DerbyConn.insertSlotsTable(classesUnique.get(i),teacher.get(j),teacherSubject.get(j).get(k),teacherCount.get(j).get(k));
                    }
                }
            }
            //System.out.println("Inserted slots");
        }
        System.out.println("Slots table created");

    }

    void  extractDetails() {

        for(int i=0;i<ReadExcel.teacher.size();i++){
            String[] tempClass=ReadExcel.teacherClass.get(i).split(",",0);
            String[] tempCount=ReadExcel.teacherClassCount.get(i).split(",",0);
            String[] tempSubject=ReadExcel.teacherSubject.get(i).split(",",0);
            ArrayList<String> tempClassArray=new ArrayList<>();
            ArrayList<String> tempCountArray=new ArrayList<>();
            ArrayList<String> tempSubjectArray=new ArrayList<>();
            ReadExcel.teacher.set(i,ReadExcel.teacher.get(i).replace(".","_"));
            ReadExcel.teacher.set(i,ReadExcel.teacher.get(i).replaceAll("\\s",""));
            teacher.add(ReadExcel.teacher.get(i));
            for (int h=0;h<tempSubject.length;h++){
                tempSubjectArray.add(tempSubject[h]);
            }
            teacherSubject.add(tempSubjectArray);
            for (int j=0;j<tempClass.length;j++){
                tempClass[j]=tempClass[j].replace("-","_");
                tempClassArray.add(tempClass[j]);
            }
            teacherClass.add(tempClassArray);
            for (int k=0;k<tempCount.length;k++){
                tempCountArray.add(tempCount[k]);
            }
            teacherCount.add(tempCountArray);
        }
        System.out.println("\nExtracted Details\n");
        System.out.println(teacher);
        System.out.println(teacher.size());
        System.out.println(teacherClass);
        System.out.println(teacherClass.size());
        System.out.println(teacherSubject);
        System.out.println(teacherSubject.size());
        System.out.println(teacherCount);
        System.out.println(teacherCount.size());


    }

    void createTeachers() {

        for (int j=0;j<teacher.size();j++){
            DerbyConn.createTeachersTable(teacher.get(j));
        }
        System.out.println("Teacher table created");
    }

    void createClasses() {

        ArrayList<String> classes=new ArrayList<>();
        for(int i=0;i<ReadExcel.teacherClass.size();i++){
            String[] tempString=ReadExcel.teacherClass.get(i).split(",",0);
            for (int j=0;j<tempString.length;j++){
                classes.add(tempString[j]);
            }
        }
        classesUnique=removeDuplicates(classes);
        //System.out.println(classesUnique);
        for (int i=0;i<classesUnique.size();i++){
            DerbyConn.createClassTable(classesUnique.get(i));
        }
        System.out.println("Class table created");

    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }


}
