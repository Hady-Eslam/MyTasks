package mytasks.Objects;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public final class Consts {
    
    private final int Months[]={31,28,31,30,31,30,31,31,30,31,30,31};
    private int Day=-1,Month=-1;
    private String DayName="" , Week[]={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    
    public Consts(){
        
        String Time=new SimpleDateFormat("dd/MM/yyyy").format( new Date() );
        Day=GetNumber( ""+Time.charAt(0)+Time.charAt(1), "From Consts In Line 21" );
        Month=GetNumber( ""+Time.charAt(3)+Time.charAt(4), "From Consts In Line 22" );
        
        Date date=null;
        try{    date=new SimpleDateFormat("dd/MM/yyyy").parse( Time ); }
        catch(Exception E){  GetOut( "From Consts In Line 26");}
        DayName=new SimpleDateFormat("EEEE",Locale.ENGLISH).format( date );
    }
    
    public String File_Path(){
        return "E://My Programs//ProgramsFiles//MyTasks//File.txt"; 
    }
    public String Ended_File_Path(){
        return "E://My Programs//ProgramsFiles//MyTasks//EndedTasks.txt";
    }
    public String Available_File_Path(){
        return "E://My Programs//ProgramsFiles//MyTasks//AvailableTasks.txt";
    }
    
    public void GetOut( String Location){
        JOptionPane.showMessageDialog( null, Location );
        System.exit(0);
    }
    public void SetDays( int Month, JComboBox CDays){
        for (int i=1;i<=Months[Month-1];i++)
            CDays.addItem(i);
    }
    public int GetNumber( String Number, String Location){
        int count=0;
        try{
            for (int i=0;i<Number.length();i++){
                count*=10;
                count+=(int)Number.charAt(i)-48;
            }
        }
        catch (Exception E){
            GetOut( Location+" Faild In Converting");
        }
        return count;
    }
    public void SetRepeats( String Text, String Location, Task task){
        
        String Number="";
        try{
            for (int i=0;i<Text.length();i++)
                if (Text.charAt(i)!=' ')
                    Number+=Text.charAt(i);
                else{
                    if (Number.length()!=0)
                        task.repeat.add(Number);
                    Number="";
                }
            if (Number.length()!=0)
                task.repeat.add(Number);
        }
        catch(Exception E){
            GetOut( Location+" Faild In Converting");
        } 
    }
    public boolean GetRepeats( int Code, ArrayList<String> repeats, String Location){
        
        if (Code==1)
            return true;
        if (Code==2){
            for (int i=0;i<repeats.size();i++)
                if (repeats.get(i).equals( DayName() ))
                    return true;
        }
        else{
            for (int i=0;i<repeats.size();i++)
                if ( Day()==GetNumber( repeats.get(i),Location ) )
                    return true;
        }
        return false;
    }
    
    public String GetDayName( int i){
        return Week[i];
    }
    public int GetDayNumber( String Day){
        for (int i=0;i<7;i++)
            if ( Day.equals(Week[i]))
                return i+1;
        return 0;
    }
    public int GetDayNameInMonth( int Day, int Month, String Location){
        Date date = null;
        try{
            date = new SimpleDateFormat( "dd/MM/yyyy" ).parse( Day+"/"+Month+"/2018" );
        }
        catch( Exception E){
            GetOut( Location);
        }
        return Get( new SimpleDateFormat( "EEEE", Locale.ENGLISH).format( date ) );
    }
    private int Get( String Day){
        for (int i=0;i<7;i++)
            if ( Day.equals(Week[i]) )
                return i+1;
        return -1;
    }
    public int GetDays( int i){
        return Months[i-1];
    }
    
    /**
     * Function For Saving Tasks
     * @param task for the task to be save
     * @param Location if error occur then it point its place in the code
     */
    public void SaveTask( Task task, String Location){
        try{
            File file=new File( task.Path );
            try ( PrintWriter in = new PrintWriter( file ) ) {
                file.delete();
                file.createNewFile();
                
                in.println("id="+task.Id);
                in.println("Name="+task.Name);
                in.println("Situation="+task.Situation);
                in.println("Start.Days="+task.Start.Days);
                in.println("Start.Month="+task.Start.Month);
                in.println("Start.Hour="+task.Start.Hour);
                in.println("Start.Minute="+task.Start.Minute);
                in.println("End.Days="+task.End.Days);
                in.println("End.Month="+task.End.Month);
                in.println("End.Hour="+task.End.Hour);
                in.println("End.Minute="+task.End.Minute);
                in.println("Path="+task.Path);
                in.println("Type="+task.Type);
                in.println("Code="+task.Code);
                in.print("Repeats=");
                for (int i=0;i<task.repeat.size();i++)
                    in.print(task.repeat.get(i)+" ");
                in.println();
                in.println("Discreption="+task.Discreption);
            }
        }
        catch(Exception E){
            GetOut( Location);
        }
    }
    
    public boolean IsOnWork( TaskDate Start){
        return ( Start.Month<Month()||Start.Month==Month()&&Start.Days<=Day() );
    }
    public boolean IsEnded( TaskDate End){
        return ( End.Month<Month()||End.Month==Month&&End.Days<Day() );
    }
    public boolean CompareTaskCount( String Get ){
        String Part1 = Get.substring(0, Get.indexOf('/'));
        String Part2 = Get.substring(Get.indexOf('/')+1);
        return Part1.equals(Part2);
    }
    
    public int Day(){
        return Day;
    }
    public int Month(){
        return Month;
    }
    public String DayName(){
        return DayName;
    }
}