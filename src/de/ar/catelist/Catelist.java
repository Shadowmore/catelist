package de.ar.catelist;

import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Catelist {

	public static void main(String[] args) {

		try {
			Display display = new Display();
			Shell shell = new Shell(display);
			shell.setSize(800, 600);
			shell.setText("Catelist");

			// TODO: Open Composite extended class

			shell.open();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
				{
					display.sleep();
				}
			}
			display.dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}