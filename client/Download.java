package client;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Download {
	
	public static int download_thread (String url, Path target) throws Exception {
		mainui.progressText.setText("");
		
		int success = 0;
		String[] image = new String[512];
		String[] filename = new String[512];
		
		Document thread = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
											.referrer("http://boards.4chan.org").get();
		String title = thread.title();
		
        Elements images = thread.getElementsByTag("img");
        
        for(int i = 0; i < images.size(); i++) {
        	image[i] = "http:" + images.get(i).attr("src").replace("s", "");
        	filename[i] = FilenameUtils.getBaseName(image[i])
					+ "."
					+ FilenameUtils.getExtension(image[i]);
        	URL website = new URL(image[i]);
        	
        	try {
        		File exists = new File(target + filename[i]);
        		if(exists.exists()) {
            		
            		mainui.progressText.setText(mainui.progressText.getText()
    						+ "[" + (i+1) + "/" + images.size() + "]"
    						+ Math.round(((double)(i+1) / images.size())*100)
    						+ "% / Image " + filename[i] + " already exists. Skipping!\r\n");
            		continue;
        		} else {
            		
		        	ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		        	FileOutputStream fos = new FileOutputStream(target + filename[i]);
		        	fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		        	fos.close();
		        	mainui.progressBar.setMaximum(images.size());
		        	mainui.progressBar.setValue(i);
		        	mainui.progressBar.setString((i+1) + "/" + images.size()
		        								+ "(" + Math.round(((double)(i+1) / images.size())*100) + "%)");
		        	mainui.progressText.setText(mainui.progressText.getText()
		        								+ "[" + (i+1) + "/" + images.size() + "]"
		        								+ Math.round(((double)(i+1) / images.size())*100)
		        								+ "% / Downloading " + image[i] + "\r\n");
		        	mainui.frmchanThreadDownloader.revalidate();
		        	mainui.frmchanThreadDownloader.repaint();
	        		success++;
        		}
        	} catch (Exception e) {
        		e.printStackTrace();
        		mainui.progressText.setText(mainui.progressText.getText()
						+ "[" + (i+1) + "/" + images.size() + "]"
						+ Math.round(((double)(i+1) / images.size())*100)
						+ "% / Error downloading " + image[i] + ":\r\n"
						+ "\u0009Message: " + e.getCause() + "\r\n");
        		continue;
        	}
        }
        if(success < 1) {
        	throw new Exception();
		}
        JOptionPane.showMessageDialog(mainui.frmchanThreadDownloader,
			    "Download Successful!\n"
			    + "Succesfully downloaded " + success + "/" + images.size()
			    + " images from \"" + title + "\"");
        mainui.progressBar.setValue(mainui.progressBar.getMaximum());
        
		return 1;
	}
}
