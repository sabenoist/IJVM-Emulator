package pad.ijvm;

import org.junit.Test;
import pad.ijvm.interfaces.IJVMInterface;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Task5 {

    IJVMInterface machine;

    @Test
    public void testParameters() throws IOException {
        machine = MachineFactory.createIJVMInstance(new File("files/task5/TestMethods.ijvm"));
        for (int i = 0; i < 20; i++) {
            machine.step();
        }

        assertEquals("Parameter 0 should be 100",machine.getLocalVariable(0), 100);
        assertEquals("Parameter 1 should be 49",machine.getLocalVariable(1), 49);
        assertEquals("Parameter 2 should be 48",machine.getLocalVariable(2), 48);
        assertEquals("Parameter 3 should be 1",machine.getLocalVariable(3), 1);
        assertEquals("Parameter 4 should be 4",machine.getLocalVariable(4), 4);
    }

    @Test
    public void testMethods() throws IOException {
        machine = MachineFactory.createIJVMInstance(new File("files/task5/TestMethods.ijvm"));
        for (int i = 0; i < 30; i++) {
            machine.step();
        }

        assertEquals("The returnvalue should be 85",machine.topOfStack(), 85);
    }

    @Test
    public void testNormalRun() throws IOException {
        machine = MachineFactory.createIJVMInstance(new File("files/task5/TestMethods.ijvm"));
        machine.run();

        assertEquals("The returnvalue should be 85",machine.topOfStack(), 85);
    }

    @Test
    public void testNormalRun2() throws IOException {
        machine = MachineFactory.createIJVMInstance(new File("files/task5/TestMethods2.ijvm"));
        machine.run();

        assertEquals("The returnvalue should be 5",machine.topOfStack(), 5);
    }
}
