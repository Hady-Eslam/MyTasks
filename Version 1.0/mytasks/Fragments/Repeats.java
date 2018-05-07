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
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import mytasks.Activities.AddTask;
import mytasks.Objects.Task;

public class Repeats extends JFrame implements ActionListener,ItemListener{
    
    private JPanel PTitle,PRepeat,PDays,PShow,PSet;
    private JButton Back,Set,Add;
    private JLabel Title,Repeat,Days;
    private JTextArea Show;
    private JComboBox CRepeat,CDays;
    private Box Small,box;
    
    private final Consts Const=new Consts();
    private Task task=new Task();
    private ArrayList<Task> Tasks=new ArrayList<>();
    private final int Notification[]={0,0,0,0,0,0,0,0,0};
    
    public Repeats(Task task,ArrayList<Task> Tasks,int[]Notification){
        
        System.arraycopy( Notification, 0, this.Notification, 0, 9 );
        this.Tasks=Tasks;
        this.task=task;
        SetFrameAndPanels();
        SetComponents();
        AddComponentsToPanels();
    }
    
    private void SetFrameAndPanels() {
    
        this.setTitle("My Tasks");
        this.setSize( 310 , 350 );
        this.setResizable(false);
        this.setLocation( 560 , 230 );
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        PTitle=new JPanel();
        PTitle.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        PRepeat=new JPanel();
        PRepeat.setLayout(new FlowLayout(FlowLayout.LEFT));
        PRepeat.setBorder(new EmptyBorder(5,5,5,5));
        
        PDays=new JPanel();
        PDays.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        PShow=new JPanel();
        
        Small=Box.createVerticalBox();
        Small.setBorder(new EmptyBorder(20,20,20,20));
        
        PSet=new JPanel();
        PSet.setBorder(new EmptyBorder(15,15,15,15));
        
        box=Box.createVerticalBox();
        box.setBorder(new EmptyBorder(5,5,5,5));
    }
    private void SetComponents() {
    
        Back=new JButton();
        Back.setText("<<==");
        Back.addActionListener( this );
        
        Title=new JLabel();
        Title.setText("Repeats");
        Title.setHorizontalAlignment(JLabel.CENTER);
        
        Repeat=new JLabel();
        Repeat.setText("Repeat : ");
        
        CRepeat=new JComboBox();
        CRepeat.addItem("Whole Time");
        CRepeat.addItem("Every Weak");
        CRepeat.addItem("Every Month");
        CRepeat.setSelectedIndex(0);
        CRepeat.addItemListener( this );
        
        Days=new JLabel();
        Days.setText("Days : ");
        
        CDays=new JComboBox();
        CDays.setMaximumRowCount(7);
        SetDays(1);
        
        Add=new JButton();
        Add.setText("Add");
        Add.addActionListener( this );
        
        Show=new JTextArea();
        Show.setText("");
        Show.setColumns(20);
        Show.setRows(5);
        Show.setLineWrap(true);
        Show.setEditable(false);
        Show.setBorder(new EmptyBorder(3,3,3,3));
        Show.setForeground(Color.red);
        Show.setBackground(this.getBackground());
        
        Set=new JButton();
        Set.setText("Set");
        Set.addActionListener( this );
    }
    private void AddComponentsToPanels() {
    
        PTitle.add(Back);
        PTitle.add(Box.createHorizontalStrut(50));
        PTitle.add(Title);
        
        PRepeat.add(Repeat);
        PRepeat.add(Box.createHorizontalStrut(5));
        PRepeat.add(CRepeat);
        
        PDays.add(Days);
        PDays.add(Box.createHorizontalStrut(10));
        PDays.add(CDays);
        PDays.add(Box.createHorizontalStrut(10));
        PDays.add(Add);
        
        PShow.add(Show);
        
        Small.add(PDays);
        Small.add(PShow);
        
        box.add(PRepeat);
        box.add(Small);
        
        PSet.add(Set);
        
        this.add(PTitle,BorderLayout.NORTH);
        this.add(box,BorderLayout.CENTER);
        this.add(PSet,BorderLayout.SOUTH);
    }
    
    private void SetDays(int set){
        switch (set) {
            case 1:
                CDays.setEnabled(false);
                break;
            case 2:
                CDays.setEnabled(true);
                CDays.removeAllItems();
                for (int i=0;i<7;i++)
                    CDays.addItem( Const.GetDayName(i) );
                break;
            default:
                CDays.setEnabled(true);
                CDays.removeAllItems();
                Const.SetDays( 1, CDays);
                break;
        }
    }

    // For Actions
    @Override
    public void actionPerformed(ActionEvent e) {
    
        if ( e.getActionCommand().equals("<<==") ){
            this.dispose();
            new AddTask( task, Tasks, Notification);
        }
        else if ( e.getActionCommand().equals("Add")&&!CRepeat.getSelectedItem().toString().equals("Whole Time") )
            Show.setText( Show.getText()+"  "+CDays.getSelectedItem().toString() );
        else if ( e.getActionCommand().equals("Set") )
            SetAction();
    }
    private void SetAction() {
    
        if ( CRepeat.getSelectedIndex()!=0 && Show.getText().length()==0 ){
            JOptionPane.showMessageDialog(null,"Please Enter Full Time");
            return ;
        }
        task.repeat.clear();
        if ( CRepeat.getSelectedIndex()==0 ){
            task.Code=1;
            task.repeat.add("0");
        }
        else{ 
            if ( CRepeat.getSelectedIndex()==1 )
                task.Code=2;
            else
                task.Code=3;
            Const.SetRepeats( Show.getText(), "From Repeats in Line 203", task );
        }
        this.dispose();
        new AddTask( task, Tasks, Notification);
    }
    
    // For Displaying
    @Override
    public void itemStateChanged(ItemEvent e) {
        switch (CRepeat.getSelectedIndex()) {
            case 0:
                SetDays(1);
                break;
            case 1:
                SetDays(2);
                break;
            default:
                SetDays(3);
                break;
        }
        Show.setText("");
    }
}