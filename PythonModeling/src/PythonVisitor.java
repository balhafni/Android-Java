import java.util.Stack;

import org.python.antlr.PythonTree;
import org.python.antlr.Visitor;
import org.python.antlr.ast.Assert;
import org.python.antlr.ast.Assign;
import org.python.antlr.ast.Attribute;
import org.python.antlr.ast.AugAssign;
import org.python.antlr.ast.BinOp;
import org.python.antlr.ast.BoolOp;
import org.python.antlr.ast.Break;
import org.python.antlr.ast.Call;
import org.python.antlr.ast.ClassDef;
import org.python.antlr.ast.Compare;
import org.python.antlr.ast.Continue;
import org.python.antlr.ast.Delete;
import org.python.antlr.ast.Dict;
import org.python.antlr.ast.DictComp;
import org.python.antlr.ast.Ellipsis;
import org.python.antlr.ast.ExceptHandler;
import org.python.antlr.ast.Exec;
import org.python.antlr.ast.Expr;
import org.python.antlr.ast.Expression;
import org.python.antlr.ast.ExtSlice;
import org.python.antlr.ast.For;
import org.python.antlr.ast.FunctionDef;
import org.python.antlr.ast.GeneratorExp;
import org.python.antlr.ast.Global;
import org.python.antlr.ast.If;
import org.python.antlr.ast.IfExp;
import org.python.antlr.ast.Import;
import org.python.antlr.ast.ImportFrom;
import org.python.antlr.ast.Index;
import org.python.antlr.ast.Interactive;
import org.python.antlr.ast.Lambda;
import org.python.antlr.ast.List;
import org.python.antlr.ast.ListComp;
import org.python.antlr.ast.Module;
import org.python.antlr.ast.Name;
import org.python.antlr.ast.Num;
import org.python.antlr.ast.Pass;
import org.python.antlr.ast.Print;
import org.python.antlr.ast.Raise;
import org.python.antlr.ast.Repr;
import org.python.antlr.ast.Return;
import org.python.antlr.ast.Set;
import org.python.antlr.ast.SetComp;
import org.python.antlr.ast.Slice;
import org.python.antlr.ast.Str;
import org.python.antlr.ast.Subscript;
import org.python.antlr.ast.Suite;
import org.python.antlr.ast.TryExcept;
import org.python.antlr.ast.TryFinally;
import org.python.antlr.ast.Tuple;
import org.python.antlr.ast.UnaryOp;
import org.python.antlr.ast.While;
import org.python.antlr.ast.With;
import org.python.antlr.ast.Yield;
import org.python.core.PyObject;

import com.ziclix.python.sql.util.Queue;

public class PythonVisitor extends Visitor {
	Stack<PyObject> stack = new Stack<>();

	@Override
	public Object visitAssert(Assert node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitAssert(node);
	}

	@Override
	public Object visitAssign(Assign node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitAssign(node);
	}

	@Override
	public Object visitAttribute(Attribute node) throws Exception {

		stack.push(node);
		evaluateNode(node);
		return super.visitAttribute(node);
	}

	@Override
	public Object visitAugAssign(AugAssign node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitAugAssign(node);
	}

	@Override
	public Object visitBinOp(BinOp node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitBinOp(node);
	}

	@Override
	public Object visitBoolOp(BoolOp node) throws Exception {
		stack.push(node);
		evaluateNode(node);
		return super.visitBoolOp(node);
	}

	@Override
	public Object visitBreak(Break node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitBreak(node);
	}

	@Override
	public Object visitCall(Call node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitCall(node);
	}

	@Override
	public Object visitClassDef(ClassDef node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitClassDef(node);
	}

	@Override
	public Object visitCompare(Compare node) throws Exception {
		stack.push(node);
		evaluateNode(node);

		return super.visitCompare(node);
	}

	@Override
	public Object visitContinue(Continue node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitContinue(node);
	}

	@Override
	public Object visitDelete(Delete node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitDelete(node);
	}

	@Override
	public Object visitDict(Dict node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitDict(node);
	}

	@Override
	public Object visitDictComp(DictComp node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitDictComp(node);
	}

	@Override
	public Object visitEllipsis(Ellipsis node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitEllipsis(node);
	}

	@Override
	public Object visitExceptHandler(ExceptHandler node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitExceptHandler(node);
	}

	@Override
	public Object visitExec(Exec node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitExec(node);
	}

	@Override
	public Object visitExpr(Expr node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitExpr(node);
	}

	@Override
	public Object visitExpression(Expression node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitExpression(node);
	}

	@Override
	public Object visitExtSlice(ExtSlice node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitExtSlice(node);
	}

	@Override
	public Object visitFor(For node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitFor(node);
	}

	@Override
	public Object visitFunctionDef(FunctionDef node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitFunctionDef(node);
	}

	@Override
	public Object visitGeneratorExp(GeneratorExp node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitGeneratorExp(node);
	}

	@Override
	public Object visitGlobal(Global node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitGlobal(node);
	}

	@Override
	public Object visitIf(If node) throws Exception {
		stack.push(node);
		evaluateNode(node);
		return super.visitIf(node);
	}

	@Override
	public Object visitIfExp(IfExp node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitIfExp(node);
	}

	@Override
	public Object visitImport(Import node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitImport(node);
	}

	@Override
	public Object visitImportFrom(ImportFrom node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitImportFrom(node);
	}

	@Override
	public Object visitIndex(Index node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitIndex(node);
	}

	@Override
	public Object visitInteractive(Interactive node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitInteractive(node);
	}

	@Override
	public Object visitLambda(Lambda node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitLambda(node);
	}

	@Override
	public Object visitList(List node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitList(node);
	}

	@Override
	public Object visitListComp(ListComp node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitListComp(node);
	}

	@Override
	public Object visitModule(Module node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitModule(node);
	}

	@Override
	public Object visitName(Name node) throws Exception {
		// TODO Auto-generated method stub
		stack.push(node);
		evaluateNode(node);
		// evaluateStack();
		return super.visitName(node);
	}

	public void evaluateStack() {
		PyObject node;
		while (!stack.isEmpty()) {
			node = stack.pop();
			evaluateNode(node);
		}

	}

	public void evaluateNode(PyObject node) {
		Attribute attr = null;
		Compare cmp = null;
		Name name = null;
		Str str = null;
		Return rtn = null;
		BoolOp op = null;

		if (node instanceof If) {
			If _if = (If) node;
			System.out.println("Stack If: " + _if.toString());
			System.out.println("____children___" + _if.getChildren());
		}
		if (node instanceof Attribute) {
			attr = (Attribute) node;
			System.out.println("Stack Attribute: " + attr.getAttr());
			System.out.println("____children___" + attr.getChildren());
		}
		if (node instanceof Compare) {
			cmp = (Compare) node;
			System.out.println("Stack Compare: " + cmp.getOps());
			System.out.println("____children___" + cmp.getChildren());
		}
		if (node instanceof Name) {
			name = (Name) node;
			System.out.println("Stack Name: " + name.getText());
			System.out.println("____children___" + name.getChildren());
		}
		if (node instanceof Str) {
			str = (Str) node;
			System.out.println("Stack string: " + str.getS());
			System.out.println("____children___" + str.getChildren());
		}
		if (node instanceof Return) {
			rtn = (Return) node;
			System.out.println("Stack Return: " + rtn.getText());
			System.out.println("____children___" + rtn.getChildren());
		}
		if (node instanceof BoolOp) {
			op = (BoolOp) node;
			System.out.println("Stack bool: " + op.getOp());
			System.out.println("____children___" + op.getChildren());
		}
	}

	@Override
	public Object visitNum(Num node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitNum(node);
	}

	@Override
	public Object visitPass(Pass node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitPass(node);
	}

	@Override
	public Object visitPrint(Print node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitPrint(node);
	}

	@Override
	public Object visitRaise(Raise node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitRaise(node);
	}

	@Override
	public Object visitRepr(Repr node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitRepr(node);
	}

	@Override
	public Object visitReturn(Return node) throws Exception {
		stack.push(node);
		evaluateNode(node);
		return super.visitReturn(node);
	}

	@Override
	public Object visitSet(Set node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitSet(node);
	}

	@Override
	public Object visitSetComp(SetComp node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitSetComp(node);
	}

	@Override
	public Object visitSlice(Slice node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitSlice(node);
	}

	@Override
	public Object visitStr(Str node) throws Exception {
		stack.push(node);
		evaluateNode(node);
		return super.visitStr(node);
	}

	@Override
	public Object visitSubscript(Subscript node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitSubscript(node);
	}

	@Override
	public Object visitSuite(Suite node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitSuite(node);
	}

	@Override
	public Object visitTryExcept(TryExcept node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitTryExcept(node);
	}

	@Override
	public Object visitTryFinally(TryFinally node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitTryFinally(node);
	}

	@Override
	public Object visitTuple(Tuple node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitTuple(node);
	}

	@Override
	public Object visitUnaryOp(UnaryOp node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitUnaryOp(node);
	}

	@Override
	public Object visitWhile(While node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitWhile(node);
	}

	@Override
	public Object visitWith(With node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitWith(node);
	}

	@Override
	public Object visitYield(Yield node) throws Exception {
		// TODO Auto-generated method stub
		return super.visitYield(node);
	}

	public String buildCompare(Attribute attr, Name name, String op) {
		return attr.getText() + op + name.getText();
	}

}
