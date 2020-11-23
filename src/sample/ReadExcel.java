package sample;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.BLANK;
//import static sample.setdet.teacher;
//import static sample.setdet.teachersubject;

public class ReadExcel {
    public static List<String> teacher = new ArrayList<>() ;
    static public List<String> teacherClass = new ArrayList<>() ;
    static public List<String> teacherSubject = new ArrayList<>() ;
    static public List<String> teacherClassCount = new ArrayList<>() ;
    public static String pth;
    ReadExcel() {
        try
        {
            int r=0;
            //DerbyConn.createSchema("teacher");

            //Creates the inputtable to store the input values

            DerbyConn.createInputTable();
            pth=pth.replace("/","\\");
            System.out.println(pth);
            FileInputStream file = new FileInputStream(new File(pth));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            //reads all the values from the excel file and store it in the array

            while (rowIterator.hasNext())
            {
//if (r==0){r++;continue;}
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                //System.out.println ("Row No.: " + row.getRowNum ());
                if(row.getRowNum()==0){
                    continue; //just skip the rows if row number is 0 or 1
                }
                    for (int cn=0;cn<row.getLastCellNum();cn++) {
                        Cell cell = row.getCell(cn);
                        if (cell != null || cell.getCellType() != BLANK) {
                            switch (cn) {
                                case 0:
                                    String tn = cell.getStringCellValue();
                                    if (tn == null) {
                                        break;
                                    }
                                    teacher.add(tn);
                                    break;

                                case 1:
                                    String cnm = cell.getStringCellValue();
                                    if (cnm == null) {
                                        break;
                                    }
//                                    String[] cna = cnm.split(",", 0);
//                                    System.out.println(cna[0]);
//                                    ArrayList<String> la1=new ArrayList<>();
//                                    for (int t=0;t<cna.length;t++){
//                                        la1.add(cna[t]);
//                                    }
                                    cnm=cnm.replace("-","_");
                                    teacherClass.add(cnm);
                                    break;

                                case 2:
                                    String sn = cell.getStringCellValue();
                                    if (sn == null) {
                                        break;
                                    }
//                                    String[] sna = sn.split(",", 0);
//                                    ArrayList<String> la2=new ArrayList<>();
//                                    for (int t=0;t<sna.length;t++){
//                                        la2.add(sna[t]);
//                                    }
                                    teacherSubject.add(sn);
                                    break;

                                case 3:
                                    switch (cell.getCellType()) {
                                        case NUMERIC:
                                            String ta =Integer.toString((int) cell.getNumericCellValue());
                                            if (ta == null) {
                                                break;
                                            }
//                                            ArrayList<String> la3=new ArrayList<>();
//                                            for (int t=0;t<ta.length;t++){
//                                                la3.add(ta[t]);
//                                            }
                                           teacherClassCount.add(ta);
                                           // System.out.print(cell.getNumericCellValue() + "\t");
                                            break;
                                        case STRING:
                                            String sbc = cell.getStringCellValue();
                                            if (sbc == null) {
                                                break;
                                            }
//                                            String[] abca = sbc.split(",", 0);
//                                            ArrayList<String> la4=new ArrayList<>();
//                                            for (int t=0;t<abca.length;t++){
//                                                la4.add(abca[t]);
//                                            }
                                            teacherClassCount.add(sbc);
                                          //  System.out.print(cell.getStringCellValue() + "\t");
                                            break;
                                    }

                            }
                        }
                    }
                   // System.out.println("  line end    ");

            }

            //Calls the class to insert all the values in the array into the table.
            //Goto DerbyConn class

            DerbyConn.insertInputTable();
            System.out.println("Input table created");

            //The Scheduler class is initiated.

            new Scheduler();
            file.close();
           // for (int u=0;u<teacher.size();u++){


                /*for (int m=0;m<teachersubject.get(u).size();m++){
                    if(teachersubject.get(u).get(m).length()>=3) {
                        if (teachersubject.get(u).get(m).substring(teachersubject.get(u).get(m).length() - 3).equals("lab")&&Integer.parseInt(setdet.tsubcnt.get(u).get(m))==6) {
                            teachersubject.get(u).add(m,teachersubject.get(u).get(m));

                            System.out.println(teacher.get(u)+" "+teachersubject.get(u).get(m));
                            setdet.tsubcnt.get(u).set(m,"3");
                            setdet.tsubcnt.get(u).add(m,"3");

                            setdet.tcls.get(u).add(m,setdet.tcls.get(u).get(m));
                            for(int y=0;y<setdet.tcls.get(u).size();y++){
                                System.out.println(setdet.tcls.get(u).get(y));
                            }
                            u=-1;
                            break;
                        }
                    }
                }*/
               /* for (int m=0;m<setdet.tsubcnt.get(u).size();m++){
                    System.out.print(setdet.tsubcnt.get(u).get(m)+" ");
                }*/
            }
//            for (int u=0;u<teacher.size();u++){
//                System.out.print(teacher.get(u));
//                for (int m=0;m<setdet.tcls.get(u).size();m++){
//
//                    System.out.print("\n"+setdet.tcls.get(u).get(m)+" ");
//                }
//                for (int m=0;m<teachersubject.get(u).size();m++){
//                    System.out.print(teachersubject.get(u).get(m)+" ");
//                }
//                for (int m=0;m<setdet.tsubcnt.get(u).size();m++){
//                    System.out.println(setdet.tsubcnt.get(u).get(m)+" ");
//                }
//            }


//            setdet.hoursperday="8";
////            setdet.daysperweek="5";
////            setdet s=new setdet();
////            s.putdata();
   //     }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
