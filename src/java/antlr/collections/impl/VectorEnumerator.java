begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.collections.impl
package|package
name|antlr
operator|.
name|collections
operator|.
name|impl
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
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|NoSuchElementException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|Enumerator
import|;
end_import

begin_comment
comment|// based on java.lang.Vector; returns any null indices between non-null ones.
end_comment

begin_class
DECL|class|VectorEnumerator
class|class
name|VectorEnumerator
implements|implements
name|Enumeration
block|{
DECL|field|vector
name|Vector
name|vector
decl_stmt|;
DECL|field|i
name|int
name|i
decl_stmt|;
DECL|method|VectorEnumerator (Vector v)
name|VectorEnumerator
parameter_list|(
name|Vector
name|v
parameter_list|)
block|{
name|vector
operator|=
name|v
expr_stmt|;
name|i
operator|=
literal|0
expr_stmt|;
block|}
DECL|method|hasMoreElements ()
specifier|public
name|boolean
name|hasMoreElements
parameter_list|()
block|{
synchronized|synchronized
init|(
name|vector
init|)
block|{
return|return
name|i
operator|<=
name|vector
operator|.
name|lastElement
return|;
block|}
block|}
DECL|method|nextElement ()
specifier|public
name|Object
name|nextElement
parameter_list|()
block|{
synchronized|synchronized
init|(
name|vector
init|)
block|{
if|if
condition|(
name|i
operator|<=
name|vector
operator|.
name|lastElement
condition|)
block|{
return|return
name|vector
operator|.
name|data
index|[
name|i
operator|++
index|]
return|;
block|}
throw|throw
operator|new
name|NoSuchElementException
argument_list|(
literal|"VectorEnumerator"
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

