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
        if (nextToken.symbol==8)
        {
            myGenerate.insertTerminal(nextToken);
        } else
            {
                myGenerate.reportError(nextToken, "Expected end");
            }
        nextToken=lex.getNextToken();
        if(nextToken.symbol==10)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "expected end of file");
            }
    }
    //Sort out logic here
    public void _statementList_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("statementList");
        nextToken = lex.getNextToken();

        if(nextToken.symbol==16 || nextToken.symbol==17 || nextToken.symbol==36 || nextToken.symbol==3 || nextToken.symbol==7||nextToken.symbol==37)
        {
            _statement_();
        }
        else if (nextToken.symbol == 10)
        {

        }
        else
        {

        }
        //System.out.println(nextToken.text);
        if (nextToken.symbol==30)
        {
            myGenerate.insertTerminal(nextToken);
        }
        if((nextToken.symbol != 8) && (nextToken.symbol!=10))
        {
            _statementList_();
        }

    }

    public void _statement_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("statement");
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
        //System.out.println(nextToken.text);
        //nextToken=lex.getNextToken();
        //System.out.println(nextToken.text);
        myGenerate.finishNonterminal("statement");
    }

    public void _assignStatement_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("assignStatements");
        if (nextToken.symbol == 16)
        {
            myGenerate.insertTerminal(nextToken);
            nextToken = lex.getNextToken();
            //Check for = symbol
            System.out.println(nextToken.text);
            if (nextToken.symbol == 1)
            {
                myGenerate.insertTerminal(nextToken);
                nextToken = lex.getNextToken();
                //System.out.println(nextToken.text);
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
        myGenerate.finishNonterminal("assignStatement");

    }

    public void _ifStatement_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("ifStatement");
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
        myGenerate.finishNonterminal("ifStatement");
    }

    public void _whileStatement_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("whileStatement");
        //Expecting while
        if (nextToken.symbol==36)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected while");
            }
    //    nextToken=lex.getNextToken();
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
        //nextToken=lex.getNextToken();
        //Expecting statement
        _statementList_();
     //   nextToken=lex.getNextToken();
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
        myGenerate.finishNonterminal("whileStatement");
    }

    public void _procedureStatement_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("procedureStatement");
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
        if (nextToken.symbol==16)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected identifier");
            }
        nextToken=lex.getNextToken();
        //Expecting left parenthesis
        //System.out.println(nextToken.text + "\n\n");
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
        //nextToken=lex.getNextToken();
        //Expecting right parenthesis
        if(nextToken.symbol==29)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected right parenthesis");
            }
        nextToken=lex.getNextToken();
        myGenerate.finishNonterminal("procedureStatement");
    }

    public void _untilStatement_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("untilStatement");
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
        myGenerate.finishNonterminal("untilStatement");
    }

    public void _forStatement_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("forStatement");
        //Expecting for
        if (nextToken.symbol == 37)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected for");
            }
        //Expecting lft parenthesis
        nextToken=lex.getNextToken();
        if(nextToken.symbol==20)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected left parenthesis");
            }
        //Expecting assignment statement
        nextToken=lex.getNextToken();
        _assignStatement_();
        //Expecting semi colon
        nextToken=lex.getNextToken();
        if (nextToken.symbol==30)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected semi colon");
            }

        //Expecting condition
        nextToken=lex.getNextToken();
        _condition_();
        //Expecting semi colon
        nextToken=lex.getNextToken();
        if (nextToken.symbol==30)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
        {
            myGenerate.reportError(nextToken, "Expected semi colon");
        }
        //Expecting assignment statement
        nextToken=lex.getNextToken();
        _assignStatement_();

        //Expecting right parenthesis
        nextToken=lex.getNextToken();
        if(nextToken.symbol == 29)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected right parenthesis");
            }
        //Expecting do
        nextToken=lex.getNextToken();
        if(nextToken.symbol == 7)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken,"Expected do");
            }
        //Expecting statement list
        nextToken=lex.getNextToken();
        _statementList_();
        //Expecing end
        nextToken=lex.getNextToken();
        if(nextToken.symbol == 8)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected end");
            }
        //Expecting loop
        nextToken=lex.getNextToken();
        if(nextToken.symbol == 23)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken,"Expected loop");
            }
        myGenerate.finishNonterminal("forStatement");
    }

    public void _argumentList_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("argumentList");
        //nextToken=lex.getNextToken();
        //Expecting identifer or agument list
        if (nextToken.symbol==16)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected Identifier");
            }
        nextToken=lex.getNextToken();
     //   System.out.println(nextToken.text);
        if (nextToken.symbol==5)
        {
            myGenerate.insertTerminal(nextToken);
            _argumentList_();
        }
        myGenerate.finishNonterminal("argumentList");
    }

    public void _condition_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("condition");
        nextToken=lex.getNextToken();
        //System.out.println(nextToken.text);
        if(nextToken.symbol==16)
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken,"Expected identifier");
            }

        //Expected conditional operator
       // nextToken=lex.getNextToken();
        _conditionalOperator_();
        //Expect identifier, number constant or string constant
        nextToken=lex.getNextToken();
        if ((nextToken.symbol == 16) | (nextToken.symbol==26) | (nextToken.symbol==31))
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected identifier/numberConstant/stringConstant");
            }
        myGenerate.finishNonterminal("conditional");
    }

    public void _conditionalOperator_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("conditionalOperator");
        nextToken=lex.getNextToken();
        if ((nextToken.symbol==15) | (nextToken.symbol==14) | (nextToken.symbol==11) | (nextToken.symbol==25) | (nextToken.symbol==22) | (nextToken.symbol==21))
        {
            myGenerate.insertTerminal(nextToken);
        }
        else
            {
                myGenerate.reportError(nextToken, "Expected operator symbol");
            }
        myGenerate.finishNonterminal("conditionalOperator");
    }

    public void _expression_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("expression");
        //nextToken=lex.getNextToken();
        _term_();

        if ((nextToken.symbol==27) | (nextToken.symbol==24))
        {
            myGenerate.insertTerminal(nextToken);
            nextToken=lex.getNextToken();
            _expression_();
        }
        myGenerate.finishNonterminal("expression");

    }

    public void _term_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("term");
        _factor_();
        nextToken=lex.getNextToken();
        //Expecting plus or minus
        if ((nextToken.symbol==27) | (nextToken.symbol==24))
        {

        }else if((nextToken.symbol==33)|(nextToken.symbol==6))
        {
            nextToken=lex.getNextToken();
            _term_();
        }
        myGenerate.finishNonterminal("term");
    }

    public void _factor_() throws IOException, CompilationException
    {
        myGenerate.commenceNonterminal("factor");
        //System.out.println(nextToken.text);
        if ((nextToken.symbol==16)||(nextToken.symbol==26) || (nextToken.symbol==13) ||(nextToken.symbol==18))
        {
            myGenerate.insertTerminal(nextToken);

        }else
            {
                if (nextToken.symbol==20)
                {
                    myGenerate.insertTerminal(nextToken);
                    _expression_();
                    if (nextToken.symbol==29)
                    {
                        myGenerate.insertTerminal(nextToken);
                    }
                    else
                        {
                            myGenerate.reportError(nextToken, "Expected right parenthesis");
                        }
                }else
                    {
                        System.out.println(nextToken.text);
                        myGenerate.reportError(nextToken, "Expected identifier/numberconstant/bracket");
                    }
            }
        myGenerate.finishNonterminal("factor");
    }

    //
    public void acceptTerminal(int symbol) throws IOException, CompilationException
    {}


}
