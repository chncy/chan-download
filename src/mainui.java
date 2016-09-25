package src;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTextField;
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

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mainui {
	
	private final static String lafGTK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
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
	private JLabel lblFilenameScheme;
	protected static JTextField scheme;
	private JPanel advancedPanel;
	private JLabel lblAdvancedSettings;
	private static int advancedSettings = 0;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					UIManager.setLookAndFeel(lafGTK);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mainui window = new mainui();
				window.frmchanThreadDownloader.setVisible(true);
			}
		});
	}
	
	public mainui() {
		initialize();
	}

	private void initialize() {
		frmchanThreadDownloader = new JFrame();
		frmchanThreadDownloader.setMinimumSize(new Dimension(500, 450));
		frmchanThreadDownloader.setTitle("4chan thread downloader");
		frmchanThreadDownloader.getContentPane().setBackground(new Color(64,69,82));
		frmchanThreadDownloader.getContentPane().setLayout(new MigLayout("hidemode 1", "[440px,grow]", "[][][][0px:n,shrink 75][19.00][grow,fill]"));
		
		JLabel lblThreadUrl = new JLabel("Thread URL");
		lblThreadUrl.setVerticalAlignment(SwingConstants.TOP);
		//lblThreadUrl.setFont(new Font("Cantarell", Font.PLAIN, 14));
		//lblThreadUrl.setForeground(Color.WHITE);
		frmchanThreadDownloader.getContentPane().add(lblThreadUrl, "flowy,cell 0 0");
		
		lblTargetDirectory = new JLabel("Target Directory");
		//lblTargetDirectory.setFont(new Font("Cantarell", Font.PLAIN, 14));
		//lblTargetDirectory.setForeground(Color.WHITE);
		frmchanThreadDownloader.getContentPane().add(lblTargetDirectory, "flowy,cell 0 1");
		
		lblAdvancedSettings = new JLabel("Advanced Settings");
		lblAdvancedSettings.setVerticalTextPosition(SwingConstants.TOP);
		lblAdvancedSettings.setVerticalAlignment(SwingConstants.TOP);
		ImageIcon ii0 = new ImageIcon(src.mainui.class.getResource("/src/images/ic_expand_less_white_18dp_1x.png"));
		ImageIcon ii1 = new ImageIcon(src.mainui.class.getResource("/src/images/ic_expand_more_white_18dp_1x.png"));
		Image ii0i = ii0.getImage();
		Image ii1i = ii1.getImage();
		Image ii0n = ii0i.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH); //scaling
		Image ii1n = ii1i.getScaledInstance(16, 16,  java.awt.Image.SCALE_SMOOTH); //scaling
		lblAdvancedSettings.setIcon(new ImageIcon(ii0n));
		//lblAdvancedSettings.setFont(new Font("Cantarell", Font.PLAIN, 14));
		//lblAdvancedSettings.setForeground(Color.WHITE);
		frmchanThreadDownloader.getContentPane().add(lblAdvancedSettings, "flowx,cell 0 2,growx,aligny top");
		
		advancedPanel = new JPanel();
		advancedPanel.setVisible(false);
		advancedPanel.setMinimumSize(new Dimension(0, 0));
		lblAdvancedSettings.setLabelFor(advancedPanel);
		//advancedPanel.setForeground(Color.WHITE);
		//advancedPanel.setFont(new Font("Cantarell", Font.PLAIN, 14));
		advancedPanel.setBorder(new MatteBorder(0, 2, 0, 0, new Color(3, 169, 244)));
		//advancedPanel.setBackground(new Color(64,69,82));
		advancedPanel.setLayout(new MigLayout("", "[fill]", "[fill]"));
		
		lblFilenameScheme = new JLabel("Filename Scheme (Leave empty for default)");
		lblFilenameScheme.setHorizontalAlignment(SwingConstants.LEFT);
		lblFilenameScheme.setHorizontalTextPosition(SwingConstants.LEFT);
		advancedPanel.add(lblFilenameScheme, "flowy,cell 0 0,growx,aligny top");
		//lblFilenameScheme.setFont(new Font("Cantarell", Font.PLAIN, 14));
		//lblFilenameScheme.setForeground(Color.WHITE);
		frmchanThreadDownloader.getContentPane().add(advancedPanel, "hidemode 3,cell 0 3,grow");
		
		scheme = new JTextField();
		advancedPanel.add(scheme, "cell 0 0,grow");
		//scheme.setBorder(new LineBorder(Color.GRAY));
		scheme.setColumns(10);
		
		btnDownloadThread = new JButton("Download Thread");
		//btnDownloadThread.setBackground(new Color(64,69,82));
		//btnDownloadThread.setBorder(new EmptyBorder(5, 8, 5, 8));
		//btnDownloadThread.setForeground(new Color(3, 169, 244));
		frmchanThreadDownloader.getContentPane().add(btnDownloadThread, "flowy,hidemode 3,cell 0 4");
		
		progressTextPane = new JScrollPane();
		progressTextPane.setMinimumSize(new Dimension(0, 75));
		progressTextPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frmchanThreadDownloader.getContentPane().add(progressTextPane, "flowy,cell 0 5,grow");
		
		progressText = new JTextArea();
		//progressText.setBorder(new MatteBorder(1, 1, 1, 1, new Color(3, 169, 244)));
		progressText.setEditable(false);
		progressTextPane.setViewportView(progressText);
		
		progressBar = new JProgressBar();
		/*progressBar.setUI(new BasicProgressBarUI() {
	        protected Color getSelectionBackground() { return new Color(51, 51, 51); }
	        protected Color getSelectionForeground() { return Color.white; }
	    });*/
		progressBar.setStringPainted(true);
		//progressBar.setBorder(null);
		//progressBar.setFont(new Font("Droid Sans", Font.BOLD, 12));
		//progressBar.setForeground(new Color(139,195,74));
		//progressBar.setBackground(Color.WHITE);
		frmchanThreadDownloader.getContentPane().add(progressBar, "cell 0 4,growx,aligny bottom");
		
		targetDirectory = new JTextField();
		targetDirectory.setHorizontalAlignment(SwingConstants.LEFT);
		//targetDirectory.setBorder(new LineBorder(Color.GRAY));
		frmchanThreadDownloader.getContentPane().add(targetDirectory, "cell 0 1,growx");
		targetDirectory.setColumns(10);
		
		btnChoose = new JButton("Choose...");
		btnChoose.setHorizontalTextPosition(SwingConstants.LEADING);
		btnChoose.setHorizontalAlignment(SwingConstants.RIGHT);
		btnChoose.setFocusPainted(false);
		btnChoose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		//btnChoose.setForeground(new Color(0, 128, 128));
		btnChoose.setBorderPainted(false);
		btnChoose.setBackground(new Color(64,69,82));
		//btnChoose.setBorder(new EmptyBorder(5, 8, 5, 8));
		btnChoose.setFont(new Font("DejaVu Sans Condensed", Font.BOLD, 13));
		btnChoose.setMnemonic('c');
		btnChoose.setMnemonic(KeyEvent.VK_C);
		frmchanThreadDownloader.getContentPane().add(btnChoose, "cell 0 1");
		
		threadURL = new JTextField();
		lblThreadUrl.setLabelFor(threadURL);
		//threadURL.setBorder(new LineBorder(new Color(128, 128, 128)));
		//threadURL.setForeground(new Color(64,69,82));
		//threadURL.setFont(new Font("Cantarell", Font.PLAIN, 14));
		frmchanThreadDownloader.getContentPane().add(threadURL, "cell 0 0,growx");
		threadURL.setColumns(10);
		
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
					    		target = ftype == 0 ? Paths.get(targetDirectory.getText())
					    							: chooser.getSelectedFile().toPath();
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
		
		lblAdvancedSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(advancedSettings == 0) {
					advancedPanel.setVisible(true);
					frmchanThreadDownloader.getContentPane().revalidate();
					lblAdvancedSettings.setIcon(new ImageIcon(ii1n));
				} else {
					advancedPanel.setVisible(false);
					frmchanThreadDownloader.getContentPane().revalidate();
					lblAdvancedSettings.setIcon(new ImageIcon(ii0n));
				}
				advancedSettings = (advancedSettings == 0) ? 1 : 0;
			}
		});
		
		//frmchanThreadDownloader.setBounds(0, 0, 450, 350);
		frmchanThreadDownloader.pack();
		frmchanThreadDownloader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
