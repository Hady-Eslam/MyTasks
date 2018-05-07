package mytasks.Fragments;

import mytasks.Objects.Consts;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import mytasks.Activities.AddTask;
import mytasks.Objects.Task;
import mytasks.Objects.TaskDate;

public class SetDate extends JFrame implements ActionListener,ItemListener{
    
    private JPanel PTitle,PDay_Month,PHour_Minute,PSet;
    private JLabel Title,Day,Month,Hour,Minute;
    private JComboBox CDay,CMonth,CHour,CMinute;
    private JButton Set,Back;
    private Box Small,box;
    
    private ArrayList<Task> Tasks=new ArrayList<>();
    private final Consts Const=new Consts();
    private int Frame=1;
    private final int Notification[]={0,0,0,0,0,0,0,0,0};
    private Task task=new Task();
    
    public SetDate( int Frame, Task task, ArrayList<Task> Tasks, int[]Notification){
        
        System.arraycopy( Notification, 0, this.Notification, 0, 9 );
        this.Tasks=Tasks;
        this.Frame=Frame;
        this.task=task;
        SetFrameAndPanels();
        SetComponents();
        AddComponentsToPanels();
    }

    private void SetFrameAndPanels() {
    
        this.setTitle("My Tasks");
        this.setSize( 300 , 300 );
        this.setResizable(false);
        this.setLocation( 560 , 230 );
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        PTitle=new JPanel();
        PTitle.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        PDay_Month=new JPanel();
        PDay_Month.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        PHour_Minute=new JPanel();
        PHour_Minute.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        Small=Box.createVerticalBox();
        Small.setBorder(new EmptyBorder(5,5,5,5));
        
        PSet=new JPanel();
        
        box=Box.createVerticalBox();
        box.setBorder(new EmptyBorder(5,5,5,5));
    }
    private void SetComponents() {
    
        Back=new JButton();
        Back.setText("<<==");
        Back.addActionListener( this );
        
        Title=new JLabel();
        if (Frame==1)
            Title.setText("Set Start Date");
        else
            Title.setText("Set End Date");
        Title.setForeground(Color.BLUE);
        
        Day=new JLabel();
        Day.setText("Day : ");
        
        CDay=new JComboBox();
        CDay.setMaximumRowCount(3);
        Const.SetDays(1, CDay);
        
        Month=new JLabel();
        Month.setText("Month : ");
        
        CMonth=new JComboBox();
        CMonth.setMaximumRowCount(3);
        for (int i=1;i<13;i++)
            CMonth.addItem(i);
        CMonth.addItemListener( this );
        
        Hour=new JLabel();
        Hour.setText("Hour : ");
        
        CHour=new JComboBox();
        CHour.setMaximumRowCount(3);
        for (int i=0;i<24;i++)
            CHour.addItem(i);
        
        Minute=new JLabel();
        Minute.setText("Minute : ");
        
        CMinute=new JComboBox();
        CMinute.setMaximumRowCount(3);
        for (int i=0;i<60;i++)
            CMinute.addItem(i);
        
        Set=new JButton();
        Set.setText("Set");
        Set.addActionListener( this );
    }
    private void AddComponentsToPanels() {
    
        PTitle.add(Back);
        PTitle.add(Box.createHorizontalStrut(40));
        PTitle.add(Title);
        
        PDay_Month.add(Month);
        PDay_Month.add(Box.createHorizontalStrut(5));
        PDay_Month.add(CMonth);
        PDay_Month.add(Box.createHorizontalStrut(15));
        PDay_Month.add(Day);
        PDay_Month.add(Box.createHorizontalStrut(10));
        PDay_Month.add(CDay);
        
        PHour_Minute.add(Hour);
        PHour_Minute.add(Box.createHorizontalStrut(13));
        PHour_Minute.add(CHour);
        PHour_Minute.add(Box.createHorizontalStrut(15));
        PHour_Minute.add(Minute);
        PHour_Minute.add(CMinute);
        
        Small.add(PDay_Month);
        Small.add(PHour_Minute);
        
        PSet.add(Set);
        
        box.add(Small);
        box.add(PSet);
        
        this.add(PTitle,BorderLayout.NORTH);
        this.add(box,BorderLayout.CENTER);
    }

    // For Actions
    @Override
    public void actionPerformed(ActionEvent e) {
    
        if ( e.getActionCommand().equals("<<==") ){
            this.dispose();
            new AddTask( task, Tasks, Notification);
        }
        else{
            if ( GetDateAndCheck() )
                Prossecc();
            else
                JOptionPane.showMessageDialog( null, "Invalid Date !!!");
        }
    }
    private boolean GetDateAndCheck() {
            
        TaskDate date=new TaskDate();
        date.Days=Const.GetNumber( CDay.getSelectedItem().toString(),"From SetDate In Line 176" );
        date.Month=Const.GetNumber( CMonth.getSelectedItem().toString(),"From SetDate In Line 177" );
        return ( !Const.IsOnWork( date ) );
    }
    private void Prossecc() {

        if ( Frame==1 ){
            task.Start.Days=Const.GetNumber( CDay.getSelectedItem().toString(),"From SetDate In Line 183" );
            task.Start.Month=Const.GetNumber( CMonth.getSelectedItem().toString(),"From SetDate In Line 184" );
            task.Start.Hour=Const.GetNumber( CHour.getSelectedItem().toString(),"From SetDate In Line 185" );
            task.Start.Minute=Const.GetNumber( CMinute.getSelectedItem().toString(),"From SetDate In Line 186" );
        }
        else{
            task.End.Days=Const.GetNumber( CDay.getSelectedItem().toString(),"From SetDate In Line 189" );
            task.End.Month=Const.GetNumber( CMonth.getSelectedItem().toString(),"From SetDate In Line 190" );
            task.End.Hour=Const.GetNumber( CHour.getSelectedItem().toString(),"From SetDate In Line 191" );
            task.End.Minute=Const.GetNumber( CMinute.getSelectedItem().toString(),"From SetDate In Line 192" );
        }
        this.dispose();
        new AddTask( task, Tasks, Notification);
    }

    // For Displaying
    @Override
    public void itemStateChanged(ItemEvent e) {
    
        CDay.removeAllItems();
        Const.SetDays( Const.GetNumber( CMonth.getSelectedItem().toString(), "From SetDate In Line 203"), CDay );
    }
}