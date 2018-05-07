package mytasks;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import mytasks.Objects.Consts;
import mytasks.Objects.Task;

public class Preparation extends JFrame{
    
    private JPanel PShow;
    private JLabel Show;
    
    private final Consts Const=new Consts();
    private ArrayList<Task> Tasks=new ArrayList<>();
    private ArrayList<String> TrashTasks=new ArrayList<>(),Paths=new ArrayList<>();
    private Task task=new Task();
    private int counter=5,Count=0,Notification[]={0,0,0,0,0,0,0,0,0};
    
    public Preparation(){
        
        SetFrameAndPanelsAndComponentsAndAddToFrame();
        GetPaths();
        GetTasks();
        SetTrashPathsIntoFile();
        SetAvailableTasksIntoFile();
        GO();
    }

    private void SetFrameAndPanelsAndComponentsAndAddToFrame() {
    
        this.setTitle("My Tasks");
        this.setSize( 300 , 300 );
        this.setResizable(false);
        this.setLocation( 530 , 230 );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        
        PShow=new JPanel();
        PShow.setBorder(new EmptyBorder(20,20,20,20));
        
        Show=new JLabel();
        Show.setText("Prgressing 0%");
        
        PShow.add(Show);
        this.add(PShow,BorderLayout.CENTER);
    }
    
    private void GetPaths() {
        File file=new File( Const.Available_File_Path() );
        try ( Scanner out = new Scanner( file ) ) {
            while(out.hasNextLine()){
                Paths.add( out.nextLine() );
            }
            Show.setText("Prgressing 5%");
        }
        catch( Exception E){
            Const.GetOut("From Preparation In Line 66");
        }
        if ( !Paths.isEmpty() )
            Count=85/Paths.size();
        else
            Count=85;
    }
    private void GetTasks() {
        
        for (int i=0;i<Paths.size();i++){

            File file=new File( Paths.get(i) );
            try ( Scanner out = new Scanner( file ) ) {
                task.Path=Paths.get(i);
                while(out.hasNextLine()){

                    String Get=out.nextLine();
                    if ( Get.charAt(0)!='P' )
                        SetTask( Get );
                }
                AnalyzeTask();
                task=new Task();
                counter+=Count;
                Show.setText("Prgressing "+counter+"%");
            }
            catch(Exception E){
                Const.GetOut("From Preparation In Line 92");
            }
        }
    }
    private void SetTask(String Get){
        
        if ( Get.charAt(0)=='i' )
            task.Id=Const.GetNumber( Get.substring(3), "From Preparation In Line 99" /* new Line In Jave*/ );
        else if ( Get.charAt(0)=='N' )
            task.Name=Get.substring(5);
        else if ( Get.charAt(0)=='T' )
            task.Type=Get.substring(5);
        else if ( Get.charAt(0)=='C' )
            task.Code=Const.GetNumber( Get.substring(5), "From Preparation In Line 105" );
        else if ( Get.charAt(0)=='D' )
            task.Discreption=Get.substring(12);
        else if ( Get.charAt(0)=='S'&&Get.charAt(1)=='i' )
            task.Situation=Get.substring(10);
        else if ( Get.charAt(0)=='S'&&Get.charAt(9)=='s' )
            task.Start.Days=Const.GetNumber( Get.substring(11), "From Preparation In Line 111" );
        else if ( Get.charAt(0)=='S'&&Get.charAt(9)=='t' )
            task.Start.Month=Const.GetNumber( Get.substring(12), "From Preparation In Line 113" );
        else if ( Get.charAt(0)=='S'&&Get.charAt(9)=='r' )
            task.Start.Hour=Const.GetNumber( Get.substring(11), "From Preparation In Line 115" );
        else if ( Get.charAt(0)=='S')
            task.Start.Minute=Const.GetNumber( Get.substring(13), "From Preparation In Line 117" );
        else if ( Get.charAt(0)=='E'&&Get.charAt(7)=='s' )
            task.End.Days=Const.GetNumber( Get.substring(9), "From Preparation In Line 119" );
        else if ( Get.charAt(0)=='E'&&Get.charAt(7)=='t' )
            task.End.Month=Const.GetNumber( Get.substring(10), "From Preparation In Line 121");
        else if ( Get.charAt(0)=='E'&&Get.charAt(7)=='r' )
            task.End.Hour=Const.GetNumber( Get.substring(9), "From Preparation In Line 123" );
        else if ( Get.charAt(0)=='E' )
            task.End.Minute=Const.GetNumber( Get.substring(11), "From Preparation In Line 125" );
        else
            if (task.Code!=1)
                Const.SetRepeats( Get.substring(8) , "From Preparation In Line 128" , task );
    }
    private void AnalyzeTask() {
        if ( !Const.IsEnded( task.End ) ){
            Put_In_Notification();
            return ;
        }
        TrashTasks.add(task.Path);
        //  number of notifications
        Notification[8]++;
        if ( task.Type.charAt(0)=='T' )
            // number of ended tasks without complete
            if ( !Const.CompareTaskCount(task.Situation) )
                Notification[3]++;
            // number of ended tasks completed
            else
                Notification[4]++;
        else
            if ( !Const.CompareTaskCount(task.Situation) )
            // number of ended goals without complete
                Notification[6]++;
            // number of ended goals completed
            else
                Notification[7]++;
    }
    private void Put_In_Notification() {
    
        Tasks.add( task);
        if ( task.Type.charAt(0)=='G'&&( Const.IsOnWork(task.Start) ) ){
            Notification[5]++;
            Notification[8]++;
        }
        else
            if (task.Start.Month==Const.Month()&&task.Start.Days==Const.Day()){
                Notification[0]++;
                Notification[8]++;
            }
            else if ( task.Code!=1&&( Const.IsOnWork(task.Start) ) ){
                if ( Const.GetRepeats( task.Code,task.repeat ,"From Preparation In Line 166" ) )
                    Notification[1]++;
                else
                    Notification[2]++;
                Notification[8]++;
            }
    }
    private void SetTrashPathsIntoFile() {
        
        File file=new File( Const.Ended_File_Path() );
        try( PrintWriter in=new PrintWriter( new FileOutputStream( file,true ) ) ){
            
            for (int i=0;i<TrashTasks.size();i++)
                in.println( TrashTasks.get(i) );
            in.close();
        }
        catch(Exception E){
            Const.GetOut("From Preparation In Line 183");
        }
        Show.setText("Prgressing 95%");
    }
    private void SetAvailableTasksIntoFile(){
        try{
            File file=new File( Const.Available_File_Path() );
            file.delete();
            file.createNewFile();
            try ( PrintWriter in = new PrintWriter(file) ) {
                for (int i=0;i<Tasks.size();i++)
                    in.println(Tasks.get(i).Path);
            }
        }
        catch(Exception E){
            Const.GetOut("From Preparation In Line 198");
        }
        Show.setText("Prgressing 100%");
    }

    private void GO(){
        this.dispose();
        new Main( Tasks, Notification);
    }
}