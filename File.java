import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class File {
	static JFrame dimensions=new JFrame("Set Dimensions");
	public static void main(String[] args) {
		dimensions=new JFrame("Set Dimensions");
    	dimensions.getContentPane().add(new StyleOptionsPanel());
    	dimensions.pack();
    	dimensions.setVisible(true);
    	
	}
	

}
class StyleOptionsPanel extends JPanel{
	private JLabel saying;
	private JLabel saying2;
	public static JButton b1;
//added these fields from Maze class to this class to open/close JFrames.
	public static JFrame dimensions=File.dimensions;
	public static JFrame window=Maze.window;
	public static Thread t=Maze.t;
	public static int temp1=Maze.temp1;
	public static int temp2=Maze.temp2;
	public static int b=Maze.b;
	public static int c=Maze.c;
	Maze gg=new Maze();
	public StyleOptionsPanel(){
		saying=new JLabel("Rows");
		saying.setFont(new Font("Helvetica", Font.PLAIN, 36));
		//Below code: when click the JButton, the first JFrame (dimensions) will close and sets the second JFrame (window) visible
		b1 = new JButton("Enter");
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(e.getSource()==b1){
				//instead of *dimensions.dispose()*, could use dimensions.setVisible(false);
					dimensions.setVisible(false);
					window.setVisible(true);
					gg.Thread();
					
				}
			}
		});
		saying2=new JLabel();
		saying2.setText(String.valueOf(b));
		saying2.setFont(new Font("Helvetica", Font.PLAIN, 20));
		//add(field);
		JLabel saying3=new JLabel();
		saying3.setText(String.valueOf(c));
		saying3.setFont(new Font("Helvetica", Font.PLAIN, 20));
		
		JLabel saying4=new JLabel("Columns");
		saying4.setFont(new Font("Helvetica", Font.PLAIN, 36));
		
		JLabel saying5=new JLabel("You Inputed: ");
		saying5.setFont(new Font("Helvetica", Font.PLAIN, 40));
	
		
		add(saying5);
	
		add(saying2);
		add(saying);
	
		add(saying3);
		add(saying4);
		add(b1);
		
		setBackground(Color.cyan);
		setPreferredSize(new Dimension(1000, 200));
		
	}

}