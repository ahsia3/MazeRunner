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

	public static void main(String[] args) {
		JFrame dimensions=new JFrame("Set Dimensions");
    	dimensions.getContentPane().add(new StyleOptionsPanel());
    	dimensions.pack();
    	dimensions.setVisible(true);
    	dimensions.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

	}

}
class StyleOptionsPanel extends JPanel{
	private JTextField field;
	private JLabel saying;
	private JButton push;
	private int count;
	private JLabel label;
	public StyleOptionsPanel(){
		StyleListener listener1=new StyleListener();
		field= new JTextField(); 
		field.setPreferredSize(new Dimension(50,50));
		field.addActionListener(listener1);
		saying=new JLabel("Enter Rows");
		saying.setFont(new Font("Helvetica", Font.PLAIN, 36));
		add(field);
		add(saying);	
	
		
		field=new JTextField();
		field.setPreferredSize(new Dimension(50,50));
		field.addActionListener(listener1);
		saying=new JLabel("Enter Columns");
		saying.setFont(new Font("Helvetica", Font.PLAIN, 36));
		add(field);
		add(saying);
		
		push = new JButton("Enter");
		add(push);
		setBackground(Color.cyan);
		setPreferredSize(new Dimension(300, 100));
		
	}
private class StyleListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			int font;
			String text = field.getText();
			font = Integer.parseInt(text);
			saying.setFont(new Font("Helvetica", Font.PLAIN, font));
		}
}

}