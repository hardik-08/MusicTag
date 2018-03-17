import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mpatric.mp3agic.NotSupportedException;


public class MainUI extends JFrame  
{
	
	JLabel picLabel;
	JLabel label[] = new JLabel[5];
	JTextField jt[] = new JTextField[5];
	JButton save,open;
	String filePath = "";
	MP3TagGetter mtg;
	MainUI mTemp;
	MainUI()
	{
		super("Mp3Tag");
		initGUI();
		mTemp = this;
	}
	
	public void initGUI()
	{
	
		Container c = getContentPane();
		setLayout(new GridLayout(7,2));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.CENTER;
		open = new JButton("Open File");
		open.addActionListener(new OpenAction(this));
		c.add(open);
		ImageIcon icon = new ImageIcon(".//resources//download.png");
		
		picLabel =  new JLabel("",icon,JLabel.CENTER);
		c.add(picLabel);
		for(int i=0;i<5;i++)
		{
				label[i] = new JLabel();
				jt[i] = new JTextField(20);
		}
		label[0].setText("Song Title");
		label[1].setText("Artist");
		label[2].setText("Album");
		label[3].setText("Year");
		label[4].setText("Genre");
		
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.anchor = GridBagConstraints.LINE_START;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		
		for(int i = 0; i<5; i++)
		{
			c.add(label[i]);
			label[i].setHorizontalAlignment(JLabel.CENTER);
			c.add(jt[i]);
		}
		save = new JButton("Save");
		save.addActionListener(new SaveAction(this));
		c.add(save);
		
		
	}
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		MainUI mu = new MainUI();
		mu.setSize(500,500);
		mu.pack();
		mu.setVisible(true);
	}
	void initTag()
	{
		mtg = new MP3TagGetter(mTemp);
	}
}

class OpenAction implements ActionListener
{
	MainUI mu;
	OpenAction(MainUI m)
	{
		mu = m;
	}
	public void actionPerformed(ActionEvent ae)
	{
		JFileChooser jc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "MP3 File", "mp3");
		    jc.setFileFilter(filter);
		    int returnVal = jc.showOpenDialog(mu);
		    if(returnVal == JFileChooser.APPROVE_OPTION)
		    {
		    	mu.filePath = jc.getSelectedFile().getAbsolutePath();
		    	System.out.println(mu.filePath);
		    	mu.initTag();
		    }
	}
}

class SaveAction implements ActionListener
{
	String savePath;
	MainUI mu;
	SaveAction(MainUI m)
	{
		mu = m;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		JFileChooser jc = new JFileChooser();
		int returnVal = jc.showSaveDialog(mu);
		if(returnVal == JFileChooser.APPROVE_OPTION&&mu.mtg.file!=null)
	    {
	    	savePath = jc.getSelectedFile().getAbsolutePath();
	    	System.out.println(mu.filePath);
	    	System.out.println(savePath);
	    	try 
	    	{
	    		mu.mtg.setData();
	    		if(mu.filePath.equals(savePath))
	    		{
	    			savePath = savePath+".temp";
	    			mu.mtg.file.save(savePath);
	    			File f = new File(mu.filePath);
	    			f.delete();
	    			File newer = new File(savePath);
	    			newer.renameTo(f);
	    			
	    			
	    		}
	    		else
	    			mu.mtg.file.save(savePath);
			} catch (NotSupportedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	    }
	}
}
