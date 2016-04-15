import syntaxtree.ClassDeclaration;
import syntaxtree.ClassExtendsDeclaration;
import syntaxtree.MainClass;
import syntaxtree.VarDeclaration;
import visitor.GJDepthFirst;

import SymbolTypes.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolVisitor extends GJDepthFirst<String,String> {
    HashMap<String,String> ClassMap;
    HashMap<String,SymbolTable> symboltable = new HashMap<String,SymbolTable>();

    HashMap<String,Variables> VariablesMap;
    HashMap<String,Methods> MethodsMap;
    String inClass = null;
    String inMethod = null;
    ArrayList<NamedVariables> args;
    SymbolTable symbol;
    Methods method;
    Variables var;
    String storedClass;
    String varType;

    public HashMap<String,SymbolTable> getSymboltable() {
        return symboltable;
    }

    public SymbolVisitor(HashMap<String,String> ClassMap) throws Exception{
        this.ClassMap = ClassMap;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> "public"
     * f4 -> "static"
     * f5 -> "void"
     * f6 -> "main"
     * f7 -> "("
     * f8 -> "String"
     * f9 -> "["
     * f10 -> "]"
     * f11 -> Identifier()
     * f12 -> ")"
     * f13 -> "{"
     * f14 -> ( VarDeclaration() )*
     * f15 -> ( Statement() )*
     * f16 -> "}"
     * f17 -> "}"
     */

    public String visit(MainClass n, String argu) throws Exception {
        // Don't forget to save variables and arguments of main method
        return null;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> ( VarDeclaration() )*
     * f4 -> ( MethodDeclaration() )*
     * f5 -> "}"
     */

    public String visit(ClassDeclaration n, String argu) throws Exception {
        this.storedClass = n.f1.accept(this,null);
        VariablesMap = new HashMap<String,Variables>();
        MethodsMap = new HashMap<String,Methods>();

        inClass = "yes";
        n.f3.accept(this,this.storedClass);
        n.f4.accept(this,this.storedClass);
        // Add the outcome to the SymbolTable
        inClass = null;

        return null;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "extends"
     * f3 -> Identifier()
     * f4 -> "{"
     * f5 -> ( VarDeclaration() )*
     * f6 -> ( MethodDeclaration() )*
     * f7 -> "}"
     */

    public String visit(ClassExtendsDeclaration n, String argu) throws Exception {
        this.storedClass = n.f1.accept(this,null);
        VariablesMap = new HashMap<String,Variables>();
        MethodsMap = new HashMap<String,Methods>();

        n.f5.accept(this,this.storedClass);
        n.f6.accept(this,this.storedClass);
        // Add the outcome to the SymbolTable

        return null;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */

    public String visit(VarDeclaration n, String storedClass) throws Exception {
        String varType = n.f0.accept(this,null);
        String varName = n.f1.f0.toString();
        var.setIsVar(true);
        var.setVarType(varType);

        if (!varType.equals("boolean") && !varType.equals("int") && !varType.equals("int[]") && !ClassMap.containsKey(varType)) {
            System.out.println("Unknown type declaration with name '" + varType + "'");
            throw new Exception();
        }
        if (inClass != null) {
            if (this.VariablesMap.containsKey(varName)) {
                System.out.println("Duplicate variable declaration with name '" + varName + "' in class '" + storedClass + "'");
                throw new Exception();
            }

            VariablesMap.put(varName,var);
        }
        else if (inMethod != null) {
            
        }

        return null;
    }
}
