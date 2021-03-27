package moe.kouyou.mikan.core.parse

import moe.kouyou.mikan.core.api.AstNode
import moe.kouyou.mikan.core.api.ParserModule
import moe.kouyou.mikan.core.lexical.*

object ProcedureParser: ParserModule<ProcedureNode> {
  override fun parse(stream: TokenStream): ProcedureNode {
    stream.expect(ReservedWord.Procedure)
    val name = stream.expectSymbol()
    if (name.ctx in ReservedWord.allReservedWords) throw RuntimeException()
    val block = BlockParser.parse(stream)
    return ProcedureNode(name.ctx, block)
  }
}

object BlockParser: ParserModule<BlockNode> {
  override fun parse(stream: TokenStream): BlockNode {
    stream.expect("{")
    val stats = arrayListOf<StmtNode>()
    stream.flushEOS()
    while (!stream.isNext("}")) {
      stats.add(StmtParser.parse(stream))
      stream.flushEOS()
    }
    stream.expect("}")
    return BlockNode(stats.toTypedArray())
  }
}

object StmtParser: ParserModule<StmtNode> {
  override fun parse(stream: TokenStream): StmtNode {
    stream.flushEOS()
    val s = stream.peek()
    return when (s.ctx) {
      "set", ReservedWord.Var -> VarParser.parse(stream)
      ReservedWord.If -> IfParser.parse(stream)
      ReservedWord.While -> WhileParser.parse(stream)
      ReservedWord.Repeat -> RepeatParser.parse(stream)
      else -> CommandParser.parse(stream)
    }
  }
}

object VarParser: ParserModule<VarNode> {
  override fun parse(stream: TokenStream): VarNode {
    //stream.expect(ReservedWord.Var)
    stream.next()
    val name = stream.expectSymbol()
    stream.expect("=")
    val expr = ExpressionParser.parse(stream)
    return VarNode(name.ctx, expr)
  }
}

object IfParser: ParserModule<IfNode> {
  override fun parse(stream: TokenStream): IfNode {
    stream.expect(ReservedWord.If)
    stream.expect("(")
    val expr = ExpressionParser.parse(stream)
    stream.expect(")")
    val block = BlockParser.parse(stream)
    return IfNode(expr, block)
  }
}

object WhileParser: ParserModule<WhileNode> {
  override fun parse(stream: TokenStream): WhileNode {
    stream.expect(ReservedWord.While)
    stream.expect("(")
    val expr = ExpressionParser.parse(stream)
    stream.expect(")")
    val block = BlockParser.parse(stream)
    return WhileNode(expr, block)
  }
}

object RepeatParser: ParserModule<RepeatNode> {
  override fun parse(stream: TokenStream): RepeatNode {
    stream.expect(ReservedWord.Repeat)
    stream.expect("(")
    val expr = ExpressionParser.parse(stream)
    stream.expect(")")
    val block = BlockParser.parse(stream)
    return RepeatNode(expr, block)
  }
}

object ExpressionParser: ParserModule<ExprNode> {
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

object CommandParser: ParserModule<CommandNode> {
  override fun parse(stream: TokenStream): CommandNode {
    val name = stream.expectSymbol()
    val args = arrayListOf<ExprNode>()
    while (!stream.isNextEOS()) args.add(ExpressionParser.parse(stream))
    return CommandNode(name.ctx, args.toTypedArray())
  }
}