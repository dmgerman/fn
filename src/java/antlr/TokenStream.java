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

begin_interface
DECL|interface|TokenStream
specifier|public
interface|interface
name|TokenStream
block|{
DECL|method|nextToken ()
specifier|public
name|Token
name|nextToken
parameter_list|()
throws|throws
name|TokenStreamException
function_decl|;
block|}
end_interface

end_unit

