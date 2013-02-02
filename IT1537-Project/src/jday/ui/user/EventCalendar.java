package jday.ui.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import jday.ui.user.EventPopupDialog;
import jday.entities.Event;
import jday.entities.Member;
import jday.entities.dao.EventDAO;
import jday.util.BackgroundPanel;
import org.freixas.jcalendar.JCalendar;
import org.freixas.jcalendar.JCalendarCombo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Date;
import java.awt.Cursor;
import org.freixas.jcalendar.DateListener;
import org.freixas.jcalendar.DateEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.MatteBorder;

public class EventCalendar extends BackgroundPanel {
	private Member m = null;
	/**
	 * Create the panel.
	 */
	public EventCalendar() {
		super();
		initialize();
	}
	
	public EventCalendar(JFrame f, Member m){
		this();
		myFrame = f;
		this.m = m;
	}
	private void initialize(){
		setForeground(new Color(255, 255, 255));
		setFont(new Font("Arial", Font.PLAIN, 14));
		setBackground(new Color(204, 51, 153));
		setSize(new Dimension(750, 500));
		setLayout(null);
		
		final JCalendar calendar = new JCalendar();
		calendar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		calendar.addDateListener(new DateListener() {
			public void dateChanged(DateEvent arg0) {
				//save date into a variable
				//open new window to show events on day clicked. New dialog?
				Event event01 = null;
				try {
					event01 = EventDAO.getEventByDate(calendar.getDate());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (event01.getDate() == null){
					JOptionPane.showMessageDialog(myFrame, "There are no events on this date.");
				}
				
				else {
				JDialog dialog = new JDialog();
				try {
					dialog = new EventPopupDialog(calendar.getDate());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//make dialog appear
				dialog.setVisible(true);
			}}
		});
		calendar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		calendar.setBounds(10, 94, 730, 395);
		add(calendar);
		
		JLabel lblEvents = new JLabel("Events");
		lblEvents.setForeground(new Color(102, 0, 102));
		lblEvents.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblEvents.setHorizontalTextPosition(SwingConstants.CENTER);
		lblEvents.setHorizontalAlignment(SwingConstants.CENTER);
		lblEvents.setBounds(10, 26, 157, 57);
		add(lblEvents);

	}
}
