import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



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
	private JTextField field;
	private JLabel saying;
	public static JButton b1;
	public static int dimension;
	
//added these fields from Maze class to this class to open/close JFrames.
	public static JFrame dimensions=File.dimensions;
	public static JFrame window=Maze.window;
	
	public StyleOptionsPanel(){
		
		StyleListener listener1=new StyleListener();
		field= new JTextField(); 
		field.setPreferredSize(new Dimension(50,50));
		field.addActionListener(listener1);
		saying=new JLabel("Enter Rows");
		saying.setFont(new Font("Helvetica", Font.PLAIN, 36));
		
		add(field);
		add(saying);	
	
		StyleListener2 listener2=new StyleListener2();
		field=new JTextField();
		field.setPreferredSize(new Dimension(50,50));
		field.addActionListener(listener2);
		saying=new JLabel("Enter Columns");
		saying.setFont(new Font("Helvetica", Font.PLAIN, 36));
		
		add(field);
		add(saying);
		
		//Below code: when click the JButton, the first JFrame (dimensions) will close and sets the second JFrame (window) visible
		b1 = new JButton("Enter");
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource()==b1){
				//instead of *dimensions.dispose()*, could use dimensions.setVisible(false);
					dimensions.setVisible(false);
					window.setVisible(true);
					window.setEnabled(true);
					
				}
			}
		});
		add(b1);
		
		setBackground(Color.cyan);
		setPreferredSize(new Dimension(350, 100));
		
	}
class StyleListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
		//dimension is the integer that user inputed from the textfield in first JFrame (dimensions).
			dimension=Integer.parseInt(field.getText());
			
		}
}
private class StyleListener2 implements ActionListener{
	public void actionPerformed(ActionEvent event){
		dimension= Integer.parseInt(field.getText());
		
	}
}
}