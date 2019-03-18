public class Generate extends AbstractGenerate {

    public void reportError(Token token, String explanatoryMessage) throws CompilationException
    {
        System.out.println(explanatoryMessage + " but found " + token.text);
        throw new CompilationException(explanatoryMessage, token.lineNumber);
    }


}
