package sample;

import java.sql.*;
import java.util.ArrayList;

public class DerbyConn {

    //This class contains all the functions to query the database

    private static Connection conn = null;
    private static Statement stmt = null;
    private static PreparedStatement prepstmt = null;

    //The connection is made to the derby

    DerbyConn(){

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection("jdbc:derby:C:\\Users\\aakas\\IdeaProjects\\chronos\\db\\chronosdb");
            if (conn != null) {
                System.out.println("Connected successfully\n");
                //JOptionPane.showMessageDialog(null, "Base de Datos Ya Leida Correctamente");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    //The Input table is created to contain the input value from the excel file.
    static void createInputTable(){

        try {

            stmt=conn.createStatement();
            stmt.executeUpdate(
                    "create table teacher.input_detials(" +
                            "id int not null generated always as identity," +
                            "primary key(id)," +
                            "teacher_name varchar(50)," +
                            "teacher_class varchar(200)," +
                            "teacher_class_count varchar(200)," +
                            "teacher_subject varchar(200)" +
                            ")");
            //System.out.println("Table created.");
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Problem at create input table\n");
            System.out.println(e.getMessage());
        }

    }

    //the excel file values are inserted into the input table
    static void insertInputTable(){

        try {

            prepstmt=conn.prepareStatement(
                    "INSERT INTO TEACHER.INPUT_DETIALS (TEACHER_NAME, TEACHER_CLASS, TEACHER_CLASS_COUNT, TEACHER_SUBJECT) values (?,?,?,?)"
            );
            for(int i=1;i<=ReadExcel.teacher.size();i++){
                prepstmt.setString(1,ReadExcel.teacher.get(i-1));
                prepstmt.setString(2,ReadExcel.teacherClass.get(i-1));
                prepstmt.setString(3,ReadExcel.teacherClassCount.get(i-1));
                prepstmt.setString(4,ReadExcel.teacherSubject.get(i-1));
                prepstmt.executeUpdate();
                //System.out.println("Inserted "+i+"\n");
            }
            System.out.println("Table created.");
            prepstmt.close();

        } catch (SQLException e) {
            System.out.println("Problem at insert input table\n");
            System.out.println(e.getMessage());
        }

    }

    static int obtainFreeSlots(String className,ArrayList<Integer> fst){

        return 0;

    }

    static int checkLabFree(String labName,int index){

        try {

            stmt=conn.createStatement();
            ResultSet rst;
            //System.out.println("SELECT OCCUPIED FROM TEACHER."+labName+" WHERE ID="+index);
            rst=stmt.executeQuery("SELECT OCCUPIED FROM TEACHER."+labName+" WHERE ID="+index);
            while (rst.next()){
                if(rst.getInt("occupied")==0){
                    return 1;
                }
                else
                {
                    return 0;
                }
            }

//            if(rstValue.get(0)==0&&rstValue.get(1)==0){
//                return 1;
//            }else{
//                if (rstValue.get(0)==0&&rstValue.get(1)==1){
//                    return 2;
//                }else {
//                    if (rstValue.get(0)==1&&rstValue.get(1)==0) {
//                        return 3;
//                    }else {
//                        return 0;
//                    }
//                }
//            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    static void insertLabTable(int index,String lab,String cls, String teach){
        try {

            stmt=conn.createStatement();
            ResultSet rst;
            //System.out.println("SELECT CLASS,TEACHER FROM TEACHER."+lab+" WHERE ID="+index);
            prepstmt=conn.prepareStatement("UPDATE TEACHER."+lab+" t SET t.CLASS=?,t.TEACHER=?,t.OCCUPIED=? WHERE t.ID=?");
            rst=stmt.executeQuery("SELECT CLASS,TEACHER FROM TEACHER."+lab+" WHERE ID="+index);
            rst.next();
                if(rst.getString("class")==null && lab.equals("CC2_LAB")){
                    for (int i=0;i<3;i++) {
                        prepstmt.setString(1, cls);
                        prepstmt.setString(2, teach);
                        prepstmt.setInt(3, 1);
                        prepstmt.setInt(4, index+i);
                        prepstmt.executeUpdate();
                    }
                }else{
                    if(rst.getString("class")==null){
                        for (int i=0;i<3;i++) {
                            prepstmt.setString(1, cls);
                            prepstmt.setString(2, teach);
                            prepstmt.setInt(3, 0);
                            prepstmt.setInt(4, index+i);
                            prepstmt.executeUpdate();
                        }
                    }else {
                        String tempClass = rst.getString("class") + "," + cls;
                        String tempTeacher = rst.getString("teacher") + "," + teach;
                        for (int j = 0; j < 3; j++) {
                            prepstmt.setString(1, tempClass);
                            prepstmt.setString(2, tempTeacher);
                            prepstmt.setInt(3, 1);
                            prepstmt.setInt(4, index + j);
                            prepstmt.executeUpdate();
                        }
                    }
                }

            prepstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertTeacherTable(int index,String cls, String teach,String sub){
        try {

            stmt=conn.createStatement();
            ResultSet rst;
            prepstmt=conn.prepareStatement("UPDATE TEACHER."+teach+" t SET t.SUBJECT=?,t.CLASS=?,t.OCCUPIED=? WHERE t.ID=?");
            for (int i=0;i<3;i++) {
                prepstmt.setString(1, sub);
                prepstmt.setString(2, cls);
                prepstmt.setInt(3, 1);
                prepstmt.setInt(4, index+i);
                prepstmt.executeUpdate();
            }
            prepstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertClassTable(int index,String cls, String teach,String sub){
        try {

            stmt=conn.createStatement();
            ResultSet rst;
            //System.out.println("SELECT CLASS,TEACHER FROM TEACHER."+lab+" WHERE ID="+index);
            prepstmt=conn.prepareStatement("UPDATE TEACHER."+cls+" t SET t.SUBJECT=?,t.TEACHER=?,t.OCCUPIED=? WHERE t.ID=?");
            rst=stmt.executeQuery("SELECT SUBJECT,TEACHER FROM TEACHER."+cls+" WHERE ID="+index);
            rst.next();

                for (int i=0;i<3;i++) {
                    prepstmt.setString(1, sub);
                    prepstmt.setString(2, teach);
                    prepstmt.setInt(3, 1);
                    prepstmt.setInt(4, index+i);
                    prepstmt.executeUpdate();
                }

//            else {
//                String tempSub=rst.getString("subject")+","+sub;
//                String tempTeacher=rst.getString("teacher")+","+teach;
//                for (int j=0;j<3;j++) {
//                    prepstmt.setString(1, tempSub);
//                    prepstmt.setString(2, tempTeacher);
//                    prepstmt.setInt(3, 1);
//                    prepstmt.setInt(4, index+j);
//                    prepstmt.executeUpdate();
//                }
//            }

            prepstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static int checkTeacherFree(String teach,int index) {

        try {
            stmt=conn.createStatement();

        ResultSet rst;
        rst=stmt.executeQuery("SELECT * FROM TEACHER."+teach.toUpperCase()+" WHERE id = "+index+"");
        while (rst.next()){
            if(rst.getInt("occupied")==0){
                return 1;
            }else {
                return 0;
            }
        }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;

    }

    static int extractLab(String className,ClassLab[] lcl){

        try {


            stmt=conn.createStatement();
            ResultSet rst;
            rst=stmt.executeQuery("SELECT * FROM TEACHER."+className.toUpperCase()+"_SLT WHERE SUBJECT LIKE '%lab'");
            //System.out.println("Table created.");
            int i=0;
            if (!rst.next()){
                return 0;
            }else {
                do {
                    //System.out.println("assigned");
                    lcl[i]=new ClassLab();
                    lcl[i].id = rst.getInt("id");
                    lcl[i].setCls(className);
                    lcl[i].setCount(rst.getInt("count"));
                    lcl[i].setSubject(rst.getString("subject"));
                    lcl[i].setTeacher(rst.getString("teacher"));
                    i++;
                }while (rst.next());
            }
            //System.out.println(Scheduler.cl[0]);
            stmt.close();



        } catch (SQLException e) {
            System.out.println("Problem at create input table\n");
            System.out.println(e.getMessage());
        }

        return 1;

    }


    static int extractSub(String className,ClassSub[] lcs){

        try {


            stmt=conn.createStatement();
            ResultSet rst;
            rst=stmt.executeQuery("SELECT * FROM TEACHER."+className.toUpperCase()+"_SLT WHERE SUBJECT NOT LIKE '%lab'");
            //System.out.println("Table created.");
            int i=0;
            if (!rst.next()){
                return 0;
            }else {
                do {
                    //System.out.println("assigned");
                    lcs[i]=new ClassSub();
                    lcs[i].id = rst.getInt("id");
                    lcs[i].setCls(className);
                    lcs[i].setCount(rst.getInt("count"));
                    lcs[i].setSubject(rst.getString("subject"));
                    lcs[i].setTeacher(rst.getString("teacher"));
                    i++;
                }while (rst.next());
            }
            //System.out.println(Scheduler.cl[0]);
            stmt.close();



        } catch (SQLException e) {
            System.out.println("Problem at create input table\n");
            System.out.println(e.getMessage());
        }

        return 1;

    }

    static void createSlotsTable(String s){

        try {
            s=s+"_slt";
            stmt=conn.createStatement();
            stmt.executeUpdate(
                    "create table teacher."+s+"(id int generated always as identity primary key,teacher varchar(50) default null,subject varchar(50) default null,count int default null,occupied int default 0)");
            //System.out.println("Table created.");

            stmt.close();

        } catch (SQLException e) {
            System.out.println("Problem at create slot table\n");
            System.out.println(e.getMessage());
        }

    }

    static void createClassTable(String className){

        try {

            stmt=conn.createStatement();
            stmt.executeUpdate(
                    "create table teacher."+className+"(id int generated always as identity primary key,subject varchar(200) default null,teacher varchar(50) default null,occupied int default 0)");
            //System.out.println("Table created.");

            prepstmt=conn.prepareStatement(
                    "INSERT INTO TEACHER."+className+" (SUBJECT,TEACHER) VALUES (DEFAULT, DEFAULT)"
            );
            for(int i=1;i<=40;i++){
                prepstmt.executeUpdate();
            }
            //System.out.println("Class Table created.");

            prepstmt.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Problem at create class table\n");
            System.out.println(e.getMessage());
        }

    }

    static void createTeachersTable(String teacherName){

        try {

            stmt=conn.createStatement();

            teacherName=teacherName.replaceAll("\\s","_");
            teacherName=teacherName.replace(".","_");
            //System.out.println(teacherName);

            stmt.executeUpdate(
                    "create table teacher."+teacherName+"(id int generated always as identity primary key,subject varchar(200) default null,class varchar(100) default null,occupied int default 0)");
            //System.out.println("Table created.");

            prepstmt=conn.prepareStatement(
                    "INSERT INTO TEACHER."+teacherName+" (SUBJECT,CLASS,OCCUPIED) VALUES (DEFAULT, DEFAULT, DEFAULT)"
            );
            for(int i=1;i<=40;i++){
                prepstmt.executeUpdate();
            }
            //System.out.println("Class Table created.");

            prepstmt.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Problem at create teacher table\n");
            System.out.println(e.getMessage());
        }

    }

    static void insertSlotsTable(String sltclass,String sltteacher,String sltsub,String sltcount){

        try {
            sltclass=sltclass+"_slt";
            sltclass=sltclass.toUpperCase();
            prepstmt=conn.prepareStatement(
                    "INSERT INTO TEACHER."+sltclass+" (TEACHER, SUBJECT, COUNT) values (?,?,?)"
            );

            prepstmt.setString(1,sltteacher);
            prepstmt.setString(2,sltsub);
            prepstmt.setInt(3,Integer.parseInt(sltcount));
            prepstmt.executeUpdate();
                //System.out.println("Inserted "+i+"\n");

            //System.out.println("Table created.");
            prepstmt.close();
            //conn.close();

        } catch (SQLException e) {
            System.out.println("Problem at insert slot table\n");
            System.out.println(e.getMessage());
        }

    }

    static void createSchema(String schemaName){

        try {

            stmt=conn.createStatement();
            stmt.execute("CREATE SCHEMA "+schemaName);
            //System.out.println("Table created.");
            stmt.close();
            //conn.close();

        } catch (SQLException e) {
            System.out.println("Problem at create schema\n");
            System.out.println(e.getMessage());
        }

    }

    public static void assignSlot(Integer index, String lab, String cls, String teach, String sub) {

        insertLabTable(index, lab, cls, teach);
        //System.out.println(classLabList.get(i)[j].getCls());
        insertTeacherTable(index, cls, teach, sub);
        insertClassTable(index, cls, teach, sub);

    }
}
