package mytasks;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import mytasks.Activities.AddTask;
import mytasks.Activities.Info_Notification;
import mytasks.Activities.Show_Task_Goal;
import mytasks.Objects.Consts;
import mytasks.Objects.Task;

public class Main extends JFrame implements ActionListener{
    
    private JPanel PTitle,PAdd,PShow_Task,PShow_Goal;
    private JLabel Title;
    private JButton Show_Task,Add,Info,Notifications,Show_Goal;
    private Box box;
    
    private ArrayList<Task> Tasks=new ArrayList<>();
    private final int Notification[]={0,0,0,0,0,0,0,0,0};
    private final Consts Const=new Consts();
    
    public Main(ArrayList<Task> Tasks,int[]Notification){
        
        System.arraycopy( Notification, 0, this.Notification, 0, 9 );
        this.Tasks=Tasks;
        SetFrameAndPanels();
        SetComponents();
        AddComponentsToPanals();
    }

    private void SetFrameAndPanels() {
    
        this.setTitle("My Tasks");
        this.setSize( 300 , 300 );
        this.setResizable(false);
        this.setLocation( 530 , 230 );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        
        PTitle=new JPanel();
        PTitle.setBorder(new EmptyBorder(5,5,5,5));
        PTitle.setLayout(new BorderLayout());
        
        PAdd=new JPanel();
        PAdd.setBorder(new EmptyBorder(10,10,10,10));
        
        PShow_Task=new JPanel();
        
        PShow_Goal=new JPanel();
        
        box=Box.createVerticalBox();
        box.setBorder(new EmptyBorder(5,5,5,5));
    }
    private void SetComponents() {
    
        Info=new JButton();
        Info.setText("Info");
        Info.addActionListener( this );
        
        Title=new JLabel();
        Title.setText( "Hello Hady" );
        Title.setHorizontalAlignment(JLabel.CENTER);
        
        Notifications=new JButton();
        if (Notification[8]!=0){
            Notifications.setText(""+Notification[8]);
            Notifications.setForeground(Color.red);
        }
        else
            Notifications.setText("0");
        Notifications.addActionListener( this );
        
        Add=new JButton();
        Add.setText("Add Task");
        Add.addActionListener( this );
        
        Show_Task=new JButton();
        Show_Task.setText("Show Tasks");
        Show_Task.addActionListener( this );
        
        Show_Goal=new JButton();
        Show_Goal.setText("Show Goal");
        Show_Goal.addActionListener( this );
    }
    private void AddComponentsToPanals() {
    
        PTitle.add(Info,BorderLayout.WEST);
        PTitle.add(Title,BorderLayout.CENTER);
        PTitle.add(Notifications,BorderLayout.EAST);
        
        PAdd.add(Add);
        
        PShow_Task.add(Show_Task);
        
        PShow_Goal.add(Show_Goal);
        
        box.add(PAdd);
        box.add(PShow_Task);
        box.add(PShow_Goal);
        
        this.add(PTitle,BorderLayout.NORTH);
        this.add(box,BorderLayout.CENTER);
    }

    // For Actions
    @Override
    public void actionPerformed(ActionEvent e) {
    
        this.dispose();
        switch (e.getActionCommand()) {
            case "Info":
                new Info_Notification( Tasks, Notification, "Information", "<html><head><style type=\"text/css\">\n"+
                        "span{color: green;}</style></head><body>\n" +
                        "		<br><br><p style=\"margin-left: 41px\">Author &nbsp;: &nbsp;"+
                        "<span>Hady Eslam</span></p>\n" +
                        "		<br><p style=\"margin-left: 12px\">Project Name &nbsp;: &nbsp;"+
                        "<span>My Tasks And Goals</span></p>\n" +
                        "		<br><p >Version Number &nbsp;: &nbsp;<span>1 . 0</span></p>\n" +
                        "		<br><p style=\"margin-left:5px\">Date Of Project &nbsp;: &nbsp;"+
                        "<span>11 / 04 / 2018</span></p>\n" +
                        "</body></html>");
                break;
            case "Add Task":
                new AddTask( new Task(), Tasks, Notification);
                break;
            case "Show Tasks":
                new Show_Task_Goal( Tasks, Notification, "Task");
                break;
            case "Show Goal":
                new Show_Task_Goal( Tasks, Notification, "Goal");
                break;
            default:
                Notification[8]=0;
                new Info_Notification( Tasks, Notification, "Notification", GetText());
                break;
        }
    }
    public String GetText(){
        String Get="<html>\n"+"<head>\n"+"<style type=\"text/css\">\n"+"span{  color: #0511FB;  }\n"+
                   "li{\n"+"color: green;\n"+"font-weight: bold;\n"+"list-style-type: none;\n"+"}\n"+"</style>\n" +
                   "</head>\n"+"<body>\n"+
                   "<p style=\"color: red\">Notification Of Today &nbsp;&nbsp; :- "
                        + "&nbsp;&nbsp;<span>"+Const.Day()+" / "+Const.Month()+" / 2018</span></p>\n"+
                   "<ol>\n";

        if ( Notification[0]!=0 )
            Get+="<li><span>"+Notification[0]+"</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Begining Tasks</li><br>\n";
        if (Notification[1]!=0)
            Get+="<li><span>"+Notification[1]+"</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Continued Tasks</li><br>\n";
        if (Notification[2]!=0)
            Get+="<li><span>"+Notification[2]+"</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Suspended Tasks</li><br>\n";
        if (Notification[3]!=0)
            Get+="<li><span>"+Notification[3]+"</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
                    + "<span style=\"color:red\"> Not Completed </span> Ended Tasks</li><br>\n";
        if (Notification[4]!=0)
            Get+="<li><span>"+Notification[4]+"</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
                    + "<span style=\"color:#0DC283\"> Successful </span> Ended Tasks</li><br>\n";
        if (Notification[5]!=0)
            Get+="<li><span>"+Notification[5]+"</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Begining Goals</li><br>\n";
        if (Notification[6]!=0)
            Get+="<li><span>"+Notification[6]+"</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
                    + "<span style=\"color:red\"> Not Completed </span> Ended Goals</li><br>\n";
        if (Notification[7]!=0)
            Get+="<li><span>"+Notification[7]+"</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
                    + "<span style=\"color:#0DC283\"> Successful </span> Ended Goals</li><br>\n";

        return ( Get+"</ol>\n"+"</body>\n"+"</html>" );
    }
}