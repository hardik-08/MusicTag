import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class MP3TagGetter 
{
	MainUI mu;
	Mp3File file;
	MP3TagGetter(MainUI m)
	{
		mu = m;
		getData();
	}
	void getData()
	{
			try 
			{
				file = new Mp3File(mu.filePath);
			} 
			catch (UnsupportedTagException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(file.hasId3v1Tag())
			{	
				ID3v1 id3v1Tag = file.getId3v1Tag();
	    		 mu.jt[0].setText(id3v1Tag.getTitle());
	    		 mu.jt[1].setText(id3v1Tag.getArtist());
	     		 mu.jt[2].setText(id3v1Tag.getAlbum());
	    		 mu.jt[3].setText(id3v1Tag.getYear());
	    		 mu.jt[4].setText(Integer.toString(id3v1Tag.getGenre())); //+ " (" + id3v1Tag.getGenreDescription() + ")");
			}
			if(file.hasId3v2Tag())
			{
				ID3v2 id3v2Tag = file.getId3v2Tag();
				byte[] albumImageData = id3v2Tag.getAlbumImage();
				if(albumImageData!=null)
				{
					Image img;
					try {
						img = ImageIO.read(new ByteArrayInputStream(albumImageData));
						
						ImageIcon imgIcon = new ImageIcon(img);
						
						if(imgIcon!=null){
							System.out.println("Not NULL");
							imgIcon.getImage().getScaledInstance(25, 20, Image.SCALE_DEFAULT);
							mu.picLabel.setIcon( new ImageIcon(imgIcon.getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT)));
							
						}
						//mu.picLabel.setIcon(imgIcon);
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			}
	}
	void setData()
	{
		
		 ID3v1 id3v1Tag = file.getId3v1Tag();
		 id3v1Tag.setTitle(mu.jt[0].getText());
		 id3v1Tag.setArtist(mu.jt[1].getText());
		 id3v1Tag.setAlbum(mu.jt[2].getText());
		 id3v1Tag.setYear(mu.jt[3].getText());
		 id3v1Tag.setGenre(Integer.parseInt(mu.jt[4].getText()));
		 file.setId3v1Tag(id3v1Tag);
		 ID3v2 id3v2Tag = file.getId3v2Tag();
		 id3v2Tag.setTitle(mu.jt[0].getText());
		 id3v2Tag.setArtist(mu.jt[1].getText());
		 id3v2Tag.setAlbum(mu.jt[2].getText());
		 id3v2Tag.setYear(mu.jt[3].getText());
		 id3v2Tag.setGenre(Integer.parseInt(mu.jt[4].getText()));
		 file.setId3v2Tag(id3v2Tag);
			 
	}
	void display()
	{
		ID3v1 id3v1Tag = file.getId3v1Tag();
		System.out.println(id3v1Tag.getTitle());
		System.out.println(id3v1Tag.getArtist());
		System.out.println(id3v1Tag.getAlbum());
		System.out.println(id3v1Tag.getYear());
		System.out.println(id3v1Tag.getGenre());
		
	}
}
