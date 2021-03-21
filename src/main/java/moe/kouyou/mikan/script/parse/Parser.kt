package moe.kouyou.mikan.script.parse

import moe.kouyou.mikan.script.lexical.*


object Parser {
  fun parse(stream: TokenStream): Array<ProcedureNode> {
    val result = arrayListOf<ProcedureNode>()
    while (stream.hasMore()) result.add(ProcedureParser.parse(stream))
    return result.toTypedArray()
  }
}

abstract class ParserModule {
  abstract fun parse(stream: TokenStream): AstNode
}

object ProcedureParser: ParserModule() {
  override fun parse(stream: TokenStream): ProcedureNode {
    stream.expect(ReservedWord.Procedure)
    val name = stream.expectSymbol()
    val block = BlockParser.parse(stream)
    return ProcedureNode(name, block)
  }
}

object BlockParser: ParserModule() {
  override fun parse(stream: TokenStream): BlockNode {
    stream.expect("{")
    val stats = arrayListOf<StatementNode>()
    stream.flushEOS()
    while (!stream.isNext("}")) {
      stats.add(StatementParser.parse(stream))
      stream.flushEOS()
    }
    stream.expect("}")
    return BlockNode(stats.toTypedArray())
  }
}

object StatementParser: ParserModule() {
  override fun parse(stream: TokenStream): StatementNode {
    stream.flushEOS()
    val s = stream.peek()
    return when (s.ctx) {
      ReservedWord.Var -> VarParser.parse(stream)
      ReservedWord.If -> IfParser.parse(stream)
      ReservedWord.While -> WhileParser.parse(stream)
      ReservedWord.Repeat -> RepeatParser.parse(stream)
      else -> SimpleCommandParser.parse(stream)
    }
  }
}

object VarParser: ParserModule() {
  override fun parse(stream: TokenStream): VarNode {
    stream.expect(ReservedWord.Var)
    val name = stream.expectSymbol()
    stream.expect("=")
    val expr = ExpressionParser.parse(stream)
    return VarNode(name, expr)
  }
}

object IfParser: ParserModule() {
  override fun parse(stream: TokenStream): IfNode {
    stream.expect(ReservedWord.If)
    stream.expect("(")
    val expr = ExpressionParser.parse(stream)
    stream.expect(")")
    val block = BlockParser.parse(stream)
    return IfNode(expr, block)
  }
}

object WhileParser: ParserModule() {
  override fun parse(stream: TokenStream): WhileNode {
    stream.expect(ReservedWord.While)
    stream.expect("(")
    val expr = ExpressionParser.parse(stream)
    stream.expect(")")
    val block = BlockParser.parse(stream)
    return WhileNode(expr, block)
  }
}

object RepeatParser: ParserModule() {
  override fun parse(stream: TokenStream): RepeatNode {
    stream.expect(ReservedWord.Repeat)
    stream.expect("(")
    val expr = ExpressionParser.parse(stream)
    stream.expect(")")
    val block = BlockParser.parse(stream)
    return RepeatNode(expr, block)
  }
}

object ExpressionParser: ParserModule() {
  override fun parse(stream: TokenStream): ExprNode {
    return levelAdd(stream)
  }
  
  private fun levelAdd(s: TokenStream): ExprNode {
    val operands = arrayListOf<AstNode>()
    val operators = arrayListOf<String>()
    operands.add(levelMul(s))
    while (s.isNext("+") || s.isNext("-")) {
      operators.add(s.next().ctx)
      operands.add(levelMul(s))
    }
    return ExprNode(operands.toTypedArray(), operators.toTypedArray())
  }
  
  private fun levelMul(s: TokenStream): ExprNode {
    val operands = arrayListOf<AstNode>()
    val operators = arrayListOf<String>()
    operands.add(levelAtom(s))
    while (s.isNext("*") || s.isNext("/") || s.isNext("%")) {
      operators.add(s.next().ctx)
      operands.add(levelAtom(s))
    }
    return ExprNode(operands.toTypedArray(), operators.toTypedArray())
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
        AtomNode(Token(TokenType.Number, "-" + s.next().ctx))
      }
      s.isNextSymbol() || s.isNextLiteral() -> AtomNode(s.next())
      else -> throw RuntimeException()
    }
  }
  
}

/**
 * 未来将会给命令开放自定义 parser
 */
abstract class CommandParser: ParserModule() {
  abstract override fun parse(stream: TokenStream): CommandNode
}

object SimpleCommandParser: CommandParser() {
  override fun parse(stream: TokenStream): CommandNode {
    val name = stream.expectSymbol()
    val args = arrayListOf<ExprNode>()
    while (!stream.isNextEOS()) args.add(ExpressionParser.parse(stream))
    return CommandNode(name.ctx, args.toTypedArray())
  }
  
}