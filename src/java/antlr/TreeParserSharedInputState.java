begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/RIGHTS.html  *  * $Id$  */
end_comment

begin_comment
comment|/** This object contains the data associated with an  *  input AST.  Multiple parsers  *  share a single TreeParserSharedInputState to parse  *  the same tree or to have the parser walk multiple  *  trees.  */
end_comment

begin_class
DECL|class|TreeParserSharedInputState
specifier|public
class|class
name|TreeParserSharedInputState
block|{
comment|/** Are we guessing (guessing>0)? */
DECL|field|guessing
specifier|public
name|int
name|guessing
init|=
literal|0
decl_stmt|;
block|}
end_class

end_unit

