begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/**  * ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  *  * Container for a C++ namespace specification.  Namespaces can be  * nested, so this contains a vector of all the nested names.  *  * @author David Wagner (JPL/Caltech) 8-12-00  *  * $Id$  */
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// ANTLR C# Code Generator by Micheal Jordan
end_comment

begin_comment
comment|//                            Kunle Odutola       : kunle UNDERSCORE odutola AT hotmail DOT com
end_comment

begin_comment
comment|//                            Anthony Oguntimehin
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// With many thanks to Eric V. Smith from the ANTLR list.
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// HISTORY:
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// 17-May-2002 kunle    Original version
end_comment

begin_comment
comment|//
end_comment

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
import|;
end_import

begin_class
DECL|class|CSharpNameSpace
specifier|public
class|class
name|CSharpNameSpace
extends|extends
name|NameSpace
block|{
DECL|method|CSharpNameSpace (String name)
specifier|public
name|CSharpNameSpace
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
comment|/**      * Method to generate the required CSharp namespace declarations      */
DECL|method|emitDeclarations (PrintWriter out)
name|void
name|emitDeclarations
parameter_list|(
name|PrintWriter
name|out
parameter_list|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"namespace "
operator|+
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"{"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Method to generate the required CSharp namespace closures      */
DECL|method|emitClosures (PrintWriter out)
name|void
name|emitClosures
parameter_list|(
name|PrintWriter
name|out
parameter_list|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"}"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

