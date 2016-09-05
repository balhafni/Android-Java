import java.io.StringWriter;

import org.python.antlr.FailFastHandler;
import org.python.antlr.PythonLexer;
import org.python.antlr.PythonParser;
import org.python.antlr.PythonTokenSource;
import org.python.antlr.PythonTree;
import org.python.antlr.PythonTreeAdaptor;
import org.python.antlr.ast.Module;
import org.python.antlr.runtime.ANTLRStringStream;
import org.python.antlr.runtime.CharStream;
import org.python.antlr.runtime.CommonTokenStream;
import org.python.antlr.runtime.RecognitionException;

public class MethodModel {

	public MethodModel() {

	}

	public void parse(String fun) throws Exception {
		CharStream stream = new ANTLRStringStream(fun);
		StringWriter writer = new StringWriter();
		writer.write(fun);
		PythonLexer lexer = new PythonLexer(stream);
		lexer.setErrorHandler(new FailFastHandler());
		CommonTokenStream tokens = new CommonTokenStream(new PythonTokenSource(new CommonTokenStream(lexer), "ascii"));
		PythonParser parser = new PythonParser(tokens);
		parser.setTreeAdaptor(new PythonTreeAdaptor());
		parser.setErrorHandler(new FailFastHandler());
		Module tree = (Module) parser.file_input().getTree();
		PythonVisitor visitor = new PythonVisitor();
		visitor.traverse(tree);

	}
}
