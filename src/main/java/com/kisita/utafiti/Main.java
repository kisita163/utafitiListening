package com.kisita.utafiti;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Main {
	
	public static String SERVICE_PATH  = "/home/firebase/firebase/caritas-50fab-b10622860737.json";
	public static String DATABASE_PATH = "https://caritas-50fab.firebaseio.com/";
	private static DatabaseReference database;
;	
	public static void init() throws IOException {
		
		FileInputStream serviceAccount = new FileInputStream(SERVICE_PATH);

		FirebaseOptions options = new FirebaseOptions.Builder()
		    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		    .setDatabaseUrl(DATABASE_PATH)
		    .build();

		FirebaseApp.initializeApp(options);
	}
	
    /**
     * Start global listener for all Posts.
     */
    public static void startListeners() {
    	
        database.child("survey").addChildEventListener(new ChildEventListener() {

            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildName) {
            
                System.out.println("onChildAdded");
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildName) {
            	
            	System.out.println("onChildChanged");
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildName) {}

            public void onCancelled(DatabaseError databaseError) {
            	
                System.out.println("startListeners: unable to attach listener to posts");
                System.out.println("startListeners: " + databaseError.getMessage());
            }
        });
    }

	public static void main(String[] args) {
		try {
			init();
	        // Shared Database reference
	        database = FirebaseDatabase.getInstance().getReference();
	        final CountDownLatch latch = new CountDownLatch(1);
	        // Start listening to the Database
	        startListeners();
	        latch.await();
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Kisita");
	}

}
