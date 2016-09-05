

import static org.junit.Assert.*;

import org.python.antlr.runtime.RecognitionException;

public class Test {

	@org.junit.Test
	public void test() throws Exception {
		MethodModel model = new MethodModel();
		model.parse("def calculateStuff():\n"+
		"\tif this.x == None:\n"+
			"\t\tif 'first if':\n"+
				"\t\t\treturn 'end first if'\n" +
			"\t\tif 'second if':\n" +
				"\t\t\treturn 'end second if'\n" +
			"\t\treturn 'end parent if'\n" +
		"\treturn this.y.x");
	}

}
