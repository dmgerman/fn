begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|// $ANTLR 2.7.4: "Parser.g" -> "SearchExpressionParser.java"$
end_comment

begin_package
DECL|package|net.sf.jabref.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|search
package|;
end_package

begin_interface
DECL|interface|SearchExpressionParserTokenTypes
specifier|public
interface|interface
name|SearchExpressionParserTokenTypes
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
DECL|field|LITERAL_and
name|int
name|LITERAL_and
init|=
literal|4
decl_stmt|;
DECL|field|LITERAL_or
name|int
name|LITERAL_or
init|=
literal|5
decl_stmt|;
DECL|field|LITERAL_not
name|int
name|LITERAL_not
init|=
literal|6
decl_stmt|;
DECL|field|LITERAL_contains
name|int
name|LITERAL_contains
init|=
literal|7
decl_stmt|;
DECL|field|LITERAL_equals
name|int
name|LITERAL_equals
init|=
literal|8
decl_stmt|;
DECL|field|WS
name|int
name|WS
init|=
literal|9
decl_stmt|;
DECL|field|LPAREN
name|int
name|LPAREN
init|=
literal|10
decl_stmt|;
DECL|field|RPAREN
name|int
name|RPAREN
init|=
literal|11
decl_stmt|;
DECL|field|EQUAL
name|int
name|EQUAL
init|=
literal|12
decl_stmt|;
DECL|field|EEQUAL
name|int
name|EEQUAL
init|=
literal|13
decl_stmt|;
DECL|field|NEQUAL
name|int
name|NEQUAL
init|=
literal|14
decl_stmt|;
DECL|field|QUOTE
name|int
name|QUOTE
init|=
literal|15
decl_stmt|;
DECL|field|STRING
name|int
name|STRING
init|=
literal|16
decl_stmt|;
DECL|field|LETTER
name|int
name|LETTER
init|=
literal|17
decl_stmt|;
DECL|field|FIELDTYPE
name|int
name|FIELDTYPE
init|=
literal|18
decl_stmt|;
DECL|field|RegularExpression
name|int
name|RegularExpression
init|=
literal|19
decl_stmt|;
DECL|field|And
name|int
name|And
init|=
literal|20
decl_stmt|;
DECL|field|Or
name|int
name|Or
init|=
literal|21
decl_stmt|;
DECL|field|Not
name|int
name|Not
init|=
literal|22
decl_stmt|;
DECL|field|ExpressionSearch
name|int
name|ExpressionSearch
init|=
literal|23
decl_stmt|;
DECL|field|LITERAL_matches
name|int
name|LITERAL_matches
init|=
literal|24
decl_stmt|;
block|}
end_interface

end_unit

