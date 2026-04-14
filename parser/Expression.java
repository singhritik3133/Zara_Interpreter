package parser;
import Evaluator.Environment;
public interface Expression {
    Object evaluate(Environment env);
}
