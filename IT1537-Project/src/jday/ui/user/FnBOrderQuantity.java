package jday.ui.user;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import jday.entities.FnB;
import jday.entities.Member;
import jday.util.BackgroundPanel;
import jday.util.FnBTableModel;

public class FnBOrderQuantity extends BackgroundPanel{


	public FnBOrderQuantity(){
		super();
		setLayout(null);

	}
	public FnBOrderQuantity(JFrame f,ArrayList<FnB>fnb, Member mem){
		super();
		myFrame = f;
		ArrayList<FnB>list = new ArrayList<FnB>(fnb);
		this.m = mem;
		
		setSize(750,500);
		setLayout(null);
		final JTable tableList = new JTable(new FnBTableModel(list));
		tableList.setBounds(0,0,510,165);
		tableList.setShowGrid(false);
		tableList.setShowVerticalLines(false);
		tableList.setShowGrid(false);
		tableList.setShowHorizontalLines(false);
		JScrollPane scrollPane = new JScrollPane(tableList);
		scrollPane.setBounds(30, 75, 510, 165);
		add(scrollPane);
	
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<FnB>fnb = new ArrayList<FnB>();
				for(int i=0; i<tableList.getRowCount();i++){
					FnB fnb1 = new FnB();
					String fnborder = (String)tableList.getValueAt(i, 0);
					int quantity = 0;
					if(tableList.isEditing()){
						String quant = (String) tableList.getValueAt(i, 1);
						if(quant != null)
						quantity = Integer.parseInt(quant);
					}
					else if(tableList.isEditing()!=true){
					quantity =((Integer)(tableList.getValueAt(i,1))).intValue();
					}
					fnb1.setFnborder(fnborder);
					fnb1.setQuantity(quantity);
					fnb.add(fnb1);
				}
				JPanel panel = new FnBDelivery(myFrame,fnb, m);
				myFrame .getContentPane().removeAll();
				myFrame.getContentPane().add(panel);
				myFrame.getContentPane().validate();
				myFrame.getContentPane().repaint();
			}
		});
		btnConfirm.setFont(new Font("Candara", Font.BOLD, 16));
		btnConfirm.setBounds(249, 400, 100, 54);
		add(btnConfirm);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JPanel panel = new FnBMain(myFrame,m);
				myFrame .getContentPane().removeAll();
				myFrame.getContentPane().add(panel);
				myFrame.getContentPane().validate();
				myFrame.getContentPane().repaint();
			}
		});
		btnBack.setFont(new Font("Candara", Font.BOLD, 16));
		btnBack.setBounds(85, 400, 100, 54);
		add(btnBack);

	}
}
