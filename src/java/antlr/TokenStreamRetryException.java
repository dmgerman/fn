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

begin_comment
comment|/**  * Aborted recognition of current token. Try to get one again.  * Used by TokenStreamSelector.retry() to force nextToken()  * of stream to re-enter and retry.  */
end_comment

begin_class
DECL|class|TokenStreamRetryException
specifier|public
class|class
name|TokenStreamRetryException
extends|extends
name|TokenStreamException
block|{
DECL|method|TokenStreamRetryException ()
specifier|public
name|TokenStreamRetryException
parameter_list|()
block|{     }
block|}
end_class

end_unit

