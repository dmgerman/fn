begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|// $ANTLR : "action.g" -> "ActionLexer.java"$
end_comment

begin_package
DECL|package|antlr.actions.csharp
package|package
name|antlr
operator|.
name|actions
operator|.
name|csharp
package|;
end_package

begin_interface
DECL|interface|ActionLexerTokenTypes
specifier|public
interface|interface
name|ActionLexerTokenTypes
block|{
DECL|field|EOF
name|int
name|EOF
init|=
literal|1
decl_stmt|;
DECL|field|NULL_TREE_LOOKAHEAD
name|int
name|NULL_TREE_LOOKAHEAD
init|=
literal|3
decl_stmt|;
DECL|field|ACTION
name|int
name|ACTION
init|=
literal|4
decl_stmt|;
DECL|field|STUFF
name|int
name|STUFF
init|=
literal|5
decl_stmt|;
DECL|field|AST_ITEM
name|int
name|AST_ITEM
init|=
literal|6
decl_stmt|;
DECL|field|TEXT_ITEM
name|int
name|TEXT_ITEM
init|=
literal|7
decl_stmt|;
DECL|field|TREE
name|int
name|TREE
init|=
literal|8
decl_stmt|;
DECL|field|TREE_ELEMENT
name|int
name|TREE_ELEMENT
init|=
literal|9
decl_stmt|;
DECL|field|AST_CONSTRUCTOR
name|int
name|AST_CONSTRUCTOR
init|=
literal|10
decl_stmt|;
DECL|field|AST_CTOR_ELEMENT
name|int
name|AST_CTOR_ELEMENT
init|=
literal|11
decl_stmt|;
DECL|field|ID_ELEMENT
name|int
name|ID_ELEMENT
init|=
literal|12
decl_stmt|;
DECL|field|TEXT_ARG
name|int
name|TEXT_ARG
init|=
literal|13
decl_stmt|;
DECL|field|TEXT_ARG_ELEMENT
name|int
name|TEXT_ARG_ELEMENT
init|=
literal|14
decl_stmt|;
DECL|field|TEXT_ARG_ID_ELEMENT
name|int
name|TEXT_ARG_ID_ELEMENT
init|=
literal|15
decl_stmt|;
DECL|field|ARG
name|int
name|ARG
init|=
literal|16
decl_stmt|;
DECL|field|ID
name|int
name|ID
init|=
literal|17
decl_stmt|;
DECL|field|VAR_ASSIGN
name|int
name|VAR_ASSIGN
init|=
literal|18
decl_stmt|;
DECL|field|COMMENT
name|int
name|COMMENT
init|=
literal|19
decl_stmt|;
DECL|field|SL_COMMENT
name|int
name|SL_COMMENT
init|=
literal|20
decl_stmt|;
DECL|field|ML_COMMENT
name|int
name|ML_COMMENT
init|=
literal|21
decl_stmt|;
DECL|field|CHAR
name|int
name|CHAR
init|=
literal|22
decl_stmt|;
DECL|field|STRING
name|int
name|STRING
init|=
literal|23
decl_stmt|;
DECL|field|ESC
name|int
name|ESC
init|=
literal|24
decl_stmt|;
DECL|field|DIGIT
name|int
name|DIGIT
init|=
literal|25
decl_stmt|;
DECL|field|INT
name|int
name|INT
init|=
literal|26
decl_stmt|;
DECL|field|INT_OR_FLOAT
name|int
name|INT_OR_FLOAT
init|=
literal|27
decl_stmt|;
DECL|field|WS
name|int
name|WS
init|=
literal|28
decl_stmt|;
block|}
end_interface

end_unit

