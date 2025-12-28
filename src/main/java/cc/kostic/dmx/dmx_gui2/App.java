package cc.kostic.dmx.dmx_gui2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class App {
	
	public static void main(String[] args) {
		// Start the Spring context in non-headless mode for GUI
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(App.class)
				.headless(false)
				.run(args);
		
		// Run the GUI on the Event Dispatch Thread
		EventQueue.invokeLater(() -> {
			AppFrame frame = ctx.getBean(AppFrame.class);
			frame.setVisible(true);
		});
	}
	
}
