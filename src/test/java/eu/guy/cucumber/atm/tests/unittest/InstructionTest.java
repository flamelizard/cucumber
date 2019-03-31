package eu.guy.cucumber.atm.tests.unittest;

import eu.guy.cucumber.atm.transactions.Instruction;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class InstructionTest {

    @Test
    public void parseFromText() {
        Instruction inst = Instruction.parseFrom("-200:55", 1);
        assertEquals("-", inst.getType());
        assertEquals("200", inst.getAmount());
        assertEquals("55", String.valueOf(inst.getAccNumber()));
        assertThat(1, equalTo(inst.getId()));
    }
}