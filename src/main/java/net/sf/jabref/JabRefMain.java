begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_comment
comment|/**  * JabRef MainClass  */
end_comment

begin_class
DECL|class|JabRefMain
specifier|public
class|class
name|JabRefMain
block|{
DECL|method|main (String[] args)
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
operator|new
name|JabRef
argument_list|()
operator|.
name|start
argument_list|(
name|args
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

