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
comment|/** Interface used by BitSet to format elements of the set when   * converting to string   */
end_comment

begin_interface
DECL|interface|CharFormatter
specifier|public
interface|interface
name|CharFormatter
block|{
DECL|method|escapeChar (int c, boolean forCharLiteral)
specifier|public
name|String
name|escapeChar
parameter_list|(
name|int
name|c
parameter_list|,
name|boolean
name|forCharLiteral
parameter_list|)
function_decl|;
DECL|method|escapeString (String s)
specifier|public
name|String
name|escapeString
parameter_list|(
name|String
name|s
parameter_list|)
function_decl|;
DECL|method|literalChar (int c)
specifier|public
name|String
name|literalChar
parameter_list|(
name|int
name|c
parameter_list|)
function_decl|;
DECL|method|literalString (String s)
specifier|public
name|String
name|literalString
parameter_list|(
name|String
name|s
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

