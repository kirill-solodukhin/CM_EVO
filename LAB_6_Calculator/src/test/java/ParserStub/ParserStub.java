package ParserStub;

import org.example.Operation;
import org.example.Parser.IParser;

public class ParserStub implements IParser
{
    private final Operation operation;

    public ParserStub(Operation operation)
    {
        this.operation = operation;
    }

    @Override
    public Operation Parse(String inputString)
    {
        return operation;
    }
}
