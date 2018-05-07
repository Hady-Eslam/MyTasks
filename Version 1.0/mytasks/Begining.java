package mytasks;

import java.awt.EventQueue;

public class Begining {

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            new Preparation();
        });
    }
}