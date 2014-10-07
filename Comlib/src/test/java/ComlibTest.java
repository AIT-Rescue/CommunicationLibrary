import junit.framework.*;

import java.io.*;
import comlib.manager.*;
import rescuecore2.config.*;


public class ComlibTest extends TestCase{

	MessageManager manager;

	public ComlibTest(String name) {
		super(name);
	}

	public void testDummyMessage() {
		try
			{
				manager = new MessageManager(new Config(new File("src/test/resources/config/kernel.cfg")));
			} catch (ConfigException e) {
				e.printStackTrace();
			}
		assertEquals(1, 1);
	}
}
