package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.JProgressBar;

import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class mainui {

	protected static JFrame frmchanThreadDownloader;
	private JTextField threadURL;
	protected static JProgressBar progressBar;
	private JLabel lblTargetDirectory;
	private JTextField targetDirectory;
	private JButton btnChoose;
	private JFileChooser chooser;
	protected static Path target = Paths.get(System.getProperty("user.home"));
	private JButton btnDownloadThread;
	protected static JScrollPane progressTextPane;
	protected static JTextArea progressText;
	private static int ftype = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					mainui window = new mainui();
					window.frmchanThreadDownloader.setVisible(true);
					UIManager.setLookAndFeel(
				            UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public mainui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmchanThreadDownloader = new JFrame();
		frmchanThreadDownloader.setTitle("4chan thread downloader");
		frmchanThreadDownloader.getContentPane().setBackground(Color.DARK_GRAY);
		frmchanThreadDownloader.getContentPane().setLayout(new MigLayout("", "[440px,grow]", "[][][][][][20.00][grow]"));
		
		JLabel lblThreadUrl = new JLabel("Thread URL");
		lblThreadUrl.setVerticalAlignment(SwingConstants.TOP);
		lblThreadUrl.setFont(new Font("Cantarell", Font.PLAIN, 14));
		lblThreadUrl.setForeground(Color.WHITE);
		frmchanThreadDownloader.getContentPane().add(lblThreadUrl, "cell 0 0");
		
		threadURL = new JTextField();
		lblThreadUrl.setLabelFor(threadURL);
		threadURL.setBorder(new LineBorder(new Color(128, 128, 128)));
		threadURL.setForeground(Color.DARK_GRAY);
		threadURL.setFont(new Font("Cantarell", Font.PLAIN, 14));
		frmchanThreadDownloader.getContentPane().add(threadURL, "cell 0 1,growx");
		threadURL.setColumns(10);
		
		lblTargetDirectory = new JLabel("Target Directory");
		lblTargetDirectory.setFont(new Font("Cantarell", Font.PLAIN, 14));
		lblTargetDirectory.setForeground(Color.WHITE);
		frmchanThreadDownloader.getContentPane().add(lblTargetDirectory, "cell 0 2");
		
		targetDirectory = new JTextField();
		targetDirectory.setBorder(new LineBorder(Color.GRAY));
		frmchanThreadDownloader.getContentPane().add(targetDirectory, "flowx,cell 0 3,growx");
		targetDirectory.setColumns(10);
		
		btnDownloadThread = new JButton("Download Thread");
		btnDownloadThread.setBackground(Color.DARK_GRAY);
		btnDownloadThread.setBorder(new EmptyBorder(5, 8, 5, 8));
		btnDownloadThread.setForeground(new Color(3, 169, 244));
		frmchanThreadDownloader.getContentPane().add(btnDownloadThread, "cell 0 4");
		
		progressBar = new JProgressBar();
		progressBar.setUI(new BasicProgressBarUI() {
	        protected Color getSelectionBackground() { return new Color(51, 51, 51); }
	        protected Color getSelectionForeground() { return Color.white; }
	    });
		progressBar.setStringPainted(true);
		progressBar.setBorder(null);
		progressBar.setFont(new Font("Droid Sans", Font.BOLD, 12));
		progressBar.setForeground(new Color(139,195,74));
		progressBar.setBackground(Color.WHITE);
		progressBar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frmchanThreadDownloader.getContentPane().add(progressBar, "cell 0 5,growx,aligny bottom");
		
		btnChoose = new JButton("Choose...");
		btnChoose.setFocusPainted(false);
		btnChoose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnChoose.setForeground(new Color(0, 128, 128));
		btnChoose.setBorderPainted(false);
		btnChoose.setBackground(Color.DARK_GRAY);
		btnChoose.setBorder(new EmptyBorder(5, 8, 5, 8));
		btnChoose.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 13));
		btnChoose.setMnemonic('c');
		btnChoose.setMnemonic(KeyEvent.VK_C);
		frmchanThreadDownloader.getContentPane().add(btnChoose, "cell 0 3");
		
		progressTextPane = new JScrollPane();
		progressTextPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frmchanThreadDownloader.getContentPane().add(progressTextPane, "cell 0 6,grow");
		
		progressText = new JTextArea();
		progressText.setBorder(null);
		progressText.setEditable(false);
		progressTextPane.setViewportView(progressText);
		
		frmchanThreadDownloader.setBackground(Color.DARK_GRAY);
		frmchanThreadDownloader.setBounds(100, 100, 500, 300);
		frmchanThreadDownloader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("Choose a target Directory");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    
			    if (chooser.showOpenDialog(frmchanThreadDownloader) == JFileChooser.APPROVE_OPTION) { 
			        try {
						target = chooser.getSelectedFile().toPath().toRealPath();
						ftype = 1;
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
			        targetDirectory.setText(target.toString());
			    } else {
			    	System.out.println("No file was selected.");
			    }
			}
		});
		
		btnDownloadThread.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(threadURL.getText().length() < 4) {
					threadURL.setBackground(new Color(244, 67, 54));
				} else if(targetDirectory.getText().length() < 1) {
					targetDirectory.setBackground(new Color(244, 67, 54));
				} else {
					Runnable runner = new Runnable()
					{
					    public void run() {
					    	try {
					    		target = ftype == 0 ? Paths.get(targetDirectory.getText()).toRealPath()
					    							: chooser.getSelectedFile().toPath().toRealPath();
					    		Download.download_thread(threadURL.getText(), target);
					    	} catch (Exception ex) {
								JOptionPane.showMessageDialog(frmchanThreadDownloader,
									    "Something went wrong, check your thread URL and target directory"
										+ "Stacktrace: " + ex.getMessage(),
									    "Fatal Error!",
									    JOptionPane.ERROR_MESSAGE);
								ex.printStackTrace();
							}
					    }
					};
					Thread t = new Thread(runner, "Code Executer");
					t.start();
				}
			}
		});
	}
}
