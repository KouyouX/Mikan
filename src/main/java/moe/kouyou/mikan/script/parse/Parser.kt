package moe.kouyou.mikan.script.parse

import moe.kouyou.mikan.script.exec.CommandManager
import moe.kouyou.mikan.script.lexical.*


object Parser {
  fun parse(stream: TokenStream): Array<AstNode.Procedure> {
    val result = arrayListOf<AstNode.Procedure>()
    while (stream.hasMore()) result.add(ProcedureParser.parse(stream))
    return result.toTypedArray()
  }
}

abstract class ParserModule {
  abstract fun parse(stream: TokenStream): AstNode
}

object ProcedureParser: ParserModule() {
  override fun parse(stream: TokenStream): AstNode.Procedure {
    stream.expect("procedure")
    val name = stream.expectSymbol()
    val block = BlockParser.parse(stream)
    return AstNode.Procedure(name, block)
  }
}

object BlockParser: ParserModule() {
  override fun parse(stream: TokenStream): AstNode.Block {
    stream.expect("{")
    val stats = arrayListOf<AstNode.Statement>()
    stream.flushEOS()
    while (!stream.isNext("}")) {
      stats.add(StatementParser.parse(stream))
      stream.flushEOS()
    }
    stream.expect("}")
    return AstNode.Block(stats.toTypedArray())
  }
}

object StatementParser: ParserModule() {
  override fun parse(stream: TokenStream): AstNode.Statement {
    stream.flushEOS()
    val s = stream.peek()
    return when (s.ctx) {
      "set" -> SetVarParser.parse(stream)
      "if" -> IfParser.parse(stream)
      "while" -> WhileParser.parse(stream)
      "repeat" -> RepeatParser.parse(stream)
      else -> SimpleCommandParser.parse(stream)
    }
  }
}

object SetVarParser: ParserModule() {
  override fun parse(s: TokenStream): AstNode.SetVar {
    s.expect("set")
    val name = s.expectSymbol()
    s.expect("=")
    val expr = ExpressionParser.parse(s)
    return AstNode.SetVar(name, expr)
  }
}

object IfParser: ParserModule() {
  override fun parse(stream: TokenStream): AstNode.If {
    stream.expect("if")
    stream.expect("(")
    val expr = ExpressionParser.parse(stream)
    stream.expect(")")
    val block = BlockParser.parse(stream)
    return AstNode.If(expr, block)
  }
}

object WhileParser: ParserModule() {
  override fun parse(stream: TokenStream): AstNode.While {
    stream.expect("while")
    stream.expect("(")
    val expr = ExpressionParser.parse(stream)
    stream.expect(")")
    val block = BlockParser.parse(stream)
    return AstNode.While(expr, block)
  }
}

object RepeatParser: ParserModule() {
  override fun parse(stream: TokenStream): AstNode.Repeat {
    stream.expect("repeat")
    stream.expect("(")
    val expr = ExpressionParser.parse(stream)
    stream.expect(")")
    val block = BlockParser.parse(stream)
    return AstNode.Repeat(expr, block)
  }
}

object ExpressionParser: ParserModule() {
  override fun parse(stream: TokenStream): AstNode.Expression {
    return levelAdd(stream)
  }
  
  private fun levelAdd(s: TokenStream): AstNode.Expression {
    val operands = arrayListOf<AstNode>()
    val operators = arrayListOf<Token>()
    operands.add(levelMul(s))
    while (s.isNextOperator()) {
      operators.add(s.next())
      operands.add(levelMul(s))
    }
    return AstNode.Expression(operands.toTypedArray(), operators.toTypedArray())
  }
  
  private fun levelMul(s: TokenStream): AstNode.Expression {
    val operands = arrayListOf<AstNode>()
    val operators = arrayListOf<Token>()
    operands.add(levelAtom(s))
    while (s.isNextOperator()) {
      operators.add(s.next())
      operands.add(levelAtom(s))
    }
    return AstNode.Expression(operands.toTypedArray(), operators.toTypedArray())
  }
  
  private fun levelAtom(s: TokenStream): AstNode {
    return when {
      s.isNext("(") -> {
        s.expect("(")
        val result = parse(s)
        s.expect(")")
        result
      }
      s.isNext("-") && s.peek(1).isNumber() -> {
        s.next()
        AstNode.Atom(Token(TokenType.Number, "-" + s.next()))
      }
      s.isNextSymbol() || s.isNextLiteral() -> AstNode.Atom(s.next())
      else -> throw RuntimeException()
    }
  }
  
}

/**
 * 未来将会给命令开放自定义 parser
 */
abstract class CommandParser: ParserModule() {
  abstract override fun parse(stream: TokenStream): AstNode.Command
}

object SimpleCommandParser: CommandParser() {
  override fun parse(stream: TokenStream): AstNode.Command {
    val name = stream.expectSymbol()
    val args = arrayListOf<AstNode.Expression>()
    while (!stream.isNextEOS()) args.add(ExpressionParser.parse(stream))
    return AstNode.Command(CommandManager.getCommand(name.ctx), args.toTypedArray())
  }
  
}