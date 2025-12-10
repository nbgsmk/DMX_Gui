package com.example.library;

import com.example.library.forms.AppFrame;
import com.example.library.forms.DmxChan;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Launch the book editor form
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				AppFrame frame = new AppFrame();
				
				frame.pack();
				frame.setVisible(true);
				
            }
        });

		
		
		// SwingUtilities.invokeLater(new Runnable() {
        //     public void run() {
        //         BookEditorExample bookEditorExample = new BookEditorExample();
        //         bookEditorExample.setVisible(true);
		//
        //         bookEditorExample.setSaveButtonListener(new SaveButtonListener() {
        //             @Override
        //             public void onSaveClicked(Book book) {
        //                 System.out.println("Entered Book Details:");
        //                 System.out.println("Book Title: " + book.getName());
        //                 System.out.println("Author: " + book.getAuthor().getName());
        //                 System.out.println("Genre: " + book.getGenre());
        //                 System.out.println("Is Unavailable: " + book.isTaken());
        //             }
        //         });
        //     }
        // });
		
		
		
		
    }
	
	

}
