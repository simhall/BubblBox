package bbox_server;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import shared.BBoxDBSQLLite;

@SpringBootApplication
public class Application {

	static PopupMenu popup = new PopupMenu();
	static MenuItem menuItemFrontend = new MenuItem("BubblBox Web");
	static MenuItem menuItemLogs = new MenuItem("View Logs");
    static MenuItem menuItemExit = new MenuItem("Exit");
    static TrayIcon trayIcon = null;
    public static BBoxDBSQLLite LogDB = null;
    
    public static void main(String[] args) {
    	LogDB = new BBoxDBSQLLite(false);
    	
        SpringApplication.run(Application.class, args);
        
        if (SystemTray.isSupported() && true) {
		    // get the SystemTray instance
		    SystemTray tray = SystemTray.getSystemTray();
		    // load an image
		    
		    
		    Image image = Toolkit.getDefaultToolkit().getImage(Application.class.getClassLoader().getResource("bubbl_box_logo_server_small.png"));
		    // create a action listener to listen for default action executed on the tray icon
		    
		    ActionListener listenerFrontend = new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		    		String url = "localhost:1337";
		    		//TimeyLog.LogInfo("Navigating to: " + url);
		    		try {
		    			java.awt.Desktop.getDesktop().browse(new URI(url));
		    		} catch (IOException ex) {
		    			//TimeyLog.LogException("Failed to open report page", e);
		    		} catch (URISyntaxException ex) {
		    			//TimeyLog.LogException("Failed to open report page", e);
		    		}
		        }
		    };
		    
		    ActionListener listenerExit = new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        	System.exit(0);
		        }
		    };
		    
		    
		    // ---- MenuItem -> Frontend
		    menuItemFrontend.addActionListener(listenerFrontend);
		    popup.add(menuItemFrontend);
		    
		    // ---- MenuItem -> Logs
		    //menuItemExit.addActionListener(listenerExit);
		    //menuSettings.add(menuItemExit);
		    
		    // ---- MenuItem -> Exit
		    menuItemExit.addActionListener(listenerExit);
		    popup.add(menuItemExit);
		    
		    // construct a TrayIcon
		    trayIcon = new TrayIcon(image, "BuBBl Box Server", popup);
		    // set the TrayIcon properties
		    //trayIcon.addActionListener(listenerExit);
		    // ...
		    // add the tray image
		    try {
		        tray.add(trayIcon);
		    } catch (AWTException e) {
		        System.err.println(e);
		    }
		    // ...
		} else {
		    // disable tray option in your application or
		    // perform other actions
		    
		}
    }
}