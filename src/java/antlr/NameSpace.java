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
DECL|class|NameSpace
specifier|public
class|class
name|NameSpace
block|{
DECL|field|names
specifier|private
name|Vector
name|names
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
DECL|field|_name
specifier|private
name|String
name|_name
decl_stmt|;
DECL|method|NameSpace (String name)
specifier|public
name|NameSpace
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|_name
operator|=
operator|new
name|String
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|_name
return|;
block|}
comment|/**      * Parse a C++ namespace declaration into seperate names      * splitting on ::  We could easily parameterize this to make      * the delimiter a language-specific parameter, or use subclasses      * to support C++ namespaces versus java packages. -DAW      */
DECL|method|parse (String name)
specifier|protected
name|void
name|parse
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|StringTokenizer
name|tok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|name
argument_list|,
literal|"::"
argument_list|)
decl_stmt|;
while|while
condition|(
name|tok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
name|names
operator|.
name|addElement
argument_list|(
name|tok
operator|.
name|nextToken
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Method to generate the required C++ namespace declarations      */
DECL|method|emitDeclarations (PrintWriter out)
name|void
name|emitDeclarations
parameter_list|(
name|PrintWriter
name|out
parameter_list|)
block|{
for|for
control|(
name|Enumeration
name|n
init|=
name|names
operator|.
name|elements
argument_list|()
init|;
name|n
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|String
name|s
init|=
operator|(
name|String
operator|)
name|n
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"ANTLR_BEGIN_NAMESPACE("
operator|+
name|s
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Method to generate the required C++ namespace closures      */
DECL|method|emitClosures (PrintWriter out)
name|void
name|emitClosures
parameter_list|(
name|PrintWriter
name|out
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
name|names
operator|.
name|size
argument_list|()
condition|;
operator|++
name|i
control|)
name|out
operator|.
name|println
argument_list|(
literal|"ANTLR_END_NAMESPACE"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

