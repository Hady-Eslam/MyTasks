package mytasks.Activities;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import mytasks.Main;
import mytasks.Objects.Consts;
import mytasks.Objects.Task;

public class Show_Task_Goal extends JFrame implements ActionListener,ItemListener{
    
    private JPanel PTitle,PType,PBody;
    private JLabel Title,Type,TaskName,Name,Status,Start,End,Repeats,Dis,id;
    private JButton Back,GO;
    private JComboBox CType,CTask;
    private JCheckBox Progress;
    private Box Small;
    
    private final Consts Const=new Consts();
    private ArrayList<Task> Tasks=new ArrayList<>();
    private final int Notification[]={0,0,0,0,0,0,0,0,0};
    private String The_Title="";
    
    public Show_Task_Goal( ArrayList<Task> Tasks, int[]Notification, String The_Title){
        
        System.arraycopy( Notification, 0, this.Notification, 0, 9 );
        this.Tasks=Tasks;
        this.The_Title=The_Title;
        SetFrameAndPanels();
        SetComponents();
        AddComponentsToPanels();
    }

    private void SetFrameAndPanels() {
    
        this.setTitle("My Tasks");
        this.setSize( 400 , 450 );
        this.setResizable(false);
        this.setLocation( 490 , 170 );
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        PTitle=new JPanel();
        PTitle.setLayout(new BorderLayout());
        PTitle.setBorder(new EmptyBorder(5,5,5,5));
        
        PType=new JPanel();
        PType.setBorder(new EmptyBorder(5,5,5,5));
        PType.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        Small=Box.createVerticalBox();
        Small.setBorder(new EmptyBorder(10,10,10,10));
        
        PBody=new JPanel();
        PBody.setLayout(new BorderLayout());
    }
    private void SetComponents() {
    
        Back=new JButton();
        Back.setText("<<==");
        Back.addActionListener( this );
        
        Title=new JLabel();
        Title.setText("Show "+The_Title);
        Title.setHorizontalAlignment(JLabel.CENTER);
        
        id=new JLabel();
        id.setText("0");
        
        Type=new JLabel();
        Type.setText("Type : ");
        
        CType=new JComboBox();
        CType.setMaximumRowCount(4);
        CType.setPrototypeDisplayValue("Hello World");
        CType.addItem("All");
        CType.addItem("Yet Started");
        CType.addItem("Current");
        CType.addItem("Today");
        CType.addItem("Month "+The_Title);
        CType.addItem("Next Month "+The_Title);
        CType.addItemListener( this );
        
        TaskName=new JLabel();
        TaskName.setText(The_Title+" Name : ");
        
        CTask=new JComboBox();
        CTask.setMaximumRowCount(4);
        CTask.setPrototypeDisplayValue("Hello World");
        
        GO=new JButton();
        GO.setText("GO");
        GO.addActionListener( this );
        
        Name=new JLabel();
        Name.setText("Name   :- ");
        
        Status=new JLabel();
        Status.setText("Status  :- ");
        
        Progress=new JCheckBox();
        Progress.setText("The Progress :- ");
        Progress.setSelected(false);
        Progress.addActionListener( this );
        
        Start=new JLabel();
        Start.setText("Start Date   :- ");
        
        End=new JLabel();
        End.setText("End Date   :- ");
        
        Repeats=new JLabel();
        Repeats.setText("Repeats   :- ");
        
        Dis=new JLabel();
        Dis.setText("Discreption   :- ");
    }
    private void AddComponentsToPanels() {
    
        PTitle.add(Back,BorderLayout.WEST);
        PTitle.add(Title,BorderLayout.CENTER);
        PTitle.add(id,BorderLayout.EAST);
        
        PType.add(Type);
        PType.add(CType);
        PType.add(Box.createHorizontalStrut(5));
        PType.add(TaskName);
        PType.add(CTask);
        PType.add(GO);
        
        Small.add(Name);
        Small.add(Box.createVerticalStrut(25));
        Small.add(Status);
        Small.add(Box.createVerticalStrut(25));
        Small.add(Progress);
        Small.add(Box.createVerticalStrut(25));
        Small.add(Start);
        Small.add(Box.createVerticalStrut(25));
        Small.add(End);
        Small.add(Box.createVerticalStrut(25));
        Small.add(Repeats);
        Small.add(Box.createVerticalStrut(25));
        Small.add(Dis);
        
        PBody.add(PType,BorderLayout.NORTH);
        PBody.add(Small,BorderLayout.CENTER);
        
        this.add(PTitle,BorderLayout.NORTH);
        this.add(PBody,BorderLayout.CENTER);
    }
    
    private void SetStatus(int Code){
        Status.setText("<html><body><p>Status &nbsp;:- &nbsp&nbsp;<span style=\"color:");
        switch (Code) {
            case 1:
                Status.setText( Status.getText()+"red\">Not Started" );
                break;
            case 2:
                Status.setText( Status.getText()+"#15B90A\">Continued" );
                break;
            default:
                Status.setText( Status.getText()+"#CFC90D\">Suspended" );
                break;
        }
        Status.setText( Status.getText()+"</span></p></body></html>" );
    }
    private void SetProgress(String Text){
        Progress.setText( "<html><body><p>The Progress &nbsp;:- &nbsp&nbsp;<span style=\"color:"
                            +Text+"</span></p></body></html>" );
    }

    // For Actions
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "<<==":
                this.dispose();
                new Main( Tasks, Notification);
                break;
            case "GO":
                GOAction();
                break;
            default:
                CheckAction();
                break;
        }
    }
    private void GOAction() {
    
        if (CTask.getSelectedIndex()==-1)
            return ;
        
        Task task=(Task)CTask.getSelectedItem();
        id.setText(""+task.Id);
        Name.setText("Name :- "+task.Name);
        Start.setText("Start Date :- "+task.Start.Days+"/"+task.Start.Month+"   "+task.Start.Hour+":"+task.Start.Minute);
        End.setText("End Date :- "+task.End.Days+"/"+task.End.Month+"   "+task.End.Hour+":"+task.End.Minute);
        Dis.setText("Discreption :- "+task.Discreption);
        Repeats.setText("Repeats :- ");
        if (!task.repeat.isEmpty())
            for (int i=0;i<task.repeat.size();i++)
                Repeats.setText( Repeats.getText()+task.repeat.get(i)+" " );
        else
            Repeats.setText( Repeats.getText()+" The Whole Time");

        if ( !Const.IsOnWork( task.Start ) ){
            SetStatus(1);
            SetProgress("red\">"+task.Situation+" Task Of Today NotCompleted");
            Progress.setSelected(false);
            Progress.setEnabled(false);
        }
        else {
            if ( Const.GetRepeats( task.Code , task.repeat,"From Show_Task_Goal In Line 226") )
                SetStatus(2);
            else
                SetStatus(3);

            Progress.setEnabled(true);
            if ( Const.CompareTaskCount(task.Situation) ){
                SetProgress("#0E8706\">"+task.Situation+" Task Of Today Completed");
                Progress.setSelected(true);
            }
            else{
                SetProgress("red\">"+task.Situation+" Task Of Today NotCompleted");
                Progress.setSelected(false);
            }
        }
    }
    private void CheckAction() {
    
        if ( CTask.getSelectedIndex()==-1 )
            return ;

        Task task=(Task)CTask.getSelectedItem();
        if ( Progress.isSelected() ){
            String set = GetNumber(task.Situation,true);
            SetProgress("#0E8706\">"+set+" Task Of Today Completed");
            task.Situation = set;
        }
        else{
            String set = GetNumber(task.Situation, false);
            SetProgress("red\">"+set+" Task Of Today NotCompleted");
            task.Situation = set;
        }
        Const.SaveTask(task, "From Show_Task_Goal In Line 258");
    }
    private String GetNumber(String Get, boolean Status) {
    
        int Number = Const.GetNumber(Get.substring(0, Get.indexOf('/')), "From Show_Task_Goal In Line 262");
        if ( Status )
            Number++;
        else Number--;
        return (Number+Get.substring(Get.indexOf('/')));
    }
    
    
    // For Displaying
    @Override
    public void itemStateChanged(ItemEvent e) {
    
        CTask.removeAllItems();
        int indix=CType.getSelectedIndex();
        for (int i=0;i<Tasks.size();i++){
            if ( CheckType(i) &&
                    (  indix==0
                    || indix==1 && !Const.IsOnWork( Tasks.get(i).Start )
                    || indix==2 && Const.IsOnWork( Tasks.get(i).Start )
                    || indix==3 && Const.IsOnWork( Tasks.get(i).Start ) 
                            && (Const.GetRepeats(Tasks.get(i).Code,Tasks.get(i).repeat,"From Show_Task_Goal In Line 282") )
                    || indix==4 && Tasks.get(i).End.Month>=Const.Month()
                    || indix==5 && Tasks.get(i).End.Month>Const.Month() ) 
                )
                CTask.addItem( Tasks.get(i) );
        }
    }
    private boolean CheckType(int i){
        return Tasks.get(i).Type.equals( The_Title );
    }
}