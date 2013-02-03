package jday.ui.admin;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Insets;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Font;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SpinnerDateModel;

import java.sql.SQLException;
import java.util.Date;
import java.util.Calendar;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import jday.entities.Event;
import jday.entities.Member;
import jday.entities.dao.EventDAO;
import jday.ui.user.EventPopupDialog;
import jday.util.BackgroundPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.freixas.jcalendar.DateEvent;
import org.freixas.jcalendar.DateListener;
import org.freixas.jcalendar.JCalendarCombo;

public class AUpdateEvent2 extends BackgroundPanel {
	private JTextField titleTxtF;
	private JTextField venueTxtF;

	/**
	 * Create the panel.
	 */
	public AUpdateEvent2() {
		super();

	}

	public AUpdateEvent2(JFrame f, Date date, Member m) throws SQLException {
		this();
		myFrame = f;
		this.m = m;
		initialize(date);
	}

	private void initialize(Date date) throws SQLException {
		setForeground(new Color(255, 255, 255));
		setFont(new Font("Arial", Font.PLAIN, 14));
		setBackground(new Color(204, 51, 153));
		setSize(new Dimension(750, 500));
		setLayout(null);

		JLabel label = new JLabel("logo");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JPanel panel = new AMainpage(myFrame,m);
				myFrame.getContentPane().removeAll();
				myFrame.getContentPane().add(panel);
				myFrame.getContentPane().validate();
				myFrame.getContentPane().repaint();

			}
		});

		String title = "";
		String info = "";
		String venue = "";
		// getEventByDate
		Event event01 = EventDAO.getEventByDate(date);
		// if not null, set the boxes with text

		if (event01.getDate() != null) {
			info = event01.getInfo();
			title = event01.getTitle();
			venue = event01.getVenue();
		}

		label.setIcon(new ImageIcon(AUpdateEvent2.class.getResource("/images/90logo.png")));
		label.setBounds(10, 11, 94, 102);
		add(label);

		JLabel lblNewLabel = new JLabel("Title:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(48, 124, 106, 27);
		add(lblNewLabel);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDescription.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDescription.setBounds(48, 326, 106, 27);
		add(lblDescription);

		// title textfield
		titleTxtF = new JTextField();
		titleTxtF.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		titleTxtF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		titleTxtF.setBounds(202, 125, 466, 27);
		add(titleTxtF);
		titleTxtF.setColumns(10);
		titleTxtF.setText(title);

		// info text area
		final JTextArea infoTxtA = new JTextArea();
		infoTxtA.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		infoTxtA.setWrapStyleWord(true);
		infoTxtA.setFocusTraversalKeysEnabled(false);
		infoTxtA.setLineWrap(true);
		infoTxtA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		infoTxtA.setBounds(202, 280, 466, 150);
		add(infoTxtA);
		infoTxtA.setText(info);

		JLabel lblNewLabel_1 = new JLabel("Edit Events");
		lblNewLabel_1.setForeground(new Color(153, 51, 153));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.BOLD, 23));
		lblNewLabel_1.setBounds(114, 43, 160, 30);
		add(lblNewLabel_1);

		JLabel lblDate = new JLabel("Date:");
		lblDate.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDate.setBounds(48, 179, 106, 27);
		add(lblDate);

		JLabel lblVenue = new JLabel("Venue:");
		lblVenue.setHorizontalAlignment(SwingConstants.TRAILING);
		lblVenue.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblVenue.setBounds(48, 236, 106, 27);
		add(lblVenue);

		// date combo box
		final JCalendarCombo dateCalCombo = new JCalendarCombo();
		dateCalCombo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		dateCalCombo.setBounds(202, 178, 307, 35);
		add(dateCalCombo);
		dateCalCombo.setDate(date);

		dateCalCombo.addDateListener(new DateListener() {
			public void dateChanged(DateEvent arg0) {
				Event event = null;
				String info = "";
				String title = "";
				String venue = "";
				//when date change, change the text fields if any
				try {
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(dateCalCombo.getDate());
					cal.add(Calendar.DATE, 1);
					Date date2 = cal.getTime();
					event = EventDAO.getEventByDate(date2);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (event.getDate() != null){
					info = event.getInfo();
					title = event.getTitle();
					venue = event.getVenue();
					
					infoTxtA.setText(info);
					titleTxtF.setText(title);
					venueTxtF.setText(venue);
				}
				
				else {
					infoTxtA.setText("");
					titleTxtF.setText("");
					venueTxtF.setText("");
				}
				
				
			}
		});

		// venue text field
		venueTxtF = new JTextField();
		venueTxtF.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		venueTxtF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		venueTxtF.setColumns(10);
		venueTxtF.setBounds(202, 237, 466, 27);
		add(venueTxtF);
		venueTxtF.setText(venue);

		// submit button
		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// overwrite database entry if not null
				try {
					EventDAO.setEvent(titleTxtF.getText(),
							dateCalCombo.getDate(), venueTxtF.getText(), infoTxtA.getText());

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				JPanel panel = new AUpdateEvent(myFrame,m);
				myFrame.getContentPane().removeAll();
				myFrame.getContentPane().add(panel);
				myFrame.getContentPane().validate();
				myFrame.getContentPane().repaint();
			}
		});
		submitBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		submitBtn.setBounds(562, 441, 106, 35);
		add(submitBtn);
		
		//back button
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JPanel panel = new AUpdateEvent(myFrame,m);
				myFrame.getContentPane().removeAll();
				myFrame.getContentPane().add(panel);
				myFrame.getContentPane().validate();
				myFrame.getContentPane().repaint();
			}
		});
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBtn.setBounds(400, 441, 106, 35);
		add(backBtn);

	}
}
