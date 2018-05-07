package mytasks.Activities;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import mytasks.Main;
import mytasks.Objects.Task;

public class Info_Notification extends JFrame implements ActionListener{
    
    private JPanel PTitle,PShow;
    private JLabel Title,Show;
    private JButton Back;
    
    private ArrayList<Task> Tasks=new ArrayList<>();
    private final int Notification[]={0,0,0,0,0,0,0,0,0};
    private String The_Title="",Text="";
    
    public Info_Notification( ArrayList<Task> Tasks, int[]Notification
            , String The_Title, String Text){
        
        System.arraycopy( Notification, 0, this.Notification, 0, 9);
        this.The_Title=The_Title;
        this.Text=Text;
        this.Tasks=Tasks;
        SetFrameAndPanels();
        SetComponentsAndAddToFram();
    }

    private void SetFrameAndPanels() {
    
        this.setTitle("My Tasks");
        this.setSize( 300 , 370 );
        this.setResizable(false);
        this.setLocation( 530 , 230 );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        
        PTitle=new JPanel();
        PTitle.setLayout(new BorderLayout());
        PTitle.setBorder(new EmptyBorder(5,5,5,5));
        
        PShow=new JPanel();
        PShow.setBorder(new EmptyBorder(10,10,10,10));
        if (The_Title.equals("Notification"))
            PShow.setLayout(new FlowLayout(FlowLayout.LEFT));
    }
    private void SetComponentsAndAddToFram() {
    
        Back=new JButton();
        Back.setText("<<==");
        Back.addActionListener( this );
        
        Title=new JLabel();
        Title.setText(The_Title);
        Title.setHorizontalAlignment(JLabel.CENTER);
        
        Show=new JLabel();
        Show.setText(Text);
        
        PTitle.add(Back,BorderLayout.WEST);
        PTitle.add(Title,BorderLayout.CENTER);
        
        PShow.add(Show);
        
        this.add(PTitle,BorderLayout.NORTH);
        this.add(PShow,BorderLayout.CENTER);
    }

    // For Back Action
    @Override
    public void actionPerformed(ActionEvent e) {
    
        this.dispose();
        new Main( Tasks, Notification);
    }
}