package jday.entities.dao;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import jday.entities.Event;

public class EventDAO {

	public static void main(String args[]) throws SQLException {
		//for testing will not affect program
		@SuppressWarnings("deprecation")
		Date date = new Date(113, 1, 29);
		EventDAO.setEvent("titleTest", date, "venueTest", "infoTest");

	}

	public static String dateToString(Date date) {
		@SuppressWarnings("deprecation")
		String date2 = date.toGMTString();
		return date2 = date2.substring(0, 11);
	}

	public static Event getEventByDate(Date date) throws SQLException {
		// do the coding to connect to actual db;

		String date2 = dateToString(date);

		DBController db = new DBController();
		db.getConnection();
		String sql = "select * from event where date = '" + date2 + "';";

		ResultSet rs = db.readRequest(sql);

		Event event = new Event();

		try {

			while (rs.next()) {
				event.setInfo(rs.getString("info"));
				event.setTitle(rs.getString("title"));
				event.setVenue(rs.getString("venue"));
				event.setDate(rs.getString("date"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.terminate();
		return event;
	}

	public static void setEvent(String title2, Date date2, String venue2, String info2) throws SQLException {
		String title = title2;
		String venue = venue2;
		String info = info2;
		String date3 = null;
		Connection con = null;
		PreparedStatement pstmt = null;

		DBController db = new DBController();
		con = db.getConnection();
		// increment date by 1 due to unknown bug (date was 1 less then selected)
		Calendar cal = Calendar.getInstance();
		cal.setTime(date2);
		cal.add(Calendar.DATE, 1);
		date2 = cal.getTime();
		String date = dateToString(date2);

		String sql = "select * from event where date = '" + date + "';";
		ResultSet rs = db.readRequest(sql);
		if (rs.next()) {
			date3 = rs.getString("date");
		}

		if (date3 != null) {
			sql = "delete from event where date = '" + date + "';";
			db.updateRequest(sql);
			sql = "insert into event (info, title, date, venue) values(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info);
			pstmt.setString(2, title);
			pstmt.setString(3, date);
			pstmt.setString(4, venue);
			pstmt.executeUpdate();
		}

		else {
			sql = "insert into event (info, title, date, venue) values(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info);
			pstmt.setString(2, title);
			pstmt.setString(3, date);
			pstmt.setString(4, venue);
			pstmt.executeUpdate();
		}

		Component frame = null;
		JOptionPane.showMessageDialog(frame, "The event has successfully been updated.");

		// delete
		// insert (create)
		// update (overwrite)
		// confirm message

		db.terminate();

	}
}
