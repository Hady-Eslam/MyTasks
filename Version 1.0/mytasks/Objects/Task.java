package mytasks.Objects;

import java.util.ArrayList;

public class Task {
    
    public String Name="",Discreption="",Path="E://My Programs//ProgramsFiles//MyTasks//"
            ,Type="",Situation="";
    public TaskDate Start=new TaskDate(),End=new TaskDate();
    public int Code=0,Id=0;
    public ArrayList<String> repeat=new ArrayList<>();
    
    @Override
    public String toString(){
        return Name;
    }
}