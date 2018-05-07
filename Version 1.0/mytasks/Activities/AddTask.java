package mytasks.Activities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import mytasks.Fragments.Repeats;
import mytasks.Fragments.SetDate;
import mytasks.Main;
import mytasks.Objects.Consts;
import mytasks.Objects.Task;

public class AddTask extends JFrame implements ActionListener{
    
    private JPanel PTitle,PTaskName,PStart,PEnd,PRepeat,PType,PDis,PSave;
    private JLabel Name,Title,Dis,TStart,TEnd,TRepeat,Type,Number;
    private JTextField TaskName;
    private JTextArea TDis;
    private JComboBox CType; 
    private JButton Back,Save,Start,End,Repeat;
    private Box Small,Big;
    
    private Task task=new Task();
    private ArrayList<Task> Tasks=new ArrayList<>();
    private final Consts Const=new Consts();
    private int id=0;
    private final int Notification[]={0,0,0,0,0,0,0,0,0};
    
    public AddTask( Task task, ArrayList<Task> Tasks, int[]Notification){
        
        System.arraycopy( Notification, 0, this.Notification, 0, 9 );
        this.Tasks=Tasks;
        this.task=task;
        GetTaskNumber();
        CreateFrameAndPanels();
        CreateComponents();
        AddComponentsToFrame();
    }
    
    private void GetTaskNumber(){
            
        File file=new File( Const.File_Path() );
        try( Scanner out = new Scanner(file) ) {
            String Get="";
            while(out.hasNext()){
                Get+=out.nextLine();
            }
            int Count=Const.GetNumber(Get,"From AddTask In Line 65");
            Count++;
            task.Id=Count;
            id=Count;
        }
        catch(Exception E){
            Const.GetOut("From AddTask In Line 71");
        }
    }
    
    private void CreateFrameAndPanels() {
    
        this.setTitle("My Tasks");
        this.setSize( 350 , 410 );
        this.setResizable(false);
        this.setLocation( 520 , 175 );
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        PTitle=new JPanel();
        PTitle.setLayout(new BorderLayout());
        PTitle.setBorder(new EmptyBorder(5,5,5,5));
        
        PTaskName=new JPanel();
        PTaskName.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        PStart=new JPanel();
        PStart.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        PEnd=new JPanel();
        PEnd.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        PRepeat=new JPanel();
        
        PType=new JPanel();
        
        PDis=new JPanel();
        PDis.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        Small=Box.createVerticalBox();
        Small.setBorder(new EmptyBorder(5,5,5,5));
        
        PSave=new JPanel();
        
        Big=Box.createVerticalBox();
        Big.setBorder(new EmptyBorder(5,5,5,5));
    }   
    private void CreateComponents(){
        
        Back=new JButton();
        Back.setText("<<==");
        Back.addActionListener( this );
        
        Title=new JLabel();
        Title.setText("Add Task");
        Title.setHorizontalAlignment(JLabel.CENTER);
        
        Number=new JLabel();
        Number.setText("Task    "+id);
        Number.setForeground(Color.red);
        Number.setHorizontalAlignment(JLabel.LEFT);
        
        Name=new JLabel();
        Name.setText("Task Name : ");
        
        TaskName=new JTextField();
        TaskName.setColumns(17);
        TaskName.setBorder(new EmptyBorder(3,3,3,3));
        TaskName.setForeground(Color.red);
        TaskName.setText(task.Name);
        
        Start=new JButton();
        Start.setText("Start Date");
        Start.addActionListener( this );
        
        TStart=new JLabel();
        if (task.Start.Days==-1)
            TStart.setText("Start Date :   ");
        else
            TStart.setText("Start Date :   "+task.Start.Days+" / "
                    +task.Start.Month+"         "+task.Start.Hour+" : "+task.Start.Minute);
        
        End=new JButton();
        End.setText("End Date");
        End.addActionListener( this );
        
        TEnd=new JLabel();
        if (task.End.Days==-1)
            TEnd.setText("End Date :   ");
        else
            TEnd.setText("End Date :     "+task.End.Days+" / "
                    +task.End.Month+"        "+task.End.Hour+" : "+task.End.Minute);
        
        Repeat=new JButton();
        Repeat.setText("Repeat");
        Repeat.addActionListener( this );
        
        TRepeat=new JLabel();
        switch (task.Code) {
            case 1:
                TRepeat.setText("Whole Time");
                break;
            case 2:
                TRepeat.setText("Every Weak");
                break;
            case 3:
                TRepeat.setText("Every Month");
                break;
            default:
                TRepeat.setText("");
                break;
        }
        
        Type=new JLabel();
        Type.setText("Select Type : ");
        
        CType=new JComboBox();
        CType.setMaximumRowCount(5);
        CType.addItem("Task");
        CType.addItem("Goal");
        if (task.Type.length()==0)
            CType.setSelectedIndex(-1);
        else
            if (task.Type.charAt(0)=='T')
                CType.setSelectedIndex(0);
            else
                CType.setSelectedIndex(1);
        
        Dis=new JLabel();
        Dis.setText("Discreption :- ");
        
        TDis=new JTextArea();
        TDis.setColumns(28);
        TDis.setRows(5);
        TDis.setBorder(new EmptyBorder(3,3,3,3));
        TDis.setForeground(Color.red);
        TDis.setLineWrap(true);
        TDis.setText( task.Discreption );
        
        Save=new JButton();
        Save.setText("Add Task");
        Save.addActionListener( this );
    }
    private void AddComponentsToFrame(){
    
        PTitle.add(Back,BorderLayout.WEST);
        PTitle.add(Title,BorderLayout.CENTER);
        PTitle.add(Number,BorderLayout.EAST);
        
        PTaskName.add(Name);
        PTaskName.add(TaskName);
        
        PStart.add(Start);
        PStart.add(Box.createHorizontalStrut(10));
        PStart.add(TStart);
        
        PEnd.add(End);
        PEnd.add(Box.createHorizontalStrut(20));
        PEnd.add(TEnd);
        
        PRepeat.add(Repeat);
        PRepeat.add(TRepeat);
        
        PType.add(Type);
        PType.add(CType);
        
        PDis.add(Dis);
        PDis.add(TDis);
        
        Small.add(PTaskName);
        Small.add(Box.createVerticalStrut(3));
        Small.add(PStart);
        Small.add(Box.createVerticalStrut(3));
        Small.add(PEnd);
        Small.add(Box.createVerticalStrut(3));
        Small.add(PRepeat);
        Small.add(Box.createVerticalStrut(3));
        Small.add(PType);
        Small.add(Box.createVerticalStrut(3));
        Small.add(PDis);
        
        PSave.add(Save);
        
        Big.add(Small);
        Big.add(PSave);
        
        this.add(PTitle,BorderLayout.NORTH);
        this.add(Big,BorderLayout.CENTER);
    }

    // For Actions
    @Override
    public void actionPerformed(ActionEvent e) {
    
        switch (e.getActionCommand()) {
            case "<<==":
                this.dispose();
                new Main( Tasks, Notification);
                break;
            case "Start Date":
                SetTask();
                this.dispose();
                new SetDate( 1, task, Tasks, Notification);
                break;
            case "End Date":
                SetTask();
                this.dispose();
                new SetDate( 2, task, Tasks, Notification);
                break;
            case "Repeat":
                SetTask();
                this.dispose();
                new Repeats( task, Tasks, Notification);
                break;
            default:
                if (CheckData()){
                    GetTaskNumber();
                    CreatePathAndSaveFile();
                    ModifyingDataInFile();
                    SetData();
                }
                else
                    JOptionPane.showMessageDialog(null,"Please Enter Full Info");
                break;
        }
    }
    private boolean CheckData() {
        if ( TaskName.getText().length()==0)
            return false;
        if ( task.Start.Days==-1)
            return false;
        if ( task.End.Days==-1)
            return false;
        if ( task.Code==0)
            return false;
        return CType.getSelectedIndex() != -1;
    }
    private void CreatePathAndSaveFile(){
        
        task.Path+=task.Id+".txt";
        File file=new File(task.Path);
        try{    file.createNewFile();   }
        catch(Exception E){
            Const.GetOut("From AddTask In Line 309");
        }
        if ( SetTask() )
            Const.SaveTask( task , "From AddTask In Line 312" );
        else
            System.out.println("The Task Or Goal Will Never Work In This Date");
    }
    private boolean SetTask(){
        
        task.Name = TaskName.getText();
        if ( CType.getSelectedIndex()!=-1 )
            task.Type = CType.getSelectedItem().toString();
        task.Discreption = TDis.getText().replace('\n', '_');
        task.Situation = GetStatus();
        return task.Situation.length() != 0;
    }
    private String GetStatus() {
    
        switch (task.Code) {
            case 1:
                return "0/1";
            
            case 3:
                int number = 0;
                for ( int i=task.Start.Month; i<=task.End.Month; i++){
                    int start = 1, end = Const.GetDays( i );
                    if ( i==task.Start.Month )
                        start = task.Start.Days;
                    if ( i==task.End.Month )
                        end = task.End.Days;
                    
                    for ( int j=start; j<=end; j++)
                        for ( int k=0; k<task.repeat.size(); k++)
                            if ( j==Const.GetNumber( task.repeat.get(k), "From AddTask In Line 342") )
                                number++;
                }
                if ( number==0 )
                    return "";
                return ("0/"+number);
            
            default:
                Calendar First = Calendar.getInstance();
                First.set(2018, task.Start.Month, task.Start.Days);
                int Count = 0;
                
                for ( int i=0; i<task.repeat.size(); i++){
                    
                    int Day = Const.GetDayNumber( task.repeat.get(i) );
                    Calendar Second = Calendar.getInstance();
                    Second.set(2018, task.End.Month, task.End.Days);
                    
                    while ( First.before(Second) ){
                        if ( First.get(Calendar.DAY_OF_WEEK)==Day )
                            Count++;
                        First.add( Calendar.DAY_OF_MONTH, 1 );
                    }
                }
                if ( Count>0 )
                    return "0/"+Count;
                return "";
        }
    }
    private void ModifyingDataInFile() {

        try{
            File file=new File( Const.File_Path() );
            file.delete();
            file.createNewFile();
            try ( PrintWriter in = new PrintWriter(file) ) {
                in.println(id);
                id++;
            }

            file=new File( Const.Available_File_Path() );
            try ( PrintWriter In = new PrintWriter( new FileOutputStream(file,true) ) ) {
                In.println(task.Path);
            }
        }
        catch(Exception E){
            Const.GetOut("From AddTask In Line 388");
        }
    }
    private void SetData(){

        if ( task.Code==1 )
            task.repeat.clear();
        Tasks.add( task );
        task=new Task();
        task.Id=id;

        Number.setText("Task    "+id);
        TaskName.setText("");
        TStart.setText("Start Date :   ");
        TEnd.setText("End Date :   ");
        TRepeat.setText("");
        CType.setSelectedIndex(-1);
        TDis.setText("");
    }
}