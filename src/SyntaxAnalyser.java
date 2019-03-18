import java.io.IOException;

public class SyntaxAnalyser extends AbstractSyntaxAnalyser {

    int indentCounter = 0;

    public SyntaxAnalyser(String filename) throws IOException
    {
        lex = new LexicalAnalyser(filename);
    }

    public void _statementPart_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("statementPart");

        if (nextToken.symbol==2)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Begin Expected");
            }
        _statementList_();

        //if (nextToken.symbol==8)
        //{}
    }
    //Sort out logic here
    public void _statementList_() throws IOException, CompilationException
    {
        nextToken = lex.getNextToken();

        if(nextToken.symbol==16 || nextToken.symbol==17 || nextToken.symbol==36 || nextToken.symbol==3 || nextToken.symbol==7||nextToken.symbol==37)
        {
            _statement_();
        }
        else if (nextToken.symbol == 10)
        {

        }
        else if(nextToken.symbol == 30)
        {
            _statementList_();
        }
        else
            {

            }
    }

    public void _statement_() throws IOException, CompilationException
    {
        if(nextToken.symbol==16)
        {
            _assignStatement_();
        }
        else if(nextToken.symbol==17)
        {
            _ifStatement_();
        }
        else if(nextToken.symbol==36)
        {
            _whileStatement_();
        }
        else if(nextToken.symbol==3)
        {
            _procedureStatement_();
        }
        else if(nextToken.symbol==7)
        {
            _untilStatement_();
        }
        else if(nextToken.symbol==37)
        {
            _forStatement_();
        }
    }

    public void _assignStatement_() throws IOException, CompilationException
    {
        if (nextToken.symbol == 16)
        {
            myGenerate.insertTerminal(nextToken);
            nextToken = lex.getNextToken();
            //Check for = symbol
            if (nextToken.symbol == 11)
            {
                nextToken = lex.getNextToken();
                //Check for string constant
                if (nextToken.symbol == 31)
                {
                    myGenerate.insertTerminal(nextToken);
                }
                //If not string constant then is expression
                else
                    {
                        _expression_();
                    }
            }
            else
                {
                    myGenerate.reportError(nextToken, "Expected =");
                }
        }

    }

    public void _ifStatement_() throws IOException, CompilationException
    {
        if (nextToken.symbol==17)
        {
            myGenerate.insertTerminal(nextToken);

        }
        else
            {
                myGenerate.reportError(nextToken, "Expected if" );
            }
        _condition_();
        //Expecting then
        nextToken = lex.getNextToken();
        if (nextToken.symbol == 34)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected then");
            }
        _statementList_();
        //Expecting else
        nextToken=lex.getNextToken();
        //Check if is an else
        if(nextToken.symbol==9)
        {
            myGenerate.insertTerminal(nextToken);
            _statementList_();
        }
        if(nextToken.symbol==8)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected end");
            }
    }

    public void _whileStatement_() throws IOException, CompilationException
    {
        //Expecting while
        if (nextToken.symbol==36)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected while");
            }
        nextToken=lex.getNextToken();
        //Expecting condition
        _condition_();
        nextToken=lex.getNextToken();
        //Expecting loop
        if (nextToken.symbol == 23)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected loop.");
            }
        nextToken=lex.getNextToken();
        //Expecting statement
        _statementList_();
        nextToken=lex.getNextToken();
        //Expecting end symbol
        if (nextToken.symbol==8)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected end");
            }
        nextToken=lex.getNextToken();
        //Expecting loop
        if (nextToken.symbol==23)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected loop");
            }
    }

    public void _procedureStatement_() throws IOException, CompilationException
    {
        //Expecting call
        if (nextToken.symbol==3)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected call");
            }
        nextToken=lex.getNextToken();
        //Expecting identifier
        if (nextToken==16)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected identifier");
            }
        nextToken=lex.getNextToken();
        //Expecting left parenthesis
        if(nextToken.symbol==20)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected left parenthesis");
            }
        nextToken=lex.getNextToken();
        //Expecting argument list
        _argumentList_();
        nextToken=lex.getNextToken();
        //Expecting right parenthesis
        if(nextToken.symbol==29)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected right parenthesis");
            }
    }

    public void _untilStatement_() throws IOException, CompilationException
    {
        //Expecting do
        if (nextToken.symbol == 7)
        {
            myGenerate.insertTerminal(nextToken);

        }
        else
            {
                myGenerate.reportError(nextToken, "Expected do");
            }
        nextToken=lex.getNextToken();
        //Expecting statement list
        _statementList_();
        nextToken=lex.getNextToken();
        //Expecting until
        if (nextToken.symbol==35)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected until");
            }
        nextToken=lex.getNextToken();
        //Expecting condition
        _condition_();
    }

    public void _forStatement_() throws IOException, CompilationException
    {
        //Expecting for
        if (nextToken.symbol == 37)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected for");
            }

        

    }

    //
    public void acceptTerminal(int symbol) throws IOException, CompilationException
    {}


}
