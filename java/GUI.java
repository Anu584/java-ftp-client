
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

 public class GUI implements ActionListener, PropertyChangeListener {
	 
	private  JFrame frame = new JFrame("File upload to FTP server");
	private  JPanel panel = new JPanel();
	private JFileChooser filechooser = new JFileChooser();
	
	private  JLabel labelHost = new JLabel("Host:");  
	private  JLabel labelPort = new JLabel("Port:");
	private  JLabel labelUsername = new JLabel("Username:");
	private  JLabel labelPassword = new JLabel("Password:");
	private  JLabel labelUploadpath = new JLabel("Uploadpath:");
	private  JLabel labelChooseafile = new JLabel("Choose a file:");
	private  JLabel labelProgess = new JLabel("Progess:");
			
	private  JTextField fieldHost = new JTextField(40);
	private  JTextField fieldPort = new JTextField(5);
	private  JTextField fieldUsername = new JTextField(30);
	private  JPasswordField fieldPassword = new JPasswordField(30);
	private  JTextField fieldUploadpath = new JTextField(30);
	private  JTextField fieldChooseafile = new JTextField(30);
	
	private JButton buttonUpload = new JButton("Upload");
	private JButton buttonBrowse = new JButton("Browse");
	private JProgressBar progressBar = new JProgressBar(0, 100);
	
	File file;
	int response;
	
	public static void main(String[] args) {
		new GUI();
		
		}

	public GUI() {
		
		buttonUpload.addActionListener(new ActionListener() {
		
		
		public void actionPerformed(ActionEvent event) {
		
			buttonUploadActionPerformed(event);
			
		}
		
		});
		
		frame.setSize(600, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		panel.setLayout(null);
		
		labelHost.setBounds(10, 20, 200, 25);
		panel.add(labelHost);
		fieldHost.setBounds(120, 20, 400, 25);
		panel.add(fieldHost); 
		
		labelPort.setBounds(10, 60,  80, 25);
		panel.add(labelPort);
		fieldPort.setBounds(120, 60, 400, 25);
		panel.add(fieldPort);
		
		labelUsername.setBounds(10, 100, 400, 25);
		panel.add(labelUsername);
		fieldUsername.setBounds(120, 100, 400, 25);
		panel.add(fieldUsername);
		
		labelPassword.setBounds(10, 140, 120, 25);
		panel.add(labelPassword);
		fieldPassword.setBounds(120, 140, 400, 25);
		panel.add(fieldPassword);
		
		labelUploadpath.setBounds(10, 180, 400, 25);
		panel.add(labelUploadpath);
		fieldUploadpath.setBounds(120, 180, 400, 25);
		panel.add(fieldUploadpath);
		
		labelChooseafile.setBounds(10, 220, 300, 25);
		panel.add(labelChooseafile);
		fieldChooseafile.setBounds(120, 220, 300, 25);
		panel.add(fieldChooseafile);
		
		buttonBrowse.setBounds(420, 220, 100, 25);
		buttonBrowse.addActionListener(this);
		panel.add(buttonBrowse);
		
		buttonUpload.setBounds(250, 260, 80, 25);
		panel.add(buttonUpload);
		
		labelProgess.setBounds(10, 300, 100, 25);
		panel.add(labelProgess);
		
		progressBar.setBounds(120, 300, 400, 25);
		progressBar.setStringPainted(true);
		panel.add(progressBar);
		
		
		frame.setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		filechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		response = filechooser.showOpenDialog(null);
		if (response == JFileChooser.APPROVE_OPTION) {
		     file = filechooser.getSelectedFile();
		     if(file.isFile()) {
		    	 fieldChooseafile.setText(file.getAbsolutePath());
		    	 System.out.print(file.getAbsolutePath());
		     }else {
		    	 System.out.println("That was not a file!");
		    	 
		     }
		}
		
	}
	
	private void buttonUploadActionPerformed(ActionEvent event) {
		String host = fieldHost.getText();
		int port = Integer.parseInt(fieldPort.getText());
		String username = fieldUsername.getSelectedText();
		String password = new String(fieldPassword.getPassword());
		String uploadPath = fieldUploadpath.getText();
		String filePath = filechooser.getSelectedFile().getAbsolutePath();
		
		File uploadFile = new File(filePath);
		progressBar.setValue(0);
		UploadTask task = new UploadTask(host, port, username, password, uploadPath, uploadFile);
		task.addPropertyChangeListener(this);
		task.execute();
	}
	
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			
		}
		
		
	}

}
