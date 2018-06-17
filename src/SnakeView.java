import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SnakeView implements Observer {
    SnakeControl control = null;
    SnakeModel model = null;

    JFrame mainFrame;
    Canvas paintCanvas;
    //JLabel labelScore;

    public static final int canvasWidth = 200;
    public static final int canvasHeight = 300;

    public static final int nodeWidth = 10;
    public static final int nodeHeight = 10;

    public SnakeView(SnakeModel model, SnakeControl control) {
        this.model = model;
        this.control = control;

        mainFrame = new JFrame("GreedSnake");

        Container cp = mainFrame.getContentPane();

        // ���������ķ�����ʾ
        //labelScore = new JLabel("Score:");
        //cp.add(labelScore, BorderLayout.NORTH);

        //�����߶���ʾ����ʾ����
       BallPane bp = new BallPane();
       bp.addKeyListener(control);
       cp.add(bp,BorderLayout.NORTH);
        
        // �����м����Ϸ��ʾ����
        paintCanvas = new Canvas();
        paintCanvas.setSize(canvasWidth + 1, canvasHeight + 1);
        paintCanvas.addKeyListener(control);
        cp.add(paintCanvas, BorderLayout.CENTER);

        // �������µİ�����
        JPanel panelButtom = new JPanel();
        panelButtom.setLayout(new BorderLayout());
        JLabel labelHelp;
        labelHelp = new JLabel("PageUp, PageDown for speed;", JLabel.CENTER);
        panelButtom.add(labelHelp, BorderLayout.NORTH);
        labelHelp = new JLabel("ENTER or R or S for start;", JLabel.CENTER);
        panelButtom.add(labelHelp, BorderLayout.CENTER);
        labelHelp = new JLabel("SPACE or P for pause", JLabel.CENTER);
        panelButtom.add(labelHelp, BorderLayout.SOUTH);
        cp.add(panelButtom, BorderLayout.SOUTH);

        mainFrame.addKeyListener(control);
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    void repaint() {
        Graphics g = paintCanvas.getGraphics();

        //draw background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        // draw the snake
        g.setColor(Color.BLACK);
        LinkedList na = model.nodeArray;
        Iterator it = na.iterator();
        while (it.hasNext()) {
            Node n = (Node) it.next();
            drawNode(g, n);
        }

        // draw the food
        g.setColor(Color.RED);
        Node n = model.food;
        drawNode(g, n);

       // updateScore();
    }

    private void drawNode(Graphics g, Node n) {
        g.fillRect(n.x * nodeWidth,
                n.y * nodeHeight,
                nodeWidth - 1,
                nodeHeight - 1);
    }

    /*public void updateScore() {
        String s = "Score: " + model.score;
        labelScore.setText(s);
    }*/

    public void update(Observable o, Object arg) {
        repaint();
    }
    
    public  class BallPane extends JPanel{
    	 int num = 0;
    	 int count = 0 ;
    	 int x1 = 0, x2 = (int) (Math.random() * 45), x3 = (int) (Math.random() * 35);
         int y1 = (int) (Math.random() * 5), y2 = (int) (Math.random() * 45+20), y3 = (int) (Math.random() * 35+20);
         //int temp = 0;
         int d=1;
         int A=0,B=0,C=0;
         int SW=0;
         Color[] x=new Color[2];
        

         
         void setxy(int x1,int x2,Graphics g){
             int [] t=new int[11];
             t[0]=0x3f;
             t[1]=0x06;
             t[2]=0x5b;
             t[3]=0x4f;
             t[4]=0x66;
             t[5]=0x6d;
             t[6]=0x7d;
             t[7]=0x27;
             t[8]=0x7f;
             t[9]=0x6f;
             
             g.setColor(x[t[x2]&0x01]); // a
             g.fillRect(460-x1, 30, 30, 10);
             g.setColor(x[(t[x2]>>1)&0x01]); // b
             g.fillRect(490-x1, 40, 10, 30);
             g.setColor(x[(t[x2]>>2)&0x01]); // c
             g.fillRect(490-x1, 80, 10, 30);            
             g.setColor(x[(t[x2]>>3)&0x01]); // d
             g.fillRect(460-x1, 110, 30, 10);
             g.setColor(x[(t[x2]>>4)&0x01]); // e
             g.fillRect(450-x1, 80, 10, 30);
             g.setColor(x[(t[x2]>>5)&0x01]); // f
             g.fillRect(450-x1, 40, 10, 30);
             g.setColor(x[(t[x2]>>6)&0x01]); // g
             g.fillRect(460-x1, 70, 30, 10);
             //g.setColor(x[0]);
             //g.fillOval(325, 160, 15, 15);
         }
         
         public BallPane() {
             x[0]=Color.WHITE;
             x[1]=Color.RED;
            // addKeyListener(this);
             setFocusable(true);
             setFocusTraversalKeysEnabled(false);
             repaint();
         }
         
         @Override
         public Dimension getPreferredSize() {
             return new Dimension(550, 150);
         }
         
         @Override
         protected void paintComponent(Graphics g) {
             super.paintComponent(g);        
             g.setColor(Color.BLACK);
             g.fillRect(0, 0, 280, 150);  
             //setxy(0,A%10,g);
             //setxy(70,(A/10)%10,g);
             //setxy(180,B%10,g);
             setxy(290,(model.score%10),g);
             setxy(360,(model.score/10)%10,g);
             setxy(430,model.score/100,g);
         }
         
        /* public void keyPressed(KeyEvent e){  
         	int c=e.getKeyCode();
         	if (c==KeyEvent.VK_LEFT) {
         		if(SW==0)
         			SW=1;
         		else
         			SW=0;
         	}
         }  
        @Override
 		public void keyReleased(KeyEvent e) {
 		}
         @Override
 		public void keyTyped(KeyEvent e) {
 		}
 		public void actionPerformed(ActionEvent arg0) {
 			if (SW==0)
 			    SW=1;
 		    else
 		    	SW=0;
 		}*/
    }
}
