begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  *  * $Id$  */
end_comment

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|impl
operator|.
name|BitSet
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|impl
operator|.
name|Vector
import|;
end_import

begin_comment
comment|/** Parser-specific grammar subclass */
end_comment

begin_class
DECL|class|TreeWalkerGrammar
class|class
name|TreeWalkerGrammar
extends|extends
name|Grammar
block|{
comment|// true for transform mode
DECL|field|transform
specifier|protected
name|boolean
name|transform
init|=
literal|false
decl_stmt|;
DECL|method|TreeWalkerGrammar (String className_, Tool tool_, String superClass)
name|TreeWalkerGrammar
parameter_list|(
name|String
name|className_
parameter_list|,
name|Tool
name|tool_
parameter_list|,
name|String
name|superClass
parameter_list|)
block|{
name|super
argument_list|(
name|className_
argument_list|,
name|tool_
argument_list|,
name|superClass
argument_list|)
expr_stmt|;
block|}
comment|/** Top-level call to generate the code for this grammar */
DECL|method|generate ()
specifier|public
name|void
name|generate
parameter_list|()
throws|throws
name|IOException
block|{
name|generator
operator|.
name|gen
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|// Get name of class from which generated parser/lexer inherits
DECL|method|getSuperClass ()
specifier|protected
name|String
name|getSuperClass
parameter_list|()
block|{
return|return
literal|"TreeParser"
return|;
block|}
comment|/**Process command line arguments.      * -trace			have all rules call traceIn/traceOut      * -traceParser		have parser rules call traceIn/traceOut      * -debug			generate debugging output for parser debugger      */
DECL|method|processArguments (String[] args)
specifier|public
name|void
name|processArguments
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|args
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|args
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"-trace"
argument_list|)
condition|)
block|{
name|traceRules
operator|=
literal|true
expr_stmt|;
name|antlrTool
operator|.
name|setArgOK
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|args
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"-traceTreeParser"
argument_list|)
condition|)
block|{
name|traceRules
operator|=
literal|true
expr_stmt|;
name|antlrTool
operator|.
name|setArgOK
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
comment|//			else if ( args[i].equals("-debug") ) {
comment|//				debuggingOutput = true;
comment|//				superClass = "parseview.DebuggingTreeWalker";
comment|//				Tool.setArgOK(i);
comment|//			}
block|}
block|}
comment|/** Set tree parser options */
DECL|method|setOption (String key, Token value)
specifier|public
name|boolean
name|setOption
parameter_list|(
name|String
name|key
parameter_list|,
name|Token
name|value
parameter_list|)
block|{
if|if
condition|(
name|key
operator|.
name|equals
argument_list|(
literal|"buildAST"
argument_list|)
condition|)
block|{
if|if
condition|(
name|value
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
literal|"true"
argument_list|)
condition|)
block|{
name|buildAST
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|value
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
literal|"false"
argument_list|)
condition|)
block|{
name|buildAST
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|antlrTool
operator|.
name|error
argument_list|(
literal|"buildAST option must be true or false"
argument_list|,
name|getFilename
argument_list|()
argument_list|,
name|value
operator|.
name|getLine
argument_list|()
argument_list|,
name|value
operator|.
name|getColumn
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
if|if
condition|(
name|key
operator|.
name|equals
argument_list|(
literal|"ASTLabelType"
argument_list|)
condition|)
block|{
name|super
operator|.
name|setOption
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
if|if
condition|(
name|super
operator|.
name|setOption
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
name|antlrTool
operator|.
name|error
argument_list|(
literal|"Invalid option: "
operator|+
name|key
argument_list|,
name|getFilename
argument_list|()
argument_list|,
name|value
operator|.
name|getLine
argument_list|()
argument_list|,
name|value
operator|.
name|getColumn
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

