package cc.kostic.dmx.dmx_gui2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class App {
	
	public static void main(String[] args) {
		System.out.println("--- JVM Version Information ---");
		System.out.println("java.version: " + System.getProperty("java.version"));
		System.out.println("java.vm.version: " + System.getProperty("java.vm.version"));
		System.out.println("java.vm.name: " + System.getProperty("java.vm.name"));
		System.out.println("java.vm.vendor: " + System.getProperty("java.vm.vendor"));
		System.out.println("---------------------------------");
		
		// Start the Spring context in non-headless mode for GUI
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(App.class)
				.headless(false)
				.run(args);
		
		// Run the GUI on the Event Dispatch Thread
		EventQueue.invokeLater(() -> {
			// GT frame = ctx.getBean(GT.class);
			AppFrame frame = ctx.getBean(AppFrame.class);
			frame.setVisible(true);
		});
	}
	
}
